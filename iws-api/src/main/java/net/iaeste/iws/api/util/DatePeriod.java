/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.DatePeriod
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

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used when a period of time is required, f.x. when a Calendar
 * entry is created, then it will take place between two times, same goes for
 * Offers which also needs multipe periods.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class DatePeriod extends AbstractVerification {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Date fromDate = null;
    private Date toDate = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public DatePeriod() {
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
    // Other Operations
    // =========================================================================

    /**
     * Checks if the given Date is after the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param period  DatePeriod to check if it comes after this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isAfter(final DatePeriod period) {
        return toDate.isAfter(period.fromDate);
    }

    /**
     * Checks if the given Period is before the current. If so, then a true is
     * returned, otherwise a false.
     *
     * @param period  DatePeriod to check if it comes before this
     * @return True if the given Date is after the current, otherwise false
     */
    public Boolean isBefore(final DatePeriod period) {
        return fromDate.isBefore(period.toDate);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setFromDate(final Date fromDate) throws IllegalArgumentException {
        ensureNotNullAndBeforeDate("fromDate", fromDate, toDate);

        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setToDate(final Date toDate) throws IllegalArgumentException {
        ensureNotNullAndAfterDate("toDate", toDate, fromDate);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DatePeriod)) {
            return false;
        }

        final DatePeriod datePeriod = (DatePeriod) obj;
        if (fromDate != null ? !fromDate.equals(datePeriod.fromDate) : datePeriod.fromDate != null) {
            return false;
        }
        return !(toDate != null ? !toDate.equals(datePeriod.toDate) : datePeriod.toDate != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (toDate != null ? toDate.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DatePeriod{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static void ensureNotNullAndBeforeDate(final String field, final Date date, final Date mustBeBefore) throws IllegalArgumentException {
        ensureNotNull(field, date);

        if ((mustBeBefore != null) && !date.isBefore(mustBeBefore)) {
            throw new IllegalArgumentException("Date " + field + " is not before the given from date.");
        }
    }

    private static void ensureNotNullAndAfterDate(final String field, final Date date, final Date mustBeAfter) throws IllegalArgumentException {
        ensureNotNull(field, date);

        if ((mustBeAfter != null) && !date.isAfter(mustBeAfter)) {
            throw new IllegalArgumentException("Date " + field + " is not after the given from date.");
        }
    }
}
