/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.Date
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Java has a Data Obejct, This is terrible, and the fixes are suppose to be
 * coming with <a href="http://jcp.org/en/jsr/detail?id=310">JSR-310</a>. In the
 * meantime, JodaTime will provide the required functionality. The primary
 * reason for encapsulating the Date functionality here, is to ensure that the
 * formats are also consisting with our needs. Which follows the
 * <a href="http://en.wikipedia.org/wiki/ISO_8601">ISO</a> standard.<br />
 * This class is a Date only class. Meaning that there is no time information
 * present.<br />
 *   Note; there is a problem with JodaTime and WebServices, meaning that we
 * need to create a special mapping or use a different internal Class for it.
 * For this reason, the usage of JodaTime is used here to protect the internal
 * Date. Thus hoping to trick the WS to disclose it.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Date", propOrder = { "midnight" })
public final class Date implements Serializable, Comparable<Date> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The internal Date was based on JodaTime, but as it caused problems with
     * WebServices, the implementation is changed to use the standard Java Date
     * Object.
     */
    @XmlElement(required = true, nillable = false)
    private final java.util.Date midnight;

    /**
     * Creates a new Date instance set to the Current Millisecond.
     */
    public Date() {
        midnight = new DateMidnight().toDate();
    }

    /**
     * Creates a new Date instance, based on the given JodaTime DateMidnight
     * Object.
     *
     * @param date {@code DateMidnight} instance, to base this instance on
     */
    public Date(final DateMidnight date) {
        midnight = date.toDate();
    }

    /**
     * Creates a new Date instance, based on the given milli seconds.
     *
     * @param millis Milli Seconds to base this instance on.
     */
    public Date(final long millis) {
        midnight = new java.util.Date(millis);
    }

    /**
     * Creates a new Date, based on the given {@code java.util.Date} instance.
     *
     * @param date {@code java.util.Date} instance, to base this instance on
     */
    public Date(final java.util.Date date) {
        midnight = new java.util.Date(date.getTime());
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
        final DateFormat formatter = new SimpleDateFormat(IWSConstants.DATE_FORMAT, IWSConstants.DEFAULT_LOCALE);
        try {
            midnight = new DateMidnight(formatter.parse(date)).toDate();
        } catch (ParseException e) {
            throw new VerificationException(e);
        }
    }

    /**
     * Reads the milli seconds since Epoch (1970-01-01 00:00:00), from the
     * internal Data.
     *
     * @return Millis since Epoch
     */
    public long getTime() {
        return midnight.getTime();
    }

    /**
     * Checks if the given Date is after the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param date Date to check if it comes after this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isAfter(final Date date) {
        return midnight.after(date.midnight);
    }

    /**
     * Checks if the given Date is before the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param date Date to check if it comes before this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isBefore(final Date date) {
        return midnight.before(date.midnight);
    }

    /**
     * Returns a {@code java.util.Date} instance, representing the given Date,
     * with the time set to midnight.
     *
     * @return {@code java.util.Date} instance for this Date
     */
    public java.util.Date toDate() {
        return new java.util.Date(midnight.getTime());
    }

    /**
     * Returns a new Date instance with the basic value of the current plus the
     * given number of Days.
     *
     * @param days Number of days to add to the current
     * @return New Date instance with the given days added
     */
    public Date plusDays(final int days) {
        return new Date(new DateMidnight(midnight).plusDays(days));
    }

    /**
     * Returns a new Date instance with the basic value of the current plus the
     * given number of Weeks.
     *
     * @param weeks Number of weeks to add to the current
     * @return New Date instance with the given weeks added
     */
    public Date plusWeeks(final int weeks) {
        return new Date(new DateMidnight(midnight).plusWeeks(weeks));
    }

    /**
     * Returns the year from the current Date.
     *
     * @return The year for this Date
     */
    public int getCurrentYear() {
        return new DateMidnight(midnight).toGregorianCalendar().get(Calendar.YEAR);
    }

    public int getCurrentMonth() {
        return new DateMidnight(midnight).toGregorianCalendar().get(Calendar.MONTH);
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
        return !((midnight != null) ? !midnight.equals(other.midnight) : (other.midnight != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (midnight != null) ? midnight.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final DateFormat formatter = new SimpleDateFormat(IWSConstants.DATE_FORMAT, IWSConstants.DEFAULT_LOCALE);
        return formatter.format(midnight).toUpperCase(IWSConstants.DEFAULT_LOCALE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Date o) {
        if (equals(o)) {
            return 0;
        } else if (isBefore(o)) {
            return -1;
        } else {
            return 1;
        }
    }
}
