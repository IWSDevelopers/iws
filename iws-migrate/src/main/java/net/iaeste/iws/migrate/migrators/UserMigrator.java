/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.migrators.UserMigrator
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
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.enums.UserType;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.core.transformers.AdministrationTransformer;
import net.iaeste.iws.migrate.daos.IWSDao;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.GroupTypeEntity;
import net.iaeste.iws.persistence.entities.PersonEntity;
import net.iaeste.iws.persistence.entities.RoleEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Converts the IW3 User + Profile Object to the IWS User, Person & Address
 * Oject Structure.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Transactional
public final class UserMigrator extends AbstractMigrator<IW3ProfilesEntity> {

    private static final Logger log = LoggerFactory.getLogger(UserMigrator.class);

    /**
     * Default Constructor for the Users Migration.
     *
     * @param iwsDao IWS Dao for persisting the new IWS Entities
     */
    public UserMigrator(final IWSDao iwsDao) {
        super(iwsDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MigrationResult migrate(final List<IW3ProfilesEntity> oldEntities) {
        int persisted = 0;
        int skipped = 0;

        for (final IW3ProfilesEntity profile : oldEntities) {
            final CountryEntity country = findExistingCountry(convert(profile.getUser().getCountry()));
            final CountryEntity nationality = findExistingCountry(profile.getUser().getNationality());
            final GroupTypeEntity groupType = iwsDao.findGroupType(GroupType.PRIVATE);
            final RoleEntity role = iwsDao.findRoleById(IWSConstants.ROLE_OWNER);

            final UserEntity userEntity = convertUser(profile);
            userEntity.getPerson().setNationality(nationality);
            userEntity.getPerson().getAddress().setCountry(country);
            final UserGroupEntity entity = preparePrivateGroup(userEntity, groupType, role);

            // Done with preparations, let's verify and persists the User
            User user = null;
            try {
                user = AdministrationTransformer.transform(userEntity);
                user.verify();
                iwsDao.persist(userEntity.getPerson().getAddress());
                iwsDao.persist(userEntity.getPerson());
                iwsDao.persist(userEntity);
                iwsDao.persist(entity.getGroup());
                iwsDao.persist(entity);
                persisted++;
            } catch (IllegalArgumentException | VerificationException e) {
                log.error("Cannot process User {} => {}", user, e.getMessage());
                skipped++;
            } catch (final RuntimeException e) {
                log.error("Unknown problem while migrating User {} => {}", user, e.getMessage());
                skipped++;
            }
        }

        return new MigrationResult(persisted, skipped);
    }

    private static UserEntity convertUser(final IW3ProfilesEntity profile) {
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

    private static UserGroupEntity preparePrivateGroup(final UserEntity user, final GroupTypeEntity groupType, final RoleEntity role) {
        final GroupEntity group = new GroupEntity();
        group.setGroupName(user.getFirstname() + ' ' + user.getLastname());

        // There's a problem with a couple of accounts where there exists
        // duplicates. Run the following against the IW3 database to see:
        // select
        //   count(userid) as records,
        //   firstname,
        //   lastname
        // from users
        // group by firstname, lastname
        // having count(userid) > 1
        // order by records desc;
        //group.setFullName("Private Group for " + group.getGroupName());
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
        entity.setPostalCode(convert(profile.getUser().getZip()));
        entity.setCity(convert(profile.getUser().getCity()));
        entity.setState(convert(profile.getUser().getRegion()));
        entity.setPobox(convert(profile.getUser().getPobox()));
        entity.setModified(convert(profile.getUser().getModified()));
        entity.setCreated(convert(profile.getUser().getCreated(), profile.getUser().getModified()));

        return entity;
    }

    private static Gender convertGender(final String gender) {
        final Gender result;

        switch (upper(gender)) {
            case "MALE" :
                result = Gender.MALE;
                break;
            case "FEMALE" :
                result = Gender.FEMALE;
                break;
            default:
                result = Gender.UNKNOWN;
        }

        return result;
    }

    private static Privacy convertPrivacy(final IW3ProfilesEntity profile) {
        final Privacy privacy;

        // Although we have 3 different levels of privacy, we're only using
        // either the Private or Protected mode. Meaning, that either the data
        // is fully privaticed or it is only viewable by the Groups. If the user
        // wishes to open up further for it, then the user must actively select
        // the public variant
        if (profile.getPrivateaddress() && profile.getPrivatephones()) {
            privacy = Privacy.PROTECTED;
        } else {
            privacy = Privacy.PRIVATE;
        }

        return privacy;
    }

    private static UserStatus convertUserStatus(final String status) {
        return UserStatus.valueOf(upper(status));
    }

    private static UserType convertUserType(final String type) {
        final UserType result;

        switch (upper(type)) {
            case "V":
                result = UserType.VOLUNTEER;
                break;
            case "E":
                result = UserType.EMPLOYED;
                break;
            case "X":
            default:
                result = UserType.UNKNOWN;
        }

        return result;
    }
}
