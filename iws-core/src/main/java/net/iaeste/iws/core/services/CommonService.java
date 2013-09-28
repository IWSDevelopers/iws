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

    /**
     * Generally speaking, if the Id is undefined, a new Entity is created. If
     * there are changes, then it is assumed that the third parameter is set,
     * otherwise no actions are made.
     *
     * @param authentication User & Group information
     * @param entity         Entity to persist
     * @param persons        Optional Person information, for updates
     */
    protected void processPerson(final Authentication authentication, final PersonEntity entity, final Person... persons) {
        final Person person = getFirstObject(persons);

        // First, deal with the internal Address
        final AddressEntity address = entity.getAddress();
        if (address != null) {
            processAddress(authentication, entity.getAddress(), person.getAddress());
        }

        // Now, we'll persist the Person
        if (entity.getId() == null) {
            dao.persist(authentication, entity);
        } else if (person != null) {
            final PersonEntity newEntity = CommonTransformer.transform(person);
            dao.persist(authentication, entity, newEntity);
        }
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
            final CountryEntity country = dao.findCountry(entity.getCountry().getCountryCode());
            entity.setCountry(country);
            dao.persist(authentication, entity);
        } else if (address != null) {
            final AddressEntity newEntity = CommonTransformer.transform(address);
            dao.persist(authentication, entity, newEntity);
        }
    }

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
