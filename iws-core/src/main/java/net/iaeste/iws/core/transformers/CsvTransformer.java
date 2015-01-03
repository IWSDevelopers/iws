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

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.exchange.OfferFields;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * CSV Transformer
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class CsvTransformer {

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
        final String input = record.get(field.getField());
        Set<String> result = CollectionTransformer.explodeStringSet(input);

        return result;
    }
}
