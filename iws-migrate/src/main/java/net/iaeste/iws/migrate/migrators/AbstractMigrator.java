/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.entities.CountryEntity;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public abstract class AbstractMigrator<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractMigrator.class);

    protected final AccessDao accessDao;

    /**
     * Empty Constructor, this is a utility Class.
     */
    protected AbstractMigrator(final AccessDao accessDao) {
        this.accessDao = accessDao;
    }

    abstract MigrationResult migrate(List<T> oldEntities);

    // =========================================================================
    // General AbstractMigrator
    // =========================================================================

    protected CountryEntity findExistingCountry(final String countrycode) {
        CountryEntity entity = null;

        try {
            if ((countrycode != null) && (countrycode.length() == 2) && !"$$".equals(countrycode)) {
                entity = accessDao.findCountry(countrycode.toUpperCase(IWSConstants.DEFAULT_LOCALE));
            }
        } catch (IWSException e) {
            log.warn("Couldn't find Entity for country {} => {}.", countrycode, e.getMessage());
            entity = null;
        }

        return entity;
    }

    protected static Float round(final Float value) {
        return (float) Math.round(value * 100) / 100;
    }

    protected static Date convert(final Date date) {
        return date == null ? new Date() : date;
    }

    protected static Date convert(final Date original, final Date alternate) {
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

    protected static String convert(final String str) {
        return StringEscapeUtils.unescapeHtml4(str);
    }

    protected static String upper(final String str) {
        return str != null ? str.toUpperCase(IWSConstants.DEFAULT_LOCALE) : null;
    }

    protected static String lowerAndShorten(final String str, final int maxLength) {
        final String result;

        if (str != null && str.length() > maxLength) {
            result = str.substring(0,maxLength).toLowerCase(IWSConstants.DEFAULT_LOCALE);
        } else {
            result = str;
        }

        return result;
    }
}
