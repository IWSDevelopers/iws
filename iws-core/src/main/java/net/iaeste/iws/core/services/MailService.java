/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.services.MailService
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

import static net.iaeste.iws.common.utils.StringUtils.toLower;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.MailinglistType;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.MailingListDao;
import net.iaeste.iws.persistence.entities.AliasEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.MailinglistEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.UserMailinglistEntity;
import net.iaeste.iws.persistence.jpa.MailingListJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * When changes for mailing lists, subscriptions or aliases are made, we have to
 * ensure that the system is updated. This class contains the functionality to
 * ensure that.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class MailService extends CommonService<MailingListDao> {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);
    private static final Pattern PATTERN_PUBLIC_ADDRESS = Pattern.compile('@' + IWSConstants.PUBLIC_EMAIL_ADDRESS);

    public MailService(final Settings settings, final EntityManager entityManager) {
        super(settings, new MailingListJpaDao(entityManager, settings));
    }

    public void synchronizeVirtualLists(final Authentication authentication) {
        final String privateList = '@' + settings.getPrivateMailAddress();

        final List<UserGroupEntity> ncsListSubscribers = dao.findMissingNcsSubscribers();
        if (!ncsListSubscribers.isEmpty()) {
            final String ncsList = settings.getNcsList() + privateList;
            final MailinglistEntity list = dao.findListByAddress(ncsList);
            addSubscribers(authentication, list, ncsListSubscribers);
        }

        // TODO Correct the logic, so the members will follow the subscription rules regarding who may write to it
        //final List<UserGroupEntity> announceListSubscribers = dao.findMissingAnnounceSubscribers();
        //if (!announceListSubscribers.isEmpty()) {
        //    final String announceList = settings.getAnnounceList() + privateList;
        //    final MailinglistEntity list = dao.findListByAddress(announceList);
        //    addSubscribers(authentication, list, announceListSubscribers);
        //}
    }

    private void addSubscribers(final Authentication authentication, final MailinglistEntity list, final List<UserGroupEntity> subscribers) {
        for (final UserGroupEntity subscriber : subscribers) {
            final UserMailinglistEntity entity = prepareSubscription(list, subscriber);
            dao.persist(authentication, entity);
            LOG.info("Subscribed {} {} to the MailingList {}.", subscriber.getUser().getFirstname(), subscriber.getUser().getLastname(), list.getListAddress());
        }
    }

    /**
     * <p>This will update all the Mailing lists present, and ensure that they
     * are synchronized with the Groups and Users.</p>
     *
     * <p>If a Group has been deleted, then the mailing list will also be
     * deleted. If Groups or Users change status from Active to Suspended or
     * reverse, then the lists will be updated accordingly.</p>
     */
    public void synchronizeMailStates() {
        final int deletedLists = dao.deleteDeadMailinglists();
        final int activatedLists = dao.activateMailinglists();
        final int suspendedLists = dao.suspendMailinglists();
        LOG.info("Updated Mailing Lists; Activated {}, Suspended {} and Deleted {}.", activatedLists, suspendedLists, deletedLists);

        final int deletedSubscriptions = dao.deleteDeadMailinglistSubscriptions();
        final int activatedPrivateSubscriptions = dao.activatePrivateMailinglistSubscriptions();
        final int activatedPublicSubscriptions = dao.activatePublicMailinglistSubscriptions();
        final int suspendedPublicSubscriptions = dao.suspendPublicMailinglistSubscriptions();
        final int suspendedPrivateSubscriptions = dao.suspendPrivateMailinglistSubscriptions();
        final int activatedSubscriptions = activatedPrivateSubscriptions + activatedPublicSubscriptions;
        final int suspendedSubscriptions = suspendedPrivateSubscriptions + suspendedPublicSubscriptions;
        LOG.info("Update Mailing List Subscriptions; Activated {}, Suspended {} and Deleted {}.", activatedSubscriptions, suspendedSubscriptions, deletedSubscriptions);
    }

    public void processAliases(final Authentication authentication) {
        final List<AliasEntity> aliases = dao.findAliases();

        for (final AliasEntity alias : aliases) {
            final MailinglistEntity found = dao.findListByAddress(alias.getAliasAddress());

            if ((alias.getExpires() != null) && alias.getExpires().after(new Date())) {
                if (found != null) {
                    dao.deleteMailinglistSubscriptions(found);
                    dao.deleteMailingList(found);
                }
            } else {
                if (found == null) {
                    final MailinglistEntity entity = new MailinglistEntity();

                    entity.setListType(MailinglistType.LIMITED_ALIAS);
                    entity.setMailReply(MailReply.REPLY_TO_SENDER);
                    entity.setStatus(GroupStatus.ACTIVE);
                    entity.setGroup(alias.getGroup());
                    entity.setListAddress(alias.getAliasAddress());
                    entity.setSubjectPrefix(alias.getGroup().getGroupName());

                    dao.persist(authentication, entity);
                    LOG.info("Alias {} created for {}.", alias.getAliasAddress(), alias.getGroup());
                }
            }
        }
    }

    public void processMissingMailingLists(final Authentication authentication) {
        final List<GroupEntity> groups = dao.findUnprocessedGroups();

        if (!groups.isEmpty()) {
            LOG.info("Found {} Active Groups without a mailing list.", groups.size());

            for (final GroupEntity group : groups) {
                processGroupLists(authentication, group);
                LOG.info("Created Mailing list(s) for {}.", group);
            }
        } else {
            LOG.info("No Groups found with missing mailing lists.");
        }
    }

    /**
     * <p>Whenever a Group is changed, i.e. created, suspended, activated,
     * deleted or simply altered - we also have to deal with the mailing lists
     * belonging to it. We're doing that via this method, which will generally
     * deal with the Group.</p>
     *
     * @param authentication User Authentication information
     * @param group Group to process mailing list(s)s for
     */
    public void processGroupLists(final Authentication authentication, final GroupEntity group) {
        // Whenever we're dealing with Group changes, we also have to deal with
        // the mailing list(s), which belongs to the Group.

        if (group.getStatus() == GroupStatus.DELETED) {
            dao.deleteMailingLists(group);
        } else {
            final List<MailinglistEntity> list = dao.findListsByGroup(group);

            if (list.isEmpty()) {
                if (group.getListName() == null) {
                    updateGroupList(authentication, group);
                    LOG.info("Update ListName for Group {} to {}.", group.getGroupName(), group.getListName());
                }

                if ((group.getListName() == null) || group.getListName().isEmpty()) {
                    LOG.info("There was a problem generating the list name for {}.", group);
                } else {
                    processNewLists(authentication, group);
                }
            } else {
                for (final MailinglistEntity entity : list) {
                    final MailinglistEntity toMerge = createGroupList(group, entity.getListType());
                    if (entity.diff(toMerge)) {
                        dao.persist(authentication, entity, toMerge);
                    }
                }
            }
        }
    }

    public void processMissingMailingListSubscriptions(final Authentication authentication) {
        final List<UserGroupEntity> userGroups = dao.findUnprocessedSubscriptions();

        for (final UserGroupEntity userGroup : userGroups) {
            processListSubscriptions(authentication, userGroup);
        }
    }

    private void processListSubscriptions(final Authentication authentication, final UserGroupEntity userGroup) {
        final List<MailinglistEntity> lists = dao.findMailinglists(userGroup.getGroup());

        for (final MailinglistEntity list : lists) {
            final UserMailinglistEntity entity = prepareSubscription(list, userGroup);
            dao.persist(authentication, entity);
            LOG.info("Subscribed {} {} to the MailingList {}.", userGroup.getUser().getFirstname(), userGroup.getUser().getLastname(), list.getListAddress());
        }
    }

    private void processNewLists(final Authentication authentication, final GroupEntity group) {
        if (group.getPrivateList()) {
            final MailinglistEntity entity = createGroupList(group, MailinglistType.PRIVATE_LIST);
            dao.persist(authentication, entity);
        }

        if (group.getPublicList()) {
            final MailinglistEntity entity = createGroupList(group, MailinglistType.PUBLIC_LIST);
            dao.persist(authentication, entity);
        }
    }

    /**
     * <p>Prepares a new Mailing List Subscription Entity for the given User.
     * The Entity is returned without un-persisted, so it can either be
     * persisted or merged with an existing entity.</p>
     *
     * <p>Regardless if a User is allowed to be on the list or not, the record
     * is added, so we have it available. This way, we can switch the flag
     * rather than having to constantly drop/create records. The initial
     * version of the Record is reflecting the state, and later runs should
     * update the state as well.</p>
     *
     * <p>Note, that we only have 1 flag in the Entity which we're packing the
     * access information into. A User can either be active or Suspended but
     * also present or not present on a mailing list. And the status flag is
     * used to reflect both. Although it can be argued to be bad design, it is
     * also a question of simplifying the logic. The Mailing List subscription
     * is only there to see if a User can or cannot receive mail. Not to show
     * the actual status.</p>
     *
     * @param list      The Mailing List to subscribe to
     * @param userGroup The User Group relation to use
     * @return New, not persisted, User Mailing List Subscription
     */
    private static UserMailinglistEntity prepareSubscription(final MailinglistEntity list, final UserGroupEntity userGroup) {
        final UserMailinglistEntity entity = new UserMailinglistEntity();

        entity.setMailinglist(list);
        entity.setUserGroup(userGroup);
        entity.setStatus(userGroup.getUser().getStatus());
        entity.setMember(userGroup.getUser().getUsername());
        entity.setMayWrite(true);

        final MailinglistType type = list.getListType();
        if (type == MailinglistType.PRIVATE_LIST) {
            entity.setMayWrite(userGroup.getWriteToPrivateList());

            if (!userGroup.getOnPrivateList()) {
                entity.setStatus(UserStatus.SUSPENDED);
            }
        } else if (!userGroup.getOnPublicList() && ((type == MailinglistType.PUBLIC_LIST) || (type == MailinglistType.LIMITED_ALIAS))) {
            entity.setStatus(UserStatus.SUSPENDED);
        }

        return entity;
    }

    /**
     * <p>Prepares a new Mailing List, it build the Entity based on the given
     * Group and list type, and returns the un-persisted Entity, which can then
     * either be persisted or merged with an existing entity.</p>
     *
     * @param group The Group to create a Mailing List Entity for
     * @param type  The List type
     * @return New Mailing List Entity
     */
    private static MailinglistEntity createGroupList(final GroupEntity group, final MailinglistType type) {
        final MailinglistEntity entity = new MailinglistEntity();
        final String listname = toLower(group.getListName()) + '@';

        entity.setGroup(group);
        entity.setStatus(group.getStatus());

        if (group.getGroupType().getGrouptype() == GroupType.PRIVATE) {
            // Private Mailing lists or "Personal Aliases", doesn't have a
            // prefix, and the type is also specific. We're also setting the
            // reply to the sender, regardless if it is set in the Group or not.
            entity.setSubjectPrefix("");
            entity.setListType(MailinglistType.PERSONAL_ALIAS);
            entity.setListAddress(listname + IWSConstants.PUBLIC_EMAIL_ADDRESS);
            entity.setMailReply(MailReply.REPLY_TO_SENDER);
        } else {
            entity.setSubjectPrefix(group.getGroupName());
            entity.setListType(type);

            if (type == MailinglistType.PRIVATE_LIST) {
                // Only the Private List option is for closes mailing lists, all
                // other is for public lists.
                entity.setListAddress(listname + IWSConstants.PRIVATE_EMAIL_ADDRESS);
                entity.setMailReply(group.getPrivateReplyTo());
            } else {
                entity.setListAddress(listname + IWSConstants.PUBLIC_EMAIL_ADDRESS);
                entity.setMailReply(group.getPublicReplyTo());
            }
        }

        return entity;
    }

    private void updateGroupList(final Authentication authentication, final GroupEntity group) {
        if (group.getGroupType().getGrouptype() == GroupType.PRIVATE) {
            final List<UserGroupEntity> users = dao.findGroupMembers(group);

            if (users.isEmpty()) {
                LOG.error("The Group {} has no members.", group);
            } else if (users.size() == 1) {
                final UserEntity user = users.get(0).getUser();
                final String alias = user.getAlias();
                final String address;

                if (alias != null) {
                    address = PATTERN_PUBLIC_ADDRESS.matcher(alias).replaceAll("");
                } else {
                    address = generateUserAlias(user.getFirstname(), user.getLastname(), false);
                }

                group.setListName(toLower(address));
                dao.persist(authentication, group);
            } else {
                LOG.error("The Groups {} has multiple members.", group);
            }
        }
    }
}
