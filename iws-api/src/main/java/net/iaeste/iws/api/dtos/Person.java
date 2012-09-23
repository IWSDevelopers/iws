/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Person
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.exceptions.VerificationException;
import net.iaeste.iws.api.requests.Verifiable;
import net.iaeste.iws.api.responses.AbstractResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * The Person Object contains the non-system specific information or private
 * information for a person. Although the Person is a core part of the User
 * Object, it is meant for all-round purposes.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CastToConcreteClass
 */
public final class Person extends AbstractResponse implements Verifiable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String alternateEmail = null;

    /**
     * Emprty Constructor.
     */
    public Person() {
    }

    /**
     * Copy Constructor.
     *
     * @param person  Person Object to copy
     */
    public Person(final Person person) {
        if (person != null) {
            alternateEmail = person.alternateEmail;
        }
    }

    public void setAlternateEmail(final String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verify() throws VerificationException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);
        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Person)) {
            return false;
        }

        final Person person = (Person) obj;

        return !(alternateEmail != null ? !alternateEmail.equals(person.alternateEmail) : person.alternateEmail != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (alternateEmail != null ? alternateEmail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "alternateEmail='" + alternateEmail + '\'' +
                '}';
    }
}
