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
package net.iaeste.iws.core.transformers;

import static net.iaeste.iws.api.constants.IWSConstants.DATE_FORMAT;
import static net.iaeste.iws.api.constants.IWSConstants.DEFAULT_LOCALE;
import static net.iaeste.iws.api.enums.exchange.OfferFields.STUDY_COMPLETED_BEGINNING;
import static net.iaeste.iws.api.enums.exchange.OfferFields.STUDY_COMPLETED_END;
import static net.iaeste.iws.api.enums.exchange.OfferFields.STUDY_COMPLETED_MIDDLE;
import static net.iaeste.iws.common.utils.StringUtils.toLower;

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
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
final class CSVTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(CSVTransformer.class);

    private static final Pattern PATTERN_UNWANTED_CHARACTERS = Pattern.compile("[_\\t\\r\\n\\u00a0]");

    /**
     * Private Constructor, this is a utility class.
     */
    private CSVTransformer() {
    }

    static void transformString(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        invokeMethodOnObject(errors, obj, field, record.get(field.getField()));
    }

    static void transformBoolean(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            invokeMethodOnObject(errors, obj, field, convertBoolean(value));
        }
    }

    static void transformInteger(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final Integer number = Integer.valueOf(value);
                invokeMethodOnObject(errors, obj, field, number);
            } catch (NumberFormatException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    static void transformBigDecimal(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
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

    static void transformFloat(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final Float number = Float.valueOf(value);
                invokeMethodOnObject(errors, obj, field, number);
            } catch (NumberFormatException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    static void transformDate(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final Date date = convertDate(errors, field, record);
        invokeMethodOnObject(errors, obj, field, date);
    }

    static void transformDatePeriod(final Map<String, String> errors, final Verifiable obj, final OfferFields startField, final OfferFields endField, final CSVRecord record) {
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

    static <T extends Enum<T>> void transformEnum(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record, final Class<T> enumType) {
        final String value = record.get(field.getField());

        if ((value != null) && !value.isEmpty()) {
            try {
                final T theEnum = Enum.valueOf(enumType, value.toUpperCase(DEFAULT_LOCALE));
                invokeMethodOnObject(errors, obj, field, theEnum);
            } catch (IllegalArgumentException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }
    }

    static <E extends Enum<E> & Descriptable<E>> void transformDescriptableEnumSet(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record, final Class<E> enumType) {
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

    static <E extends Enum<E> & Descriptable<E>> void transformDescriptableEnum(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record, final Class<E> enumType) {
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

    static void transformStringSet(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());
        final String parsedValue = PATTERN_UNWANTED_CHARACTERS.matcher(value).replaceAll(" ").trim();

        final Set<String> set = CollectionTransformer.explodeStringSet(parsedValue);
        invokeMethodOnObject(errors, obj, field, set);
    }

    static void transformTypeOfWork(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
        final Boolean typeR = convertBoolean(record.get(OfferFields.WORK_TYPE_P.getField()));
        final Boolean typeO = convertBoolean(record.get(OfferFields.WORK_TYPE_R.getField()));
        final Boolean typeF = convertBoolean(record.get(OfferFields.WORK_TYPE_W.getField()));

        if (convertBoolean(record.get(OfferFields.WORK_TYPE_N.getField()))) {
            LOG.info("Ignoring the TypeOfWork 'N'.");
        }

        // Using the Boolean comparison as it reduces the NPath complexity
        final int sum = Boolean.compare(typeR, false)
                      + Boolean.compare(typeO, false)
                      + Boolean.compare(typeF, false);

        if (sum > 1) {
            errors.put(field.getField(), "Multiple TypeOfWork is set, only one is allowed.");
        } else if (sum == 0) {
            errors.put(field.getField(), "No TypeOfWork defined.");
        } else {
            final TypeOfWork value;
            if (typeR) {
                value = TypeOfWork.R;
            } else if (typeO) {
                value = TypeOfWork.O;
            } else {
                value = TypeOfWork.F;
            }

            invokeMethodOnObject(errors, obj, field, value);
        }
    }

    static void transformStudyLevels(final Map<String, String> errors, final Verifiable obj, final OfferFields field, final CSVRecord record) {
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

    private static Date convertDate(final Map<String, String> errors, final OfferFields field, final CSVRecord record) {
        final String value = record.get(field.getField());
        Date result = null;

        if ((value != null) && !value.isEmpty()) {
            try {
                final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, DEFAULT_LOCALE);
                result = new Date(formatter.parse(value));
            } catch (ParseException e) {
                LOG.debug(e.getMessage(), e);
                errors.put(field.getField(), e.getMessage());
            }
        }

        return result;
    }

    /**
     * <p>Reflective invocation of the Object Setter methods. To enforce the
     * Setter Validation checks on the values. Required to avoid that illegal
     * values is being processed.</p>
     *
     * <p>The method will also catch any thrown IllegalArgument Exceptions and
     * add the result to the error map given.</p>
     *
     * @param errors   Validation Error Map
     * @param obj      The Object to invoke the Setter on
     * @param field    The Object field to be set
     * @param argument Argument to the Setter
     * @throws IWSException If a Reflection Error occurred.
     */
    private static <O extends Verifiable> void invokeMethodOnObject(final Map<String, String> errors, final O obj, final OfferFields field, final Object argument) {
        if ((field.getMethod() != null) && field.useField(OfferFields.Type.DOMESTIC)) {
            try {
                final Method implementation = obj.getClass().getMethod(field.getMethod(), field.getArgumentClass());
                implementation.invoke(obj, argument);
            } catch (InvocationTargetException e) {
                // The Reflection Framework is wrapping all caught Exceptions
                // inside the Invocation Target Exception. Since our setters
                // is throwing an IllegalArgument Exception, we have to see if
                // this occurs.
                if (e.getTargetException() instanceof IllegalArgumentException) {
                    LOG.debug("Setter {} Invocation Error: {}", field.getMethod(), e.getTargetException(), e.getTargetException());
                    errors.put(field.getField(), e.getTargetException().getMessage());
                } else {
                    LOG.debug("Setter {} Invocation Error: {}", field.getMethod(), e.getMessage(), e);
                    errors.put(field.getField(), e.getMessage());
                }
            } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException e) {
                // The Reflection framework forces a check for the
                // NoSuchMethodException. Additionally, if the Java Security
                // Manager is used, we may also see a SecurityException.
                //   Invoking the method may also result in either an
                // IllegalArgumentException or IllegalAccessException.
                LOG.error(e.getMessage(), e);
                throw new IWSException(IWSErrors.FATAL, e.getMessage(), e);
            }
        } else {
            LOG.warn("Cannot set field {}, as there is no method associated with it.", field.getField());
        }
    }
}
