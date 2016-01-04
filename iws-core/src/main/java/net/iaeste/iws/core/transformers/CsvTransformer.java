/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.CsvTransformer
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

import static net.iaeste.iws.api.enums.exchange.OfferFields.STUDY_COMPLETED_BEGINNING;
import static net.iaeste.iws.api.enums.exchange.OfferFields.STUDY_COMPLETED_END;
import static net.iaeste.iws.api.enums.exchange.OfferFields.STUDY_COMPLETED_MIDDLE;
import static net.iaeste.iws.common.utils.StringUtils.toLower;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.enums.Descriptable;
import net.iaeste.iws.api.enums.exchange.OfferFields;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.EnumUtil;
import net.iaeste.iws.api.util.Verifiable;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * CSV Transformer.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class CsvTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(CsvTransformer.class);

    private static final Pattern PATTERN_UNWANTED_CHARACTERS = Pattern.compile("[_\\t\\r\\n\\u00a0]");

    /**
     * Private Constructor, this is a utility class.
     */
    private CsvTransformer() {
    }

    public static void transformString(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        invokeMethodOnObject(errors, obj, field, record.get(field.getField()));
    }

    public static void transformBoolean(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            invokeMethodOnObject(errors, obj, field, convertBoolean(value));
        }
    }

    public static void transformInteger(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final Integer number = Integer.parseInt(value);
                invokeMethodOnObject(errors, obj, field, number);
            } catch (NumberFormatException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    public static void transformBigDecimal(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final BigDecimal number = new BigDecimal(value);
                invokeMethodOnObject(errors, obj, field, number);
            } catch (NumberFormatException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    public static void transformFloat(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final Float number = Float.parseFloat(value);
                invokeMethodOnObject(errors, obj, field, number);
            } catch (NumberFormatException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    public static void transformDate(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final Date date = convertDate(errors, field, record);
        invokeMethodOnObject(errors, obj, field, date);
    }

    public static void transformDatePeriod(final Map<String, String> errors, final Verifiable obj, final OfferFields startField, final OfferFields endField, final CSVRecord record) {
        final Date from = convertDate(errors, startField, record);
        final Date to = convertDate(errors, endField, record);

        if ((from != null) && (to != null)) {
            try {
                final DatePeriod period = new DatePeriod(from, to);
                invokeMethodOnObject(errors, obj, startField, period);
            } catch (IllegalArgumentException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(startField.getField(), e.getMessage());
            }
        }
    }

    public static <T extends Enum<T>> void transformEnum(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record, final Class<T> enumType) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final T theEnum = Enum.valueOf(enumType, value.toUpperCase(IWSConstants.DEFAULT_LOCALE));
                invokeMethodOnObject(errors, obj, field, theEnum);
            } catch (IllegalArgumentException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    public static <E extends Enum<E> & Descriptable<E>> void transformDescriptableEnumSet(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record, final Class<E> enumType) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final Set<E> set = CollectionTransformer.explodeEnumSet(enumType, value);
                invokeMethodOnObject(errors, obj, field, set);
            } catch (IllegalArgumentException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    public static <E extends Enum<E> & Descriptable<E>> void transformDescriptableEnum(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record, final Class<E> enumType) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final E theEnum = EnumUtil.valueOf(enumType, value);
                invokeMethodOnObject(errors, obj, field, theEnum);
            } catch (IllegalArgumentException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    public static void transformStringSet(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());
        final String parsedValue = PATTERN_UNWANTED_CHARACTERS.matcher(value).replaceAll(" ").trim();

        final Set<String> set = CollectionTransformer.explodeStringSet(parsedValue);
        invokeMethodOnObject(errors, obj, field, set);
    }

    public static void transformTypeOfWork(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final Boolean typeR = convertBoolean(record.get(OfferFields.WORK_TYPE_P.getField()));
        final Boolean typeO = convertBoolean(record.get(OfferFields.WORK_TYPE_R.getField()));
        final Boolean typeF = convertBoolean(record.get(OfferFields.WORK_TYPE_W.getField()));

        if (convertBoolean(record.get(OfferFields.WORK_TYPE_N.getField()))) {
            LOG.info("Ignoring the TypeOfWork 'N'.");
        }

        final int sum = (typeR ? 1 : 0) + (typeO ? 1 : 0) + (typeF ? 1 : 0);
        if (sum > 1) {
            errors.put(field.getField(), "Multiple TypeOfWork is set, only one is allowed.");
        } else if (sum == 0) {
            errors.put(field.getField(), "No TypeOfWork defined.");
        } else {
            TypeOfWork value = null;
            if (typeR) {
                value = TypeOfWork.R;
            } else if (typeO) {
                value = TypeOfWork.O;
            } else if (typeF) {
                value = TypeOfWork.F;
            }

            invokeMethodOnObject(errors, obj, field, value);
        }
    }

    public static void transformStudyLevels(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final Set<StudyLevel> value = EnumSet.noneOf(StudyLevel.class);
        final Boolean beginning = convertBoolean(record.get(STUDY_COMPLETED_BEGINNING.getField()));
        final Boolean middle = convertBoolean(record.get(STUDY_COMPLETED_MIDDLE.getField()));
        final Boolean end = convertBoolean(record.get(STUDY_COMPLETED_END.getField()));

        if (!beginning && !middle && !end) {
            errors.put(field.getField(), "No StudyLevel defined.");
        } else {
            if (beginning) {
                value.add(StudyLevel.B);
            }
            if (middle) {
                value.add(StudyLevel.M);
            }
            if (end) {
                value.add(StudyLevel.E);
            }
        }

        invokeMethodOnObject(errors, obj, field, value);
    }

    // =========================================================================
    // Common internal functionality
    // =========================================================================

    private static Boolean convertBoolean(final String value) {
        return "yes".equals(toLower(value));
    }

    private static Date convertDate(final Map<String, String> errors, final OfferFields field, final CSVRecord record) throws IllegalArgumentException {
        final String value = record.get(field.getField());
        Date result = null;

        if ((value != null) && !value.isEmpty()) {
            final DateFormat formatter = new SimpleDateFormat(IWSConstants.DATE_FORMAT, IWSConstants.DEFAULT_LOCALE);
            try {
                result = new Date(formatter.parse(value));
            } catch (ParseException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }

        return result;
    }

    /**
     * Reflective invocation of the Object Setter methods. To enforce the Setter
     * Validation checks on the values. Required to avoid that illegal values is
     * being processed.<br />
     *   The method will also catch any thrown IllegalArgument Exceptions and
     * add the result to the error map given.
     *
     * @param errors Validation Error Map
     * @param obj    The Object to invoke the Setter on
     * @param field  The Object field to be set
     * @param args   Arguments to the Setter
     * @throws IWSException If a Reflection Error occurred.
     */
    private static <O extends Verifiable> void invokeMethodOnObject(final Map<String, String> errors, final O obj, final OfferFields field, final Object... args) throws IWSException {
        if ((field.getMethod() != null) && field.useField(OfferFields.Type.DOMESTIC)) {
            try {
                final Method implementation = obj.getClass().getMethod(field.getMethod(), field.getArgumentClasses());
                implementation.invoke(obj, args);
            } catch (IllegalArgumentException e) {
                LOG.debug("Setter {} Invocation Error: {}", field.getMethod(), e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            } catch (SecurityException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                // The Reflection framework forces a check for the NoSuchMethod
                // and InvocationTarget Exceptions. Additionally, if the Java
                // Security Manager is used, we may also see a SecurityException
                LOG.error(e.getMessage(), e);
                throw new IWSException(IWSErrors.FATAL, e.getMessage(), e);
            }
        } else {
            LOG.warn("Cannot set field {}, as there is no method associated with it.", field.getField());
        }
    }
}
