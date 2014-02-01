/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.AbstractMigrator
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.migrators;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Helpers {

    // =========================================================================
    // General Helpers
    // =========================================================================

    public static Float round(final Float value) {
        return (float) Math.round(value * 100) / 100;
    }

    public static Date convert(final Date date) {
        return (date == null) ? new Date() : date;
    }

    public static Date convert(final Date original, final Date alternate) {
        final Date result;

        if (original != null) {
            result = original;
        } else if (alternate != null) {
            result = alternate;
        } else {
            result = new Date();
        }

        return result;
    }

    public static String convert(final byte[] bytes) {
        final String result;

        if (bytes != null) {
            try {
                result = StringEscapeUtils.unescapeHtml4(new String(bytes, "ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                throw new IWSException(IWSErrors.ERROR, "Unresolvable encoding problem: " + e.getMessage(), e);
            }
        } else {
            result = null;
        }

        return result;
    }

    public static String upper(final String str) {
        return (str != null) ? str.toUpperCase(IWSConstants.DEFAULT_LOCALE) : null;
    }

    public static String lowerAndShorten(final String str, final int maxLength) {
        final String result;

        if ((str != null) && (str.length() > maxLength)) {
            result = str.substring(0,maxLength).toLowerCase(IWSConstants.DEFAULT_LOCALE);
        } else {
            result = str;
        }

        return result;
    }
}