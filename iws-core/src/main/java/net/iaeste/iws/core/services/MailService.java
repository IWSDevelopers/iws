/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.core.services;

import static net.iaeste.iws.common.utils.StringUtils.toLower;

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
 * <p>When changes for mailing lists, subscriptions or aliases are made, we have
 * to ensure that the system is updated. This class contains the functionality
 * to ensure that.</p>
 *
 * <p>Please note, that the Virtual Mailing List &quot;announce&quot; is
 * currently disabled, as the logic for controlling who may write is pending.
 * For Private lists, the change of the &quot;mayWrite&quot; flag is not yet
 * being updated.</p>
 *
 * <p>When a User changes Username, it must also be reflected here.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class MailService extends CommonService<MailingListDao> {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    public MailService(final Settings settings, final EntityManager entityManager) {
        super(settings, new MailingListJpaDao(entityManager, settings));
    }

    // =========================================================================
    // Public methods for handling Mail Service Synchronization
    // =========================================================================

    /**
     * Mailing Lists is one of the core parts of the IWS. Each Group can have
     * either a public, a private or both kinds of mailing lists. And Users can
     * be subscribed to either one or the other. Please see the GroupType for
     * more information about what kind of Mailing Lists a Group can have.
     *
     * @param authentication User Authentication information
     * @return Number of changes
     */
    public int processMissingMailingLists(final Authentication authentication) {
        final List<GroupEntity> groups = dao.findUnprocessedGroups();

        if (!groups.isEmpty()) {
            LOG.info("Found {} Active Groups without a mailing list.", groups.size());

            for (final GroupEntity group : groups) {
                processGroupLists(authentication, group);
                LOG.info("Created Mailing list(s) for {}.", group);
            }
        } else {
            LOG.debug("No Groups found with missing mailing lists.");
        }

        return groups.size();
    }

    /**
     *  <p>Aliases is a feature, whereby a Group which have changed name can
     *  keep the old name for a period. The period is determined by an
     *  expiration date associated with the Alias. But it can also be that
     *  there is no expiration. Most National Committees going from Cooperating
     *  Institution to Full Member require a transition period, before their
     *  new address is known to all. Others, like the Board have aliases of a
     *  permanent basis, like president - the president alias must always
     *  work.</p>
     *
     *  <p>Aliases are always re-processed, as it can be that they change. And
     *  as there is a rather limited amount of Aliases, reducing the logic for
     *  handling these makes it easier.</p>
     *
     *  <p>Aliases is treated as a public mailing list, but is marked as a
     *  &quot;limited alias&quot;, as they can expire. But otherwise it is
     *  treated just as any other public mailing list.</p>
     *
     * @param authentication User Authentication information
     * @return Number of changes
     */
    public int processAliases(final Authentication authentication) {
        final List<AliasEntity> aliases = dao.findAliases();
        int changes = 0;

        for (final AliasEntity alias : aliases) {
            final String address = alias.getAliasAddress() + '@' + settings.getPublicMailAddress();
            final MailinglistEntity found = dao.findMailingList(address);

            if ((alias.getExpires() != null) && alias.getExpires().after(new Date())) {
                if (found != null) {
                    dao.deleteMailinglistSubscriptions(found);
                    dao.delete(found);
                    changes++;
                }
            } else {
                if (found == null) {
                    final MailinglistEntity entity = new MailinglistEntity();

                    entity.setListType(MailinglistType.LIMITED_ALIAS);
                    entity.setMailReply(MailReply.REPLY_TO_SENDER);
                    entity.setStatus(GroupStatus.ACTIVE);
                    entity.setGroup(alias.getGroup());
                    entity.setListAddress(address);
                    entity.setSubjectPrefix(alias.getGroup().getGroupName());

                    dao.persist(authentication, entity);
                    LOG.info("Alias {} created for {}.", address, alias.getGroup());
                    changes++;
                }
            }
        }

        return changes;
    }

    /**
     * Users may belong to an arbitrary number of Groups, and their relation to
     * a Group is also reflected in their inclusion on Mailing Lists.
     * Regardless of the relationship, a User will always be present on the
     * Lists which the Group may have, so if a Group may have two Lists (Public
     * &amp; Private), the User has a relation to both. However, each relation
     * also have a state, and only if the User according to the Group Relation
     * is allowed to receive and write to a list, will the state allow it.
     *
     * @param authentication User Authentication information
     * @return Number of changes
     */
    public int processMissingMailingListSubscriptions(final Authentication authentication) {
        final List<UserGroupEntity> userGroups = dao.findUnprocessedSubscriptions();
        int changes = 0;

        for (final UserGroupEntity userGroup : userGroups) {
            changes += processListSubscriptions(authentication, userGroup);
        }

        return changes;
    }

    /**
     * <p>This will update all the Mailing lists present, and ensure that they
     * are synchronized with the Groups and Users.</p>
     *
     * <p>If a Group has been deleted, then the mailing list will also be
     * deleted. If Groups or Users change status from Active to Suspended or
     * reverse, then the lists will be updated accordingly.</p>
     *
     * @return Number of changes
     */
    public int synchronizeMailStates() {
        final int deletedLists = dao.deleteDeprecatedMailinglists();
        final int activatedLists = dao.activateMailinglists();
        final int suspendedLists = dao.suspendMailinglists();
        if ((deletedLists + activatedLists + suspendedLists) > 0) {
            LOG.info("Updated Mailing Lists; Activated {}, Suspended {} and Deleted {}.", activatedLists, suspendedLists, deletedLists);
        }

        final int deletedSubscriptions = dao.deleteDeprecatedMailinglistSubscriptions();
        final int activatedPrivateSubscriptions = dao.activatePrivateMailinglistSubscriptions();
        final int activatedPublicSubscriptions = dao.activatePublicMailinglistSubscriptions();
        final int suspendedPrivateSubscriptions = dao.suspendPrivateMailinglistSubscriptions();
        final int suspendedPublicSubscriptions = dao.suspendPublicMailinglistSubscriptions();
        final int addedWritingRights = dao.addWritePermission();
        final int removedWritingRights = dao.removeWritePermission();

        final int activatedSubscriptions = activatedPrivateSubscriptions + activatedPublicSubscriptions;
        final int suspendedSubscriptions = suspendedPrivateSubscriptions + suspendedPublicSubscriptions;
        if ((activatedSubscriptions + addedWritingRights + removedWritingRights + suspendedSubscriptions + deletedSubscriptions) > 0) {
            LOG.info("Update Mailing List Subscriptions; Activated {}, Write {}, Read {}, Suspended {} and Deleted {}.", activatedSubscriptions, addedWritingRights, removedWritingRights, suspendedSubscriptions, deletedSubscriptions);
        }

        final int updatedAddress = dao.updateSubscribedAddress();
        if (updatedAddress > 0) {
            LOG.info("Updated {} Subscriptions, where the username was changed.", updatedAddress);
        }

        return activatedLists + suspendedLists + deletedLists +
                activatedSubscriptions + addedWritingRights + removedWritingRights +
                suspendedSubscriptions + deletedSubscriptions + updatedAddress;
    }

    /**
     * <p>In IWS, almost all Mailing Lists is build based on the rules for a
     * given Group. However, there is also exceptions to this rule, and these
     * fall under the Virtual Mailing Lists category.</p>
     *
     * <p>A Virtual Mailing List is one where the Subscription Rule is rather
     * special. By default, there's is 2 such Mailing Lists, the NC's Mailing
     * Lists, going to all National Committees, Board &amp; International
     * Groups. And the Announce List, which is limited regarding who may write
     * to it, but otherwise goes to everybody.</p>
     *
     * <p>As the Mailing List Synchronization above cannot and should not be
     * aware of Rules regarding Virtual Mailing lists, it is important that we
     * handle Virtual Mailing Lists as the last part, so we rules can be
     * adjusted accordingly.</p>
     *
     * @param authentication User Authentication information
     * @return Number of changes
     */
    public int synchronizeVirtualLists(final Authentication authentication) {
        final String privateList = '@' + settings.getPrivateMailAddress();
        final String ncsListAddress = settings.getNcsList() + privateList;
        final MailinglistEntity ncsList = dao.findMailingList(ncsListAddress);
        int changes = 0;

        final List<UserGroupEntity> ncsListSubscribers = dao.findMissingNcsSubscribers();
        if (!ncsListSubscribers.isEmpty()) {
            addSubscribers(authentication, ncsList, ncsListSubscribers);
            changes += ncsListSubscribers.size();
        }

        final List<UserGroupEntity> ncsListUnsubscribers = dao.findDeprecatedNcsSubscribers();
        if (!ncsListUnsubscribers.isEmpty()) {
            removeSubscribers(ncsList, ncsListUnsubscribers);
            changes += ncsListUnsubscribers.size();
        }

        if (changes > 0) {
            LOG.info("NC's Mailing List; Added {} and removed {} Subscribers.", ncsListSubscribers.size(), ncsListUnsubscribers.size());
        }

        // TODO Correct the logic, so the members will follow the subscription rules regarding who may write to it
        //final List<UserGroupEntity> announceListSubscribers = dao.findMissingAnnounceSubscribers();
        //if (!announceListSubscribers.isEmpty()) {
        //    final String announceList = settings.getAnnounceList() + privateList;
        //    final MailinglistEntity list = dao.findMailingList(announceList);
        //    addSubscribers(authentication, list, announceListSubscribers);
        //    changes += list.size();
        //}

        return changes;
    }

    // =========================================================================
    // Internal helper methods
    // =========================================================================

    private void addSubscribers(final Authentication authentication, final MailinglistEntity list, final List<UserGroupEntity> subscribers) {
        if (list != null) {
            for (final UserGroupEntity subscriber : subscribers) {
                final UserMailinglistEntity entity = prepareSubscription(list, subscriber);
                dao.persist(authentication, entity);
                LOG.info("Subscribed {} {} to the {} MailingList.", subscriber.getUser().getFirstname(), subscriber.getUser().getLastname(), list.getListAddress());
            }
        } else {
            LOG.error("Cannot add the Users {} to a null list, please check the IWS Configuration, as the Virtual List settings is wrong.", subscribers.size());
        }
    }

    private void removeSubscribers(final MailinglistEntity list, final List<UserGroupEntity> subscribers) {
        if (list != null) {
            for (final UserGroupEntity subscriber : subscribers) {
                final UserMailinglistEntity entity = dao.findSubscription(list, subscriber);
                dao.delete(entity);
                LOG.info("Removed {} {} from the {} MailingList.", subscriber.getUser().getFirstname(), subscriber.getUser().getLastname(), list.getListAddress());
            }
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
    private void processGroupLists(final Authentication authentication, final GroupEntity group) {
        // Whenever we're dealing with Group changes, we also have to deal with
        // the mailing list(s), which belongs to the Group.

        if (group.getStatus() == GroupStatus.DELETED) {
            dao.deleteMailingLists(group);
        } else {
            final List<MailinglistEntity> list = dao.findMailingList(group);

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

    private int processListSubscriptions(final Authentication authentication, final UserGroupEntity userGroup) {
        final List<MailinglistEntity> lists = dao.findMailingList(userGroup.getGroup());

        for (final MailinglistEntity list : lists) {
            final UserMailinglistEntity entity = prepareSubscription(list, userGroup);
            dao.persist(authentication, entity);
            LOG.info("Subscribed {} {} to the MailingList {}.", userGroup.getUser().getFirstname(), userGroup.getUser().getLastname(), list.getListAddress());
        }

        return lists.size();
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

        if (list != null) {
            final MailinglistType type = list.getListType();
            if (type == MailinglistType.PRIVATE_LIST) {
                entity.setMayWrite(userGroup.getWriteToPrivateList());

                if (!userGroup.getOnPrivateList()) {
                    entity.setStatus(UserStatus.SUSPENDED);
                }
            } else if (!userGroup.getOnPublicList() && ((type == MailinglistType.PUBLIC_LIST) || (type == MailinglistType.LIMITED_ALIAS))) {
                entity.setStatus(UserStatus.SUSPENDED);
            }
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
    private MailinglistEntity createGroupList(final GroupEntity group, final MailinglistType type) {
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
            entity.setListAddress(listname + settings.getPublicMailAddress());
            entity.setMailReply(MailReply.REPLY_TO_SENDER);
        } else {
            entity.setSubjectPrefix(group.getGroupName());
            entity.setListType(type);

            if (type == MailinglistType.PRIVATE_LIST) {
                // Only the Private List option is for closes mailing lists, all
                // other is for public lists.
                entity.setListAddress(listname + settings.getPrivateMailAddress());
                entity.setMailReply(group.getPrivateReplyTo());
            } else {
                entity.setListAddress(listname + settings.getPublicMailAddress());
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
                    final Pattern pattern = Pattern.compile('@' + settings.getPublicMailAddress());
                    address = pattern.matcher(alias).replaceAll("");
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
