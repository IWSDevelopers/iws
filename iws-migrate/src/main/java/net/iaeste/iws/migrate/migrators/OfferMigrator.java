/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.OfferMigrator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.migrators;

import static net.iaeste.iws.migrate.migrators.Helpers.convert;
import static net.iaeste.iws.migrate.migrators.Helpers.lowerAndShorten;
import static net.iaeste.iws.migrate.migrators.Helpers.round;
import static net.iaeste.iws.common.utils.StringUtils.toUpper;

import net.iaeste.iws.api.dtos.exchange.Offer;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.CollectionTransformer;
import net.iaeste.iws.core.transformers.ExchangeTransformer;
import net.iaeste.iws.migrate.daos.IWSDao;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.exchange.EmployerEntity;
import net.iaeste.iws.persistence.entities.exchange.OfferEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Note, that due to the problem with existing duplication among the IW3
 * Reference Numbers, re-running this migration will not fail, since the
 * correction logic will simply try to fix the problems.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class OfferMigrator implements Migrator<IW3OffersEntity> {

    private static final Logger log = LoggerFactory.getLogger(OfferMigrator.class);
    private static final Character REFNO_START_SERIALNUMBER = 'A';
    private static final Date SEPTEMBER_1ST_2013 = new Date(1377986400000L);

    @Autowired
    private IWSDao iwsDao;

    private final Map<Integer, GroupEntity> nationalGroups = new HashMap<>(80);

    /**
     * To try to speed up the migration, and not having to check all refno's,
     * an extract from the database was made, which can be checked against,
     * rather than making expensive DB lookups for all offers. Query is belonw:
     * <pre>
     *   select systemrefno
     *   from offers
     *   group by systemrefno having count(*) > 1
     *   order by systemrefno;
     * </pre>
     */
    private static final String duplicateRefnos =
            "BD13-0007,BD13-0008,BR11-0686,BR12-0925,BR13-1007,BY13-0026," +
            "CA10-0008,CN07-0001,CN08-0001,CN08-0002,CN08-0003,CN08-0004," +
            "CN08-0005,CN08-0006,CN08-0007,CN10-0001,CN11-0008,CN11-0035," +
            "CN11-0036,CN11-0037,CN11-0038,CN12-0041,CN12-0042,CN12-0043," +
            "CN12-0044,CN12-0045,CN12-0046,CN12-0047,CN12-0048,CN12-0049," +
            "CN12-0050,CN12-0051,CN12-0052,CN12-0053,CN12-0054,CN12-0055," +
            "CN12-0056,CN12-0057,CN12-0058,CN12-0059,CN12-0060,CN12-0061," +
            "CN12-0062,CN12-0063,CN12-0064,CN12-0065,CN12-0066,CN12-0067," +
            "CN12-0068,CN12-0069,CN12-0070,CN12-0071,CN12-0072,CN12-0073," +
            "CN12-0074,CN12-0075,CN12-0076,CN12-0077,CN12-0078,CN13-0460," +
            "CO12-0142,CO12-0234,DE12-4224,HR13-0259,IN07-0003,IN07-0004," +
            "IN10-0001,IN10-0002,IN10-0003,IN10-0004,IN10-0005,IN10-0006," +
            "IN10-0007,IN10-0008,IT12-0016,JO11-0044,JP13-0231,KR07-0001," +
            "KR07-0002,KR07-0003,KR08-0001,KR08-0002,KR09-0001,KR10-0001," +
            "KR10-0002,KR10-0003,KR13-0125,KR13-0128,KR13-0130,KR13-0132," +
            "KR13-0147,MT13-0050,NG10-0001,NG10-0002,NG10-0003,NG10-0004," +
            "NG10-0005,NG10-0006,NG10-0007,OM11-0037,PS13-0086,QA12-0010," +
            "RO13-0093,SE11-0007,TN13-0401,TN13-0404,TR12-0147,TR12-0270," +
            "TR12-0370,VN13-0066,JO14-0159";

    public OfferMigrator() {
    }

    public OfferMigrator(final IWSDao iwsDao) {
        this.iwsDao = iwsDao;
    }

    private void init() {
        if (nationalGroups.isEmpty()) {
            // We have 150.000+ records in the database, but less than 100 groups.
            // We cache all Groups so we can make a direct Id comparison rather than
            // trusting that the JPA implementation is caching things properly
            final List<GroupEntity> groups = iwsDao.findAllGroups(GroupType.NATIONAL);
            for (final GroupEntity entity : groups) {
                nationalGroups.put(entity.getOldId(), entity);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MigrationResult migrate() {
        throw new IllegalArgumentException("This Migrator is not allowed here.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = "transactionManagerIWS", propagation = Propagation.REQUIRES_NEW)
    public MigrationResult migrate(final List<IW3OffersEntity> oldEntities) {
        init();
        int persisted = 0;
        int skipped = 0;

        for (final IW3OffersEntity oldEntity : oldEntities) {
            final OfferEntity offerEntity = convertOffer(oldEntity);
            final GroupEntity groupEntity = nationalGroups.get(oldEntity.getGroupid());
            EmployerEntity employerEntity = iwsDao.findUniqueEmployer(groupEntity, offerEntity.getEmployer());

            try {
                if (employerEntity == null) {
                    employerEntity = prepareAndPersistEmployer(oldEntity, offerEntity, groupEntity);
                }
                offerEntity.setEmployer(employerEntity);
                final Offer offer = ExchangeTransformer.transform(offerEntity);
                offer.verify();
                iwsDao.persist(offerEntity);
                persisted++;
            } catch (IllegalArgumentException | VerificationException e) {
                log.error("Cannot process Offer with refno:{} => {}", offerEntity.getRefNo(), e.getMessage());
                skipped++;
            }
        }

        return new MigrationResult(persisted, skipped);
    }

    // =========================================================================
    // Internal Offer Migration Methods
    // =========================================================================

    private EmployerEntity prepareAndPersistEmployer(final IW3OffersEntity oldEntity, final OfferEntity offerEntity, final GroupEntity groupEntity) {
        final CountryEntity countryEntity = iwsDao.findCountry(oldEntity.getCountryid());

        final EmployerEntity employerEntity;
        employerEntity = offerEntity.getEmployer();
        employerEntity.getAddress().setCountry(countryEntity);
        employerEntity.setGroup(groupEntity);

        iwsDao.persist(employerEntity.getAddress());
        iwsDao.persist(employerEntity);

        return employerEntity;
    }

    private OfferEntity convertOffer(final IW3OffersEntity oldOffer) {
        final OfferEntity entity = new OfferEntity();

        entity.setRefNo(convertRefno(oldOffer.getSystemrefno(), oldOffer.getCreated()));
        entity.setOldOfferId(oldOffer.getOfferid());
        entity.setOldRefno(convert(oldOffer.getLocalrefno()));
        entity.setEmployer(convertEmployer(oldOffer));
        entity.setWorkDescription(convert(oldOffer.getWorkkind()));
        entity.setTypeOfWork(convertTypeOfWork(oldOffer));
        entity.setStudyLevels(convertStudyLevels(oldOffer));
        entity.setFieldOfStudies(convertFieldOfStudies(oldOffer));
        entity.setSpecializations(convertSpecializations(oldOffer));
        entity.setPrevTrainingRequired(convertTrainingRequired(oldOffer.getTrainingrequired()));
        entity.setOtherRequirements(oldOffer.getOtherrequirements());
        entity.setMinimumWeeks(oldOffer.getWeeksmin());
        entity.setMaximumWeeks(oldOffer.getWeeksmax());
        entity.setFromDate(selectMinDate(oldOffer.getFromdate(), oldOffer.getTodate()));
        entity.setToDate(selectMaxDate(oldOffer.getFromdate(), oldOffer.getTodate()));
        entity.setFromDate2(selectMinDate(oldOffer.getFromdate2(), oldOffer.getTodate2()));
        entity.setToDate2(selectMaxDate(oldOffer.getFromdate2(), oldOffer.getTodate2()));
        entity.setUnavailableFrom(selectMinDate(oldOffer.getHolidaysfromdate(), oldOffer.getHolidaystodate()));
        entity.setUnavailableTo(selectMaxDate(oldOffer.getHolidaysfromdate(), oldOffer.getHolidaystodate()));
        entity.setLanguage1(convertLanguage(oldOffer.getLanguage1(), Language.ENGLISH));
        entity.setLanguage1Level(convertLanguageLevel(oldOffer.getLanguage1Level(), LanguageLevel.E));
        entity.setLanguage1Operator(convertLanguageOperator(oldOffer.getLanguage1Or()));
        entity.setLanguage2(convertLanguage(oldOffer.getLanguage2(), null));
        entity.setLanguage2Level(convertLanguageLevel(oldOffer.getLanguage2Level(), null));
        entity.setLanguage2Operator(convertLanguageOperator(oldOffer.getLanguage2Or()));
        entity.setLanguage3(convertLanguage(oldOffer.getLanguage3(), null));
        entity.setLanguage3Level(convertLanguageLevel(oldOffer.getLanguage3Level(), null));
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
        entity.setExchangeYear(convertExchangeYear(oldOffer.getExchangeyear(), oldOffer.getCreated()));
        entity.setModified(convert(oldOffer.getModified()));
        entity.setCreated(convert(oldOffer.getCreated()));

        return entity;
    }

    /**
     * In IW3, there exists 25.335 Offers, for which there is 25.211 unique
     * reference numbers, with some being reused up to 4 times!
     *
     * @param systemrefno IW3 System Refno
     * @return IWS RefNo
     */
    private String convertRefno(final String systemrefno, final Date created) {
        // IW3 Refno format: CCYY-nnnn
        // IWS Refno format: CC-YYYY-nnnnnn
        final String countryCode = systemrefno.substring(0,2);
        final String year = created.after(SEPTEMBER_1ST_2013) ? "14" : systemrefno.substring(2,4);
        final String serialNumber = systemrefno.substring(5,9);
        final Character appender;
        if (duplicateRefnos.contains(systemrefno)) {
            final Long count = iwsDao.countRefNos(countryCode, year, serialNumber);
            appender = (char) (REFNO_START_SERIALNUMBER + count);
            if (!appender.equals(REFNO_START_SERIALNUMBER)) {
                log.info("Duplicate Reference number {} found, will append {} to it.", systemrefno, appender);
            }
        } else {
            appender = REFNO_START_SERIALNUMBER;
        }

        return countryCode + "-20" + year + '-' + appender + '0' + serialNumber;
    }

    private static Date selectMinDate(final Date fromdate, final Date todate) {
        final Date result;

        if ((fromdate != null) && (todate != null)) {
            result = fromdate.before(todate) ? fromdate : todate;
        } else {
            result = fromdate;
        }

        return result;
    }

    private static Date selectMaxDate(final Date fromdate, final Date todate) {
        final Date result;

        if ((fromdate != null) && (todate != null)) {
            result = fromdate.before(todate) ? todate : fromdate;
        } else {
            result = todate;
        }

        return result;
    }

    /**
     * IW3 contains several fields where the information about work type is
     * divied. In IWS, there's a single entry. So the question is how to map it
     * over, since there can be several types marked for the IW3 records.<br />
     *   The following query will yield the result from below, using the
     * Mid-November snapshot of the IW3 database, that Henrik made.
     * <pre>
     *   select
     *     count(offerid) as records,
     *     worktype,
     *     worktype_n,
     *     worktype_p,
     *     worktype_r,
     *     worktype_w
     *   from offers
     *   group by
     *     worktype,
     *     worktype_n,
     *     worktype_p,
     *     worktype_r,
     *     worktype_w;
     * </pre>
     * <br />
     * <pre>
     * records   worktype   worktype_n   worktype_p   worktype_r   worktype_w
     *    9311          x     false        true         false        false
     *       5          x     true         true         true         true
     *    1260          x     false        true         true         false
     *       4          x     true         true         false        true
     *     780          x     false        false        false        false
     *   12022          x     false        false        true         false
     *      11          x     true         false        false        true
     *     581          x     false        true         false        true
     *       5          x     true         true         true         false
     *      93          x     false        true         true         true
     *      10          x     true         true         false        false
     *     679          x     false        false        false        true
     *       7          x     true         false        true         false
     *     335          x     true         false        false        false
     *     232          x     false        false        true         true
     * </pre>
     *
     * @param oldOffer IW3 Offer
     * @return Type of Work
     */
    private static TypeOfWork convertTypeOfWork(final IW3OffersEntity oldOffer) {
        final TypeOfWork result;

        if (oldOffer.getWorktypeR()) {
            result = TypeOfWork.R;
        } else if (oldOffer.getWorktypeP()) {
            result = TypeOfWork.O;
        } else if (oldOffer.getWorktypeW()) {
            result = TypeOfWork.F;
        } else {
            result = TypeOfWork.O;
        }

        return result;
    }

    /**
     * StudyLevel is handled using a concatenated String in IWS, but in IW3, it
     * is comprised of 4 different fields.
     * <pre>
     *   select
     *     count(offerid) as records,
     *     studycompleted,
     *     studycompleted_b,
     *     studycompleted_m,
     *     studycompleted_e
     *   from offers
     *   group by
     *     studycompleted,
     *     studycompleted_b,
     *     studycompleted_m,
     *     studycompleted_e;
     * </pre>
     * Yielding the following result:
     * <pre>
     *   records  studycompleted  studycompleted_b  studycompleted_m  studycompleted_e
     *         7       NULL            true              false             true
     *        12       a               false             false             false
     *      3348       m               false             true              false
     *       672       NULL            true              true              true
     *         1       5               false             false             true
     *         1       b               false             true              false
     *      3137       NULL            false             true              false
     *        19       m               false             true              true
     *         2       b               false             false             true
     *         5       5               true              true              true
     *      6753       NULL            false             false             true
     *         3       e               false             true              true
     *       169       NULL            true              false             false
     *         2       m               true              true              false
     *       350       b               true              false             false
     *         1       b               false             true              true
     *      7778       NULL            false             true              true
     *      2584       e               false             false             true
     *       281       NULL            true              true              false
     *       209       5               false             false             false
     *         1       b               true              true              false
     * </pre>
     *
     * @param oldOffer IW3 Offer to read studylevel from
     * @return IWS StudyLevel
     */
    private static String convertStudyLevels(final IW3OffersEntity oldOffer) {
        final Set<StudyLevel> result = EnumSet.noneOf (StudyLevel.class);

        if (oldOffer.getStudycompletedB()) {
            result.add(StudyLevel.B);
        }
        if (oldOffer.getStudycompletedM()) {
            result.add(StudyLevel.M);
        }
        if (oldOffer.getStudycompletedE()) {
            result.add(StudyLevel.E);
        }

        return CollectionTransformer.concatEnumCollection(result);
    }

    /**
     * The entire list of Faculties (Fields of Study in IWS), has been mapped
     * over. The setup is based on the following SQL:
     * <pre>
     *   select
     *     count(f.facultyid) as records,
     *     upper(f.faculty)
     *   from
     *     faculties f,
     *     offers o
     *   where f.facultyid = o.facultyid
     *   group by f.faculty
     *   order by records desc;
     * </pre>
     *
     * @param oldOffer Old Offer
     * @return String value for the Fields of Study in IWS
     */
    private static String convertFieldOfStudies(final IW3OffersEntity oldOffer) {
        final String faculty = convert(oldOffer.getFaculty().getFaculty());
        final FieldOfStudy field;

        if ((faculty != null) && !faculty.isEmpty()) {
            final String toCheck = toUpper(faculty.replace(' ', '_').trim());

            switch (toCheck) {
                case "AERONAUTICAL_ENGINEERING":
                    field = FieldOfStudy.AERONAUTIC_ENGINEERING;
                    break;
                case "AGRONOMY":
                case "DAIRYING":
                case "FORESTRY":
                case "HORTICULTURE":
                    field = FieldOfStudy.AGRICULTURE;
                    break;
                case "DRAWING":
                case "GRAPHICS_&_PRINTING":
                    field = FieldOfStudy.APPLIED_ARTS;
                    break;
                case "AQUATIC_&_ENVIRONMENTAL_ENGINEERING":
                case "MARINE_BIOLOGY":
                    field = FieldOfStudy.AQUA_CULTURE;
                    break;
                case "ARCHITECTURE":
                case "LANDSCAPE_PLANNING":
                    field = FieldOfStudy.ARCHITECTURE;
                    break;
                case "BIOLOGY":
                case "ECOLOGY":
                case "MOLECULAR_BIOLOGY":
                case "ZOOLOGY":
                    field = FieldOfStudy.BIOLOGY;
                    break;
                case "BIOMEDICAL":
                    field = FieldOfStudy.BIOMEDICAL_SCIENCE;
                    break;
                case "BIOCHEMISTRY":
                case "BIOENGINEERING":
                case "BIOTECHNOLOGY":
                case "MICROBIOLOGY":
                case "PHARMACY":
                    field = FieldOfStudy.BIOTECHNOLOGY;
                    break;
                case "CHEMICAL_ENGINEERING":
                case "CHEMISTRY":
                case "PHARMACEUTICAL_STUDIES":
                case "SANITARY_ENGINEERING":
                    field = FieldOfStudy.CHEMISTRY;
                    break;
                case "CIVIL_ENGINEERING":
                case "CONSTRUCTION":
                case "CULTURE_TECHNICS-RURAL":
                case "MINING":
                case "TRAFFIC_ENGINEERING":
                    field = FieldOfStudy.CIVIL_ENGINEERING;
                    break;
                case "BUSINESS_STUDIES":
                case "COMMERCE":
                case "ECONOMICS":
                case "HOTEL_MANAGEMENT":
                case "MANAGEMENT":
                    field = FieldOfStudy.ECONOMY_AND_MANAGEMENT;
                    break;
                case "LANGUAGES":
                    field = FieldOfStudy.EDUCATION;
                    break;
                case "POWER_ENGINEERING":
                    field = FieldOfStudy.ENERGY_ENGINEERING;
                    break;
                case "ENVIRONMENTAL_ENGINEERING":
                case "ENVIRONMENTAL_PLANNING_AND_DESIGN":
                case "ENVIRONMENTAL_SCIENCE":
                case "WASTE_WATER_TREATMENT":
                    field = FieldOfStudy.ENVIRONMENTAL_ENGINEERING;
                    break;
                case "AUTOMATION":
                case "ELECTRICAL_ENGINEERING":
                case "ELECTRONICS":
                case "ELECTROTECHNICAL_ENGINEERING":
                case "MECHATRONICS":
                    field = FieldOfStudy.ELECTRICAL_ENGINEERING;
                    break;
                case "FOOD_TECHNOLOGY":
                    field = FieldOfStudy.FOOD_SCIENCE;
                    break;
                case "GEODESY":
                case "GEOGRAPHY":
                case "GEOLOGY":
                case "GEOSCIENCE":
                case "METALLURGY":
                    field = FieldOfStudy.GEOSCIENCE;
                    break;
                case "INDUSTRIAL_ENGINEERING":
                    field = FieldOfStudy.INDUSTRIAL_ENGINEERING;
                    break;
                case "COMPUTER_ENGINEERING":
                case "COMPUTER_SCIENCE":
                case "COMPUTER_SCIENCE_AND_ECONOMICS":
                case "INFORMATION_TECHNOLOGY":
                case "PROGRAMMING":
                case "SOFTWARE":
                case "TELECOMMUNICATIONS":
                    field = FieldOfStudy.IT;
                    break;
                case "MATERIAL_SCIENCE":
                case "MINERAL_PROCESSING":
                case "TEXTILE_TECHNOLOGY":
                    field = FieldOfStudy.MATERIAL_SCIENCE;
                    break;
                case "MATHEMATICS":
                case "STATISTICS":
                    field = FieldOfStudy.MATHEMATICS;
                    break;
                case "BIOMECHANICAL":
                case "MANUFACTURING_ENGINEERING":
                case "MECHANICAL_ENGINEERING":
                case "NAVAL_ARCHITECTURE":
                case "NAVAL_ENGINEERING":
                case "OFFSHORE":
                case "OIL_TECHNOLOGY":
                case "PETROLEUM_ENGINEERING":
                case "PROCESS_ENGINEERING":
                case "SHIP_BUILDING":
                case "SYSTEMS_ENGINEERING":
                case "WOOD_AND_PAPER_TECHNOLOGY":
                    field = FieldOfStudy.MECHANICAL_ENGINEERING;
                    break;
                case "BIOPHYSICS":
                case "ENGINEERING_PHYSICS":
                case "OPTICS":
                case "PHYSICS":
                    field = FieldOfStudy.PHYSICS;
                    break;
                case "VETERINARY_SCIENCE":
                    field = FieldOfStudy.VETERINARY_SCIENCE;
                    break;
                case "OTHER_ENGINEERING_&_TECHNO":
                case "OTHER_ENGINEERING_&AMP;_TECHNO":
                    field = FieldOfStudy.OTHER;
                    break;
                default:
                    log.info("The Field of Study (Faculty) {} is not mapped over.", toCheck);
                    field = FieldOfStudy.OTHER;
            }
        } else {
            field = FieldOfStudy.OTHER;
        }

        return field.name();
    }

    private static String convertSpecializations(final IW3OffersEntity oldOffer) {
        final String specialization = convert(oldOffer.getSpecialization());
        final String other = convert(oldOffer.getFacultyother());

        return  specialization + (((other != null) && !other.isEmpty()) ? ('|' + other) : "");
    }

    /**
     * In IW3, the Trainingrequired fiels was a String, in IWS it is a Boolean.
     * Converting means that we have to cover crawl through the long and boring
     * list of crapola that people can add...<br />
     *   The following query gives a hint has to what have to deal with:<br />
     * <pre>
     *   select
     *     count(a.offerid) as records,
     *     a.required
     *   from (
     *     select
     *       o.offerid,
     *       lower(left(o.trainingrequired, 4)) as required
     *     from offers o) a
     *   group by a.required;
     * </pre>
     * Note; that by using a left function call - we're only looking at the
     * first few letters, making it easier to just have a trimmed result to
     * look at.
     *
     * @param trainingrequired IW3 field for training required
     * @return True or False
     */
    private static Boolean convertTrainingRequired(final String trainingrequired) {
        // We're looking at the first 4 characters, since we need to
        // differentiate between "not " and "nothing"
        final String required = lowerAndShorten(trainingrequired, 4);
        final Boolean result;

        switch (required) {
            case "": // Empty String
            case "-":
            case "kein":
            case "no":
            case "no.":
            case "no,":
            case "not":
            case "none":
            case "nil":
            case "n/a":
            case "n.a.":
            case "n":
            case "no(s":
            case "no n":
            case "no b":
            case "on":
                result = false;
                break;
            default:
                result = true;
        }

        return result;
    }

    /**
     * Languages are a nightmare in IW3, since there exists so many different
     * ways whereby our über intelligent administrators and professors believe
     * they should be written! The Enlish language variant alone constitues 42
     * different ways of being added (and add the German offers where language
     * must be "good")! The following query can help by only looking at the
     * first few letters, and this making it easier to find patterns.<br />
     * <pre>
     *   select
     *     count(offerid) as records,
     *     language1
     *   from (
     *     select
     *       i.offerid,
     *       lower(left(i.language1, 3)) as language1
     *     from offers i) o
     *   group by language1
     *   order by records desc;
     * </pre>
     *
     * @param language        Language to map
     * @param defaultLanguage Default language if given is null
     * @return IWS Language
     */
    private static Language convertLanguage(final String language, final Language defaultLanguage) {
        final String toCheck = toUpper(language);
        final Language result;

        if ((toCheck != null) && !toCheck.isEmpty()) {
            // Seriously, we have so many language requirements... For
            // simplicity, we're defaulting to English, and just adding a large
            // enough base for the remainder to hopefully not offend too many.
            switch (toCheck) {
                // The cases are according to popularity, ignoring "good" and "excellent"
                case "DEU": // Deutsch
                case "GEM": // GE[R]MAN
                case "GER": // German
                    result = Language.GERMAN;
                    break;
                case "SPA": // Spanish
                    result = Language.SPANISH;
                    break;
                case "FRE": // French
                    result = Language.FRENCH;
                    break;
                case "POR": // Portuguese
                    result = Language.PORTUGUESE;
                    break;
                case "RUS": // Russian
                    result = Language.RUSSIAN;
                    break;
                case "ITA": // Italian
                    result = Language.ITALIAN;
                    break;
                case "POL": // Polish
                    result = Language.POLISH;
                    break;
                case "HUN": // Hungarian
                    result = Language.HUNGARIAN;
                    break;
                case "ARA": // Arabic
                    result = Language.ARABIC;
                    break;
                default:
                    result = Language.ENGLISH;
            }
        } else {
            result = defaultLanguage;
        }

        return result;
    }

    /**
     * From the IW3 source code (upl/exchange/outbox/update.upl), we can see
     * that the level is defined as follows:
     * <ol>
     *   <li>Fair</li>
     *   <li>Good</li>
     *   <li>Excellent</li>
     * </ol>
     * However, from the IW3 database we can see that 357 records have level 0
     * (zero) or -1 (minus one). Hence, we simply convert these to null!
     *
     * @param languageLevel IW3 Language Level
     * @return IWS Language Level
     */
    private static LanguageLevel convertLanguageLevel(final Integer languageLevel, final LanguageLevel defaultLevel) {
        final LanguageLevel result;

        if (languageLevel == 1) {
            result = LanguageLevel.F;
        } else if (languageLevel == 2) {
            result = LanguageLevel.G;
        } else if (languageLevel == 3) {
            result = LanguageLevel.E;
        } else {
            result = defaultLevel;
        }

        return result;
    }

    private static LanguageOperator convertLanguageOperator(final Boolean languageOr) {
        final LanguageOperator result;

        if (languageOr != null) {
            if (languageOr) {
                result = LanguageOperator.O;
            } else {
                result = LanguageOperator.A;
            }
        } else {
            result = null;
        }

        return result;
    }

    private static PaymentFrequency convertFrequency(final String frequency) {
        final PaymentFrequency result;

        switch (frequency) {
            case "d":
                result = PaymentFrequency.DAILY;
                break;
            case "w":
                result = PaymentFrequency.WEEKLY;
                break;
            case "2":
                result = PaymentFrequency.BYWEEKLY;
                break;
            case "m":
                result = PaymentFrequency.MONTHLY;
                break;
            default:
                result = PaymentFrequency.YEARLY;
        }

        return result;
    }

    /**
     * From the IW3 database, we can dig out the usage using this query:
     * <pre>
     *   select count(offerid) as records, status from offers group by status;
     * </pre>
     * Result is:
     * <pre>
     *     Records   Status
     *       23953     n
     *        1382     d
     * </pre>
     *
     * @param status IW3 Offer Status
     * @return IWS Offer Status
     */
    private static OfferState convertOfferState(final String status) {
        final OfferState result;

        if (status != null) {
            switch (status) {
                case "n":
                    result = OfferState.NEW;
                    break;
                case "d":
                    // Okay, we have some with status 'd', assuming it means that
                    // they are deleted!
                    result = OfferState.DELETED;
                    break;
                default:
                    //result = null;
                    result = OfferState.SHARED;
            }
        } else {
            result = null;
        }

        return result;
    }

    private static Integer convertExchangeYear(final Integer exchangeyear, final Date created) {
        final Integer result;

        if (exchangeyear == 1970) {
            // Switcherland is having a couple of Offers with Exchange year
            // 1970, although their reference numbers are marked as 2011
            result = 2011;
        } else if (created.after(SEPTEMBER_1ST_2013)) {
            result = 2014;
        } else {
            result = exchangeyear;
        }

        return result;
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
        log.trace("Workhours: weekly = {}, daily = {}", entity.getWeeklyHours(), entity.getDailyHours());
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
