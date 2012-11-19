/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.utils.Date
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.exceptions.VerificationException;
import org.joda.time.DateMidnight;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Java has a Data Obejct, This is terrible, and the fixes are suppose to be
 * coming with <a href="http://jcp.org/en/jsr/detail?id=310">JSR-310</a>. In the
 * meantime, JodaTime will provide the required functionality. The primary
 * reason for encapsulating the Date functionality here, is to ensure that the
 * formats are also consisting with our needs. Which follows the
 * <a href="http://en.wikipedia.org/wiki/ISO_8601">ISO</a> standard.<br />
 *   This class is a Date only class. Meaning that there is no time information
 * present.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class Date implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Internal Date format. */
    private static final DateFormat FORMAT = new SimpleDateFormat(IWSConstants.DATE_FORMAT, IWSConstants.DEFAULT_LOCALE);

    /** The internal Date, implementation uses the JodaTime Classes. */
    private final DateMidnight date;

    /**
     * Creates a new Date instance set to the Current Millisecond.
     */
    public Date() {
        date = new DateMidnight();
    }

    /**
     * Creates a new Date instance, based on the given JodaTime DateMidnight
     * Object.
     *
     * @param date {@code DateMidnight} instance, to base this instance on
     */
    public Date(final DateMidnight date) {
        this.date = date;
    }

    /**
     * Creates a new Date, based on the given {@code java.util.Date} instance.
     *
     * @param date {@code java.util.Date} instance, to base this instance on
     */
    public Date(final java.util.Date date) {
        this.date = new DateMidnight(date);
    }

    /**
     * Creates a new Date instance, based on the provided String based date. If
     * the given Date is not compliant with the IWS Format, then an Exception
     * of type {@code VerificationException} is thrown.
     *
     * @param date String based date, following the IWS format
     * @see IWSConstants#DATE_FORMAT
     */
    public Date(final String date) {
        try {
            this.date = new DateMidnight(FORMAT.parse(date));
        } catch (ParseException e) {
            throw new VerificationException(e);
        }
    }

    /**
     * Checks if the given Date is after the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param date  Date to check if it comes after this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isAfter(final Date date) {
        return this.date.isAfter(date.date);
    }

    /**
     * Checks if the given Date is before the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param date  Date to check if it comes before this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isBefore(final Date date) {
        return this.date.isBefore(date.date);
    }

    /**
     * Returns a {@code java.util.Date} instance, representing the given Date,
     * with the time set to midnight.
     *
     * @return {@code java.util.Date} instance for this Date
     */
    public java.util.Date toDate() {
        return date.toDate();
    }

    /**
     * Returns a new Date instance with the basic value of the current plus the
     * given number of Days.
     *
     * @param days Number of days to add to the current
     * @return New Date instance with the given days added
     */
    public Date plusDays(final int days) {
        return new Date(date.plusDays(days));
    }

    /**
     * Returns a new Date instance with the basic value of the current plus the
     * given number of Weeks.
     *
     * @param weeks Number of weeks to add to the current
     * @return New Date instance with the given weeks added
     */
    public Date plusWeeks(final int weeks) {
        return new Date(date.plusWeeks(weeks));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Date)) {
            return false;
        }

        final Date other = (Date) obj;
        return !(date != null ? !date.equals(other.date) : other.date != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return FORMAT.format(date.toDate());
    }
}
