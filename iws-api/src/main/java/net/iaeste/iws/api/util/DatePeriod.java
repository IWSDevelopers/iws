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
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class is used when a period of time is required, f.x. when a Calendar
 * entry is created, then it will take place between two times, same goes for
 * Offers which also needs multiple periods.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datePeriod", propOrder = { "fromDate", "toDate" })
public final class DatePeriod extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private Date fromDate = null;
    @XmlElement(required = true, nillable = false) private Date toDate = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public DatePeriod() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor.
     *
     * @param fromDate Period Start date
     * @param toDate   Period End date
     */
    public DatePeriod(final Date fromDate, final Date toDate) {
        setFromDate(fromDate);
        setToDate(toDate);
    }

    /**
     * Copy Constructor.
     *
     * @param period DatePeriod Object to copy
     */
    public DatePeriod(final DatePeriod period) {
        if (period != null) {
            fromDate = period.fromDate;
            toDate = period.toDate;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the start date or from date for this Period. The value must be set,
     * i.e. not null. The from date cannot be before the paired to date, if
     * either null or after the to date, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param fromDate Start of the Period
     * @throws IllegalArgumentException if either null or after the to date
     */
    public void setFromDate(final Date fromDate) {
        ensureNotNullAndBeforeOrAtDate("fromDate", fromDate, toDate);

        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    /**
     * Sets the end date or to date for this Period. The value must be set, i.e.
     * not null. The to date cannot be before the paired from date, if either
     * null or before the from date, then the method will throw an
     * {@code IllegalArgumentException}.
     *
     * @param toDate End of the Period
     * @throws IllegalArgumentException if either null or before the from date
     */
    public void setToDate(final Date toDate) {
        ensureNotNullAndAfterOrAtDate("toDate", toDate, fromDate);

        this.toDate = toDate;
    }

    public Date getToDate() {
        return toDate;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "fromDate", fromDate);
        isNotNull(validation, "toDate", toDate);

        return validation;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static void ensureNotNullAndBeforeOrAtDate(final String field, final Date date, final Date mustBeBefore) {
        ensureNotNull(field, date);

        if ((mustBeBefore != null) && date.isAfter(mustBeBefore)) {
            throw new IllegalArgumentException("Date " + field + " is not at or before the given from date.");
        }
    }

    private static void ensureNotNullAndAfterOrAtDate(final String field, final Date date, final Date mustBeAfter) {
        ensureNotNull(field, date);

        if ((mustBeAfter != null) && date.isBefore(mustBeAfter)) {
            throw new IllegalArgumentException("Date " + field + " is not at or after the given from date.");
        }
    }
}
