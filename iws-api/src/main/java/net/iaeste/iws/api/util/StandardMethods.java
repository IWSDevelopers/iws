/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.StandardMethods
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>All DTOs, Requests and Response Classes, which is considered basic
 * classes, must all implement some standard functionality, such as the equals
 * and hashCode methods as well as toString. This annotation is designed to act
 * as a way to control what will be part of which implementation, so rather than
 * having some very complex logic in the classes, we can simplify it with a set
 * of standard methods, which via Reflection is implementing the
 * functionality.</p>
 *
 * <p>Any class containing this annotation will automatically be a standard
 * Class, meaning that if the method is present, the fields will all
 * automatically be included, unless implicitly annotated with one of the
 * alternative For values.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface StandardMethods {

    For value() default For.ALL;

    /**
     * Enumerated type for the fields in a Class. It tells if the field is to
     * be used for either hash methods (equals and hashCode), toString or all.
     */
    enum For {

        /**
         * Indicates that this field should not be used in neither the hash
         * methods nor the toString method.
         */
        NONE,

        /**
         * The equals and hashCode methods must always be paired, meaning that
         * if one exist, then the other must also be present. The hash entry
         * simply indicated that the field is required for both. If this value
         * is chosen, then the field is only suitable for the hash methods, not
         * the toString method.
         */
        HASH,

        /**
         * The toString, is used to generate a way to represent the content for
         * logging or displaying in a uniform way. Note, that not all logic is
         * suitable for logging and displaying. If this value is chosen, it
         * means that the field is only suitable for the toString method, not
         * the hash methods.
         */
        TOSTRING,

        /**
         * Indicating that the field is to be used for both the hash methods and
         * the toString method.
         */
        ALL
    }
}
