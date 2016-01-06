/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.ViewsDao
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

import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.views.DomesticOfferStatisticsView;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.ForeignOfferStatisticsView;
import net.iaeste.iws.persistence.views.OfferSharedToGroupView;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.SharedOfferView;
import net.iaeste.iws.persistence.views.StudentView;

import java.util.List;
import java.util.Set;

/**
 * This DAO contains the various views that exists, and is used mainly for
 * searches for Objects using a read-only database connection, i.e. no
 * transaction.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface ViewsDao {

    List<ForeignOfferStatisticsView> findForeignOfferStatistics(GroupEntity group, Integer year);

    List<DomesticOfferStatisticsView> findDomesticOfferStatistics(GroupEntity group, Integer year);

    EmployerView findEmployer(Long groupId, String externalId);

    List<EmployerView> findEmployers(Long groupId, Paginatable page);

    List<EmployerView> findEmployers(Long groupId, Paginatable page, String partialName);

    List<OfferView> findDomesticOffers(Authentication authentication, Integer exchangeYear, Set<OfferState> states, Boolean retrieveCurrentAndNextExchangeYear, Paginatable page);

    List<OfferView> findDomesticOffersByOfferIds(Authentication authentication, Integer exchangeYear, List<String> offerIds);

    List<SharedOfferView> findSharedOffers(Authentication authentication, Integer exchangeYear, Set<OfferState> states, Boolean retrieveCurrentAndNextExchangeYear, Paginatable page);

    List<SharedOfferView> findSharedOffersByOfferIds(Authentication authentication, Integer exchangeYear, List<String> offerIds);

    List<OfferSharedToGroupView> findSharedToGroup(Long parentId, Integer exchangeYear, List<String> externalOfferIds);

    List<StudentView> findStudentsForMemberGroup(Long groupId, Paginatable page);
}
