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
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.util.Date;
import net.iaeste.iws.api.util.DatePeriod;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.PersonEntity;

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

    public static Person transform(final PersonEntity entity) {
        Person person = null;

        if (entity != null) {
            person = new Person();

            person.setId(entity.getExternalId());
            person.setAddress(transform(entity.getAddress()));
            person.setAlternateEmail(entity.getEmail());
            person.setPhone(entity.getPhone());
            person.setMobile(entity.getMobile());
            person.setFax(entity.getFax());
            person.setBirthday(convert(entity.getBirthday()));
            person.setGender(entity.getGender());
        }

        return person;
    }

    public static PersonEntity transform(final Person person) {
        PersonEntity entity = null;

        if (person != null) {
            entity = new PersonEntity();

            entity.setExternalId(person.getId());
            entity.setAddress(transform(person.getAddress()));
            entity.setEmail(person.getAlternateEmail());
            entity.setPhone(person.getPhone());
            entity.setMobile(person.getMobile());
            entity.setFax(person.getFax());
            entity.setBirthday(convert(person.getBirthday()));
            entity.setGender(person.getGender());
        }

        return entity;
    }

    public static Group transform(final GroupEntity entity) {
        Group group = null;

        if (entity != null) {
            group = new Group();

            group.setId(entity.getExternalId());
            group.setGroupName(entity.getGroupName());
            group.setGroupType(transform(entity.getGroupType()));
            group.setDescription(entity.getDescription());
            group.setCountry(transform(entity.getCountry()));
        }

        return group;
    }

    public static GroupEntity transform(final Group group) {
        GroupEntity entity = null;

        if (group != null) {
            entity = new GroupEntity();

            entity.setExternalId(group.getId());
            entity.setGroupName(group.getGroupName());
            entity.setGroupType(transform(group.getGroupType()));
            entity.setDescription(group.getDescription());
            entity.setCountry(transform(group.getCountry()));
        }

        return entity;
    }

    public static GroupTypeEntity transform(final GroupType type) {
        GroupTypeEntity entity = null;

        if (type != null) {
            entity = new GroupTypeEntity();
            entity.setGrouptype(type);
        }

        return entity;
    }

    public static GroupType transform(final GroupTypeEntity entity) {
        GroupType type = null;

        if (entity != null) {
            type = entity.getGrouptype();
        }

        return type;
    }

    public static Address transform(final AddressEntity entity) {
        Address address = null;

        if (entity != null) {
            address = new Address();

            address.setStreet1(entity.getStreet1());
            address.setStreet2(entity.getStreet2());
            address.setZip(entity.getZip());
            address.setCity(entity.getCity());
            address.setState(entity.getState());
            address.setPobox(entity.getPobox());
            address.setCountry(transform(entity.getCountry()));
        }

        return address;
    }

    public static AddressEntity transform(final Address address) {
        AddressEntity entity = null;

        if (address != null) {
            entity = new AddressEntity();

            entity.setStreet1(address.getStreet1());
            entity.setStreet2(address.getStreet2());
            entity.setZip(address.getZip());
            entity.setCity(address.getCity());
            entity.setState(address.getState());
            entity.setPobox(address.getPobox());
            entity.setCountry(transform(address.getCountry()));
        }

        return entity;
    }

    public static Country transform(final CountryEntity entity) {
        Country country = null;

        if (entity != null) {
            country = new Country();

            country.setCountryCode(entity.getCountryCode());
            country.setCountryName(entity.getCountryName());
            country.setCountryNameFull(entity.getCountryNameFull());
            country.setCountryNameNative(entity.getCountryNameNative());
            country.setNationality(entity.getNationality());
            country.setCitizens(entity.getCitizens());
            country.setPhonecode(entity.getPhonecode());
            country.setCurrency(entity.getCurrency());
            country.setLanguages(entity.getLanguages());
            country.setMembership(entity.getMembership());
            country.setMemberSince(entity.getMemberSince());
        }

        return country;
    }

    public static CountryEntity transform(final Country country) {
        CountryEntity entity = null;

        if (country != null) {
            entity = new CountryEntity();

            entity.setCountryCode(country.getCountryCode());
            entity.setCountryName(country.getCountryName());
            entity.setCountryNameFull(country.getCountryNameFull());
            entity.setCountryNameNative(country.getCountryNameNative());
            entity.setNationality(country.getNationality());
            entity.setCitizens(country.getCitizens());
            entity.setPhonecode(country.getPhonecode());
            entity.setCurrency(country.getCurrency());
            entity.setLanguages(country.getLanguages());
            entity.setMembership(country.getMembership());
            entity.setMemberSince(country.getMemberSince());
        }

        return entity;
    }

    static DatePeriod transform(final java.util.Date fromDate, final java.util.Date toDate) {
        final DatePeriod result;

        if ((fromDate != null) && (toDate != null)) {
            result = new DatePeriod(convert(fromDate), convert(toDate));
        } else {
            result = null;
        }

        return result;
    }

    static java.util.Date readFromDateFromPeriod(final DatePeriod period) {
        return period != null ? convert(period.getFromDate()) : null;
    }

    static java.util.Date readToDateFromPeriod(final DatePeriod period) {
        return period != null ? convert(period.getToDate()) : null;
    }

    static java.util.Date convert(final Date date) {
        return date != null ? date.toDate() : null;
    }

    static Date convert(final java.util.Date date) {
        return date != null ? new Date(date) : null;
    }
}
