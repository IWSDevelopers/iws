/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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

import static net.iaeste.iws.common.utils.StringUtils.toLower;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.constants.exchange.IWSExchangeConstants;
import net.iaeste.iws.api.enums.exchange.OfferFields;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.api.util.Verifiable;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * CSV Transformer.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class CsvTransformer {

    private static final Logger log = LoggerFactory.getLogger(CsvTransformer.class);

    /**
     * Private Constructor, this is a utility class.
     */
    private CsvTransformer() {
    }

    public static Float toFloat(final Map<String, String> errors, final CSVRecord record, final OfferFields field) {
        final String input = record.get(field.getField());
        Float result = null;

        if (input != null && !input.isEmpty()) {
            try {
                result = Float.parseFloat(input);
            } catch (NumberFormatException e) {
                errors.put(field.getField(), e.getMessage());
            }
        }

        return result;
    }

    public static Integer toInteger(final Map<String, String> errors, final CSVRecord record, final OfferFields field) {
        final String input = record.get(field.getField());
        Integer result = null;

        if (input != null && !input.isEmpty()) {
            try {
                result = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                errors.put(field.getField(), e.getMessage());
            }
        }

        return result;
    }

    public static BigDecimal toBigDecimal(final Map<String, String> errors, final CSVRecord record, final OfferFields field) {
        final String input = record.get(field.getField());
        BigDecimal result = null;

        if (input != null && !input.isEmpty()) {
            try {
                result = new BigDecimal(input);
            } catch (NumberFormatException e) {
                errors.put(field.getField(), e.getMessage());
            }
        }

        return result;
    }

    public static DatePeriod toDatePeriod(final Map<String, String> errors, final CSVRecord record, final OfferFields inputFrom, final OfferFields inputTo) {
        final Date from = toDate(errors, record, inputFrom);
        final Date to = toDate(errors, record, inputTo);
        DatePeriod result = null;

        if ((from != null) || (to != null)) {
            try {
                result = new DatePeriod();
                result.setFromDate(from);
                result.setToDate(to);
            } catch (IllegalArgumentException e) {
                errors.put(inputFrom.getField() + '/' + inputTo.getField(), e.getMessage());
            }
        }

        return result;
    }

    public static Date toDate(final Map<String, String> errors, final CSVRecord record, final OfferFields field) {
        final String input = record.get(field.getField());
        Date result = null;

        if (input != null && !input.isEmpty()) {
            try {
                result = new Date(IWSConstants.FORMATTER.parse(input));
            } catch (ParseException e) {
                errors.put(field.getField(), e.getMessage());
            }
        }

        return result;
    }

    public static Boolean toBoolean(final Map<String, String> errors, final CSVRecord record, final OfferFields field) {
        final String input = record.get(field.getField());
        Boolean result = null;

        if (input != null && !input.isEmpty()) {
            result = "yes".equalsIgnoreCase(input);
        }

        return result;
    }

    public static <T extends Enum<T>> T toEnum(final Map<String, String> errors, final CSVRecord record, final OfferFields field, final Class<T> enumType) {
        final String input = record.get(field.getField());
        T result = null;

        if (input != null && !input.isEmpty()) {
            try {
                result = Enum.valueOf(enumType, input);
            } catch (IllegalArgumentException e) {
                errors.put(field.getField(), e.getMessage());
            }
        }

        return result;
    }

    public static TypeOfWork toTypeOfWork(final Map<String, String> errors, final String field, final String inputP, final String inputR, final String inputW, final String inputN) {
        TypeOfWork result = null;
        Boolean typeR = "yes".equalsIgnoreCase(inputP);
        Boolean typeO = "yes".equalsIgnoreCase(inputR);
        Boolean typeF = "yes".equalsIgnoreCase(inputW);
        //inputN is ignored

        int sum = (typeR ? 1 : 0) + (typeO ? 1 : 0) + (typeF ? 1 : 0);

        if (sum > 1) {
            //error
        } else if (typeR) {
            result = TypeOfWork.R;
        } else if (typeO) {
            result = TypeOfWork.O;
        } else if (typeF) {
            result = TypeOfWork.F;
        }

        return result;
    }

    public static Set<StudyLevel> toStudyLevels(final Map<String, String> errors, final String field, final String inputBeginning, final String inputMiddle, final String inputEnd) {
        Set<StudyLevel> result = new HashSet<>();
        Boolean levelBeginning = "yes".equalsIgnoreCase(inputBeginning);
        Boolean levelMiddle = "yes".equalsIgnoreCase(inputMiddle);
        Boolean levelEnd = "yes".equalsIgnoreCase(inputEnd);

        if (levelBeginning) {
            result.add(StudyLevel.B);
        }
        if (levelMiddle) {
            result.add(StudyLevel.M);
        }
        if (levelEnd) {
            result.add(StudyLevel.E);
        }

        return result;
    }

    public static <T extends Enum<T>> Set<T> toEnumSet(final Map<String, String> errors, final CSVRecord record, final OfferFields field, final Class<T> enumType) {
        final String input = record.get(field.getField());
        Set<T> result = null;

        try {
            result = CollectionTransformer.explodeEnumSet(enumType, input);
        } catch (IllegalArgumentException e) {
            errors.put(field.getField(), e.getMessage());
        }

        return result;
    }

    public static Set<String> toStringSet(final Map<String, String> errors, final CSVRecord record, final OfferFields field) {
        Set<String> result = CollectionTransformer.explodeStringSet(record.get(field.getField()));

        // Note, change was made as part of ticket #966. However, this method
        // is *not* suppose to make error handling, this is done via the setter.
        // So the construction needs some refactoring. Below, is an example of
        // how it can be done better, using the information we have in the
        // OfferFields enum type, and using a reflective invocation of the
        // Object setter to invoke the error checks.
        //   Having the error check here is disturbing, but can be conidered an
        // extra safeguard. What would be better, is of the CSV from Korea that
        // caused the problem could be found. It will enlighten precisely how
        // it was possible to circumvent the IWS safeguards.
        if (field == OfferFields.SPECIALIZATION) {
            final int max = IWSExchangeConstants.MAX_OFFER_SPECIALIZATIONS;
            if (result.size() > max) {
                errors.put(field.getField(), "The field specializations may not containt more than " + max + " Objects.");
            }
        }

        return result;
    }

    // =========================================================================
    // Improved version of the converting of CSV to Verifiable Object fields
    // =========================================================================

    public static void transformString(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        invokeMethodOnObject(errors, obj, field, record.get(field));
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
                errors.put(startField.getField(), e.getMessage());
            }
        }
    }

    public static <T extends Enum<T>> void transformEnum(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record, final Class<T> enumType) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final T theEnum = Enum.valueOf(enumType, value);
                invokeMethodOnObject(errors, obj, field, theEnum);
            } catch (IllegalArgumentException e) {
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    public static <T extends Enum<T>> void transformEnumSet(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record, final Class<T> enumType) {
        final String value = record.get(field.getField());

        try {
            Set<T> set = CollectionTransformer.explodeEnumSet(enumType, value);
            invokeMethodOnObject(errors, obj, field, set);
        } catch (IllegalArgumentException e) {
            errors.put(field.getField(), e.getMessage());
        }
    }

    public static void transformStringSet(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        Set<String> set = CollectionTransformer.explodeStringSet(record.get(field));
        invokeMethodOnObject(errors, obj, field, set);
    }

    public static void transformTypeOfWork(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final String inputP, final String inputR, final String inputW, final String inputN) {
        Boolean typeR = convertBoolean(inputP);
        Boolean typeO = convertBoolean(inputR);
        Boolean typeF = convertBoolean(inputW);

        if (convertBoolean(inputN)) {
            log.info("Ignoring the TypeOfWork 'N'.");
        }

        if (((typeR ? 1 : 0) + (typeO ? 1 : 0) + (typeF ? 1 : 0)) > 1) {
            errors.put(field.getField(), "Multiple TypeOfWork is set, only one is allowed.");
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

    public static void transformStudyLevels(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final String inputBeginning, final String inputMiddle, final String inputEnd) {
        Set<StudyLevel> value = EnumSet.noneOf(StudyLevel.class);

        if ("yes".equals(toLower(inputBeginning))) {
            value.add(StudyLevel.B);
        }
        if ("yes".equals(toLower(inputMiddle))) {
            value.add(StudyLevel.M);
        }
        if ("yes".equals(toLower(inputEnd))) {
            value.add(StudyLevel.E);
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
            try {
                result = new Date(IWSConstants.FORMATTER.parse(value));
            } catch (ParseException e) {
                errors.put(field.getField(), e.getMessage());
            }
        }

        return result;
    }

    /**
     * Reflective invocation of the Object Setter methods. To enfoce the Setter
     * Validation checks on the values. Required to avoid that illegal values is
     * being processed.<br />
     *   The method will also catch any thrown IllegalArgument Exceptions and
     * add the result to the error map given.
     *
     * @param errors Validation Error Map
     * @param obj    The Object to invoke the Setter on
     * @param field  The Object field to be set
     * @param args   Arguments to the Setter
     * @throws net.iaeste.iws.api.exceptions.IWSException If a Reflection Error occurred.
     */
    private static <O extends Verifiable> void invokeMethodOnObject(final Map<String, String> errors, final O obj, final OfferFields field, final Object... args) throws IWSException {
        if (field.getMethod() != null) {
            try {
                final Method implementation = obj.getClass().getMethod(field.getField());
                implementation.invoke(obj, args);
            } catch (IllegalArgumentException e) {
                errors.put(field.getField(), e.getMessage());
            } catch (SecurityException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                // The Reflection framework forces a check for the NoSuchMethod
                // and InvocationTarget Exceptions. Additionally, if the Java
                // Security Manager is used, we may also see a SecurityException
                log.error(e.getMessage(), e);
                throw new IWSException(IWSErrors.FATAL, e.getMessage(), e);
            }
        } else {
            log.warn("Cannot set field " + field + ", as there is no method associated with it.");
        }
    }
}
