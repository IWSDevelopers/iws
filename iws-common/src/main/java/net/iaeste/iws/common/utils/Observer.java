/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.Observer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.utils;

import net.iaeste.iws.common.configuration.Settings;

import javax.persistence.EntityManager;

/**
 * The Observer interface is used by the "observers" in the
 * <a href="http://en.wikipedia.org/wiki/Observer_pattern">Observer Design
 * Pattern</a>. The Observable interface is used by those classes, who wishes
 * to become observable or "subjects".
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @see <a href="http://en.wikipedia.org/wiki/Observer_pattern">Observer Design Pattern</a>
 */
public interface Observer {

    /**
     * Whenever an observer needs to be notified of a change, this method is
     * invoked with the subject as parameter. The method is invoked by the
     * Observable notityObservers method.
     *
     * @param  subject  the observable object
     */
    void update(Observable subject);

    //TODO better identifier, UUID?
    Long getId();
    void setId(Long id);

    /**
     * Method to initialize Observer
     *
     * @param iwsEntityManager
     * @param mailingEntityManager
     * @param settings
     */
    void init(EntityManager iwsEntityManager, EntityManager mailingEntityManager, Settings settings);
}
