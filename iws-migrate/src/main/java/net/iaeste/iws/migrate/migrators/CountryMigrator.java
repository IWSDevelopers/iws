/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.CountryMigrator
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

import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.migrate.entities.IW3CountriesEntity;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional
public final class CountryMigrator extends AbstractMigrator<IW3CountriesEntity> {

    private static final Logger log = LoggerFactory.getLogger(CountryMigrator.class);

    /**
     * Default Constructor for the Countries Migration.
     *
     * @param accessDao IWS Dao for persisting the new IWS Entities
     */
    public CountryMigrator(final AccessDao accessDao) {
        super(accessDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MigrationResult migrate(final List<IW3CountriesEntity> oldEntities) {
        int persisted = 0;
        int skipped = 0;

        for (final IW3CountriesEntity oldCountry : oldEntities) {
            // Skip incorrect Chile Country record
            if (!"c".equals(oldCountry.getCountryid())) {
                final CountryEntity entity = convertEntity(oldCountry);
                try {
                    final Country country = CommonTransformer.transform(entity);
                    country.verify();
                    accessDao.persist(entity);
                    persisted++;
                } catch (IllegalArgumentException | VerificationException e) {
                    log.error("Cannot process Country id:{}, name = {} => {}", oldCountry.getCountryid(), oldCountry.getCountryname(), e.getMessage());
                    skipped++;
                }
            } else {
                log.info("Skipping {}, the CountryCode is invalid '{}'.", oldCountry.getCountryname(), oldCountry.getCountryid());
                skipped++;
            }
        }

        return new MigrationResult(persisted, skipped);
    }

    // =========================================================================
    // Internal Countries Migration Methods
    // =========================================================================

    private static CountryEntity convertEntity(final IW3CountriesEntity oldCountry) {
        final CountryEntity result = new CountryEntity();

        result.setCountryCode(upper(oldCountry.getCountryid()));
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

    private static Currency convertCurrency(final byte[] currencyBytes, final byte[] countrynameBytes) {
        final String currency = convert(currencyBytes);
        final String countryname = convert(countrynameBytes);
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

    private static Currency valueOf(final String currency, final String countryname) {
        Currency result = null;

        if ((currency != null) && (currency.length() == 3)) {
            try {
                result = Currency.valueOf(currency);
            } catch (final IllegalArgumentException e) {
                log.debug("Unable to convert currency " + currency + " for country " + countryname + ": " + e.getMessage(), e);
                result = null;
            }
        }

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
