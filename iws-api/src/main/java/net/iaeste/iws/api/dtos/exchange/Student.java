/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.Student
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * Standard IAESTE Student.
 *
 * @author  Teis Lindemark / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Student extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private User user = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Student() {
    }

    /**
     * Default Constructor.
     *
     * @param user User
     */
    public Student(final User user) {
        setUser(user);
    }

    /**
     * Copy Constructor.
     *
     * @param student Student Object to copy
     */
    public Student(final Student student) {
        if (student != null) {
            user = student.user;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setUser(final User user) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("user", user);
        this.user = new User(user);
    }

    public User getUser() {
        return new User(user);
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "user", user);

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
        if (!(obj instanceof Student)) {
            return false;
        }

        final Student student = (Student) obj;
        return !(user != null ? !user.equals(student.user) : student.user != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Student{" +
                "user=" + user +
                '}';
    }
}
