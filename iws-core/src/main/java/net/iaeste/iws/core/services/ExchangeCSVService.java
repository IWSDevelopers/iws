/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.core.services;

import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.exchange.CSVProcessingErrors;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.exchange.OfferFields;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.util.Verifications;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class ExchangeCSVService extends CommonCSVService<ExchangeDao> {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeCSVService.class);

    private final AccessDao accessDao;

    public ExchangeCSVService(final Settings settings, final ExchangeDao dao, final AccessDao accessDao) {
        super(settings, dao);

        this.accessDao = accessDao;
    }

    public OfferCSVUploadResponse uploadOffers(final Authentication authentication, final OfferCSVUploadRequest request) {
        final OfferCSVUploadResponse response = new OfferCSVUploadResponse();
        final Map<String, OfferCSVUploadResponse.ProcessingResult> processingResult = new HashMap<>();
        final Map<String, CSVProcessingErrors> errors = new HashMap<>();

        final OfferCSVUploadRequest.FieldDelimiter delimiter = (request.getDelimiter() != null) ? request.getDelimiter() : DELIMITER;

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getData());
             Reader reader = new InputStreamReader(byteArrayInputStream, IWSConstants.DEFAULT_ENCODING);
             CSVParser parser = getDefaultCsvParser(reader, delimiter.getDescription())) {
            final Set<String> headers = readHeader(parser);
            final Set<String> expectedHeaders = new HashSet<>(createFirstRow(OfferFields.Type.UPLOAD));
            if (headers.containsAll(expectedHeaders)) {
                for (final CSVRecord record : parser.getRecords()) {
                    process(processingResult, errors, authentication, record);
                }
            } else {
                throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Invalid CSV header");
            }
        } catch (IllegalArgumentException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "The header is invalid: " + e.getMessage() + '.', e);
        } catch (IOException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "CSV upload processing failed", e);
        }

        response.setProcessingResult(processingResult);
        response.setErrors(errors);
        return response;
    }

    private static Set<String> readHeader(final CSVParser parser) {
        final Map<String, Integer> map = parser.getHeaderMap();

        if (map == null) {
            throw new IWSException(IWSErrors.CSV_HEADER_ERROR, "The CSV did not have a valid header.");
        }

        return map.keySet();
    }

    private static CSVParser getDefaultCsvParser(final Reader input, final char delimiter) {
        try {
            return CSVFormat.RFC4180
                    .withDelimiter(delimiter)
                    .withHeader()
                    .parse(input);
        } catch (IOException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Creating CSVParser failed", e);
        }
    }

    private static Offer extractOfferFromCSV(final Authentication authentication, final Map<String, String> errors, final CSVRecord record) {
        // Extract the Country from the Authentication Information
        final Country country = CommonTransformer.transform(authentication.getGroup().getCountry());

        // Read the Address from the CSV and assign the found Country to it
        final Address address = CommonTransformer.addressFromCsv(record, errors);
        address.setCountry(country);

        // Read the Employer from the CSV, and assign the transformed Address from it
        final Employer employer = ExchangeTransformer.employerFromCsv(record, errors);
        employer.setAddress(address);

        // Read the Offer from the CSV, and assign the transformed Employer to it
        final Offer offer = ExchangeTransformer.offerFromCsv(record, errors);
        offer.setEmployer(employer);

        // As all the Setters from the Offer has been invoked, all errors for
        // this Offer has already been caught. Invoking the validator will only
        // generate additional false error messages, since the validator will
        // apply null checks to those values that failed the Setter checks and
        // has not been set. Example, if the RefNo is wrong, then the Setter
        // will reject it but not set it, the Validator will see it as null and
        // set that as error as well, which is incorrect.
        return offer;
    }

    private void process(final Map<String, OfferCSVUploadResponse.ProcessingResult> processingResult, final Map<String, CSVProcessingErrors> errors, final Authentication authentication, final CSVRecord record) {
        final Map<String, String> conversionErrors = new HashMap<>(0);
        String refNo = "";

        try {
            refNo = record.get(OfferFields.REF_NO.getField());
            final Offer csvOffer = extractOfferFromCSV(authentication, conversionErrors, record);

            final CSVProcessingErrors validationErrors = new CSVProcessingErrors(conversionErrors);
            if (validationErrors.isEmpty()) {
                processingResult.put(refNo, processOffer(authentication, refNo, csvOffer));
            } else {
                LOG.warn(formatLogMessage(authentication, "CSV Offer with RefNo " + refNo + " has some Problems: " + conversionErrors));
                processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.ERROR);
                errors.put(refNo, validationErrors);
            }
        } catch (IllegalArgumentException | IWSException e) {
            LOG.debug(e.getMessage(), e);
            LOG.warn(formatLogMessage(authentication, "CSV Offer with RefNo " + refNo + " has a Problem: " + e.getMessage()));
            processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.ERROR);
            if (errors.containsKey(refNo)) {
                errors.get(refNo).put("general", e.getMessage());
            } else {
                final CSVProcessingErrors generalError = new CSVProcessingErrors();
                generalError.put("general", e.getMessage());
                if (!conversionErrors.isEmpty()) {
                    generalError.putAll(conversionErrors);
                }
                errors.put(refNo, generalError);
            }
        }
    }

    private OfferCSVUploadResponse.ProcessingResult processOffer (final Authentication authentication, final String refNo, final Offer csvOffer) {
        final OfferEntity existingEntity = dao.findOfferByRefNo(authentication, refNo);
        final OfferEntity newEntity = ExchangeTransformer.transform(csvOffer);
        final OfferCSVUploadResponse.ProcessingResult result;

        if (existingEntity != null) {
            permissionCheck(authentication, authentication.getGroup());

            //keep original offer state
            newEntity.setStatus(existingEntity.getStatus());

            csvOffer.getEmployer().setEmployerId(existingEntity.getEmployer().getExternalId());
            final EmployerEntity employerEntity = process(authentication, csvOffer.getEmployer());
            existingEntity.setEmployer(employerEntity);

            newEntity.setExternalId(existingEntity.getExternalId());
            dao.persist(authentication, existingEntity, newEntity);
            LOG.info(formatLogMessage(authentication, "CSV Update of Offer with RefNo '%s' completed.", newEntity.getRefNo()));
            result = OfferCSVUploadResponse.ProcessingResult.UPDATED;
        } else {
            // First, we need an Employer for our new Offer. The Process
            // method will either find an existing Employer or create a
            // new one.
            final EmployerEntity employer = process(authentication, csvOffer.getEmployer());

            // Add the Group to the Offer, otherwise our ref.no checks will fail
            employer.setGroup(authentication.getGroup());

            newEntity.setEmployer(employer);

            ExchangeService.verifyRefnoValidity(newEntity);

            newEntity.setExchangeYear(Verifications.calculateExchangeYear());
            // Add the employer to the Offer
            newEntity.setEmployer(employer);
            // Set the Offer status to New
            newEntity.setStatus(OfferState.NEW);

            // Persist the Offer with history
            dao.persist(authentication, newEntity);
            LOG.info(formatLogMessage(authentication, "CSV Import of Offer with RefNo '%s' completed.", newEntity.getRefNo()));
            result = OfferCSVUploadResponse.ProcessingResult.ADDED;
        }

        return result;
    }

    /**
     * Processes an Employer from the CSV file. This is done by first trying to
     * lookup the Employer via the unique characteristics for an Employer - and
     * only of no existing records is found, will a new record be created. If
     * a record is found, the changes will be merged and potentially also
     * persisted.<br />
     *   If more than one Employer is found, then an Identification Exception is
     * thrown.
     *
     * @param authentication The users Authentication information
     * @param employer       The Employer to find / create
     * @return Employer Entity found or created
     */
    private EmployerEntity process(final Authentication authentication, final Employer employer) {
        // If the Employer provided is having an Id set - then we need to update
        // the existing record, otherwise we will try to see if we can find a
        // similar Employer and update it. If we can neither find an Employer by
        // the Id, not the unique information - then we will create a new one.
        EmployerEntity entity;
        if (employer.getEmployerId() != null) {
            // Id exists, so we simply find the Employer based on that
            entity = dao.findEmployer(authentication, employer.getEmployerId());
            LOG.debug(formatLogMessage(authentication, "Employer lookup for Id '%s' gave '%s'.", employer.getEmployerId(), entity.getName()));
        } else {
            // No Id was set, so we're trying to find the Employer based on the
            // Unique information
            entity = dao.findUniqueEmployer(authentication, employer);
            LOG.debug(formatLogMessage(authentication, "Unique Employer for name '%s' gave '%s'.", employer.getName(), (entity != null) ? entity.getName() : "null"));
        }

        if (entity == null) {
            entity = ExchangeTransformer.transform(employer);
            final GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
            entity.setGroup(nationalGroup);
            processAddress(authentication, entity.getAddress());
            dao.persist(authentication, entity);
            LOG.info(formatLogMessage(authentication, "Have added the Employer '%s' for '%s'.", employer.getName(), authentication.getGroup().getGroupName()));
        } else {
            final EmployerEntity updated = ExchangeTransformer.transform(employer);
            processAddress(authentication, entity.getAddress(), employer.getAddress());
            dao.persist(authentication, entity, updated);
            LOG.info(formatLogMessage(authentication, "Have updated the Employer '%s' for '%s'.", employer.getName(), authentication.getGroup().getGroupName()));
        }

        return entity;
    }
}
