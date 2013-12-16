/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.converters.UserConverter
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

import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.PersonEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

/**
 * Converts the IW3 User + Profile Object to the IWS User, Person & Address
 * Oject Structure.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class UserConverter extends CommonConverter {

    public UserEntity convert(final IW3ProfilesEntity profile) {
        final UserEntity entity = new UserEntity();

        // We're saving the IW3 Id temporarily in IWS, meaning until IWS has
        // become feature complete (in comparison with IW3), and all data has
        // been successfully migrated.
        entity.setOldId(profile.getUser().getUserid());
        entity.setUsername(convert(profile.getUser().getUsername()));
        entity.setAlias(convert(profile.getMailalias()));
        entity.setPassword(profile.getUser().getPassword());
        entity.setSalt("undefined");
        entity.setFirstname(convert(profile.getUser().getFirstname()));
        entity.setLastname(convert(profile.getUser().getLastname()));
        entity.setPerson(convertPerson(profile));
        entity.setStatus(convertUserStatus(profile.getStatus()));
        entity.setType(convertUserType(profile.getUser().getType()));
        entity.setPrivateData(convertPrivacy(profile));
        entity.setModified(convert(profile.getUser().getModified()));
        entity.setCreated(convert(profile.getUser().getCreated(), profile.getUser().getModified()));

        return entity;
    }

    public UserGroupEntity preparePrivateGroup(final UserEntity user, final GroupTypeEntity groupType, final RoleEntity role) {
        final GroupEntity group = new GroupEntity();
        group.setGroupName(convert(user.getFirstname() + ' ' + user.getLastname()));
        group.setGroupType(groupType);
        group.setModified(convert(user.getModified()));
        group.setCreated(convert(user.getCreated(), user.getModified()));

        final UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setUser(user);
        userGroup.setGroup(group);
        userGroup.setRole(role);
        userGroup.setModified(convert(user.getModified()));
        userGroup.setCreated(convert(user.getCreated(), user.getModified()));

        return userGroup;
    }

    private static PersonEntity convertPerson(final IW3ProfilesEntity profile) {
        final PersonEntity entity = new PersonEntity();

        entity.setAddress(convertAddress(profile));
        entity.setEmail(null); // We do not have this field in IW3
        entity.setPhone(convert(profile.getUser().getPhone()));
        entity.setMobile(convert(profile.getUser().getMobile()));
        entity.setFax(convert(profile.getUser().getFax()));
        entity.setBirthday(profile.getUser().getBirthday());
        entity.setGender(convertGender(profile.getUser().getGender()));
        entity.setModified(convert(profile.getUser().getModified()));
        entity.setCreated(convert(profile.getUser().getCreated(), profile.getUser().getModified()));

        return entity;
    }

    /**
     * Converts the Profile Private Address information. The Country is not
     * added initially, since we do not wish to add DB calls in the converter,
     * which serves a more general purpose.
     *
     * @param profile Profile to read the private Address from
     * @return Address Entity
     */
    private static AddressEntity convertAddress(final IW3ProfilesEntity profile) {
        final AddressEntity entity = new AddressEntity();

        entity.setStreet1(convert(profile.getUser().getStreet1()));
        entity.setStreet2(convert(profile.getUser().getStreet2()));
        entity.setZip(convert(profile.getUser().getZip()));
        entity.setCity(convert(profile.getUser().getCity()));
        entity.setState(convert(profile.getUser().getRegion()));
        entity.setPobox(convert(profile.getUser().getPobox()));
        entity.setModified(convert(profile.getUser().getModified()));
        entity.setCreated(convert(profile.getUser().getCreated(), profile.getUser().getModified()));

        return entity;
    }
}
