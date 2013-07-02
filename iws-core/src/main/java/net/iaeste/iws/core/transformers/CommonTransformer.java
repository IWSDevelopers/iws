/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.transformers.CommonTransformer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.transformers;

import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Country;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;

/**
 * Transformation of Common Objects.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CommonTransformer {

    /**
     * Private Constructor, this is a utility class.
     */
    private CommonTransformer() {
    }

    public static Address transform(final AddressEntity entity) {
        final Address address;

        if (entity != null) {
            address = new Address();

            address.setId(entity.getExternalId());
            address.setStreet1(entity.getStreet1());
            address.setStreet2(entity.getStreet2());
            address.setZip(entity.getZip());
            address.setCity(entity.getCity());
            address.setPobox(entity.getPobox());
            address.setCountry(transform(entity.getCountry()));
        } else {
            address = null;
        }

        return address;
    }

    public static AddressEntity transform(final Address address) {
        final AddressEntity entity;
        if (address != null) {
            entity = new AddressEntity();

            entity.setExternalId(address.getId());
            entity.setStreet1(address.getStreet1());
            entity.setStreet2(address.getStreet2());
            entity.setZip(address.getZip());
            entity.setCity(address.getCity());
            entity.setPobox(address.getPobox());
            entity.setCountry(transform(address.getCountry()));
        } else {
            entity = null;
        }

        return entity;
    }

    public static Country transform(final CountryEntity entity) {
        final Country country;

        if (entity != null) {
            country = new Country();
        } else {
            country = null;
        }

        return country;
    }

    public static CountryEntity transform(final Country country) {
        final CountryEntity entity;

        if (country != null) {
            entity = new CountryEntity();
        } else {
            entity = null;
        }

        return entity;
    }
}
