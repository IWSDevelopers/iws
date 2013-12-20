/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.converters.CountryConverter
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

import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.migrate.entities.IW3CountriesEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CountryConverter extends CommonConverter {

    public CountryEntity convert(final IW3CountriesEntity oldCountry) {
        final CountryEntity result = new CountryEntity();

        result.setCountryCode(upper(convert(oldCountry.getCountryid())));
        result.setCountryName(convert(oldCountry.getCountryname()));
        result.setCountryNameFull(convert(oldCountry.getCountrynamefull()));
        result.setCountryNameNative(convert(oldCountry.getCountrynamenative()));
        result.setNationality(convert(oldCountry.getNationality()));
        result.setCitizens(convert(oldCountry.getCitizens()));
        result.setLanguages(convert(oldCountry.getLanguages()));
        result.setCurrency(convertCurrency(oldCountry.getCurrency(), oldCountry.getCountryname()));
        result.setPhonecode(convert(oldCountry.getPhonecode()));
        result.setMembership(convertMembership(oldCountry.getMembership()));
        result.setMemberSince(convertMemberSince(oldCountry));

        result.setModified(convert(oldCountry.getModified()));
        result.setCreated(convert(oldCountry.getCreated(), oldCountry.getModified()));

        return result;
    }

    private static Membership convertMembership(final Integer membership) {
        final Membership result;

        switch (membership) {
            case 1:
                result = Membership.FULL_MEMBER;
                break;
            case 2:
                result = Membership.ASSOCIATE_MEMBER;
                break;
            case 3:
                result = Membership.COOPERATING_INSTITUTION;
                break;
            case 4:
                result = Membership.FORMER_MEMBER;
                break;
            case 5:
                result = Membership.LISTED;
                break;
            case 6:
                result = Membership.UNLISTED;
                break;
            case 0:
            default:
                // Damn, we have no clue!
                result = Membership.UNKNOWN;
        }

        return result;
    }

    private static Integer convertMemberSince(final IW3CountriesEntity oldCountry) {
        Integer result;

        switch (oldCountry.getMembership()) {
            case 1:
            case 2:
            case 3:
                result = oldCountry.getMembershipyear();
                break;
            default:
                result = null;
        }

        // Jamaica has incorrect membership since information in IW3!
        if ("jm".equals(oldCountry.getCountryid())) {
            result = 2006;
        }

        return result;
    }
}
