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
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.requests.exchange.OfferCSVDownloadRequest;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.api.responses.exchange.OfferCSVDownloadResponse;
import net.iaeste.iws.api.responses.exchange.OfferCSVUploadResponse;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.transformers.ViewTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.ExchangeDao;
import net.iaeste.iws.persistence.ViewsDao;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.SharedOfferView;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public class ExchangeCSVService {

    private final static char DELIMITER = ',';

    private final ExchangeDao dao;
    private final ViewsDao viewsDao;

    public ExchangeCSVService(final ExchangeDao dao, final ViewsDao viewsDao) {
        this.dao = dao;
        this.viewsDao = viewsDao;
    }

    public OfferCSVUploadResponse uploadOffers(final Authentication authentication, final OfferCSVUploadRequest request) {
        throw new IWSException(IWSErrors.NOT_IMPLEMENTED, "Fetch Offers as CSV is not yet implemented, see Trac Ticket #920.");
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
