/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.annotations.IWSBean
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.cdi;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * CDI excepts that all classes are uniquely identifiable, using type checks. If
 * multiple beans exists of the same type, then it is not able to correctly find
 * and inject this. By introducing a new Annotation for our Beans, it is
 * possible to better control what Bean is expected. CDI is used for both the
 * Bean definition, and for the Bean injection, to better control it.<br />
 *   The annotation takes a type as value, which is then used to uniquely
 * identify the Bean. For more information about this approach, please see the
 * Blog from <a href="http://antoniogoncalves.org/2011/04/07/injection-with-cdi-part-i/">Antonia goncalves</a>.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR })
public @interface IWSBean {

    Type value() default Type.PRIMARY;

    /**
     * We have different types of Qualifiers for our Beans, so we can deploy them
     * properly. The different types are controlled via this Enum.
     */
    enum Type {

        /** Standard Beans for IWS. */
        PRIMARY,

        /** For secondary or external Resources / Beans. */
        SECONDARY
    }
}
