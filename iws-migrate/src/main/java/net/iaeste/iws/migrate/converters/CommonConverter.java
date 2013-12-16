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

    public static Long convertId(final Integer oldId) {
        // Seriously, why can't I just upgrade an Integer!
        return 0L + oldId;
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

        if (currency != null) {
            converted = valueOf(upper(currency));
        }

        if ("Estonia".equals(countryname)) {
            // Estonia converted from EEK to EUR in 2011.
            converted = Currency.EUR;
        }

        if (converted == null) {
            for (final Currency cur : Currency.values()) {
                if (upper(cur.getDescription()).contains(upper(countryname))) {
                    converted = cur;
                    break;
                }
            }
        }

        return converted;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static Currency valueOf(final String currency) {
        Currency result = null;

        if ((currency != null) && (currency.length() == 3)) {
            try {
                result = Currency.valueOf(currency);
            } catch (IllegalArgumentException e) {
                log.info("Unable to convert currency " + currency + ": " + e.getMessage(), e);
                result = null;
            }
        }

        return result;
    }
}
