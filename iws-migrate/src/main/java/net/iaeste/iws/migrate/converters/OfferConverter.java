package net.iaeste.iws.migrate.converters;

import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;

import java.math.BigDecimal;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferConverter extends CommonConverter {

    public OfferEntity convert(final IW3OffersEntity oldOffer) {
        final OfferEntity entity = new OfferEntity();

        entity.setRefNo(convertRefno(oldOffer.getSystemrefno()));
        entity.setOldRefno(convert(oldOffer.getLocalrefno()));
        entity.setEmployer(convertEmployer(oldOffer));
        entity.setWorkDescription(convert(oldOffer.getWorkkind()));
        entity.setTypeOfWork(convertTypeOfWork(oldOffer.getWorktype()));
        entity.setStudyLevels(convertStudyLevels(oldOffer));
        entity.setFieldOfStudies(convertFieldOfStudies(oldOffer));
        entity.setSpecializations(convertSpecializations(oldOffer.getSpecialization()));
        entity.setPrevTrainingRequired(convertTrainingRequired(oldOffer.getTrainingrequired()));
        entity.setOtherRequirements(oldOffer.getOtherrequirements());
        entity.setMinimumWeeks(oldOffer.getWeeksmin());
        entity.setMaximumWeeks(oldOffer.getWeeksmax());
        entity.setFromDate(oldOffer.getFromdate());
        entity.setToDate(oldOffer.getTodate());
        entity.setFromDate2(oldOffer.getFromdate2());
        entity.setToDate2(oldOffer.getTodate2());
        entity.setUnavailableFrom(oldOffer.getHolidaysfromdate());
        entity.setUnavailableTo(oldOffer.getHolidaystodate());
        entity.setLanguage1(convertLanguage(oldOffer.getLanguage1(), Language.ENGLISH));
        entity.setLanguage1Level(convertLanguageLevel(oldOffer.getLanguage1Level()));
        entity.setLanguage1Operator(convertLanguageOperator(oldOffer.getLanguage1Or()));
        entity.setLanguage2(convertLanguage(oldOffer.getLanguage2(), null));
        entity.setLanguage2Level(convertLanguageLevel(oldOffer.getLanguage2Level()));
        entity.setLanguage2Operator(convertLanguageOperator(oldOffer.getLanguage2Or()));
        entity.setLanguage3(convertLanguage(oldOffer.getLanguage3(), null));
        entity.setLanguage3Level(convertLanguageLevel(oldOffer.getLanguage3Level()));
        entity.setPayment(BigDecimal.valueOf(oldOffer.getPayment()));
        entity.setPaymentFrequency(convertFrequency(oldOffer.getPaymentfrequency()));
        // Set via the Employer Country
        //entity.setCurrency(offer.getCurrency());
        entity.setDeduction(convert(oldOffer.getDeduction()));
        entity.setLivingCost(BigDecimal.valueOf(oldOffer.getLivingcost()));
        entity.setLivingCostFrequency(convertFrequency(oldOffer.getLivingcostfrequency()));
        entity.setLodgingBy(convert(oldOffer.getLodging()));
        entity.setLodgingCost(BigDecimal.valueOf(oldOffer.getLodgingcost()));
        entity.setLodgingCostFrequency(convertFrequency(oldOffer.getLodgingcostfrequency()));
        entity.setAdditionalInformation(convert(oldOffer.getComment()));
        entity.setStatus(convertOfferState(oldOffer.getStatus()));
        entity.setNumberOfHardCopies(oldOffer.getNohardcopies());
        entity.setNominationDeadline(oldOffer.getDeadline());
        entity.setExchangeYear(convertExchangeYear(oldOffer.getExchangeyear()));
        entity.setModified(convert(oldOffer.getModified()));
        entity.setCreated(convert(oldOffer.getCreated(), oldOffer.getModified()));

        return entity;
    }

    private static String convertRefno(final String systemrefno) {
        // IW3 Refno format: CCYY-nnnn
        // IWS Refno format: CC-YYYY-nnnnnn
        final String countryCode = systemrefno.substring(0,2);
        final String year = systemrefno.substring(2,4);
        final String serialNumber = systemrefno.substring(5,9);

        return countryCode + "-20" + year + "-00" + serialNumber;
    }

    private static TypeOfWork convertTypeOfWork(final String worktype) {
        return TypeOfWork.R;
    }

    private static String convertStudyLevels(final IW3OffersEntity oldOffer) {
        return "B,M,E";
    }

    private static String convertFieldOfStudies(final IW3OffersEntity oldOffer) {
        return "Something";
    }

    private static String convertSpecializations(final String specialization) {
        return "Something";
    }

    private static Boolean convertTrainingRequired(final String trainingrequired) {
        return true;
    }

    private static Language convertLanguage(final String language, final Language defaultLanguage) {
        return defaultLanguage;
    }

    private static LanguageLevel convertLanguageLevel(final Integer languageLevel) {
        return LanguageLevel.E;
    }

    private static LanguageOperator convertLanguageOperator(final Boolean languageOr) {
        return null;
    }

    private static PaymentFrequency convertFrequency(final String frequency) {
        return PaymentFrequency.MONTHLY;
    }

    private static OfferState convertOfferState(final String status) {
        return OfferState.NEW;
    }

    private Integer convertExchangeYear(final Integer exchangeyear) {
        return 2013;
    }

    private static EmployerEntity convertEmployer(final IW3OffersEntity oldOffer) {
        final EmployerEntity entity = new EmployerEntity();

        entity.setName(convert(oldOffer.getEmployername()));
        // Apparently we didn't have this value earlier
        //entity.setDepartment(convert(oldOffer.getemployer.getDepartment());
        entity.setBusiness(convert(oldOffer.getBusiness()));
        entity.setAddress(convertAddress(oldOffer));
        entity.setNumberOfEmployees(convert(oldOffer.getEmployees()));
        entity.setWebsite(convert(oldOffer.getWebsite()));
        entity.setWorkingPlace(convert(oldOffer.getWorkplace()));
        entity.setNearestAirport(convert(oldOffer.getAirport()));
        entity.setNearestPublicTransport(oldOffer.getTransport());
        entity.setWeeklyHours(round(oldOffer.getHoursweekly()));
        entity.setDailyHours(round(oldOffer.getHoursdaily()));
        entity.setModified(convert(oldOffer.getModified()));
        entity.setCreated(convert(oldOffer.getCreated(), oldOffer.getModified()));

        return entity;
    }

    private static AddressEntity convertAddress(final IW3OffersEntity oldOffer) {
        final AddressEntity entity = new AddressEntity();

        entity.setStreet1(convert(oldOffer.getEmployeraddress1()));
        entity.setStreet2(convert(oldOffer.getEmployeraddress2()));
        // Country will be set in the invoking method
        entity.setModified(convert(oldOffer.getModified()));
        entity.setCreated(convert(oldOffer.getCreated(), oldOffer.getModified()));

        return entity;
    }
}
