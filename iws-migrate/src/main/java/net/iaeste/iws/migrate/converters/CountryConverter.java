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

    public CountryEntity convert(final IW3CountriesEntity entity) {
        final CountryEntity result = new CountryEntity();

        result.setCountryCode(upper(convert(entity.getCountryid())));
        result.setCountryName(convert(entity.getCountryname()));
        result.setCountryNameFull(convert(entity.getCountrynamefull()));
        result.setCountryNameNative(convert(entity.getCountrynamenative()));
        result.setNationality(convert(entity.getNationality()));
        result.setCitizens(convert(entity.getCitizens()));
        result.setLanguages(convert(entity.getLanguages()));
        result.setCurrency(convertCurrency(entity.getCurrency(), entity.getCountryname()));
        result.setPhonecode(convert(entity.getPhonecode()));
        result.setMembership(convertMembership(entity.getMembership()));
        result.setMemberSince(entity.getMembershipyear());

        result.setModified(convert(entity.getModified()));
        result.setCreated(convert(entity.getCreated(), entity.getModified()));

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
}
