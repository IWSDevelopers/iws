/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.ViewTransformer
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

import static net.iaeste.iws.core.transformers.EmbeddedConverter.convert;

import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.dtos.exchange.Employer;
import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.dtos.exchange.Student;
import net.iaeste.iws.api.dtos.exchange.StudentApplication;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.util.DateTime;
import net.iaeste.iws.common.utils.StringUtils;
import net.iaeste.iws.persistence.views.ApplicationView;
import net.iaeste.iws.persistence.views.AttachedFileView;
import net.iaeste.iws.persistence.views.EmbeddedAddress;
import net.iaeste.iws.persistence.views.EmbeddedCountry;
import net.iaeste.iws.persistence.views.EmbeddedEmployer;
import net.iaeste.iws.persistence.views.EmbeddedOffer;
import net.iaeste.iws.persistence.views.EmbeddedOfferGroup;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.OfferSharedToGroupView;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.SharedOfferView;
import net.iaeste.iws.persistence.views.StudentView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ViewTransformer {

    /**
     * Private Constructor, this is a utility class.
     */
    private ViewTransformer() {
    }

    /**
     * Transforms the {@link EmployerView} to an {@link Employer} DTO Object. As
     * the DTO contains several sub-objects, which again may contain certain
     * other objects, the transformer is building the entire Object Structure.
     *
     * @param view EmployerView to transform
     * @return Employer DTO Object to display externally
     */
    public static Employer transform(final EmployerView view) {
        final Country country = convert(view.getCountry());

        final Group group = convert(view.getGroup());
        group.setCountry(country);

        final Address address = convert(view.getAddress());
        address.setCountry(country);

        final Employer employer = convert(view.getEmployer());
        employer.setAddress(address);
        employer.setGroup(group);

        return employer;
    }

    /**
     * Transforms the {@link OfferView} to an {@link Offer} DTO Object. As the
     * DTO contains several sub-objects, which again may contain certain other
     * objects, the transformer is building the entire Object Structure.
     *
     * @param view OfferView to transform
     * @return Offer DTO Object to display externally
     */
    public static Offer transform(final OfferView view) {
        final Country country = convert(view.getCountry());

        final Group group = convert(view.getGroup());
        group.setCountry(country);

        final Address address = convert(view.getAddress());
        address.setCountry(country);

        final Employer employer = convert(view.getEmployer());
        employer.setAddress(address);
        employer.setGroup(group);

        final Offer offer = convert(view.getOffer());
        offer.setNsFirstname(view.getNsFirstname());
        offer.setNsLastname(view.getNsLastname());
        offer.setEmployer(employer);

        return offer;
    }

    /**
     * Transforms the {@link OfferView} to an {@link Offer} DTO Object. As the
     * DTO contains several sub-objects, which again may contain certain other
     * objects, the transformer is building the entire Object Structure.
     *
     * @param view OfferView to transform
     * @return Offer DTO Object to display externally
     */
    public static Offer transform(final SharedOfferView view) {
        final Country country = convert(view.getCountry());

        final Group group = convert(view.getGroup());
        group.setCountry(country);

        final Address address = convert(view.getAddress());
        address.setCountry(country);

        final Employer employer = convert(view.getEmployer());
        employer.setAddress(address);
        employer.setGroup(group);

        final Offer offer = convert(view.getOffer());
        offer.setNsFirstname(view.getNsFirstname());
        offer.setNsLastname(view.getNsLastname());
        offer.setEmployer(employer);

        // Shared offers are per definition never from yourself, so this value
        // is *always* set to null, to avoid exposure
        offer.setPrivateComment(null);

        // Showing the OfferGroup status rather than the real Offer Status
        offer.setStatus(view.getOfferGroup().getStatus());

        offer.setHidden(view.getOfferGroup().getHidden());
        offer.setShared(new DateTime(view.getOfferGroup().getCreated()));

        return offer;
    }

    /**
     * Transforms the {@link StudentView} to an {@link Student} DTO Object. As
     * the DTO contains several sub-objects, which again may contain certain
     * other objects, the transformer is building the entire Object Structure.
     *
     * @param view StudentView to transform
     * @return Student DTO Object to display externally
     */
    public static Student transform(final StudentView view) {
        final Student student = convert(view.getStudent());

        final User user = convert(view.getUser());
        student.setUser(user);

        return student;
    }

    /**
     * Transforms the {@link net.iaeste.iws.persistence.views.ApplicationView}
     * to an {@link net.iaeste.iws.api.dtos.exchange.StudentApplication} DTO Object.
     * As the DTO contains several sub-objects, which again may contain certain
     * other objects, the transformer is building the entire Object Structure.
     *
     * @param view ApplicationView to transform
     * @return StudentApplication DTO Object to display externally
     */
    public static StudentApplication transform(final ApplicationView view) {
        final StudentApplication application = convert(view.getApplication());
        application.setOfferId(view.getOfferExternalId());
        application.setOfferState(view.getOfferState());

        final Address homeAddress = convert(view.getHomeAddress());
        application.setHomeAddress(homeAddress);

        final Address termsAddress = convert(view.getTermsAddress());
        application.setAddressDuringTerms(termsAddress);

        final Country nationality = convert(view.getNationality());
        if (nationality != null) {
            application.setNationality(nationality);
        }

        final Student student = convert(view.getStudent());
        final User user = convert(view.getUser());
        student.setUser(user);
        application.setStudent(student);

        return application;
    }

    public static Group transform(final OfferSharedToGroupView view) {
        final Country country = convert(view.getCountry());
        final Group group = convert(view.getGroup());
        group.setCountry(country);

        return group;
    }

    /**
     * Transforms the File from the Attached File View to a File DTO Object.
     * Note, that the File Data is omitted, since it is stored in the file
     * system, and should only be fetched if explicitly requested.
     *
     * @param view AttachedFileView to transform
     * @return File DTO
     */
    public static File transform(final AttachedFileView view) {
        final File file = new File();

        file.setFileId(view.getFile().getExternalId());
        file.setGroup(convert(view.getGroup()));
        file.setUser(convert(view.getUser()));
        file.setFilename(view.getFile().getFileName());
        file.setFilesize(view.getFile().getSize());
        file.setMimetype(view.getFile().getMimeType());
        file.setDescription(view.getFile().getDescription());
        file.setKeywords(view.getFile().getKeywords());
        file.setChecksum(view.getFile().getChecksum());
        file.setModified(CommonTransformer.convert(view.getFile().getModified()));
        file.setCreated(CommonTransformer.convert(view.getFile().getCreated()));

        return file;
    }

    /**
     * Transforms the {@link SharedOfferView} to an {@link java.util.List <Object>} for CSVPrinter.
     *
     * @param view SharedOfferView to transform
     * @return List of offer objects to be exported to CSV
     */
    public static List<Object> transformToStringList(final SharedOfferView view) {
        //use following?
        //public static List<String> transformToStringList(final Class<List> list, final SharedOfferView view) {
        final List<Object> result = new ArrayList<>();

        final EmbeddedOffer embeddedOffer = view.getOffer() != null ? view.getOffer() : new EmbeddedOffer();
        final EmbeddedEmployer embeddedEmployer = view.getEmployer() != null ? view.getEmployer() : new EmbeddedEmployer();
        final EmbeddedAddress embeddedAddress = view.getAddress() != null ? view.getAddress() : new EmbeddedAddress();
        final EmbeddedCountry embeddedCountry = view.getCountry() != null ? view.getCountry() : new EmbeddedCountry();
        final EmbeddedOfferGroup embeddedOfferGroup = view.getOfferGroup() != null ? view.getOfferGroup() : new EmbeddedOfferGroup();

        final String exportedRefNo = embeddedOffer.getOldRefNo() != null ? embeddedOffer.getOldRefNo() : embeddedOffer.getRefNo();

        result.add(exportedRefNo);
        result.add(embeddedOffer.getNominationDeadline());
        result.add(null);
        result.add(embeddedEmployer.getName());
        result.add(embeddedAddress.getStreet1());
        result.add(embeddedAddress.getStreet2());
        result.add(embeddedAddress.getPobox());
        result.add(embeddedAddress.getPostalCode());
        result.add(embeddedAddress.getCity());
        result.add(embeddedAddress.getState());
        result.add(embeddedCountry.getCountryName());
        result.add(embeddedEmployer.getWebsite());
        result.add(embeddedEmployer.getWorkingPlace());
        result.add(embeddedEmployer.getBusiness());
        result.add(null);
        result.add(embeddedEmployer.getNearestPublicTransport());
        result.add(embeddedEmployer.getNearestPublicTransport());
        result.add(embeddedEmployer.getNumberOfEmployees());
        result.add(embeddedOffer.getWeeklyHours());
        result.add(embeddedOffer.getDailyHours());
        result.add(CommonTransformer.convertToYesNo(embeddedEmployer.getCanteen()));

        String fieldOfStudiesString = "";
        for (FieldOfStudy fieldOfStudy : CollectionTransformer.explodeEnumSet(FieldOfStudy.class, embeddedOffer.getFieldOfStudies())) {
            if (fieldOfStudiesString.isEmpty()) {
                fieldOfStudiesString = fieldOfStudy.getDescription();
            } else {
                fieldOfStudiesString = fieldOfStudiesString + ", " + fieldOfStudy.getDescription();
            }
        }
        result.add(fieldOfStudiesString);


        String specializationsString = "";
        for (String spe : CollectionTransformer.explodeStringSet(embeddedOffer.getSpecializations())) {
            String toPut = spe.charAt(0) + spe.toLowerCase().replace('_', ' ').substring(1);
            if (!specializationsString.isEmpty()) {
                specializationsString = specializationsString + ", " + toPut;
            } else {
                specializationsString = toPut;
            }
        }
        result.add(specializationsString);

        result.add(CommonTransformer.convertToYesNo(embeddedOffer.getPrevTrainingRequired()));
        result.add(StringUtils.removeLineBreaks(embeddedOffer.getOtherRequirements()));
        result.add(StringUtils.removeLineBreaks(embeddedOffer.getWorkDescription()));
        result.add(embeddedOffer.getMinimumWeeks());
        result.add(embeddedOffer.getMaximumWeeks());
        result.add(embeddedOffer.getFromDate());
        result.add(embeddedOffer.getToDate());

        boolean studyBeginning = false;
        boolean studyMiddle = false;
        boolean studyEnd = false;

        for (StudyLevel sl : CollectionTransformer.explodeEnumSet(StudyLevel.class, embeddedOffer.getStudyLevels())) {
            switch(sl) {
                case B:studyBeginning = true; break;
                case M:studyMiddle = true; break;
                case E:studyEnd = true; break;
            }
        }
        result.add(CommonTransformer.convertToYesNo(studyBeginning));
        result.add(CommonTransformer.convertToYesNo(studyMiddle));
        result.add(CommonTransformer.convertToYesNo(studyEnd));

        if (TypeOfWork.R.equals(embeddedOffer.getTypeOfWork())) {
            result.add(CommonTransformer.convertToYesNo(true));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
        } else if (TypeOfWork.O.equals(embeddedOffer.getTypeOfWork())) {
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(true));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
        } else if (TypeOfWork.F.equals(embeddedOffer.getTypeOfWork())) {
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(true));
            result.add(CommonTransformer.convertToYesNo(false));
        } else {
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
        }

        result.add(embeddedOffer.getLanguage1() != null ? embeddedOffer.getLanguage1().getDescription() : null);
        result.add(embeddedOffer.getLanguage1Level() != null ? embeddedOffer.getLanguage1Level().getDescription() : null);
        result.add(embeddedOffer.getLanguage1Operator() != null ? embeddedOffer.getLanguage1Operator().getDescription() : null);

        result.add(embeddedOffer.getLanguage2() != null ? embeddedOffer.getLanguage2().getDescription() : null);
        result.add(embeddedOffer.getLanguage2Level() != null ? embeddedOffer.getLanguage2Level().getDescription() : null);
        result.add(embeddedOffer.getLanguage2Operator() != null ? embeddedOffer.getLanguage2Operator().getDescription() : null);

        result.add(embeddedOffer.getLanguage3() != null ? embeddedOffer.getLanguage3().getDescription() : null);
        result.add(embeddedOffer.getLanguage3Level() != null ? embeddedOffer.getLanguage3Level().getDescription() : null);

        result.add(embeddedCountry.getCurrency() != null ? embeddedCountry.getCurrency().getDescription() : null);
        result.add(embeddedOffer.getPayment());
        result.add(embeddedOffer.getPaymentFrequency() != null ? embeddedOffer.getPaymentFrequency().getDescription() : null);
        result.add(embeddedOffer.getDeduction());
        result.add(embeddedOffer.getLodgingBy());
        result.add(embeddedOffer.getLodgingCost());
        result.add(embeddedOffer.getLodgingCostFrequency() != null ? embeddedOffer.getLodgingCostFrequency().getDescription() : null);
        result.add(embeddedOffer.getLivingCost());
        result.add(embeddedOffer.getLivingCostFrequency() != null ? embeddedOffer.getLivingCostFrequency().getDescription() : null);
        result.add(embeddedOffer.getNumberOfHardCopies());
        result.add(embeddedOfferGroup.getStatus() != null ? embeddedOfferGroup.getStatus().getDescription() : null);


        result.add(embeddedOffer.getFromDate2());
        result.add(embeddedOffer.getToDate2());

        result.add(embeddedOffer.getUnavailableFrom());
        result.add(embeddedOffer.getUnavailableTo());

        result.add(StringUtils.removeLineBreaks(embeddedOffer.getAdditionalInformation()));

        result.add(embeddedOfferGroup.getCreated());

        result.add(embeddedOffer.getModified());
        result.add(view.getNsFirstname());
        result.add(view.getNsLastname());

        result.add(embeddedCountry.getCountryName());

        return result;
    }

    /**
     * Transforms the {@link OfferView} to an {@link java.util.List <Object>} for CSVPrinter.
     *
     * @param view OfferView to transform
     * @return List of offer objects to be exported to CSV
     */
    public static List<Object> transformToStringList(final OfferView view) {
        //use following?
        //public static List<String> transformToStringList(final Class<List> list, final SharedOfferView view) {
        final List<Object> result = new ArrayList<>();

        final EmbeddedOffer embeddedOffer = view.getOffer() != null ? view.getOffer() : new EmbeddedOffer();
        final EmbeddedEmployer embeddedEmployer = view.getEmployer() != null ? view.getEmployer() : new EmbeddedEmployer();
        final EmbeddedAddress embeddedAddress = view.getAddress() != null ? view.getAddress() : new EmbeddedAddress();
        final EmbeddedCountry embeddedCountry = view.getCountry() != null ? view.getCountry() : new EmbeddedCountry();

        final String exportedRefNo = embeddedOffer.getOldRefNo() != null ? embeddedOffer.getOldRefNo() : embeddedOffer.getRefNo();

        result.add(exportedRefNo);
        result.add(embeddedOffer.getNominationDeadline());
        result.add(embeddedOffer.getPrivateComment());
        result.add(embeddedEmployer.getName());
        result.add(embeddedAddress.getStreet1());
        result.add(embeddedAddress.getStreet2());
        result.add(embeddedAddress.getPobox());
        result.add(embeddedAddress.getPostalCode());
        result.add(embeddedAddress.getCity());
        result.add(embeddedAddress.getState());
        result.add(embeddedCountry.getCountryName());
        result.add(embeddedEmployer.getWebsite());
        result.add(embeddedEmployer.getWorkingPlace());
        result.add(embeddedEmployer.getBusiness());
        result.add(null);
        result.add(embeddedEmployer.getNearestPublicTransport());
        result.add(embeddedEmployer.getNearestPublicTransport());
        result.add(embeddedEmployer.getNumberOfEmployees());
        result.add(embeddedOffer.getWeeklyHours());
        result.add(embeddedOffer.getDailyHours());
        result.add(CommonTransformer.convertToYesNo(embeddedEmployer.getCanteen()));

        String fieldOfStudiesString = "";
        for (FieldOfStudy fieldOfStudy : CollectionTransformer.explodeEnumSet(FieldOfStudy.class, embeddedOffer.getFieldOfStudies())) {
            if (fieldOfStudiesString.isEmpty()) {
                fieldOfStudiesString = fieldOfStudy.getDescription();
            } else {
                fieldOfStudiesString = fieldOfStudiesString + ", " + fieldOfStudy.getDescription();
            }
        }
        result.add(fieldOfStudiesString);


        String specializationsString = "";
        for (String spe : CollectionTransformer.explodeStringSet(embeddedOffer.getSpecializations())) {
            String toPut = spe.charAt(0) + spe.toLowerCase().replace('_', ' ').substring(1);
            if (!specializationsString.isEmpty()) {
                specializationsString = specializationsString + ", " + toPut;
            } else {
                specializationsString = toPut;
            }
        }
        result.add(specializationsString);

        result.add(CommonTransformer.convertToYesNo(embeddedOffer.getPrevTrainingRequired()));
        result.add(StringUtils.removeLineBreaks(embeddedOffer.getOtherRequirements()));
        result.add(StringUtils.removeLineBreaks(embeddedOffer.getWorkDescription()));
        result.add(embeddedOffer.getMinimumWeeks());
        result.add(embeddedOffer.getMaximumWeeks());
        result.add(embeddedOffer.getFromDate());
        result.add(embeddedOffer.getToDate());

        boolean studyBeginning = false;
        boolean studyMiddle = false;
        boolean studyEnd = false;

        for (StudyLevel sl : CollectionTransformer.explodeEnumSet(StudyLevel.class, embeddedOffer.getStudyLevels())) {
            switch(sl) {
                case B:studyBeginning = true; break;
                case M:studyMiddle = true; break;
                case E:studyEnd = true; break;
            }
        }
        result.add(CommonTransformer.convertToYesNo(studyBeginning));
        result.add(CommonTransformer.convertToYesNo(studyMiddle));
        result.add(CommonTransformer.convertToYesNo(studyEnd));

        if (TypeOfWork.R.equals(embeddedOffer.getTypeOfWork())) {
            result.add(CommonTransformer.convertToYesNo(true));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
        } else if (TypeOfWork.O.equals(embeddedOffer.getTypeOfWork())) {
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(true));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
        } else if (TypeOfWork.F.equals(embeddedOffer.getTypeOfWork())) {
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(true));
            result.add(CommonTransformer.convertToYesNo(false));
        } else {
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
            result.add(CommonTransformer.convertToYesNo(false));
        }

        result.add(embeddedOffer.getLanguage1() != null ? embeddedOffer.getLanguage1().getDescription() : null);
        result.add(embeddedOffer.getLanguage1Level() != null ? embeddedOffer.getLanguage1Level().getDescription() : null);
        result.add(embeddedOffer.getLanguage1Operator() != null ? embeddedOffer.getLanguage1Operator().getDescription() : null);

        result.add(embeddedOffer.getLanguage2() != null ? embeddedOffer.getLanguage2().getDescription() : null);
        result.add(embeddedOffer.getLanguage2Level() != null ? embeddedOffer.getLanguage2Level().getDescription() : null);
        result.add(embeddedOffer.getLanguage2Operator() != null ? embeddedOffer.getLanguage2Operator().getDescription() : null);

        result.add(embeddedOffer.getLanguage3() != null ? embeddedOffer.getLanguage3().getDescription() : null);
        result.add(embeddedOffer.getLanguage3Level() != null ? embeddedOffer.getLanguage3Level().getDescription() : null);

        result.add(embeddedCountry.getCurrency() != null ? embeddedCountry.getCurrency().getDescription() : null);
        result.add(embeddedOffer.getPayment());
        result.add(embeddedOffer.getPaymentFrequency() != null ? embeddedOffer.getPaymentFrequency().getDescription() : null);
        result.add(embeddedOffer.getDeduction());
        result.add(embeddedOffer.getLodgingBy());
        result.add(embeddedOffer.getLodgingCost());
        result.add(embeddedOffer.getLodgingCostFrequency() != null ? embeddedOffer.getLodgingCostFrequency().getDescription() : null);
        result.add(embeddedOffer.getLivingCost());
        result.add(embeddedOffer.getLivingCostFrequency() != null ? embeddedOffer.getLivingCostFrequency().getDescription() : null);
        result.add(embeddedOffer.getNumberOfHardCopies());
        result.add(embeddedOffer.getStatus() != null ? embeddedOffer.getStatus().getDescription() : null);


        result.add(embeddedOffer.getFromDate2());
        result.add(embeddedOffer.getToDate2());

        result.add(embeddedOffer.getUnavailableFrom());
        result.add(embeddedOffer.getUnavailableTo());

        result.add(StringUtils.removeLineBreaks(embeddedOffer.getAdditionalInformation()));

        result.add(null);

        result.add(embeddedOffer.getModified());
        result.add(view.getNsFirstname());
        result.add(view.getNsLastname());

        result.add(embeddedCountry.getCountryName());

        return result;
    }
}
