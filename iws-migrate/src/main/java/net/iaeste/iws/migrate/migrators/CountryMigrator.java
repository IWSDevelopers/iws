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

import static net.iaeste.iws.migrate.migrators.Helpers.convert;
import static net.iaeste.iws.migrate.migrators.Helpers.upper;

import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.migrate.daos.IWSDao;
import net.iaeste.iws.migrate.entities.IW3CountriesEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class CountryMigrator implements Migrator<IW3CountriesEntity> {

    private static final Logger log = LoggerFactory.getLogger(CountryMigrator.class);

    @Autowired
    private IWSDao iwsDao;

    public CountryMigrator() {
    }

    public CountryMigrator(final IWSDao iwsDao) {
        this.iwsDao = iwsDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MigrationResult migrate() {
        throw new IllegalArgumentException("This Migrator is not allowed here.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = "transactionManagerIWS", propagation = Propagation.REQUIRES_NEW)
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
                    iwsDao.persist(entity);
                    persisted++;
                } catch (IllegalArgumentException | VerificationException e) {
                    log.error("Cannot process Country id:{}, name = {} => {}", oldCountry.getCountryid(), oldCountry.getCountryname(), e.getMessage());
                    skipped++;
                }
            } else {
                log.info("Skipping {}, the CountryCode is invalid '{}'.", convert(oldCountry.getCountryname()), oldCountry.getCountryid());
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

        switch (value) {
            case "BGL": // Bulgaria converted in 1999 to BGN
                converted = Currency.BGN;
                break;
            case "EEK": // Estonia converted from EEK to EUR in 2011.
                converted = Currency.EUR;
                break;
            case "GHC": // Ghana converted in 2007 to the third Cedi
                converted = Currency.GHS;
                break;
            case "JD": // Invalid Currency for Jordan
                converted = Currency.JOD;
                break;
            case "NEP": // Not sure why we've had the wrong currency for Nepal in the DB!
                converted = Currency.NPR;
                break;
            case "ROL": // Romania converted in 2005 to the fourth Leu
                converted = Currency.RON;
                break;
            case "TRL": // Turkey converted in 2005 to the second Lira
                converted = Currency.TRY;
                break;
            case "UYP": // Not sure why we've had the wrong currency for Uruguay in the DB!
                converted = Currency.UYU;
                break;
            case "": // as 138 countries have no currency we just log this at debug level to avoid being spammed!
                log.debug("Failed to convert Currency '" + currency + "' for " + countryname + ", falling back to USD.");
            case "XYZ": // Test value
                converted = Currency.USD;
                break;
            case "ZMK": // Zambia converted in 2013 to a new currency
                converted = Currency.ZMW;
                break;
            default:
                if (currency != null) {
                    converted = valueOf(value, countryname);
                }
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
