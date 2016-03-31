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
package net.iaeste.iws.persistence;

import net.iaeste.iws.api.dtos.exchange.Employer;
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

    List<String> findOfferRefNoForEmployers(List<Employer> employers);
}
