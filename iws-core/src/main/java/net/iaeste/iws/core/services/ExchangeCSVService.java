/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.ExchangeCSVService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.exchange.OfferFields;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.core.transformers.ViewTransformer;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.SharedOfferView;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class ExchangeCSVService extends CommonService<ExchangeDao> {

    private static final Logger log = LoggerFactory.getLogger(ExchangeCSVService.class);

    private static final char DELIMITER = ',';

    private final AccessDao accessDao;
    private final ViewsDao viewsDao;

    public ExchangeCSVService(final Settings settings, final ExchangeDao dao, final AccessDao accessDao, final ViewsDao viewsDao) {
        super(settings, dao);

        this.accessDao = accessDao;
        this.viewsDao = viewsDao;
    }

    public OfferCSVUploadResponse uploadOffers(final Authentication authentication, final OfferCSVUploadRequest request) {
        final OfferCSVUploadResponse response = new OfferCSVUploadResponse();
        final Map<String, OfferCSVUploadResponse.ProcessingResult> processingResult = new HashMap<>();
        final Map<String, Map<String, String>> errors = new HashMap<>();

        final char fieldDelimiter = request.getDelimiter() != null ? request.getDelimiter().getDescription() : DELIMITER;

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getData());
             Reader reader = new InputStreamReader(byteArrayInputStream, IWSConstants.DEFAULT_ENCODING);
             CSVParser parser = getDefaultCsvParser(reader, fieldDelimiter)) {
            final Map<String, Integer> headersMap = parser.getHeaderMap();
            final Set<String> headers = headersMap.keySet();
            final Set<String> expectedHeaders = new HashSet<>(createDomesticFirstRow());
            if (expectedHeaders.equals(headers)) {
                for (final CSVRecord record : parser.getRecords()) {
                    process(processingResult, errors, authentication, record);
                }
            } else {
                throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Invalid CSV header");
            }
        } catch (IOException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "CSV upload processing failed", e);
        }

        response.setProcessingResult(processingResult);
        response.setErrors(errors);
        return response;
    }

    public OfferCSVDownloadResponse downloadOffers(final Authentication authentication, final OfferCSVDownloadRequest request) {
        final OfferCSVDownloadResponse response;
        switch (request.getFetchType()) {
            case DOMESTIC:
                response = new OfferCSVDownloadResponse(findDomesticOffers(authentication, request));
                break;
            case SHARED:
                response = new OfferCSVDownloadResponse(findSharedOffers(authentication, request));
                break;
            default:
                throw new PermissionException("The search type is not permitted.");
        }

        return response;
    }

    private byte[] findDomesticOffers(final Authentication authentication, final OfferCSVDownloadRequest request) {
        final List<String> offerIds = request.getOfferIds();
        final Paginatable page = request.getPagingInformation();
        final Integer exchangeYear = request.getExchangeYear();

        final List<OfferView> found;
        if (offerIds.isEmpty()) {
            //paging could make a problem here if it returns only some offers
            found = viewsDao.findDomesticOffers(authentication, exchangeYear, page);
        } else {
            found = viewsDao.findDomesticOffersByOfferIds(authentication, exchangeYear, offerIds);
        }

        byte[] result = null;
        if (!found.isEmpty()) {
            result = domesticToCsv(found);
        }

        return result;
    }

    private byte[] findSharedOffers(final Authentication authentication, final OfferCSVDownloadRequest request) {
        final List<String> offerIds = request.getOfferIds();
        final Paginatable page = request.getPagingInformation();
        final Integer exchangeYear = request.getExchangeYear();

        final List<SharedOfferView> found;
        if (offerIds.isEmpty()) {
            //paging could make a problem here if it returns only some offers
            found = viewsDao.findSharedOffers(authentication, exchangeYear, page);
        } else {
            found = viewsDao.findSharedOffersByOfferIds(authentication, exchangeYear, offerIds);
        }

        byte[] data = null;
        if (!found.isEmpty()) {
            data = sharedToCsv(found);
        }

        return data;
    }

    private CSVParser getDefaultCsvParser(final Reader input, final char delimiter) {
        try {
            return CSVFormat.RFC4180.withDelimiter(delimiter)
                    .withHeader()
                    .parse(input);
        } catch (IOException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Creating CSVParser failed", e);
        }
    }

    private CSVPrinter getDefaultCsvPrinter(final Appendable output) {
        try {
            return CSVFormat.RFC4180.withDelimiter(DELIMITER)
                                    .withNullString("")
                                    .print(output);
        } catch (IOException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Creating CSVPrinter failed", e);
        }
    }

    private byte[] sharedToCsv(final List<SharedOfferView> offers) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             OutputStreamWriter streamWriter = new OutputStreamWriter(stream, IWSConstants.DEFAULT_ENCODING);
             BufferedWriter writer = new BufferedWriter(streamWriter);
             CSVPrinter printer = getDefaultCsvPrinter(writer)) {
            printer.printRecord(createForeignFirstRow());

            for (final SharedOfferView offer : offers) {
                printer.printRecord(ViewTransformer.transformToStringList(offer));
            }

            writer.flush();
            streamWriter.flush();
            stream.flush();
            return stream.toByteArray();
        } catch (IOException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Serialization to CSV failed", e);
        }
    }

    private byte[] domesticToCsv(final List<OfferView> offers) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             OutputStreamWriter streamWriter = new OutputStreamWriter(stream, IWSConstants.DEFAULT_ENCODING);
             BufferedWriter writer = new BufferedWriter(streamWriter);
             CSVPrinter printer = getDefaultCsvPrinter(writer)) {
            printer.printRecord(createDomesticFirstRow());

            for (final OfferView offer : offers) {
                printer.printRecord(ViewTransformer.transformToStringList(offer));
            }

            writer.flush();
            streamWriter.flush();
            stream.flush();
            return stream.toByteArray();
        } catch (IOException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Serialization to CSV failed", e);
        }
    }

    private void process(final Map<String, OfferCSVUploadResponse.ProcessingResult> processingResult, final Map<String, Map<String, String>> errors, final Authentication authentication, final CSVRecord record) {
        String refNo = "";
        final Map<String, String> conversionErrors = new HashMap<>(0);

        try {
            refNo = record.get(OfferFields.REFNO.getField());
            final Offer csvOffer = ExchangeTransformer.offerFromCsv(record, conversionErrors);
            final Employer csvEmployer = ExchangeTransformer.employerFromCsv(record, conversionErrors);
            final Address csvAddress = CommonTransformer.addressFromCsv(record);
            final Country country = CommonTransformer.transform(authentication.getGroup().getCountry());
            csvAddress.setCountry(country);
            csvEmployer.setAddress(csvAddress);
            csvOffer.setEmployer(csvEmployer);

            final Map<String, String> validationErrors = csvOffer.validate();
            validationErrors.putAll(conversionErrors);
            if (validationErrors.isEmpty()) {
                final OfferEntity existingEntity = dao.findOfferByRefNo(authentication, refNo);
                final OfferEntity newEntity = ExchangeTransformer.transform(csvOffer);

                if (existingEntity != null) {
                    permissionCheck(authentication, authentication.getGroup());

                    //keep original offer state
                    newEntity.setStatus(existingEntity.getStatus());

                    csvEmployer.setEmployerId(existingEntity.getEmployer().getExternalId());
                    final EmployerEntity employerEntity = process(authentication, csvEmployer);
                    existingEntity.setEmployer(employerEntity);

                    newEntity.setExternalId(existingEntity.getExternalId());
                    dao.persist(authentication, existingEntity, newEntity);
                    log.info(formatLogMessage(authentication, "CSV Update of Offer with RefNo '%s' completed.", newEntity.getRefNo()));
                    processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.Updated);
                } else {
                    // First, we need an Employer for our new Offer. The Process
                    // method will either find an existing Employer or create a
                    // new one.
                    final EmployerEntity employer = process(authentication, csvOffer.getEmployer());

                    // Add the Group to the Offer, otherwise our refno checks will fail
                    employer.setGroup(authentication.getGroup());

                    newEntity.setEmployer(employer);

                    ExchangeService.verifyRefnoValidity(newEntity);

                    newEntity.setExchangeYear(AbstractVerification.calculateExchangeYear());
                    // Add the employer to the Offer
                    newEntity.setEmployer(employer);
                    // Set the Offer status to New
                    newEntity.setStatus(OfferState.NEW);

                    // Persist the Offer with history
                    dao.persist(authentication, newEntity);
                    log.info(formatLogMessage(authentication, "CSV Import of Offer with RefNo '%s' completed.", newEntity.getRefNo()));
                    processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.Added);
                }
            } else {
                log.warn(formatLogMessage(authentication, "CSV Offer with RefNo " + refNo + " has some Problems: " + validationErrors));
                processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.Error);
                errors.put(refNo, validationErrors);
            }
        } catch (IllegalArgumentException | IWSException e) {
            log.warn(formatLogMessage(authentication, "CSV Offer with RefNo " + refNo + " has a Problem: " + e.getMessage()), e);
            processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.Error);
            if (errors.containsKey(refNo)) {
                errors.get(refNo).put("general", e.getMessage());
            } else {
                final Map<String, String> generalError = new HashMap<>(1);
                generalError.put("general", e.getMessage());
                if (!conversionErrors.isEmpty()) {
                    generalError.putAll(conversionErrors);
                }
                errors.put(refNo, generalError);
            }
        }
    }

    /**
     * Processes an Employer from the CSV file. This is done by first trying to
     * lookup the Employer via the unique characteristica for an Employer - and
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
            log.debug(formatLogMessage(authentication, "Employer lookup for Id '%s' gave '%s'.", employer.getEmployerId(), entity.getName()));
        } else {
            // No Id was set, so we're trying to find the Employer based on the
            // Unique information
            entity = dao.findUniqueEmployer(authentication, employer);
            log.debug(formatLogMessage(authentication, "Unique Employer for name '%s' gave '%s'.", employer.getName(), entity != null ? entity.getName() : "null"));
        }

        if (entity == null) {
            entity = ExchangeTransformer.transform(employer);
            final GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
            entity.setGroup(nationalGroup);
            processAddress(authentication, entity.getAddress());
            dao.persist(authentication, entity);
            log.info(formatLogMessage(authentication, "Have added the Employer '%s' for '%s'.", employer.getName(), authentication.getGroup().getGroupName()));
        } else {
            final EmployerEntity updated = ExchangeTransformer.transform(employer);
            processAddress(authentication, entity.getAddress(), employer.getAddress());
            dao.persist(authentication, entity, updated);
            log.info(formatLogMessage(authentication, "Have updated the Employer '%s' for '%s'.", employer.getName(), authentication.getGroup().getGroupName()));
        }

        return entity;
    }

    private List<String> createForeignFirstRow() {
        final List<String> result = new ArrayList<>();

        result.add(OfferFields.REFNO.getField());
        result.add(OfferFields.DEADLINE.getField());
        result.add(OfferFields.COMMENT.getField());
        result.add(OfferFields.EMPLOYER.getField());
        result.add(OfferFields.STREET_1.getField());
        result.add(OfferFields.STREET_2.getField());
        result.add(OfferFields.POST_BOX.getField());
        result.add(OfferFields.POSTAL_CODE.getField());
        result.add(OfferFields.CITY.getField());
        result.add(OfferFields.STATE.getField());
        result.add(OfferFields.COUNTRY.getField());
        result.add(OfferFields.WEBSITE.getField());
        result.add(OfferFields.WORKPLACE.getField());
        result.add(OfferFields.BUSINESS.getField());
        result.add(OfferFields.RESPONSIBLE.getField());
        result.add(OfferFields.AIRPORT.getField());
        result.add(OfferFields.TRANSPORT.getField());
        result.add(OfferFields.EMPLOYEES.getField());
        result.add(OfferFields.HOURS_WEEKLY.getField());
        result.add(OfferFields.HOURS_DAILY.getField());
        result.add(OfferFields.CANTEEN.getField());
        result.add(OfferFields.FACULTY.getField());
        result.add(OfferFields.SPECIALIZATION.getField());
        result.add(OfferFields.TRAINING_REQUIRED.getField());
        result.add(OfferFields.OTHER_REQUIREMENTS.getField());
        result.add(OfferFields.WORKKIND.getField());
        result.add(OfferFields.WEEKS_MIN.getField());
        result.add(OfferFields.WEEKS_MAX.getField());
        result.add(OfferFields.FROM.getField());
        result.add(OfferFields.TO.getField());
        result.add(OfferFields.STUDY_COMPLETED_BEGINNING.getField());
        result.add(OfferFields.STUDY_COMPLETED_MIDDLE.getField());
        result.add(OfferFields.STUDY_COMPLETED_END.getField());
        result.add(OfferFields.WORK_TYPE_P.getField());
        result.add(OfferFields.WORK_TYPE_R.getField());
        result.add(OfferFields.WORK_TYPE_W.getField());
        result.add(OfferFields.WORK_TYPE_N.getField());
        result.add(OfferFields.LANGUAGE_1.getField());
        result.add(OfferFields.LANGUAGE_1_LEVEL.getField());
        result.add(OfferFields.LANGUAGE_1_OR.getField());
        result.add(OfferFields.LANGUAGE_2.getField());
        result.add(OfferFields.LANGUAGE_2_LEVEL.getField());
        result.add(OfferFields.LANGUAGE_2_OR.getField());
        result.add(OfferFields.LANGUAGE_3.getField());
        result.add(OfferFields.LANGUAGE_3_LEVEL.getField());
        result.add(OfferFields.CURRENCY.getField());
        result.add(OfferFields.PAYMENT.getField());
        result.add(OfferFields.PAYMENT_FREQUENCY.getField());
        result.add(OfferFields.DEDUCTION.getField());
        result.add(OfferFields.LODGING.getField());
        result.add(OfferFields.LODGING_COST.getField());
        result.add(OfferFields.LODGING_COST_FREQUENCY.getField());
        result.add(OfferFields.LIVING_COST.getField());
        result.add(OfferFields.LIVING_COST_FREQUENCY.getField());
        result.add(OfferFields.NO_HARD_COPIES.getField());
        result.add(OfferFields.STATUS.getField());
        result.add(OfferFields.PERIOD_2_FROM.getField());
        result.add(OfferFields.PERIOD_2_TO.getField());
        result.add(OfferFields.HOLIDAYS_FROM.getField());
        result.add(OfferFields.HOLIDAYS_TO.getField());
        result.add(OfferFields.ADDITIONAL_INFO.getField());
        result.add(OfferFields.SHARED.getField());
        result.add(OfferFields.LAST_MODIFIED.getField());
        result.add(OfferFields.NS_FIRST_NAME.getField());
        result.add(OfferFields.NS_LAST_NAME.getField());

        return result;
    }

    private List<String> createDomesticFirstRow() {
        final List<String> result = new ArrayList<>();

        result.add(OfferFields.REFNO.getField());
        result.add(OfferFields.OFFER_TYPE.getField());
        result.add(OfferFields.EXCHANGE_TYPE.getField());
        result.add(OfferFields.DEADLINE.getField());
        result.add(OfferFields.COMMENT.getField());
        result.add(OfferFields.EMPLOYER.getField());
        result.add(OfferFields.STREET_1.getField());
        result.add(OfferFields.STREET_2.getField());
        result.add(OfferFields.POST_BOX.getField());
        result.add(OfferFields.POSTAL_CODE.getField());
        result.add(OfferFields.CITY.getField());
        result.add(OfferFields.STATE.getField());
        result.add(OfferFields.COUNTRY.getField());
        result.add(OfferFields.WEBSITE.getField());
        result.add(OfferFields.WORKPLACE.getField());
        result.add(OfferFields.BUSINESS.getField());
        result.add(OfferFields.RESPONSIBLE.getField());
        result.add(OfferFields.AIRPORT.getField());
        result.add(OfferFields.TRANSPORT.getField());
        result.add(OfferFields.EMPLOYEES.getField());
        result.add(OfferFields.HOURS_WEEKLY.getField());
        result.add(OfferFields.HOURS_DAILY.getField());
        result.add(OfferFields.CANTEEN.getField());
        result.add(OfferFields.FACULTY.getField());
        result.add(OfferFields.SPECIALIZATION.getField());
        result.add(OfferFields.TRAINING_REQUIRED.getField());
        result.add(OfferFields.OTHER_REQUIREMENTS.getField());
        result.add(OfferFields.WORKKIND.getField());
        result.add(OfferFields.WEEKS_MIN.getField());
        result.add(OfferFields.WEEKS_MAX.getField());
        result.add(OfferFields.FROM.getField());
        result.add(OfferFields.TO.getField());
        result.add(OfferFields.STUDY_COMPLETED_BEGINNING.getField());
        result.add(OfferFields.STUDY_COMPLETED_MIDDLE.getField());
        result.add(OfferFields.STUDY_COMPLETED_END.getField());
        result.add(OfferFields.WORK_TYPE_P.getField());
        result.add(OfferFields.WORK_TYPE_R.getField());
        result.add(OfferFields.WORK_TYPE_W.getField());
        result.add(OfferFields.WORK_TYPE_N.getField());
        result.add(OfferFields.LANGUAGE_1.getField());
        result.add(OfferFields.LANGUAGE_1_LEVEL.getField());
        result.add(OfferFields.LANGUAGE_1_OR.getField());
        result.add(OfferFields.LANGUAGE_2.getField());
        result.add(OfferFields.LANGUAGE_2_LEVEL.getField());
        result.add(OfferFields.LANGUAGE_2_OR.getField());
        result.add(OfferFields.LANGUAGE_3.getField());
        result.add(OfferFields.LANGUAGE_3_LEVEL.getField());
        result.add(OfferFields.CURRENCY.getField());
        result.add(OfferFields.PAYMENT.getField());
        result.add(OfferFields.PAYMENT_FREQUENCY.getField());
        result.add(OfferFields.DEDUCTION.getField());
        result.add(OfferFields.LODGING.getField());
        result.add(OfferFields.LODGING_COST.getField());
        result.add(OfferFields.LODGING_COST_FREQUENCY.getField());
        result.add(OfferFields.LIVING_COST.getField());
        result.add(OfferFields.LIVING_COST_FREQUENCY.getField());
        result.add(OfferFields.NO_HARD_COPIES.getField());
        result.add(OfferFields.STATUS.getField());
        result.add(OfferFields.PERIOD_2_FROM.getField());
        result.add(OfferFields.PERIOD_2_TO.getField());
        result.add(OfferFields.HOLIDAYS_FROM.getField());
        result.add(OfferFields.HOLIDAYS_TO.getField());
        result.add(OfferFields.ADDITIONAL_INFO.getField());
        result.add(OfferFields.SHARED.getField());

        return result;
    }
}
