/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.migrate.entities.IW3FileEntity;
import net.iaeste.iws.migrate.entities.IW3GroupsEntity;
import net.iaeste.iws.migrate.entities.IW3Offer2GroupEntity;
import net.iaeste.iws.migrate.entities.IW3OffersEntity;
import net.iaeste.iws.migrate.entities.IW3ProfilesEntity;
import net.iaeste.iws.migrate.entities.IW3User2GroupEntity;

import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface IW3Dao {

    List<IW3CountriesEntity> findAllCountries();

    List<IW3GroupsEntity> findAllGroups();

    Long countProfiles();
    List<IW3ProfilesEntity> findProfiles(int page);

    Long countUserGroups();
    List<IW3User2GroupEntity> findUserGroups(int page);

    Long countOffers();
    List<IW3OffersEntity> findOffers(int page);

    Long countOfferGroups();
    List<IW3Offer2GroupEntity> findOfferGroups(int page);

    Long countFiles();
    List<IW3FileEntity> findFiles(int page);
}
