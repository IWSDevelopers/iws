/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.Observable
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

/**
 * Although there already exists a Java.util implementation of the Observer
 * design pattern, we're adding our own to provide logging/debugging mechanisms.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public interface Observable {

    /**
     * Adds the Observer object to the list of observers to be notified by the
     * subject.
     *
     * @param observer Observer object to be nofified by the subject
     */
    void addObserver(Observer observer);

    /**
     * Deletes an Observer object from the list of observers. This means that
     * the observer object will no longer receive notifications by the subject.
     *
     * @param observer Observer object to be removed
     */
    void deleteObserver(Observer observer);

    /**
     * Iterates over the list of observers, and invokes their update methods,
     * thus notifying them of the update.
     */
    void notifyObservers();
}
