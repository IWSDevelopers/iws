package net.iaeste.iws.client.spring;

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

    private static EntityManager instance;

    public synchronized static EntityManager getInstance() {
        if (instance == null) {
            ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
            EntityManagerFactory factory = (EntityManagerFactory) applicationContext.getBean(EntityManagerFactory.class);
            instance = factory.createEntityManager();
        }
        return instance;
    }
}
