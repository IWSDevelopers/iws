/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.converters.AbstractConverter
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.converters;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.enums.UserType;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class CommonConverter {

    private static final Logger log = LoggerFactory.getLogger(CommonConverter.class);

    // =========================================================================
    // General Converters
    // =========================================================================

    public static Float round(final Float value) {
        return (float) Math.round(value * 100) / 100;
    }

    public static Date convert(final Date date) {
        return date == null ? new Date() : date;
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

    public static String convert(final String str) {
        return StringEscapeUtils.unescapeHtml4(str);
    }

    public static String upper(final String str) {
        return str != null ? str.toUpperCase(IWSConstants.DEFAULT_LOCALE) : null;
    }

    // =========================================================================
    // Enum Converters
    // =========================================================================

    public static UserType convertUserType(final String type) {
        final UserType result;

        switch (upper(type)) {
            case "V":
                result = UserType.VOLUNTEER;
                break;
            case "E":
                result = UserType.EMPLOYED;
                break;
            case "X":
            default:
                result = UserType.UNKNOWN;
        }

        return result;
    }

    public static Gender convertGender(final String gender) {
        final Gender result;

        switch (upper(gender)) {
            case "MALE" :
                result = Gender.MALE;
                break;
            case "FEMALE" :
                result = Gender.FEMALE;
                break;
            default:
                result = Gender.UNKNOWN;
        }

        return result;
    }

    public static Privacy convertPrivacy(final IW3ProfilesEntity profile) {
        final Privacy privacy;

        // Although we have 3 different levels of privacy, we're only using
        // either the Private or Protected mode. Meaning, that either the data
        // is fully privaticed or it is only viewable by the Groups. If the user
        // wishes to open up further for it, then the user must actively select
        // the public variant
        if (profile.getPrivateaddress() && profile.getPrivatephones()) {
            privacy = Privacy.PROTECTED;
        } else {
            privacy = Privacy.PRIVATE;
        }

        return privacy;
    }

    public static UserStatus convertUserStatus(final String status) {
        return UserStatus.valueOf(upper(status));
    }

    public static Currency convertCurrency(final String currency, final String countryname) {
        Currency converted = null;
        final String value = upper(currency);

        if ("Estonia".equals(countryname)) {
            // Estonia converted from EEK to EUR in 2011.
            converted = Currency.EUR;
        } else if ("BGL".equals(value)) {
            // Bulgaria converted in 1999 to BGN
            converted = Currency.BGN;
        } else if ("ZMK".equals(value)) {
            // Zambia converted in 2013 to a new currency
            converted = Currency.ZMW;
        } else if ("GHC".equals(value)) {
            // Ghana converted in 2007 to the third Cedi
            converted = Currency.ZMW;
        } else if ("ROL".equals(value)) {
            // Romania converted in 2005 to the fourth Leu
            converted = Currency.RON;
        } else if ("TRL".equals(value)) {
            // Turkey converted in 2005 to the second Lira
            converted = Currency.TRY;
        } else if ("UYP".equals(value)) {
            // Not sure why we've had the wrong currency for Uruguay in the DB!
            converted = Currency.UYU;
        } else if (currency != null) {
            converted = valueOf(value, countryname);
        }

        if (converted == null) {
            for (final Currency cur : Currency.values()) {
                if (upper(cur.getDescription()).contains(upper(countryname))) {
                    converted = cur;
                    break;
                }
            }
        }

        // Finally fallback solution...
        if (converted == null) {
            // as 138 countries have no currency we just log this at debug level to avoid being spammed!
            log.debug("Failed to convert Currency '" + currency + "' for " + countryname + ", falling back to USD.");
            converted = Currency.USD;
        }

        return converted;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static Currency valueOf(final String currency, final String countryname) {
        Currency result = null;

        if ((currency != null) && (currency.length() == 3)) {
            try {
                result = Currency.valueOf(currency);
            } catch (IllegalArgumentException e) {
                log.debug("Unable to convert currency " + currency + " for country " + countryname + ": " + e.getMessage(), e);
                result = null;
            }
        }

        return result;
    }
}
