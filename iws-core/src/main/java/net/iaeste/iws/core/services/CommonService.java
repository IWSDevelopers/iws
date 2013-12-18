/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.CommonService
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.services;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.BasicDao;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.PersonEntity;

/**
 * All Common Service funtionality is collected here. Although the Class ought
 * to be Abstract, since we should (or cat) not use it directly, it should not
 * be instantiated anywhere, but rather just extended in our Actual Services.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class CommonService<T extends BasicDao> {

    protected final T dao;

    protected CommonService(final T dao) {
        this.dao = dao;
    }

    // =========================================================================
    // Common Person Entity Methods
    // =========================================================================

    /**
     * Creates and Persists a new (empty) {@code PersonEntity} with an internal
     * {@code AddressEntity}.
     *
     * @param authentication User Authentication information
     * @return Empty {@code PersonEntity}
     */
    protected PersonEntity createEmptyPerson(final Authentication authentication) {
        // Create & Persist the Person Entity
        final PersonEntity person = new PersonEntity();
        person.setAddress(createEmptyAddress(authentication));
        dao.persist(authentication, person);

        // Return the new Entity
        return person;
    }

    /**
     * Generally speaking, if the Id is undefined, a new Entity is created. If
     * there are changes, then it is assumed that the third parameter is set,
     * otherwise no actions are made.
     *
     * @param authentication User & Group information
     * @param entity         Entity to persist
     * @param persons        Optional Person information, for updates
     * @return The persists {@code PersonEntity}
     */
    protected PersonEntity processPerson(final Authentication authentication, final PersonEntity entity, final Person... persons) {
        PersonEntity result = null;

        if (entity != null) {
            final Person person = getFirstObject(persons);

            if (person != null) {
                // First, deal with the internal Address
                final AddressEntity address = entity.getAddress();
                if (address != null) {
                    processAddress(authentication, entity.getAddress(), person.getAddress());
                }

                final PersonEntity newEntity = CommonTransformer.transform(person);
                // Now, we'll persist the Person
                if (entity.getId() == null) {
                    dao.persist(authentication, newEntity);
                    result = newEntity;
                } else {
                    dao.persist(authentication, entity, newEntity);
                    result = entity;
                }
            } else {
                result = entity;
            }
        }

        return result;
    }

    /**
     * To guarantee Personal Privacy, deleted users must have their Personal
     * details deleted as well. This method will handle that.
     *
     * @param person {@code PersonEntity} to delete
     */
    protected void deletePerson(final PersonEntity person) {
        if (person != null) {
            deleteAddress(person.getAddress());
            dao.delete(person);
        }
    }

    // =========================================================================
    // Common Address Entity Methods
    // =========================================================================

    /**
     * Creates and Persists a new (empty) {@code AddressEntity}.
     *
     * @param authentication User Authentication information
     * @return Empty {@code AddressEntity}
     */
    protected AddressEntity createEmptyAddress(final Authentication authentication) {
        // Create & Persist the Address Entity
        final AddressEntity address = new AddressEntity();

        // By default, we're going to set the Country of the address to the one
        // from the Group
        address.setCountry(authentication.getGroup().getCountry());

        // Now, we can persist the Address
        dao.persist(authentication, address);

        // Return the new Entity
        return address;
    }

    /**
     * Generally speaking, if the Id is undefined, a new Entity is created. If
     * there are changes, then it is assumed that the third parameter is set,
     * otherwise no actions are made.
     *
     * @param authentication User & Group information
     * @param entity         Entity to persist
     * @param addresses      Optional Address information, for updates
     */
    protected void processAddress(final Authentication authentication, final AddressEntity entity, final Address... addresses) {
        final Address address = getFirstObject(addresses);

        if (entity.getId() == null) {
            final CountryEntity country = findCountry(authentication, entity.getCountry());
            entity.setCountry(country);
            dao.persist(authentication, entity);
        } else if (address != null) {
            final AddressEntity newEntity = CommonTransformer.transform(address);
            dao.persist(authentication, entity, newEntity);
        }
    }

    private CountryEntity findCountry(final Authentication authentication, final CountryEntity country) {
        final CountryEntity entity;

        if ((country == null) || (country.getCountryCode() == null)) {
            entity = authentication.getGroup().getCountry();
        } else if (country.getId() == null) {
            entity = dao.findCountry(country.getCountryCode());
        } else {
            entity = country;
        }

        return entity;
    }

    /**
     * Delete the given Address information.
     *
     * @param address {@code AddressEntity} to delete
     */
    protected void deleteAddress(final AddressEntity address) {
        if (address != null) {
            dao.delete(address);
        }
    }

//    // =========================================================================
//    // Common Attachment Methods
//    // =========================================================================
//
//    protected processAttachedFiles(final Authentication authentication, final String table, final Long record, final List<FileEntity> files) {
//
//    }
//
//    protected List<FileEntity> fetchAttachedFiles(final Authentication authentication, final String table, final Long record) {
//
//    }

    // =========================================================================
    // Other Common Methods
    // =========================================================================

    /**
     * Formats a given String using our default {@code Locale} and returns the
     * result.
     *
     * @param message The String to format
     * @param objects Objects to be added to the String
     * @return Formatted String
     */
    protected String format(final String message, final Object... objects) {
        return String.format(IWSConstants.DEFAULT_LOCALE, message, objects);
    }

    /**
     * Checks if the user is permitted to access the requested Object, by
     * comparing the Owning Group for the Object. If not allowed, then a
     * {@code PermissionException} is thrown.
     *
     * @param authentication Authentication Object
     * @param group          The group to check if the user is in
     */
    protected void permissionCheck(final Authentication authentication, final GroupEntity group) {
        if (!authentication.getGroup().getId().equals(group.getId())) {
            throw new PermissionException("User is not member of the group " + group.getGroupName());
        }
    }

    private static <T> T getFirstObject(final T... objs) {
        final T result;

        if ((objs != null) && (objs.length == 1)) {
            result = objs[0];
        } else {
            result = null;
        }

        return result;
    }
}
