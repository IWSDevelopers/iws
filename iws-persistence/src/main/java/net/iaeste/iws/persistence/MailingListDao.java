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

import java.util.List;

/**
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
     * @return List of current mailing lists
     */
    List<MailinglistEntity> findMailinglists(GroupEntity group);

    MailinglistEntity findListByAddress(String address);

    List<MailinglistEntity> findListsByGroup(GroupEntity group);

    List<GroupEntity> findUnprocessedGroups();
    List<UserGroupEntity> findUnprocessedSubscriptions();


    int activateMailinglists();
    int suspendMailinglists();
    int deleteDeadMailinglists();
    int deleteMailingLists(GroupEntity group);

    int activateMailinglistSubscriptions();
    int suspendMailinglistSubscriptions();
    int deleteDeadMailinglistSubscriptions();

    List<AliasEntity> findAliases();

    int deleteMailinglistSubscriptions(MailinglistEntity mailingList);

    void deleteMailingList(MailinglistEntity mailinglist);

    List<UserGroupEntity> findMissingNcsSubscribers();

    List<UserGroupEntity> findMissingAnnounceSubscribers();
}
