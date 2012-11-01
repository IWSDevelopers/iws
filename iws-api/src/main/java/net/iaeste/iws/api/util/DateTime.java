/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.DateTime
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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass, ConstantConditions
 */
public class DateTime implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Internal Date format. */
    private static final DateFormat FORMAT = new SimpleDateFormat(IWSConstants.DATE_FORMAT, IWSConstants.DEFAULT_LOCALE);

    /** The internal Date, implementation uses the JodaTime Classes. */
    private final org.joda.time.DateTime dateTime;

    /**
     * Creates a new Date instance set to the Current Millisecond.
     */
    public DateTime() {
        dateTime = new org.joda.time.DateTime();
    }

    /**
     * Creates a new Date instance, based on the given JodaTime DateMidnight
     * Object.
     *
     * @param dateTime {@code org.joda.time.DateTime} instance, to base this instance on
     */
    public DateTime(final org.joda.time.DateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Creates a new Date, based on the given {@code java.util.Date} instance.
     *
     * @param dateTime {@code java.util.Date} instance, to base this instance on
     */
    public DateTime(final java.util.Date dateTime) {
        this.dateTime = new org.joda.time.DateTime(dateTime);
    }

    /**
     * Checks if the given Date is after the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param dateTime  Date to check if it comes after this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isAfter(final DateTime dateTime) {
        return this.dateTime.isAfter(dateTime.dateTime);
    }

    /**
     * Checks if the given Date is before the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param dateTime  Date to check if it comes before this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isBefore(final DateTime dateTime) {
        return this.dateTime.isBefore(dateTime.dateTime);
    }

    /**
     * Returns a {@code java.util.Date} instance, representing the given Date,
     * with the time set to midnight.
     *
     * @return {@code java.util.Date} instance for this Date
     */
    public java.util.Date toDate() {
        return dateTime.toDate();
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

        final DateTime other = (DateTime) obj;
        return !(dateTime != null ? !dateTime.equals(other.dateTime) : other.dateTime != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return dateTime != null ? dateTime.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return FORMAT.format(dateTime.toDate());
    }
}
