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
 * <p>The IWS representation of Date and Time is handled with this Class, which
 * serves as a wrapper using both the standard Java Date Class and the JodaTime
 * DateTime class. It was initially written with JodaTime as internal DateTime
 * representation in anticipation of the Java8 rework of the Calendar API.</p>
 *
 * <p>However, the expose of WebServices was causing problems, as the DateTime
 * Object from JodaTime wasn't properly displayed. So instead the internal
 * representation is made with the standard Java Date class, using JodaTime's
 * DateTime Object to wrap certain features that otherwise would cause test
 * problems.</p>
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
    @XmlElement(required = true)
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

    public DateTime(final DateTime dateTime) {
        this.timestamp = (dateTime != null) ? dateTime.toDate() : new Date();
    }

    /**
     * Creates a new Date, based on the given {@code java.util.Date} instance.
     *
     * @param dateTime {@code java.util.Date} instance, to base this instance on
     */
    public DateTime(final Date dateTime) {
        timestamp = (dateTime != null) ? new Date(dateTime.getTime()) : new Date();
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
    private Boolean isBefore(final DateTime dateTime) {
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
        return (timestamp != null) ? timestamp.equals(other.timestamp) : (other.timestamp == null);
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
        final int result;

        if (equals(o)) {
            result = 0;
        } else if (isBefore(o)) {
            result = -1;
        } else {
            result = 1;
        }

        return result;
    }
}
