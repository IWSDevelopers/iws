/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.TypeOfWork
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums.exchange;

/**
 * All types of work that can be selected in the system
 *
 * @author  Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection EnumeratedConstantNamingConvention
 */
public enum TypeOfWork {

    R("Research and Development"),
    P("Professional"),
    W("Working Environment"),
    N("Non Specific");

    private final String description;

    TypeOfWork(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Same as valueOf but for when an argument is null then null is returned.
     *
     * @param typeOfWork String which should be value of TypeOfWork
     * @return null or enum value
     */
    public static TypeOfWork toValue(final String typeOfWork) {
        return typeOfWork == null ? null : valueOf(typeOfWork);
    }
}
