/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.builders.Builder
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fitnesse.builders;

import net.iaeste.iws.common.utils.JSON;

/**
 * Class helps in building objects for later use in other tests.
 *
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public abstract class Builder<T> {
    protected T product;

    /**
     * Method is executed before every new row in a fitnesse table.
     */
    public abstract void reset();

    /**
     * Method is executed after setting all values from a fitnesse table.
     */
    public abstract void execute();

    /**
     * Object builded from set values.
     *
     * @return Serialized JSON representation of an object of type T.
     */
    public String result() {
        return JSON.serialize(product);
    }

    /**
     * Pretty printing of builded object.
     *
     * @return readable String representation of the object.
     */
    public String print() {
        return product.toString();
    }
}
