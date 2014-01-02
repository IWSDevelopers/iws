/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-migrate) - net.iaeste.iws.migrate.daos.IW3Dao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.migrate.daos;

import net.iaeste.iws.migrate.entities.IW3CountriesEntity;
import net.iaeste.iws.migrate.entities.IW3FacultiesEntity;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.migrate.entities.IW3Offer2GroupEntity;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface IW3Dao {

    IW3FacultiesEntity findFaculty(String name);

    List<IW3CountriesEntity> findAllCountries();

    List<IW3GroupsEntity> findAllGroups();

    List<IW3ProfilesEntity> findAllProfiles();

    List<IW3User2GroupEntity> findAllUserGroups();

    List<IW3OffersEntity> findAllOffers(int page, int size);

    List<IW3Offer2GroupEntity> findAllOfferGroups();
}
