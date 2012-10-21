/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.utils.DateComparator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * As {@link java.util.Date} stores Date and Time information,
 * a Date only comparator is needed.
 * Note, according to the Trac log, this class was added since the information
 * stored in the database was not containing Time information. If this is a
 * problem, then fix it in the database - NOT by adding such crap!
 *
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class DateComparator {
    /**
     * Compares its two Dates. Returns a negative integer,
     * zero, or a positive integer as the Date is less than, equal
     * to, or greater than the second.<p>
     *
     * @param d1 the first Date to be compared.
     * @param d2 the second Date to be compared.
     * @return a negative integer, zero, or a positive integer as the
     *         first Date is less than, equal to, or greater than the
     *         second.
     * @throws NullPointerException if an argument is null
     */
    public static int compare(final Date d1, final Date d2) {
        final Calendar c1 = Calendar.getInstance();
        final Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);

        return c1.compareTo(c2);
    }

    /**
     * Indicates whether #d1 and #d2 are equal.
     *
     * @param d1 the first Date to be compared.
     * @param d2 the second Date to be compared.
     * @return <code>true</code> only if #d1 is equal to #d2.
     */
    public static boolean equals(final Date d1, final Date d2) {
        return compare(d1, d2) == 0;
    }
}