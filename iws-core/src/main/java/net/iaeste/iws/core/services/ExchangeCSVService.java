/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
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
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.exceptions.IdentificationException;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.SharedOfferView;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

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

    private final static char DELIMITER = ',';

    private final ExchangeDao dao;
    private final AccessDao accessDao;
    private final ViewsDao viewsDao;

    public ExchangeCSVService(final Settings settings, final ExchangeDao dao, final AccessDao accessDao, final ViewsDao viewsDao) {
        super(settings, dao);

        this.dao = dao;
        this.accessDao = accessDao;
        this.viewsDao = viewsDao;
    }

    public OfferCSVUploadResponse uploadOffers(final Authentication authentication, final OfferCSVUploadRequest request) {
        final OfferCSVUploadResponse response = new OfferCSVUploadResponse();
        Map<String, OfferCSVUploadResponse.ProcessingResult> processingResult = new HashMap<>();
        final Map<String, Map<String, String>> errors = new HashMap<>();

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.getData());
             Reader reader = new InputStreamReader(byteArrayInputStream);
             CSVParser parser = getDefaultCsvParser(reader)) {
            Map<String, Integer> headersMap = parser.getHeaderMap();
            Set<String> headers = headersMap.keySet();
            Set<String> expectedHeaders = new HashSet<>(createFirstRow());
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

    private CSVParser getDefaultCsvParser(final Reader input) {
        try {
            return CSVFormat.RFC4180.withDelimiter(DELIMITER)
                    .withHeader()
                            //.withNullString("")
                    .parse(input);
        } catch (IOException e) {
            throw new IWSException(IWSErrors.PROCESSING_FAILURE, "Creating CSVPrinter failed", e);
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
             BufferedWriter writer = new BufferedWriter(streamWriter)) {

            CSVPrinter printer = getDefaultCsvPrinter(writer);
            printer.printRecord(createFirstRow());

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
             BufferedWriter writer = new BufferedWriter(streamWriter)) {

            CSVPrinter printer = getDefaultCsvPrinter(writer);
            printer.printRecord(createFirstRow());

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
        try {
            refNo = record.get("Ref.No");
            final Map<String, String> conversionErrors = new HashMap<>(0);
            final Offer csvOffer = ExchangeTransformer.offerFromCsv(record, conversionErrors);
            final Employer csvEmployer= ExchangeTransformer.employerFromCsv(record, conversionErrors);
            final Address csvAddress = CommonTransformer.addressFromCsv(record);
            final Country csvCountry = CommonTransformer.countryFromCsv(record); //There is no information about country except a name, useless for upload
//            csvCountry.setCurrency(csvOffer.getCurrency());
            //use current country instead
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
                    //TODO ExchangeService has this check but why to check authentication.getGroup when permissionCheck
                    //     then takes authentication.getGroup again? should be offer.employer.group instead?
                    //permissionCheck(authentication, existingEntity.getEmployer().getGroup()); ?
                    permissionCheck(authentication, authentication.getGroup());

                    //keep original offer state
                    newEntity.setStatus(existingEntity.getStatus());

                    csvEmployer.setEmployerId(existingEntity.getEmployer().getExternalId());
                    final EmployerEntity employerEntity = process(authentication, csvEmployer);
                    existingEntity.setEmployer(employerEntity);

                    newEntity.setExternalId(existingEntity.getExternalId());
                    dao.persist(authentication, existingEntity, newEntity);
                    processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.Updated);
                } else {
                    //create employer
                    final EmployerEntity employer = process(authentication, csvOffer.getEmployer());

                    // Add the Group to the Offer, otherwise our refno checks will fail
                    employer.setGroup(authentication.getGroup());

                    newEntity.setEmployer(employer);

                    ExchangeService.verifyRefnoValidity(authentication, newEntity);

                    newEntity.setExchangeYear(AbstractVerification.calculateExchangeYear());
                    // Add the employer to the Offer
                    newEntity.setEmployer(employer);
                    // Set the Offer status to New
                    newEntity.setStatus(OfferState.NEW);

                    // Persist the Offer with history
                    dao.persist(authentication, newEntity);
                    processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.Added);
                }
            } else {
                processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.Error);
                errors.put(refNo, validationErrors);
            }
        } catch (IllegalArgumentException|IWSException e) {
            processingResult.put(refNo, OfferCSVUploadResponse.ProcessingResult.Error);
            if (errors.containsKey(refNo)) {
                errors.get(refNo).put("general", e.getMessage());
            } else {
                final Map<String, String> generalError = new HashMap<>(1);
                generalError.put("general", e.getMessage());
                errors.put(refNo, generalError);
            }
        }
    }

    private EmployerEntity process(final Authentication authentication, final Employer employer) {
        EmployerEntity entity;
        try {
            entity = dao.findEmployer(employer.getEmployerId());
        } catch (IdentificationException e) {
            //no or multiple employers
            entity = null;
            employer.setEmployerId(null);
        }

        if (entity == null) {
            entity = ExchangeTransformer.transform(employer);
            GroupEntity nationalGroup = accessDao.findNationalGroup(authentication.getUser());
            entity.setGroup(nationalGroup);
            processAddress(authentication, entity.getAddress());
            dao.persist(authentication, entity);
        } else {
            final EmployerEntity updated = ExchangeTransformer.transform(employer);
            processAddress(authentication, entity.getAddress(), employer.getAddress());
            dao.persist(authentication, entity, updated);
        }
        return entity;
    }

    private AddressEntity process(final Authentication authentication, final Address address, final AddressEntity existingEntity) {
        AddressEntity result;
        final AddressEntity newAddress = CommonTransformer.transform(address);
        if (existingEntity == null) {
            dao.persist(authentication, newAddress);
            result = newAddress;
        } else {
            dao.persist(authentication, existingEntity, newAddress);
            result = existingEntity;
        }
        return result;
    }

    private List<String> createFirstRow() {
        final List<String> result = new ArrayList<>();
        result.add("Ref.No");
        result.add("Deadline");
        result.add("Comment");
        result.add("Employer");
        result.add("Street1");
        result.add("Street2");
        result.add("PostBox");
        result.add("PostalCode");
        result.add("City");
        result.add("State");
        result.add("Country");
        result.add("Website");
        result.add("Workplace");
        result.add("Business");
        result.add("Responsible");
        result.add("Airport");
        result.add("Transport");
        result.add("Employees");
        result.add("HoursWeekly");
        result.add("HoursDaily");
        result.add("Canteen");
        result.add("Faculty");
        result.add("Specialization");
        result.add("TrainingRequired");
        result.add("OtherRequirements");
        result.add("Workkind");
        result.add("WeeksMin");
        result.add("WeeksMax");
        result.add("From");
        result.add("To");
        result.add("StudyCompleted_Beginning");
        result.add("StudyCompleted_Middle");
        result.add("StudyCompleted_End");
        result.add("WorkType_P");
        result.add("WorkType_R");
        result.add("WorkType_W");
        result.add("WorkType_N");
        result.add("Language1");
        result.add("Language1Level");
        result.add("Language1Or");
        result.add("Language2");
        result.add("Language2Level");
        result.add("Language2Or");
        result.add("Language3");
        result.add("Language3Level");
        result.add("Currency");
        result.add("Payment");
        result.add("PaymentFrequency");
        result.add("Deduction");
        result.add("Lodging");
        result.add("LodgingCost");
        result.add("LodgingCostFrequency");
        result.add("LivingCost");
        result.add("LivingCostFrequency");
        result.add("NoHardCopies");
        result.add("Status");

        result.add("Period2_From");
        result.add("Period2_To");
        result.add("Holidays_From");
        result.add("Holidays_To");

        result.add("Additional_Info");

        result.add("Shared");

        result.add("Last modified");
        result.add("NS First Name");
        result.add("NS Last Name");

        return result;
    }

}
