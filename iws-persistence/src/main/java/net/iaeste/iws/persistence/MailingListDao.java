/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.MailingListDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence;

import net.iaeste.iws.persistence.entities.AliasEntity;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.MailinglistEntity;
import net.iaeste.iws.persistence.entities.UserGroupEntity;
import net.iaeste.iws.persistence.entities.UserMailinglistEntity;

import java.util.List;

/**
 * <p>Data Access Object for the IWS Mailing List And Subscription System. This
 * DAO contain all the functionality requred for handling the Mail System, from
 * ensuring that data is present, update it if needed and remove deprecated
 * data.</p>
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface MailingListDao extends BasicDao {

    /**
     * <p>Finds all the Mailing lists, which a Group has, this include both
     * public, private lists as well as alias lists regardless of their current
     * state.</p>
     *
     * @param group The group to find the mailing lists for
     * @return List of current mailing lists for the given Group
     */
    List<MailinglistEntity> findMailingList(GroupEntity group);

    /**
     * <p>Finds a Mailing List, matching the given Address. If no such list is
     * found, a null is returned.</p>
     *
     * @param address The Mailing List Address
     * @return Mailing List Entity or null
     */
    MailinglistEntity findMailingList(String address);

    /**
     * <p>Retrieves the list of Aliases currently in the Database. An alias is a
     * different name for a Group, which may exist for a limited time or
     * permanently, Aliases is always being processed completely.</p>
     *
     * @return List of IWS Group Aliases
     */
    List<AliasEntity> findAliases();

    /**
     * <p>Retrieves a specific Subscription to a Mailing List. If no such
     * Subscription exists, a null is returned.</p>
     *
     * @param list       The Mailing List to find the Subscriber for
     * @param subscriber The Subscribing User Group Relation
     * @return Mailing List Subscription or null
     */
    UserMailinglistEntity findSubscription(MailinglistEntity list, UserGroupEntity subscriber);

    /**
     * <p>Retrieves a list of Groups, for whom no Mailing Lists currently exist.
     * Only those Groups where nothing is currently processing in the Mailing
     * List table will be returned, nothing else.</p>
     *
     * @return List of Groups with no existing Mailing Lists
     */
    List<GroupEntity> findUnprocessedGroups();

    /**
     * <p>Finds a list of not-yet processed UserGroup Relations, to make sure that
     * Subscriptions is created for these.</p>
     *
     * @return List of Missing Mailing List Subscribers
     */
    List<UserGroupEntity> findUnprocessedSubscriptions();

    /**
     * <p>The most important virtual Mailing List, is the NC's Mailing List,
     * which is the general purpose discussion and announcement list for the
     * Board, National Committees, Staff & International Groups.</p>
     *
     * @return List of missing NC's List Subscribers
     */
    List<UserGroupEntity> findMissingNcsSubscribers();

    /**
     * <p>Whenever someone is no longer allowed to be on the NC's Mailing List,
     * he or she should be removed. This will retrieve a list of all those who
     * should be removed from the NC's Mailing List.</p>
     *
     * @return List of deprecated NC's Mailing List Subscribers
     */
    List<UserGroupEntity> findDeprecatedNcsSubscribers();

    /**
     * <p>Second Virtual Mailing List, is the Announce list, which is going to
     * all Users of the IntraWeb. This mailing list is restricted in who may
     * write to it, as only those on a public Administration Group List may
     * write to it.</p>
     *
     * <p>The goal of the Announce List, is to make sure that important
     * information, like System Downtime, is communicated to everybody.</p>
     *
     * @return List of missing Announce List Subscribers
     */
    List<UserGroupEntity> findMissingAnnounceSubscribers();

    /**
     * <p>Whenever user changes the UserName, the MailingList Subscriptions must
     * also be updated. The method will return the number of UserName's which
     * was updated. However, if a User is present in multiple Groups, the number
     * of actual records updated in the database may be higher.</p>
     *
     * @return Number of updated UserName's
     */
    int updateSubscribedAddress();

    /**
     * <p>Whenever a Mailing List is considered deprecated, i.e. it should no
     * longer exist, the Subscriptions to the Mailing List must also be removed.
     * This method will handle deleting all such records.</p>
     *
     * @param mailingList The Mailing List to remove Subscribers from
     * @return Number of removed Subscriptions
     */
    int deleteMailinglistSubscriptions(MailinglistEntity mailingList);

    int activateMailinglists();
    int suspendMailinglists();
    int deleteDeprecatedMailinglists();
    int deleteMailingLists(GroupEntity group);

    int activatePrivateMailinglistSubscriptions();
    int activatePublicMailinglistSubscriptions();
    int addWritePermission();
    int removeWritePermission();
    int suspendPrivateMailinglistSubscriptions();
    int suspendPublicMailinglistSubscriptions();
    int deleteDeprecatedMailinglistSubscriptions();
}
