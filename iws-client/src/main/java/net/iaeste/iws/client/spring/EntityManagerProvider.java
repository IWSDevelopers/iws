package net.iaeste.iws.client.spring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
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
