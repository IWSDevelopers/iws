/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.CommitteeDao
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

import net.iaeste.iws.api.enums.GroupStatus;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.persistence.entities.UserGroupEntity;

import java.util.List;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public interface CommitteeDao extends BasicDao {

    List<UserGroupEntity> findCommitteesByContryIds(List<String> countryIds, Set<GroupStatus> statuses);

    List<UserGroupEntity> findCommitteesByMembership(Membership membership, Set<GroupStatus> statuses);

    List<UserGroupEntity> findAllCommittees(Set<GroupStatus> statuses);
}
