/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.notifications.consumers.NotificationSystemAdministration
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.notifications.consumers;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.exceptions.IWSException;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.common.utils.Observer;
import net.iaeste.iws.common.utils.StringUtils;
import net.iaeste.iws.persistence.AccessDao;
import net.iaeste.iws.persistence.MailingListDao;
import net.iaeste.iws.persistence.NotificationDao;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingAliasEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListEntity;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListMembershipEntity;
import net.iaeste.iws.persistence.jpa.AccessJpaDao;
import net.iaeste.iws.persistence.jpa.MailingListJpaDao;
import net.iaeste.iws.persistence.jpa.NotificationJpaDao;
import net.iaeste.iws.persistence.views.NotificationJobTasksView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Notification consumer for administration the system (mailing list, aliases, etc.)
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public class NotificationSystemAdministration implements Observer {

    private static final Logger log = LoggerFactory.getLogger(NotificationSystemAdministration.class);

    private Long id = null;
    private static final Integer ATTEMPTS_LIMIT = 3;
    private boolean initialized = false;

    private EntityManager mailingListEntityManager;

    private AccessDao accessDao;
    private MailingListDao mailingListDao;
    private NotificationDao notificationDao;
    private Settings settings;

    public NotificationSystemAdministration() {
    }

    @Override
    public void init(final EntityManager iwsEntityManager, final EntityManager mailingEntityManager, final Settings settings) {
        this.accessDao = new AccessJpaDao(iwsEntityManager);
        this.mailingListDao = new MailingListJpaDao(mailingEntityManager);
        this.notificationDao = new NotificationJpaDao(iwsEntityManager);
        this.settings = settings;

//        mailingListDao = new MailingListJpaDao(iwsEntityManager);
        mailingListEntityManager = mailingEntityManager;

        initialized = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable subject) {
        if (initialized) {
            try {
                processMessages();
            } catch (IWSException e) {
                log.error("IWS error occurred", e);
            } catch (Exception e) {
                //catching all exceptions other than IWSException to prevent
                //stopping notification processing and leaving error message in log
                log.error("System error occurred", e);
            }
        } else {
            log.warn("Update called for uninitialized observer");
        }
    }

    private void processMessages() {
        //TODO this DB request doesn't work just after the task is persisted, I (Pavel) have no idea why. once it's solved, some TODOs in NotificationManager(Bean) could fixed
        final List<NotificationJobTasksView> jobTasks = notificationDao.findUnprocessedNotificationJobTaskByConsumerId(id, ATTEMPTS_LIMIT);
        for (final NotificationJobTasksView jobTask : jobTasks) {
            log.info("Processing system notification job task " + jobTask.getId());
            processTask(jobTask);
        }
    }

    private void processTask(final NotificationJobTasksView task) {
        log.info("Processing system notification job task " + task.getId());
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(task.getObject());
             final ObjectInputStream objectStream = new ObjectInputStream(inputStream);) {

            final Map<NotificationField, String> fields = (Map<NotificationField, String>) objectStream.readObject();
            NotificationProcessTaskStatus processedStatus = NotificationProcessTaskStatus.ERROR;
            //TODO task is not processed, so value false is hardcoded for now, should be changed or deleted once problems are solved
            notificationDao.updateNotificationJobTask(task.getId(), false, task.getAttempts()+1);
            if (fields != null) {
                processedStatus = processTask(fields, task.getNotificationType());
            }
            final boolean processed = processedStatus != NotificationProcessTaskStatus.ERROR;
            log.info("Notification job task " + task.getId() + " attempt number is going to be updated to " + (task.getAttempts()+1) + ", processed set to " + processed);
            notificationDao.updateNotificationJobTask(task.getId(), processed, task.getAttempts()+1);
            log.info("Notification job task " + task.getId() + " was updated");
        } catch (IOException|ClassNotFoundException|IllegalArgumentException e) {
            final boolean processed = false;
            log.info("Notification job task " + task.getId() + " failed, task is going to be updated to " + (task.getAttempts()+1) + ", processed set to " + processed);
            notificationDao.updateNotificationJobTask(task.getId(), processed, task.getAttempts()+1);
            log.info("Notification job task " + task.getId() + " was updated");
            log.error(e.getMessage(), e);
        } catch (IWSException e) {
            //prevent throwing IWSException out, it stops the timer to run this processing
            final boolean processed = false;
            notificationDao.updateNotificationJobTask(task.getId(), processed, task.getAttempts()+1);
            log.error("Error during notification processing", e);
        }
    }

    private NotificationProcessTaskStatus processTask(final Map<NotificationField, String> fields, final NotificationType type) {
        final NotificationProcessTaskStatus status;

        switch (type) {
            case NEW_USER:
                status = prepareNewUserNotificationSetting(fields.get(NotificationField.EMAIL));
                break;
            case USER_ACTIVATED:
                status = prepareActivatedUser(fields.get(NotificationField.EMAIL));
                break;
            case NEW_GROUP:
                createGroupMailinglist(fields.get(NotificationField.GROUP_NAME), fields.get(NotificationField.COUNTRY_NAME), fields.get(NotificationField.GROUP_TYPE), fields.get(NotificationField.GROUP_EXTERNAL_ID));
                status = NotificationProcessTaskStatus.OK;
                break;
            case PROCESS_MAILING_LIST:
                updateGroupMailingList(fields.get(NotificationField.GROUP_NAME), fields.get(NotificationField.COUNTRY_NAME), fields.get(NotificationField.GROUP_TYPE), fields.get(NotificationField.GROUP_EXTERNAL_ID));
                status = NotificationProcessTaskStatus.OK;
                break;
            case CHANGE_IN_GROUP_MEMBERS:
                updateMailingListSubscription(fields.get(NotificationField.GROUP_TYPE), fields.get(NotificationField.GROUP_EXTERNAL_ID), fields.get(NotificationField.ROLE), fields.get(NotificationField.EMAIL), fields.get(NotificationField.USER_STATUS), fields.get(NotificationField.ON_PRIVATE_LIST), fields.get(NotificationField.ON_PUBLIC_LIST));
                status = NotificationProcessTaskStatus.OK;
                break;
            case USERNAME_UPDATED:
                processUpdatedUsername(fields.get(NotificationField.EMAIL), fields.get(NotificationField.NEW_USERNAME));
                status = NotificationProcessTaskStatus.OK;
                break;
            case NEW_GROUP_OWNER:
                processNewGroupOwner(fields.get(NotificationField.GROUP_TYPE), fields.get(NotificationField.EMAIL));
            default:
                // For all other cases
                status = NotificationProcessTaskStatus.NOT_FOR_ME;
        }

        return status;
    }

    private void processNewGroupOwner(final String groupTypeName, final String username) {
        try {
            final GroupType groupType = GroupType.valueOf(groupTypeName);

            if (groupType == GroupType.NATIONAL) {
                log.info("New owner for group type " + groupType + ", adding user " + username + "to ncs list");
                addToNcsList(username);
            }
        } catch (IllegalArgumentException e) {
            log.error("Processing new group owner notification failed", e);
        }
    }

    private void addToNcsList(final String username) {
        MailingListEntity ncsList = mailingListDao.findNcsList(settings.getNcsList());

        if (ncsList != null) {
            MailingListMembershipEntity subscription = mailingListDao.findMailingListSubscription(ncsList.getId(), username);

            if (subscription == null) {
                log.info("Adding user " + username + " to ncs list");
                subscription = new MailingListMembershipEntity();
                subscription.setMailingList(ncsList);
                subscription.setMember(StringUtils.toLower(username));
                mailingListEntityManager.persist(subscription);
            }
        } else {
            log.error("NCs mailing list lookup failed using '" + settings.getNcsList() + "' address");
        }
    }

    private void removeFromNcsList(final String username) {
        log.info("Deleting user " + username + " from ncs list");
        MailingListEntity ncsList = mailingListDao.findNcsList(settings.getNcsList());

        if (ncsList != null) {
            MailingListMembershipEntity subscription = mailingListDao.findMailingListSubscription(ncsList.getId(), username);

            if (subscription != null) {
                mailingListEntityManager.remove(subscription);
                log.info("User " + username + " deleted from ncs list");
            }
        } else {
            log.error("NCs mailing list lookup failed using '" + settings.getNcsList() + "' address");
        }
    }

    private NotificationProcessTaskStatus prepareNewUserNotificationSetting(final String username) {
        final NotificationProcessTaskStatus status;
        final UserEntity user = accessDao.findUserByUsername(username);
        if (user != null) {
            log.info("User " + user.getId() + " to prepare " + NotificationType.ACTIVATE_USER + " notification for found");
            UserNotificationEntity userNotification = notificationDao.findUserNotificationSetting(user, NotificationType.ACTIVATE_USER);
            if (userNotification == null) {
                userNotification = new UserNotificationEntity(user, NotificationType.ACTIVATE_USER, NotificationFrequency.IMMEDIATELY);
                notificationDao.persist(userNotification);
                log.info("Notification setting " + NotificationType.ACTIVATE_USER + " for user " + user.getId() + " created");
            }

            status = NotificationProcessTaskStatus.OK;
        } else {
            log.warn("User " + username + " to prepare notification for was not found");
            status = NotificationProcessTaskStatus.ERROR;
        }

        return status;
    }

    private void processUpdatedUsername(final String oldAddress, final String newAddress) {
        updateUserSubscriptionEmail(oldAddress, newAddress);
        updateUsernameInMailingAlias(oldAddress, newAddress);
    }

    private NotificationProcessTaskStatus prepareActivatedUser(final String username) {
        NotificationProcessTaskStatus status;
        final UserEntity user = accessDao.findActiveUserByUsername(username);

        if (user != null) {
            log.info("Activated user " + user.getId() + " was found");
            createUserMailingAlias(user);
            status = prepareActivatedUserNotificationSetting(user);
        }
        else {
            log.warn("Activated user " + username + " was not found");
            status = NotificationProcessTaskStatus.ERROR;
        }

        return status;
    }

    private void createUserMailingAlias(final UserEntity user) {
        MailingAliasEntity alias = mailingListDao.findMailingAliasByUsername(user.getUsername());
        if (alias == null) {
            alias = new MailingAliasEntity();
            alias.setUserName(StringUtils.toLower(user.getUsername()));
        }
        if (user.getAlias() != null) {
            alias.setUserAlias(StringUtils.toLower(user.getAlias()));

            mailingListEntityManager.persist(alias);
        } else {
            throw new IllegalArgumentException("Null user alias not allowed, user id " + user.getId());
        }
    }

    private void updateUsernameInMailingAlias(final String oldAddress, final String newAddress) {
        if (newAddress != null && !newAddress.isEmpty()) {
            mailingListDao.updateUsernameInMailingAlias(oldAddress, newAddress);
        }
    }

    private NotificationProcessTaskStatus prepareActivatedUserNotificationSetting(final UserEntity user) {
        final NotificationProcessTaskStatus status;
        final Set<NotificationType> notificationTypes = new HashSet<>(16);
        notificationTypes.add(NotificationType.UPDATE_USERNAME);
        notificationTypes.add(NotificationType.RESET_PASSWORD);
        notificationTypes.add(NotificationType.RESET_SESSION);
        notificationTypes.add(NotificationType.NEW_GROUP_OWNER);

        if (user != null) {
            for (final NotificationType notificationType : notificationTypes) {
                log.info("Setting " + notificationType + " for user " + user.getId());
                UserNotificationEntity userNotification = notificationDao.findUserNotificationSetting(user, notificationType);
                if (userNotification == null) {
                    userNotification = new UserNotificationEntity(user, notificationType, NotificationFrequency.IMMEDIATELY);
                    notificationDao.persist(userNotification);
                    log.info("Setting " + notificationType + " for user " + user.getId() + " created");
                } else {
                    log.info("Setting " + notificationType + " for user " + user.getId() + "exists already");
                }
            }
            status = NotificationProcessTaskStatus.OK;
        } else {
            log.warn("No user to set system notification for");
            status = NotificationProcessTaskStatus.ERROR;
        }

        return status;
    }

    private void createGroupMailinglist(final String groupName, final String countryName, final String type, final String groupExternalId) {
        try {
            final GroupType groupType = GroupType.valueOf(type);

            if (hasPublicList(groupType)) {
                createMailingList(groupExternalId, getPublicListAddress(groupType, groupName, countryName), false);
            }

            if (hasPrivateList(groupType)) {
                createMailingList(groupExternalId, getPrivateListAddress(groupType, groupName, countryName), false);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void updateGroupMailingList(final String groupName, final String countryName, final String type, final String groupExternalId) {
        try {
            final GroupType groupType = GroupType.valueOf(type);

            if (hasPublicList(groupType)) {
                final MailingListEntity publicList = mailingListDao.findPublicMailingList(groupExternalId);
                if (publicList != null) {
                    publicList.setListAddress(getPublicListAddress(groupType, groupName, countryName));
                    publicList.setModified(new Date());
                    //mailingListDao.persist(publicList);
                    mailingListEntityManager.persist(publicList);
                } else {
                    log.error("Logic approach unclear: Modify non-existing list -> throw exception? ignore?");
                }
            }

            if (hasPrivateList(groupType)) {
                final MailingListEntity privateList = mailingListDao.findPrivateMailingList(groupExternalId);

                if (privateList != null) {
                    privateList.setListAddress(getPrivateListAddress(groupType, groupName, countryName));
                    privateList.setModified(new Date());
//                    mailingListDao.persist(privateList);
                    mailingListEntityManager.persist(privateList);
                } else {
                    log.error("Logic approach unclear: Modify non-existing list -> throw exception? ignore?");
                }
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void createMailingList(final String groupId, final String address, final boolean privateList) {
        final MailingListEntity list = new MailingListEntity();
        list.setExternalId(groupId);
        list.setListAddress(StringUtils.toLower(address));
        list.setPrivateList(privateList);

        mailingListEntityManager.persist(list);
    }

    private void updateMailingListSubscription(final String type, final String groupExternalId, final String role, final String emailAddress, final String userStatus, final String onPrivateList, final String onPublicList) {
        try {
            final GroupType groupType = GroupType.valueOf(type);
            final UserGroupEntity userGroup = accessDao.findGroupMemberByUsernameAndGroupExternalId(emailAddress, groupExternalId);

            if (groupType == GroupType.NATIONAL) {
                if (isRoleForNcsList(role) && UserStatus.ACTIVE.name().equals(userStatus) && userGroup != null) {
                    addToNcsList(emailAddress);
                } else {
                    removeFromNcsList(emailAddress);
                }
            }

            //only active users can be subscribed to mailing lists
            final boolean subscribedToPublicList = "true".equals(onPublicList) && UserStatus.ACTIVE.name().equals(userStatus) && userGroup != null;
            final boolean subscribedToPrivateList = "true".equals(onPrivateList) && UserStatus.ACTIVE.name().equals(userStatus) && userGroup != null;

            log.debug("Trying to update mailing list membership for user " + emailAddress + " with role " + role + " and status " + userStatus + " for list id '"
                    + groupExternalId + "'. Membership private: " + subscribedToPrivateList + "; public: " + subscribedToPublicList);

            if (hasPublicList(groupType)) {
                final MailingListEntity publicMailingList = mailingListDao.findPublicMailingList(groupExternalId);
                if (publicMailingList != null) {
                    log.debug("Public mailing list has ID="+publicMailingList.getId());
                } else {
                    log.debug("Private mailing list with external ID '" + groupExternalId + "' was not found");
                }
                updateListSubscription(publicMailingList, emailAddress, subscribedToPublicList);
            }

            if (hasPrivateList(groupType)) {
                final MailingListEntity privateMailingList = mailingListDao.findPrivateMailingList(groupExternalId);
                if (privateMailingList != null) {
                    log.debug("Private mailing list has ID="+privateMailingList.getId());
                } else {
                    log.debug("Private mailing list with external ID '" + groupExternalId + "' was not found");
                }
                updateListSubscription(privateMailingList, emailAddress, subscribedToPrivateList);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static boolean isRoleForNcsList(final String role) {
        final Set<String> rolesForNcsList = new HashSet<>(2);
        rolesForNcsList.add("owner");
        rolesForNcsList.add("moderator");
        return rolesForNcsList.contains(role.toLowerCase(IWSConstants.DEFAULT_LOCALE));
    }

    private void updateListSubscription(final MailingListEntity mailingList, final String emailAddress, final boolean onList) {
        if (mailingList != null) {
            final MailingListMembershipEntity subscription = mailingListDao.findMailingListSubscription(mailingList.getId(), emailAddress);
            if (subscription != null) {
                log.debug("Mailing list subscription has ID="+subscription.getId());
            } else {
                log.debug("Subscription to list was not found");
            }
            if (subscription != null && !onList) {
                log.debug("Removing user from mailing list");
//                mailingListDao.delete(subscription);
                mailingListEntityManager.remove(subscription);
            } else if (subscription == null && onList) {
                log.debug("Adding user to mailing list");
                createListSubscription(mailingList, emailAddress);
            }
        }
    }

    private void createListSubscription(final MailingListEntity mailingList, final String emailAddress) {
        final MailingListMembershipEntity subscription = new MailingListMembershipEntity();
        subscription.setMailingList(mailingList);
        subscription.setMember(emailAddress);
//        mailingListDao.persist(subscription);
        mailingListEntityManager.persist(subscription);
    }

    private void updateUserSubscriptionEmail(final String oldAddress, final String newAddress) {
        if (newAddress != null && !newAddress.isEmpty()) {
            mailingListDao.updateUserSubscriptionEmail(newAddress, oldAddress);
        }
    }

    private static boolean hasPublicList(final GroupType type) {
        final boolean result;

        switch (type) {
            //TODO general secretary

            case INTERNATIONAL:
            case NATIONAL:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }

    private static boolean hasPrivateList(final GroupType type) {
        final boolean result;

        switch (type) {
            //TODO general secretary

            case INTERNATIONAL:
            case LOCAL:
            case MEMBER:
            case NATIONAL:
            case WORKGROUP:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }

    private String getPublicListAddress(final GroupType type, final String groupName, final String countryName) {
        final String name;

        switch (type) {
            //TODO general secretary

            case NATIONAL:
                name = StringUtils.convertToAsciiMailAlias(countryName) + '@' + settings.getPublicMailAddress();
                break;
            case INTERNATIONAL:
                name = StringUtils.convertToAsciiMailAlias(groupName) + '@' + settings.getPublicMailAddress();
                break;
            default:
                name = "";
        }

        return name;
    }

    private String getPrivateListAddress(final GroupType type, final String groupName, final String countryName) {
        final String name;

        switch (type) {
            //TODO general secretary
            case MEMBER:
                name = StringUtils.convertToAsciiMailAlias(countryName) + '@' + settings.getPrivateMailAddress();
                break;
            case INTERNATIONAL:
                name = StringUtils.convertToAsciiMailAlias(groupName) + '@' + settings.getPrivateMailAddress();
                break;
            case NATIONAL:
                name = StringUtils.convertToAsciiMailAlias(countryName) + ".staff" + '@' + settings.getPrivateMailAddress();
                break;
            case LOCAL:
            case WORKGROUP:
                name = StringUtils.convertToAsciiMailAlias(countryName) + '.' + StringUtils.convertToAsciiMailAlias(groupName) + '@' + settings.getPrivateMailAddress();
                break;
            default:
                name = "";
        }

        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }
}
