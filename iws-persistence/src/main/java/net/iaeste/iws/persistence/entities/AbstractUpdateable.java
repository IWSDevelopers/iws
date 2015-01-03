/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.entities.AbstractUpdateable
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.entities;

import net.iaeste.iws.api.constants.IWSConstants;

/**
 * To Implement the diff method required by the Updateable interface, we have
 * this little Abstract class that contains helper methods.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public abstract class AbstractUpdateable<T> implements Updateable<T> {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Returns 1 (one) if the two Objects are different, otherwise a 0 (zero)
     * is returned.
     *
     * @param first  The Object to compare against
     * @param second The other Object to compare with
     * @return 1 (one) if the Objects are different, otherwise 0 (zero)
     */
    protected static <T> int different(final T first, final T second) {
        // To make it easier to read, the conditional expression is expanded
        final int result;

        if (first != null) {
            if (first.equals(second)) {
                result = 0;
            } else {
                result = 1;
            }
        } else {
            if (second == null) {
                result = 0;
            } else {
                result = 1;
            }
        }

        return result;

        //return (first != null ? first.equals(second) : second == null) ? 0 : 1;
    }
}
