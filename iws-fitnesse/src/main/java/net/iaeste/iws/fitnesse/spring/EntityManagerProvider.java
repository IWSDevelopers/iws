/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.spring.EntityManagerProvider
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse.spring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public final class EntityManagerProvider {

    private static EntityManagerProvider instance = null;
    private static final Object LOCK = new Object();
    private final EntityManager entityManager;

    private EntityManagerProvider() {
        final ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        final EntityManagerFactory factory = applicationContext.getBean(EntityManagerFactory.class);

        entityManager = factory.createEntityManager();
    }

    public static EntityManagerProvider getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new EntityManagerProvider();
            }

            return instance;
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
