/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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
import java.util.EnumSet;
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

    private static final Logger LOG = LoggerFactory.getLogger(NotificationSystemAdministration.class);

    private Long id = null;
    private static final Integer ATTEMPTS_LIMIT = 3;
    private boolean initialized = false;

    private EntityManager iwsEntityManager;
    private AccessDao accessDao;
    private MailingListDao mailingListDao;
    private NotificationDao notificationDao;
    private Settings settings;

    public NotificationSystemAdministration() {
    }

    @Override
    public void init(final EntityManager iwsEntityManager, final Settings settings) {
        this.accessDao = new AccessJpaDao(iwsEntityManager);
        this.mailingListDao = new MailingListJpaDao(iwsEntityManager);
        this.notificationDao = new NotificationJpaDao(iwsEntityManager);
        this.iwsEntityManager = iwsEntityManager;
        this.settings = settings;

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
                LOG.error("IWS error occurred: {}.", e.getMessage(), e);
            } catch (RuntimeException e) {
                //catching all exceptions other than IWSException to prevent
                //stopping notification processing and leaving error message in log
                LOG.error("System error occurred: {}.", e.getMessage(), e);
            }
        } else {
            LOG.warn("Update called for uninitialized observer.");
        }
    }

    private void processMessages() {
        //TODO this DB request doesn't work just after the task is persisted, I (Pavel) have no idea why. once it's solved, some TODOs in NotificationManager(Bean) could fixed
        final List<NotificationJobTasksView> jobTasks = notificationDao.findUnprocessedNotificationJobTaskByConsumerId(id, ATTEMPTS_LIMIT);
        for (final NotificationJobTasksView jobTask : jobTasks) {
            LOG.info("Processing system notification job task {}", jobTask.getId());
            processTask(jobTask);
        }
    }

    private void processTask(final NotificationJobTasksView task) {
        if ((task != null) && (task.getObject() != null)) {
            LOG.info("Processing system notification job task {}", task.getId());
            try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(task.getObject());
                 final ObjectInputStream objectStream = new ObjectInputStream(inputStream);) {

                final Map<NotificationField, String> fields = (Map<NotificationField, String>) objectStream.readObject();
                NotificationProcessTaskStatus processedStatus = NotificationProcessTaskStatus.ERROR;
                //TODO task is not processed, so value false is hardcoded for now, should be changed or deleted once problems are solved
                notificationDao.updateNotificationJobTask(task.getId(), false, task.getAttempts() + 1);
                if (fields != null) {
                    processedStatus = processTask(fields, task.getNotificationType());
                }
                final boolean processed = processedStatus != NotificationProcessTaskStatus.ERROR;
                LOG.info("Notification job task {} attempt number is going to be updated to {}, processed set to {}", task.getId(), task.getAttempts() + 1, processed);
                notificationDao.updateNotificationJobTask(task.getId(), processed, task.getAttempts() + 1);
                LOG.info("Notification job task {} was updated", task.getId());
            } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
                final boolean processed = false;
                LOG.info("Notification job task {} failed, task is going to be updated to {}, processed set to " + processed, task.getId(), task.getAttempts() + 1);
                notificationDao.updateNotificationJobTask(task.getId(), processed, task.getAttempts() + 1);
                LOG.info("Notification job task {} was updated", task.getId());
                LOG.error(e.getMessage(), e);
            } catch (IWSException e) {
                //prevent throwing IWSException out, it stops the timer to run this processing
                final boolean processed = false;
                notificationDao.updateNotificationJobTask(task.getId(), processed, task.getAttempts() + 1);
                LOG.error("Error during notification processing", e);
            }
        } else {
            if (task != null) {
                LOG.error("Processing of the {} which contains no Object, cannot be completed.", task);
            } else {
                LOG.error("Processing of a NULL task will not work.");
            }
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
                status = NotificationProcessTaskStatus.NOT_FOR_ME;
                break;
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
                LOG.info("New owner for group type {}, adding user {}to ncs list.", GroupType.NATIONAL, username);
                addToNcsList(username);
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Processing new group owner notification failed.", e);
        }
    }

    private void addToNcsList(final String username) {
        final MailingListEntity ncsList = mailingListDao.findNcsList(settings.getNcsList());

        if (ncsList != null) {
            final MailingListMembershipEntity subscription = mailingListDao.findMailingListSubscription(ncsList.getId(), username);

            if (subscription == null) {
                LOG.info("Adding user {} to ncs list.", username);
                final MailingListMembershipEntity entity = new MailingListMembershipEntity();
                entity.setMailingList(ncsList);
                entity.setMember(StringUtils.toLower(username));
                accessDao.persist(entity);
            }
        } else {
            LOG.error("NCs mailing list lookup failed using '{}' address.", settings.getNcsList());
        }
    }

    private void removeFromNcsList(final String username) {
        LOG.info("Deleting user {} from ncs list.", username);
        final MailingListEntity ncsList = mailingListDao.findNcsList(settings.getNcsList());

        if (ncsList != null) {
            final MailingListMembershipEntity subscription = mailingListDao.findMailingListSubscription(ncsList.getId(), username);

            if (subscription != null) {
                iwsEntityManager.remove(subscription);
                LOG.info("User {} deleted from ncs list.", username);
            }
        } else {
            LOG.error("NCs mailing list lookup failed using '{}' address.", settings.getNcsList());
        }
    }

    private NotificationProcessTaskStatus prepareNewUserNotificationSetting(final String username) {
        final NotificationProcessTaskStatus status;
        final UserEntity user = accessDao.findUserByUsername(username);
        if (user != null) {
            LOG.info("User {} to prepare {} notification for found.", user.getId(), NotificationType.ACTIVATE_NEW_USER);
            final UserNotificationEntity userNotification = notificationDao.findUserNotificationSetting(user, NotificationType.ACTIVATE_NEW_USER);

            if (userNotification == null) {
                final UserNotificationEntity entity = new UserNotificationEntity(user, NotificationType.ACTIVATE_NEW_USER, NotificationFrequency.IMMEDIATELY);
                notificationDao.persist(entity);
                LOG.info("Notification setting {} for user {} created.", NotificationType.ACTIVATE_NEW_USER, user.getId());
            }

            status = NotificationProcessTaskStatus.OK;
        } else {
            LOG.warn("User {} to prepare notification for was not found.", username);
            status = NotificationProcessTaskStatus.ERROR;
        }

        return status;
    }

    private void processUpdatedUsername(final String oldAddress, final String newAddress) {
        updateUserSubscriptionEmail(oldAddress, newAddress);
        updateUsernameInMailingAlias(oldAddress, newAddress);
    }

    private NotificationProcessTaskStatus prepareActivatedUser(final String username) {
        final NotificationProcessTaskStatus status;
        final UserEntity user = accessDao.findActiveUserByUsername(username);

        if (user != null) {
            LOG.info("Activated user {} was found", user.getId());
            createUserMailingAlias(user);
            status = prepareActivatedUserNotificationSetting(user);
        }
        else {
            LOG.warn("Activated user {} was not found", username);
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

            accessDao.persist(alias);
        } else {
            throw new IllegalArgumentException("Null user alias not allowed, user id " + user.getId());
        }
    }

    private void updateUsernameInMailingAlias(final String oldAddress, final String newAddress) {
        if ((newAddress != null) && !newAddress.isEmpty()) {
            mailingListDao.updateUsernameInMailingAlias(oldAddress, newAddress);
        }
    }

    private NotificationProcessTaskStatus prepareActivatedUserNotificationSetting(final UserEntity user) {
        final Set<NotificationType> notificationTypes = EnumSet.noneOf(NotificationType.class);
        notificationTypes.add(NotificationType.UPDATE_USERNAME);
        notificationTypes.add(NotificationType.RESET_PASSWORD);
        notificationTypes.add(NotificationType.RESET_SESSION);
        notificationTypes.add(NotificationType.NEW_GROUP_OWNER);
        final NotificationProcessTaskStatus status;

        if (user != null) {
            for (final NotificationType notificationType : notificationTypes) {
                LOG.info("Setting {} for user {}.", notificationType, user.getId());

                if (notificationDao.findUserNotificationSetting(user, notificationType) == null) {
                    final UserNotificationEntity entity = new UserNotificationEntity(user, notificationType, NotificationFrequency.IMMEDIATELY);
                    notificationDao.persist(entity);
                    LOG.info("Setting {} for user {} created.", notificationType, user.getId());
                } else {
                    LOG.info("Setting {} for user {} exists already.", notificationType, user.getId());
                }
            }
            status = NotificationProcessTaskStatus.OK;
        } else {
            LOG.warn("No user to set system notification for.");
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
            LOG.error(e.getMessage(), e);
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
                    accessDao.persist(publicList);
                } else {
                    LOG.error("Logic approach unclear: Modify non-existing list -> throw exception? ignore?");
                }
            }

            if (hasPrivateList(groupType)) {
                final MailingListEntity privateList = mailingListDao.findPrivateMailingList(groupExternalId);

                if (privateList != null) {
                    privateList.setListAddress(getPrivateListAddress(groupType, groupName, countryName));
                    privateList.setModified(new Date());
                    accessDao.persist(privateList);
                } else {
                    LOG.error("Logic approach unclear: Modify non-existing list -> throw exception? ignore?");
                }
            }
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void createMailingList(final String groupId, final String address, final boolean privateList) {
        final MailingListEntity list = new MailingListEntity();
        list.setExternalId(groupId);
        list.setListAddress(StringUtils.toLower(address));
        list.setPrivateList(privateList);

        accessDao.persist(list);
    }

    private void updateMailingListSubscription(final String type, final String groupExternalId, final String role, final String emailAddress, final String userStatus, final String onPrivateList, final String onPublicList) {
        try {
            final GroupType groupType = GroupType.valueOf(type);
            final UserGroupEntity userGroup = accessDao.findGroupMemberByUsernameAndGroupExternalId(emailAddress, groupExternalId);

            if (groupType == GroupType.NATIONAL) {
                if (accessDao.findNcsMember(emailAddress) != null) {
                    addToNcsList(emailAddress);
                } else {
                    removeFromNcsList(emailAddress);
                }
            }

            //only active users can be subscribed to mailing lists
            final boolean subscribedToPublicList = "true".equals(onPublicList) && UserStatus.ACTIVE.name().equals(userStatus) && (userGroup != null);
            final boolean subscribedToPrivateList = "true".equals(onPrivateList) && UserStatus.ACTIVE.name().equals(userStatus) && (userGroup != null);

            LOG.debug("Trying to update mailing list membership for user {} with role {} and status {} for list id '{}'. Membership private: {}; public: {}", emailAddress, role, userStatus, groupExternalId, subscribedToPrivateList, subscribedToPublicList);

            if (hasPublicList(groupType)) {
                final MailingListEntity publicMailingList = mailingListDao.findPublicMailingList(groupExternalId);
                if (publicMailingList != null) {
                    LOG.debug("Public mailing list has ID={}.", publicMailingList.getId());
                } else {
                    LOG.debug("Private mailing list with external ID '{}' was not found.", groupExternalId);
                }
                updateListSubscription(publicMailingList, emailAddress, subscribedToPublicList);
            }

            if (hasPrivateList(groupType)) {
                final MailingListEntity privateMailingList = mailingListDao.findPrivateMailingList(groupExternalId);
                if (privateMailingList != null) {
                    LOG.debug("Private mailing list has Id = {}.", privateMailingList.getId());
                } else {
                    LOG.debug("Private mailing list with external Id '{}' was not found.", groupExternalId);
                }
                updateListSubscription(privateMailingList, emailAddress, subscribedToPrivateList);
            }
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void updateListSubscription(final MailingListEntity mailingList, final String emailAddress, final boolean onList) {
        if (mailingList != null) {
            final MailingListMembershipEntity subscription = mailingListDao.findMailingListSubscription(mailingList.getId(), emailAddress);
            if (subscription != null) {
                LOG.debug("Mailing list subscription has Id = {}.", subscription.getId());
            } else {
                LOG.debug("Subscription to list was not found.");
            }
            if ((subscription != null) && !onList) {
                LOG.debug("Removing user from mailing list.");
                iwsEntityManager.remove(subscription);
            } else if ((subscription == null) && onList) {
                LOG.debug("Adding user to mailing list.");
                createListSubscription(mailingList, emailAddress);
            }
        }
    }

    private void createListSubscription(final MailingListEntity mailingList, final String emailAddress) {
        final MailingListMembershipEntity subscription = new MailingListMembershipEntity();
        subscription.setMailingList(mailingList);
        subscription.setMember(emailAddress);
        accessDao.persist(subscription);
    }

    private void updateUserSubscriptionEmail(final String oldAddress, final String newAddress) {
        if ((newAddress != null) && !newAddress.isEmpty()) {
            mailingListDao.updateUserSubscriptionEmail(newAddress, oldAddress);
        }
    }

    private static boolean hasPublicList(final GroupType type) {
        return type.getMayHavePublicMailinglist();
        //final boolean result;
        //
        //switch (type) {
        //    case ADMINISTRATION:
        //    case INTERNATIONAL:
        //    case NATIONAL:
        //        result = true;
        //        break;
        //    default:
        //        result = false;
        //}
        //
        //return result;
    }

    private static boolean hasPrivateList(final GroupType type) {
        return type.getMayHavePrivateMailinglist();
        //final boolean result;
        //
        //switch (type) {
        //    case ADMINISTRATION:
        //    case INTERNATIONAL:
        //    case LOCAL:
        //    case MEMBER:
        //    case NATIONAL:
        //    case WORKGROUP:
        //        result = true;
        //        break;
        //    default:
        //        result = false;
        //}
        //
        //return result;
    }

    private String getPublicListAddress(final GroupType type, final String groupName, final String countryName) {
        final String name;

        switch (type) {
            case NATIONAL:
                name = StringUtils.convertToAsciiMailAlias(countryName) + '@' + settings.getPublicMailAddress();
                break;
            case ADMINISTRATION:
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
            case MEMBER:
                name = StringUtils.convertToAsciiMailAlias(countryName) + '@' + settings.getPrivateMailAddress();
                break;
            case ADMINISTRATION:
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
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Long getId() {
        return id;
    }
}
