/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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

import static net.iaeste.iws.common.utils.HashcodeGenerator.generateHash;
import static net.iaeste.iws.common.utils.PasswordGenerator.generatePassword;
import static net.iaeste.iws.common.utils.StringUtils.toLower;
import static net.iaeste.iws.core.transformers.StorageTransformer.transform;
import static net.iaeste.iws.api.util.LogUtil.formatLogMessage;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.Address;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.dtos.Person;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.exceptions.AuthorizationException;
import net.iaeste.iws.common.utils.StringUtils;
import net.iaeste.iws.core.exceptions.PermissionException;
import net.iaeste.iws.core.transformers.CommonTransformer;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.BasicDao;
import net.iaeste.iws.persistence.entities.AddressEntity;
import net.iaeste.iws.persistence.entities.CountryEntity;
import net.iaeste.iws.persistence.entities.FileEntity;
import net.iaeste.iws.persistence.entities.FolderEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.PersonEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * All Common Service funtionality is collected here. Although the Class ought
 * to be Abstract, since we should (or cat) not use it directly, it should not
 * be instantiated anywhere, but rather just extended in our Actual Services.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class CommonService<T extends BasicDao> {

    private static final Logger LOG = LoggerFactory.getLogger(CommonService.class);

    protected final Settings settings;
    protected final T dao;

    protected CommonService(final Settings settings, final T dao) {
        this.settings = settings;
        this.dao = dao;
    }

    // =========================================================================
    // Common Person Entity Methods
    // =========================================================================

    /**
     * Creates and persists a new UserEntity in the database. The Entity is
     * based on the User Credentials, that is stored in the Request Object, with
     * the exception of the Username, that due to a pre-processing, is provided
     * as a parameter.<br />
     *   The creation process will run some checks, and also generate some
     * information by default. First, the user alias will be generated, if no
     * alias can be generated (user provided information was not unique enough),
     * then the create processs will fail.<br />
     *   If no password was provided, then a random password is generated and
     * returned to the user in the activation e-mail. Regardless, a salt is
     * generated and used together with the password to create a cryptographical
     * hashvalue that is then stored. The Salt is also stored in the database
     * for verification when the user attempts to login.<br />
     *   Finally, an Activation Code is generated, this is required for the user
     * to activate the account, if an account is not activated, then it cannot
     * be used.<br />
     *   If no errors occurred during the creation, the new {@code UserEntity}
     * is returned, otherwise an {@code IWSException} is thrown.
     *
     * @param authentication Authentication information from the requesting user
     * @param username       Pre-processed username
     * @param password       Password
     * @param firstname      User firstname
     * @param lastname       User lastname or family name
     * @param studentAccount If account is a Student Account or not
     * @return Newly created {@code UserEntity} Object
     * @throws IWSException if unable to create the user
     */
    protected UserEntity createAndPersistUserEntity(final Authentication authentication, final String username, final String password, final String firstname, final String lastname, final boolean studentAccount) throws IWSException {
        final UserEntity user = new UserEntity();

        // First, the Password. If no password is specified, then we'll generate
        // one. Regardlessly, the password is set in the UserEntity, for the
        // Notification
        final String thePassword = password == null ? generatePassword() : toLower(password);

        // As we doubt that a user will provide enough entropy to enable us to
        // generate a hash value that cannot be looked up in rainbow tables,
        // we're "salting" it, and additionally storing the the random part of
        // the salt in the Entity as well, the hardcoded part of the Salt is
        // stored in the Hashcode Generator
        final String salt = UUID.randomUUID().toString();

        // Now, set all the information about the user and persist the Account
        user.setUsername(username);
        user.setTemporary(thePassword);
        user.setPassword(generateHash(thePassword, salt));
        user.setSalt(salt);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAlias(generateUserAlias(firstname, lastname, studentAccount));
        user.setCode(generateHash(username + firstname + lastname, UUID.randomUUID().toString()));
        user.setPerson(createEmptyPerson(authentication));
        dao.persist(authentication, user);

        return user;
    }

    private String generateUserAlias(final String firstname, final String lastname, final boolean studentAccount) throws IWSException {
        String alias = null;

        if (!studentAccount) {
            final String name = StringUtils.convertToAsciiMailAlias(firstname + '.' + lastname);
            final String address = '@' + settings.getPublicMailAddress();

            final Long serialNumber = dao.findNumberOfAliasesForName(name);
            if ((serialNumber != null) && (serialNumber > 0)) {
                alias = name + (serialNumber + 1) + address;
            } else {
                alias = name + address;
            }
        }

        return alias;
    }

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
        person.setUnderstoodPrivacy(false);
        person.setAcceptNewsletters(true);
        dao.persist(authentication, person);

        // Return the new Entity
        return person;
    }

    protected GroupEntity createAndPersistPrivateGroup(final UserEntity user) {
        final GroupEntity group = new GroupEntity();
        final GroupType type = GroupType.PRIVATE;

        group.setGroupName(user.getFirstname() + ' ' + user.getLastname());
        group.setGroupType(dao.findGroupType(type));
        // As we have Unique Constraints on the List Types, we also have to
        // set them accordingly
        group.setPrivateList(type.getMayHavePrivateMailinglist());
        group.setPublicList(type.getMayHavePublicMailinglist());
        group.setPrivateReplyTo(MailReply.REPLY_TO_LIST);
        group.setPublicReplyTo(MailReply.REPLY_TO_SENDER);
        dao.persist(group);

        return group;
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
        final Person person = getFirstObject(persons);
        final PersonEntity newEntity = CommonTransformer.transform(person);
        final PersonEntity persisted;

        if (entity == null) {
            if (newEntity != null) {
                newEntity.setAddress(processAddress(authentication, null, person.getAddress()));
                dao.persist(authentication, newEntity);
                persisted = newEntity;
            } else {
                persisted = null;
            }
        } else {
            entity.setAddress(processAddress(authentication, entity.getAddress(), person.getAddress()));
            if (entity.getId() == null) {
                // We merge outside of the Persistence Scope, since we otherwise
                // will attempt to add history information, which with a
                // non-persisted entity will cause problems
                entity.merge(newEntity);
                dao.persist(authentication, entity);
            } else {
                dao.persist(authentication, entity, newEntity);
            }
            persisted = entity;
        }

        return persisted;
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
     * @return Persisted Address Entity
     */
    protected AddressEntity processAddress(final Authentication authentication, final AddressEntity entity, final Address... addresses) {
        final AddressEntity newEntity = CommonTransformer.transform(getFirstObject(addresses));
        final AddressEntity persisted;

        if (entity == null) {
            // Okay, no Address Entity exists - lets simply use the newEntity as
            // our base, provided that it is valid
            if (newEntity != null) {
                final CountryEntity country = findCountry(authentication, null);
                newEntity.setCountry(country);
                dao.persist(authentication, newEntity);
                persisted = newEntity;
            } else {
                persisted = null;
            }
        } else if (entity.getId() == null) {
            // The Address Entity was not earlier persisted. We're adding
            // Country and filling in the Address fields
            final CountryEntity country = findCountry(authentication, entity.getCountry());
            entity.setCountry(country);
            // We merge outside of the Persistence Scope, since we otherwise
            // will attempt to add history information, which with a
            // non-persisted entity will cause problems
            entity.merge(newEntity);
            dao.persist(authentication, entity);
            persisted = entity;
        } else {
            // The Address Entity was earlier persisted, let's just merge the
            // changes into it
            dao.persist(authentication, entity, newEntity);
            persisted = entity;
        }

        return persisted;
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

    private CountryEntity findCountry(final Authentication authentication, final CountryEntity country) {
        final CountryEntity entity;

        if ((country == null) || (country.getCountryCode() == null)) {
            if (authentication.getGroup() != null) {
                entity = authentication.getGroup().getCountry();
            } else {
                entity = dao.findMemberGroup(authentication.getUser()).getCountry();
            }
        } else if (country.getId() == null) {
            entity = dao.findCountry(country.getCountryCode());
        } else {
            entity = country;
        }

        return entity;
    }

    // =========================================================================
    // Common Attachment Methods
    // =========================================================================

    protected FileEntity processFile(final Authentication authentication, final File file, final FolderEntity... folder) {
        final String externalId = file.getFileId();
        final byte[] data = file.getFiledata();
        final FileEntity entity;

        if (externalId == null) {
            final String newId = UUID.randomUUID().toString();
            final String storedNamed = authentication.getGroup().getExternalId() + '/' + newId;

            entity = transform(file, folder != null ? folder[0] : null);
            entity.setExternalId(newId);
            entity.setChecksum(calculateChecksum(data));
            entity.setStoredFilename(storedNamed);
            entity.setFilesize((data != null) ? data.length : 0);
            entity.setUser(authentication.getUser());
            entity.setGroup(authentication.getGroup());
            // TODO The Storage Service & Common Service needs to be updated to better control how and where files are handled
            if (folder != null && folder.length == 1) {
                entity.setFolder(folder[0]);
            } else {
                entity.setFolder(null);
            }

            writeFileToSystem(storedNamed, data);
            dao.persist(authentication, entity);
        } else {
            entity = dao.findFileByUserAndExternalId(authentication.getUser(), externalId);
            if (entity != null) {
                final FileEntity changes = transform(file, entity.getFolder());
                final Long checksum = calculateChecksum(data);
                if (!entity.getChecksum().equals(checksum)) {
                    writeFileToSystem(entity.getStoredFilename(), data);
                    changes.setChecksum(checksum);
                    changes.setFilesize((data != null) ? data.length : 0);
                }
                dao.persist(authentication, entity, changes);
            } else {
                throw new AuthorizationException("The user is not authorized to process this file.");
            }
        }

        return entity;
    }

    protected byte[] readFile(final FileEntity entity) {
        final byte[] bytes = readFileFromSystem(entity.getStoredFilename());

        if (calculateChecksum(bytes) != entity.getChecksum()) {
            throw new IWSException(IWSErrors.ERROR, "The file checksum ia incorrect, most likely the file has been corrupted.");
        }

        return bytes;
    }

    protected void deleteFile(final Authentication authentication, final File file, final StorageType type) {
        final FileEntity entity;
        if (type == StorageType.ATTACHED_TO_APPLICATION) {
            entity = dao.findAttachedFileByUserAndExternalId(authentication.getGroup(), file.getFileId());
        } else {
            entity = dao.findFileByUserAndExternalId(authentication.getUser(), file.getFileId());
        }

        if (entity != null) {
            final String filename = entity.getFilename();
            deleteFileFromSystem(entity.getStoredFilename());
            final int attachmentsDeleted = dao.deleteAttachmentRecord(entity);
            dao.delete(entity);

            LOG.info(formatLogMessage(authentication, "File %s, Attached %d times, has been successfully deleted from the IWS.", filename, attachmentsDeleted));
        } else {
            throw new AuthorizationException("The user is not authorized to process this file.");
        }
    }

    /**
     * Calculates the Checksum for a given Byte array. The checksum is a simple
     * value that helps determine if the data has been updated or not. If they
     * have been updated, it must be from a user action, otherwise it is cause
     * for verifying the underlying system to see if data corruption has taken
     * place.
     *
     * @param array Byte array to check
     * @return checksum value
     */
    private static long calculateChecksum(final byte[] array) {
        final long crc;

        if ((array != null) && (array.length > 0)) {
            final Checksum checksum = new CRC32();
            checksum.update(array, 0, array.length);

            crc = checksum.getValue();
        } else {
            crc = 0;
        }

        return crc;
    }

    private void writeFileToSystem(final String name, final byte[] bytes) {
        if ((bytes != null) && (bytes.length > 0)) {
            checkDirectoryExistsOrCreate(name);

            try {
                Files.write(getPath(name), bytes);
            } catch (IOException e) {
                throw new IWSException(IWSErrors.FATAL, "I/O Error while attempting to write file: " + e.getMessage(), e);
            }
        }
    }

    private byte[] readFileFromSystem(final String name) {
        try {
            return Files.readAllBytes(getPath(name));
        } catch (IOException e) {
            throw new IWSException(IWSErrors.ERROR, "I/O Error while attempting to read file: " + e.getMessage(), e);
        }
    }

    private void deleteFileFromSystem(final String name) {
        try {
            Files.delete(getPath(name));
        } catch (IOException e) {
            throw new IWSException(IWSErrors.ERROR, "The File could not be deleted: " + e.getMessage(), e);
        }
    }

    private Path getPath(final String name) {
        return Paths.get(settings.getRootFilePath() + '/' + name);
    }

    private void checkDirectoryExistsOrCreate(final String fileWithPartialPath) {
        final String dir = fileWithPartialPath.substring(0, fileWithPartialPath.indexOf('/'));
        final String systemPath = settings.getRootFilePath() + '/' + dir + '/';//"/name";
        final java.io.File file = new java.io.File(systemPath);

        if (!file.exists()) {
            // It can be that none of the directories exists, so let's just let
            // Java crawl through them all and create them for us
            if (!file.mkdirs()) {
                throw new IWSException(IWSErrors.ERROR, "Cannot create the directory to store the Group files.");
            }
        }
    }

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

    /**
     * Java doesn't support default values directly, but with Varargs act as a
     * default value, hence we use it to determine if an Object is present or
     * not without needing to write extra methods to deal with the
     * variations.<br />
     *   This method is here to retrieve the first Object from a List which can
     * have the following values, null, empty or one Object on the list. The
     * method will simply return either null or the first Object found.
     *
     * @param objs Object listing to get the first valid Object from
     * @return First valid Object or null
     */
    @SafeVarargs
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
