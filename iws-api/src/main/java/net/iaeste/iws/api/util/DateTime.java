/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The IWS representation of Date and Time is handled with this Class, which
 * serves as a wrapper using both the standard Java Date Class and the JodaTime
 * DateTime class. It was initially written with JodaTime as internal DateTime
 * representation in anticipation of the Java8 rework of the Calendar API.<br />
 *   However, the expose of WebServices was causing problems, as the DateTime
 * Object from JodaTime wasn't properly displayed. So instead the internal
 * representation is made with the standard Java Date class, using JodaTime's
 * DateTime Object to wrap certain features that otherwise would cause test
 * problems.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dateTime")
public final class DateTime implements Serializable, Comparable<DateTime> {

    /**{@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The internal Date was initially based on JodaTime, but JodaTime is having
     * a problem when it comes to WebServices, so instead the internal Date
     * representation is based on the standard Java Date Class.
     */
    @XmlElement(required = true, nillable = false)
    private final java.util.Date timestamp;

    /**
     * Creates a new Date instance set to the Current Millisecond.
     */
    public DateTime() {
        timestamp = new Date();
    }

    public DateTime(final long millis) {
        timestamp = new Date(millis);
    }

    /**
     * Creates a new Date, based on the given {@code java.util.Date} instance.
     *
     * @param dateTime {@code java.util.Date} instance, to base this instance on
     */
    public DateTime(final Date dateTime) {
        timestamp = dateTime != null ? new Date(dateTime.getTime()) : new Date();
    }

    /**
     * Checks if the given Date is after the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param dateTime Date to check if it comes after this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isAfter(final DateTime dateTime) {
        return timestamp.after(dateTime.timestamp);
    }

    /**
     * Checks if the given Date is before the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param dateTime Date to check if it comes before this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isBefore(final DateTime dateTime) {
        return timestamp.before(dateTime.timestamp);
    }

    /**
     * Returns a {@code java.util.Date} instance, representing the given Date,
     * with the time set to midnight.
     *
     * @return {@code java.util.Date} instance for this Date
     */
    public Date toDate() {
        return new Date(timestamp.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DateTime)) {
            return false;
        }

        final DateTime other = (DateTime) obj;
        return !((timestamp != null) ? !timestamp.equals(other.timestamp) : (other.timestamp != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (timestamp != null) ? timestamp.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final DateFormat formatter = new SimpleDateFormat(IWSConstants.DATE_TIME_FORMAT, IWSConstants.DEFAULT_LOCALE);
        return formatter.format(timestamp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final DateTime o) {
        if (equals(o)) {
            return 0;
        } else if (isBefore(o)) {
            return -1;
        } else {
            return 1;
        }
    }
}
