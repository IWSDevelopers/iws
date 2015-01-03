/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.ExchangeTransformer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.transformers;

import static net.iaeste.iws.api.enums.exchange.OfferFields.*;

import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.OfferGroup;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.Specialization;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.persistence.entities.AttachmentEntity;
import net.iaeste.iws.persistence.entities.exchange.ApplicationEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.PublishingGroupEntity;
import net.iaeste.iws.persistence.entities.exchange.StudentEntity;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Transformer for the Exchange module, handles transformation of the DTO Objects
 * to and from the Entity data structure.
 *
 * @author  Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ExchangeTransformer {

    /**
     * Private Constructor, this is a utility class.
     */
    private ExchangeTransformer() {
    }

    public static OfferEntity transform(final Offer offer) {
        OfferEntity result = null;

        if (offer != null) {
            result = new OfferEntity();

            result.setExternalId(offer.getOfferId());
            result.setRefNo(offer.getRefNo());
            result.setOfferType(offer.getOfferType());
            result.setExchangeType(offer.getExchangeType());
            result.setEmployer(transform(offer.getEmployer()));
            result.setWorkDescription(offer.getWorkDescription());
            result.setWeeklyHours(offer.getWeeklyHours());
            result.setDailyHours(offer.getDailyHours());
            result.setWeeklyWorkDays(offer.getWeeklyWorkDays());
            result.setTypeOfWork(offer.getTypeOfWork());
            result.setStudyLevels(CollectionTransformer.concatEnumCollection(offer.getStudyLevels()));
            result.setFieldOfStudies(CollectionTransformer.concatEnumCollection(offer.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.join(offer.getSpecializations()));
            result.setPrevTrainingRequired(offer.getPreviousTrainingRequired());
            result.setOtherRequirements(offer.getOtherRequirements());
            result.setMinimumWeeks(offer.getMinimumWeeks());
            result.setMaximumWeeks(offer.getMaximumWeeks());
            result.setFromDate(CommonTransformer.readFromDateFromPeriod(offer.getPeriod1()));
            result.setToDate(CommonTransformer.readToDateFromPeriod(offer.getPeriod1()));
            result.setFromDate2(CommonTransformer.readFromDateFromPeriod(offer.getPeriod2()));
            result.setToDate2(CommonTransformer.readToDateFromPeriod(offer.getPeriod2()));
            result.setUnavailableFrom(CommonTransformer.readFromDateFromPeriod(offer.getUnavailable()));
            result.setUnavailableTo(CommonTransformer.readToDateFromPeriod(offer.getUnavailable()));
            result.setLanguage1(offer.getLanguage1());
            result.setLanguage1Level(offer.getLanguage1Level());
            result.setLanguage1Operator(offer.getLanguage1Operator());
            result.setLanguage2(offer.getLanguage2());
            result.setLanguage2Level(offer.getLanguage2Level());
            result.setLanguage2Operator(offer.getLanguage2Operator());
            result.setLanguage3(offer.getLanguage3());
            result.setLanguage3Level(offer.getLanguage3Level());
            result.setPayment(offer.getPayment());
            result.setPaymentFrequency(offer.getPaymentFrequency());
            result.setCurrency(offer.getCurrency());
            result.setDeduction(offer.getDeduction());
            result.setLivingCost(offer.getLivingCost());
            result.setLivingCostFrequency(offer.getLivingCostFrequency());
            result.setLodgingBy(offer.getLodgingBy());
            result.setLodgingCost(offer.getLodgingCost());
            result.setLodgingCostFrequency(offer.getLodgingCostFrequency());
            result.setAdditionalInformation(offer.getAdditionalInformation());
            result.setPrivateComment(offer.getPrivateComment());
            result.setStatus(offer.getStatus());
            result.setNumberOfHardCopies(offer.getNumberOfHardCopies());
            result.setNominationDeadline(CommonTransformer.convert(offer.getNominationDeadline()));
        }

        return result;
    }

    public static Offer offerFromCsv(final CSVRecord record, final Map<String, String> offerErrors) {
        Offer result = null;

        if (record != null) {
            result = new Offer();

            result.setRefNo(record.get(REFNO.getField()));
            result.setOfferType(CsvTransformer.toEnum(offerErrors, record, OFFER_TYPE, OfferType.class));
            // ExchangeType is relevant when an Offer is shared - not when uploading
            //result.setExchangeType(CsvTransformer.toEnum(offerErrors, record, EXCHANGE_TYPE, ExchangeType.class));

            //employer is read in separate way and is assigned afterwards
            //result.setEmployer(employerFromCsv(record, offerErrors));

            result.setWorkDescription(record.get("Workkind"));
            result.setWeeklyHours(CsvTransformer.toFloat(offerErrors, record, HOURS_WEEKLY));
            result.setDailyHours(CsvTransformer.toFloat(offerErrors, record, HOURS_DAILY));
            //not existing in CSV?
            //result.setWeeklyWorkDays(record.getWeeklyWorkDays());
            result.setTypeOfWork(CsvTransformer.toTypeOfWork(offerErrors, "WorkType", record.get(WORK_TYPE_P.getField()), record.get(WORK_TYPE_R.getField()), record.get(WORK_TYPE_W.getField()), record.get(WORK_TYPE_N.getField())));
            result.setStudyLevels(CsvTransformer.toStudyLevels(offerErrors, "StudyCompleted_*", record.get(STUDY_COMPLETED_BEGINNING.getField()), record.get(STUDY_COMPLETED_MIDDLE.getField()), record.get(STUDY_COMPLETED_END.getField())));
            result.setFieldOfStudies(CsvTransformer.toEnumSet(offerErrors, record, FACULTY, FieldOfStudy.class));
            result.setSpecializations(CsvTransformer.toStringSet(offerErrors, record, SPECIALIZATION));
            result.setPreviousTrainingRequired(CsvTransformer.toBoolean(offerErrors, record, TRAINING_REQUIRED));
            result.setOtherRequirements(record.get(OTHER_REQUIREMENTS.getField()));
            result.setMinimumWeeks(CsvTransformer.toInteger(offerErrors, record, WEEKS_MIN));
            result.setMaximumWeeks(CsvTransformer.toInteger(offerErrors, record, WEEKS_MAX));

            result.setPeriod1(CsvTransformer.toDatePeriod(offerErrors, record, FROM, TO));
            result.setPeriod2(CsvTransformer.toDatePeriod(offerErrors, record, PERIOD_2_FROM, PERIOD_2_TO));
            result.setUnavailable(CsvTransformer.toDatePeriod(offerErrors, record, HOLIDAYS_FROM, HOLIDAYS_TO));
            result.setLanguage1(CsvTransformer.toEnum(offerErrors, record, LANGUAGE_1, Language.class));
            result.setLanguage1Level(CsvTransformer.toEnum(offerErrors, record, LANGUAGE_1_LEVEL, LanguageLevel.class));
            result.setLanguage1Operator(CsvTransformer.toEnum(offerErrors, record, LANGUAGE_1_OR, LanguageOperator.class));
            result.setLanguage2(CsvTransformer.toEnum(offerErrors, record, LANGUAGE_2, Language.class));
            result.setLanguage2Level(CsvTransformer.toEnum(offerErrors, record, LANGUAGE_2_LEVEL, LanguageLevel.class));
            result.setLanguage2Operator(CsvTransformer.toEnum(offerErrors, record, LANGUAGE_2_OR, LanguageOperator.class));
            result.setLanguage3(CsvTransformer.toEnum(offerErrors, record, LANGUAGE_3, Language.class));
            result.setLanguage3Level(CsvTransformer.toEnum(offerErrors, record, LANGUAGE_3_LEVEL, LanguageLevel.class));
            result.setPayment(CsvTransformer.toBigDecimal(offerErrors, record, PAYMENT));
            result.setPaymentFrequency(CsvTransformer.toEnum(offerErrors, record, PAYMENT_FREQUENCY, PaymentFrequency.class));
            // Should be set via the Country
            result.setCurrency(CsvTransformer.toEnum(offerErrors, record, CURRENCY, Currency.class));
            result.setDeduction(record.get(DEDUCTION.getField()));
            result.setLivingCost(CsvTransformer.toBigDecimal(offerErrors, record, LIVING_COST));
            result.setLivingCostFrequency(CsvTransformer.toEnum(offerErrors, record, LIVING_COST_FREQUENCY, PaymentFrequency.class));
            result.setLodgingBy(record.get(LODGING.getField()));
            result.setLodgingCost(CsvTransformer.toBigDecimal(offerErrors, record, LODGING_COST));
            result.setLodgingCostFrequency(CsvTransformer.toEnum(offerErrors, record, LODGING_COST_FREQUENCY, PaymentFrequency.class));
            result.setAdditionalInformation(record.get(ADDITIONAL_INFO.getField()));
            result.setPrivateComment(record.get(COMMENT.getField()));
            //result.setStatus(); ignored intentionally
            result.setNumberOfHardCopies(CsvTransformer.toInteger(offerErrors, record, NO_HARD_COPIES));
            result.setNominationDeadline(CsvTransformer.toDate(offerErrors, record, DEADLINE));
        }

        return result;
    }

    public static Offer transform(final OfferEntity entity) {
        Offer result = null;

        if (entity != null) {
            result = new Offer();

            result.setOfferId(entity.getExternalId());
            result.setRefNo(entity.getRefNo());
            result.setOfferType(entity.getOfferType());
            result.setExchangeType(entity.getExchangeType());
            result.setOldRefNo(entity.getOldRefno());
            result.setEmployer(transform(entity.getEmployer()));
            result.setWorkDescription(entity.getWorkDescription());
            result.setTypeOfWork(entity.getTypeOfWork());
            result.setWeeklyHours(entity.getWeeklyHours());
            result.setDailyHours(entity.getDailyHours());
            result.setWeeklyWorkDays(entity.getWeeklyWorkDays());
            result.setStudyLevels(CollectionTransformer.explodeEnumSet(StudyLevel.class, entity.getStudyLevels()));
            result.setFieldOfStudies(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, entity.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.explodeStringSet(entity.getSpecializations()));
            result.setPreviousTrainingRequired(entity.getPrevTrainingRequired());
            result.setOtherRequirements(entity.getOtherRequirements());
            result.setLanguage1(entity.getLanguage1());
            result.setLanguage1Level(entity.getLanguage1Level());
            result.setLanguage1Operator(entity.getLanguage1Operator());
            result.setLanguage2(entity.getLanguage2());
            result.setLanguage2Level(entity.getLanguage2Level());
            result.setLanguage2Operator(entity.getLanguage2Operator());
            result.setLanguage3(entity.getLanguage3());
            result.setLanguage3Level(entity.getLanguage3Level());
            result.setMinimumWeeks(entity.getMinimumWeeks());
            result.setMaximumWeeks(entity.getMaximumWeeks());
            result.setPeriod1(CommonTransformer.transform(entity.getFromDate(), entity.getToDate()));
            result.setPeriod2(CommonTransformer.transform(entity.getFromDate2(), entity.getToDate2()));
            result.setUnavailable(CommonTransformer.transform(entity.getUnavailableFrom(), entity.getUnavailableTo()));
            result.setPayment(entity.getPayment());
            result.setPaymentFrequency(entity.getPaymentFrequency());
            result.setCurrency(entity.getCurrency());
            result.setDeduction(entity.getDeduction());
            result.setLivingCost(entity.getLivingCost());
            result.setLivingCostFrequency(entity.getLivingCostFrequency());
            result.setLodgingBy(entity.getLodgingBy());
            result.setLodgingCost(entity.getLodgingCost());
            result.setLodgingCostFrequency(entity.getLodgingCostFrequency());
            result.setNominationDeadline(CommonTransformer.convert(entity.getNominationDeadline()));
            result.setNumberOfHardCopies(entity.getNumberOfHardCopies());
            result.setAdditionalInformation(entity.getAdditionalInformation());
            result.setPrivateComment(entity.getPrivateComment());
            result.setStatus(entity.getStatus());
            result.setModified(new DateTime(entity.getModified()));
            result.setCreated(new DateTime(entity.getCreated()));
        }

        return result;
    }

    public static Employer transform(final EmployerEntity entity) {
        Employer result = null;

        if (entity != null) {
            result = new Employer();

            result.setEmployerId(entity.getExternalId());
            result.setGroup(CommonTransformer.transform(entity.getGroup()));
            result.setName(entity.getName());
            result.setDepartment(entity.getDepartment());
            result.setBusiness(entity.getBusiness());
            result.setAddress(CommonTransformer.transform(entity.getAddress()));
            result.setEmployeesCount(entity.getNumberOfEmployees());
            result.setWebsite(entity.getWebsite());
            result.setWorkingPlace(entity.getWorkingPlace());
            result.setNearestAirport(entity.getNearestAirport());
            result.setNearestPublicTransport(entity.getNearestPublicTransport());
            result.setCanteen(entity.getCanteen());
        }

        return result;
    }

    public static EmployerEntity transform(final Employer employer) {
        EmployerEntity result = null;

        if (employer != null) {
            result = new EmployerEntity();

            result.setExternalId(employer.getEmployerId());
            result.setGroup(CommonTransformer.transform(employer.getGroup()));
            result.setName(employer.getName());
            result.setDepartment(employer.getDepartment());
            result.setBusiness(employer.getBusiness());
            result.setAddress(CommonTransformer.transform(employer.getAddress()));
            result.setNumberOfEmployees(employer.getEmployeesCount());
            result.setWebsite(employer.getWebsite());
            result.setWorkingPlace(employer.getWorkingPlace());
            result.setNearestAirport(employer.getNearestAirport());
            result.setNearestPublicTransport(employer.getNearestPublicTransport());
            result.setCanteen(employer.getCanteen());
        }

        return result;
    }

    public static Employer employerFromCsv(final CSVRecord record, final Map<String, String> errors) {
        Employer result = null;

        if (record != null) {
            result = new Employer();

            result.setName(record.get(EMPLOYER.getField()));
            //result.setDepartment(); not in CSV
            result.setBusiness(record.get(BUSINESS.getField()));
            //read separately, assigned later
            //result.setAddress(CommonTransformer.addressFromCsv(record));
            result.setEmployeesCount(record.get(EMPLOYEES.getField()));
            result.setWebsite(record.get(WEBSITE.getField()));
            result.setWorkingPlace(record.get(WORKPLACE.getField()));
            result.setNearestAirport(record.get(AIRPORT.getField()));
            result.setNearestPublicTransport(record.get(TRANSPORT.getField()));
            result.setCanteen(CsvTransformer.toBoolean(errors, record, CANTEEN));
        }

        return result;
    }

    public static OfferGroup transform(final OfferGroupEntity entity) {
        OfferGroup result = null;

        if (entity != null) {
            result = new OfferGroup();

            result.setOfferRefNo(entity.getOffer().getRefNo());
            result.setGroupId(entity.getGroup().getExternalId());
            result.setStatus(entity.getStatus());
            result.setComment(entity.getComment());
            result.setModified(new DateTime(entity.getModified()));
            result.setCreated(new DateTime(entity.getCreated()));
        }

        return result;
    }

    public static Student transform(final StudentEntity entity) {
        Student result = null;

        if (entity != null) {
            result = new Student();

            result.setUser(AdministrationTransformer.transform(entity.getUser()));
            result.setStudyLevel(entity.getStudyLevel());
            result.setFieldOfStudies(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, entity.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.explodeEnumSet(Specialization.class, entity.getSpecializations()));
            result.setAvailable(CommonTransformer.transform(entity.getAvailableFrom(), entity.getAvailableTo()));
            result.setLanguage1(entity.getLanguage1());
            result.setLanguage1Level(entity.getLanguage1Level());
            result.setLanguage2(entity.getLanguage2());
            result.setLanguage2Level(entity.getLanguage2Level());
            result.setLanguage3(entity.getLanguage3());
            result.setLanguage3Level(entity.getLanguage3Level());
            result.setModified(new DateTime(entity.getModified()));
            result.setCreated(new DateTime(entity.getCreated()));
        }

        return result;
    }

    public static StudentEntity transform(final Student student) {
        StudentEntity entity = null;

        if (student != null) {
            entity = new StudentEntity();

            entity.setUser(AdministrationTransformer.transform(student.getUser()));
            entity.setStudyLevel(student.getStudyLevel());
            entity.setFieldOfStudies(CollectionTransformer.concatEnumCollection(student.getFieldOfStudies()));
            entity.setSpecializations(CollectionTransformer.concatEnumCollection(student.getSpecializations()));
            entity.setAvailableFrom(CommonTransformer.readFromDateFromPeriod(student.getAvailable()));
            entity.setAvailableTo(CommonTransformer.readToDateFromPeriod(student.getAvailable()));
            entity.setLanguage1(student.getLanguage1());
            entity.setLanguage1Level(student.getLanguage1Level());
            entity.setLanguage2(student.getLanguage2());
            entity.setLanguage2Level(student.getLanguage2Level());
            entity.setLanguage3(student.getLanguage3());
            entity.setLanguage3Level(student.getLanguage3Level());
        }

        return entity;
    }

    public static StudentApplication transform(final ApplicationEntity entity, final List<AttachmentEntity> attachments) {
        final StudentApplication application = transform(entity);

        final List<File> files = new ArrayList<>(attachments.size());
        for (final AttachmentEntity attachment : attachments) {
            final File file = StorageTransformer.transform(attachment.getFile());
            files.add(file);
        }
        application.setAttachments(files);

        return application;
    }

    public static StudentApplication transform(final ApplicationEntity entity) {
        StudentApplication result = null;

        if (entity != null) {
            result = new StudentApplication();

            result.setApplicationId(entity.getExternalId());
            result.setOfferId(entity.getOfferGroup().getOffer().getExternalId());
            result.setOfferState(entity.getOfferGroup().getOffer().getStatus());
            result.setStudent(transform(entity.getStudent()));
            result.setStatus(entity.getStatus());
            result.setHomeAddress(CommonTransformer.transform(entity.getHomeAddress()));
            result.setEmail(entity.getEmail());
            result.setPhoneNumber(entity.getPhoneNumber());
            result.setAddressDuringTerms(CommonTransformer.transform(entity.getAddressDuringTerms()));
            result.setDateOfBirth(CommonTransformer.convert(entity.getDateOfBirth()));
            result.setUniversity(entity.getUniversity());
            result.setPlaceOfBirth(entity.getPlaceOfBirth());
            result.setNationality(CommonTransformer.transform(entity.getNationality()));
            result.setGender(entity.getGender());
            result.setCompletedYearsOfStudy(entity.getCompletedYearsOfStudy());
            result.setTotalYearsOfStudy(entity.getTotalYearsOfStudy());
            result.setIsLodgingByIaeste(entity.getIsLodgingByIaeste());
            result.setLanguage1(entity.getLanguage1());
            result.setLanguage1Level(entity.getLanguage1Level());
            result.setLanguage2(entity.getLanguage2());
            result.setLanguage2Level(entity.getLanguage2Level());
            result.setLanguage3(entity.getLanguage3());
            result.setLanguage3Level(entity.getLanguage3Level());
            result.setAvailable(CommonTransformer.transform(entity.getInternshipStart(), entity.getInternshipEnd()));
            result.setFieldOfStudies(CollectionTransformer.explodeEnumSet(FieldOfStudy.class, entity.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.explodeStringList(entity.getSpecializations()));
            result.setPassportNumber(entity.getPassportNumber());
            result.setPassportPlaceOfIssue(entity.getPassportPlaceOfIssue());
            result.setPassportValidUntil(entity.getPassportValidUntil());
            result.setRejectByEmployerReason(entity.getRejectByEmployerReason());
            result.setRejectDescription(entity.getRejectDescription());
            result.setRejectInternalComment(entity.getRejectInternalComment());
            result.setNominatedAt((entity.getNominatedAt() != null) ? new DateTime(entity.getNominatedAt()) : null);
            result.setModified(new DateTime(entity.getModified()));
            result.setCreated(new DateTime(entity.getCreated()));
        }

        return result;
    }

    public static ApplicationEntity transform(final StudentApplication application) {
        ApplicationEntity result = null;

        if (application != null) {
            result = new ApplicationEntity();

            result.setExternalId(application.getApplicationId());
            //Can't get OfferGroup from Offer, leaving empty and it has to be completed directly by OfferGroupEntity
            //result.setOffer(transform(application.getOffer()));
            result.setStudent(transform(application.getStudent()));
            result.setStatus(application.getStatus());
            result.setHomeAddress(CommonTransformer.transform(application.getHomeAddress()));
            result.setEmail(application.getEmail());
            result.setPhoneNumber(application.getPhoneNumber());
            result.setAddressDuringTerms(CommonTransformer.transform(application.getAddressDuringTerms()));
            result.setDateOfBirth(CommonTransformer.convert(application.getDateOfBirth()));
            result.setUniversity(application.getUniversity());
            result.setPlaceOfBirth(application.getPlaceOfBirth());
            result.setNationality(CommonTransformer.transform(application.getNationality()));
            result.setGender(application.getGender());
            result.setCompletedYearsOfStudy(application.getCompletedYearsOfStudy());
            result.setTotalYearsOfStudy(application.getTotalYearsOfStudy());
            result.setIsLodgingByIaeste(application.getIsLodgingByIaeste());
            result.setLanguage1(application.getLanguage1());
            result.setLanguage1Level(application.getLanguage1Level());
            result.setLanguage2(application.getLanguage2());
            result.setLanguage2Level(application.getLanguage2Level());
            result.setLanguage3(application.getLanguage3());
            result.setLanguage3Level(application.getLanguage3Level());
            result.setInternshipStart(CommonTransformer.readFromDateFromPeriod(application.getAvailable()));
            result.setInternshipEnd(CommonTransformer.readToDateFromPeriod(application.getAvailable()));
            result.setFieldOfStudies(CollectionTransformer.concatEnumCollection(application.getFieldOfStudies()));
            result.setSpecializations(CollectionTransformer.join(application.getSpecializations()));
            result.setPassportNumber(application.getPassportNumber());
            result.setPassportPlaceOfIssue(application.getPassportPlaceOfIssue());
            result.setPassportValidUntil(application.getPassportValidUntil());
            result.setRejectByEmployerReason(application.getRejectByEmployerReason());
            result.setRejectDescription(application.getRejectDescription());
            result.setRejectInternalComment(application.getRejectInternalComment());
            result.setNominatedAt((application.getNominatedAt() != null) ? application.getNominatedAt().toDate() : null);
        }

        return result;
    }

    public static PublishingGroup transform(final PublishingGroupEntity list) {
        PublishingGroup result = null;

        if (list != null) {
            result = new PublishingGroup();

            result.setName(list.getName());
            result.setPublishingGroupId(list.getExternalId());
            result.setGroups(AdministrationTransformer.transform(list.getList()));
        }

        return result;
    }

    public static PublishingGroupEntity transform(final PublishingGroup list) {
        PublishingGroupEntity result = null;

        if (list != null) {
            result = new PublishingGroupEntity();

            result.setName(list.getName());
            result.setExternalId(list.getPublishingGroupId());
            //there is no need to transform Group to GroupEntity, we have to fetch them from DB
        }

        return result;
    }
}
