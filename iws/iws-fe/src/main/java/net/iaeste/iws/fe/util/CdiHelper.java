/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.util.CdiHelper
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fe.util;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;

/**
 * Helper class for programatic injection of resources.
 * Can be used if e.g. injection via @Inject is not available.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class CdiHelper {

    /**
     * Injects resources marked with @Inject into the given
     * object
     *
     * @param injectionObject the Object into which to inject
     */
    public static void programmaticInjection(Object injectionObject) {
        BeanManager beanManager = BeanManagerProvider.getBeanManager();
        AnnotatedType annotatedType = beanManager.createAnnotatedType(injectionObject.getClass());
        InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotatedType);
        CreationalContext creationalContext = beanManager.createCreationalContext(null);
        injectionTarget.inject(injectionObject, creationalContext);
        creationalContext.release();
    }

}
