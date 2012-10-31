/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.AbstractFixture
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.responses.Fallible;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
abstract class AbstractFixture<T extends Fallible> implements Fixture {

    protected T response = null;
    protected String testId = null;
    protected String testCase = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void testId(final String str) {
        testId = str;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void testCase(final String str) {
        testCase = str;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestOk() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.isOk();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer errorCode() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.getError().getError();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String errorMessage() {
        if (response == null) {
            throw new StopTestException("The result object must not be null!");
        }

        return response.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        response = null;
        testId = null;
        testCase = null;
    }

    /**
     * Parses the given String as either a Date or a Timestamp. If it is a Date,
     * i.e. "yyyy-MM-dd", then the time "02:00:00" is being appended, to ensure
     * that neither summer/winter time nor special locale settings will affect
     * the date test.
     *
     * @param date The date as String to parse
     * @return Parsed Date object or null
     * @throws StopTestException if unable to properly parse date
     * @see IWSConstants#DATE_FORMAT
     */
    public Date parse(final String date) {
        Date result = null;

        if (date != null) {
            final String dateToParse = date.trim();
            if (!dateToParse.isEmpty()) {
                try {
                    final DateFormat formatter = new SimpleDateFormat(IWSConstants.DATE_FORMAT, IWSConstants.DEFAULT_LOCALE);
                    result = formatter.parse(dateToParse);
                } catch (final ParseException e) {
                    throw new StopTestException("Expected date format is '" + IWSConstants.DATE_FORMAT + "' but got " + date, e);
                }
            }
        }

        return result;
    }
}
