/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.util.Paginatable;
import net.iaeste.iws.persistence.views.AttachedFileView;
import net.iaeste.iws.persistence.views.EmployerView;
import net.iaeste.iws.persistence.views.OfferView;
import net.iaeste.iws.persistence.views.StudentView;

import java.util.List;

/**
 * This DAO contains the various views that exists, and is used mainly for
 * searches for Objects using a read-only database connection, i.e. no
 * transaction.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface ViewsDao {

    EmployerView findEmployer(Long groupId, String externalId);

    List<EmployerView> findEmployers(Long groupId, Paginatable page);

    List<EmployerView> findEmployers(Long groupId, Paginatable page, String partialName);

    List<OfferView> findAllOffers(Authentication authentication, Integer exchangeYear);

    List<StudentView> findStudentsForMemberGroup(Long groupId, Paginatable page);

    List<AttachedFileView> findAttachments(String table, Long id);
}
