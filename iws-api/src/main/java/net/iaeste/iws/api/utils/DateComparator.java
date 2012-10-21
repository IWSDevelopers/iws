package net.iaeste.iws.api.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * As {@link java.util.Date} stores Date and Time information,
 * a Date only comparator is needed.
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