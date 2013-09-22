package net.iaeste.iws.persistence.jpa;

import net.iaeste.iws.persistence.MailingListDao;
import net.iaeste.iws.persistence.entities.mailing_list.MailingListEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class MailingListJpaDao extends BasicJpaDao implements MailingListDao {

    /**
     * Default Constructor.
     *
     * @param entityManager Entity Manager instance to use
     */
    public MailingListJpaDao(final EntityManager entityManager) {
        super(entityManager);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public MailingListEntity findPublicMailingList(final String externalId) {
        final Query query = entityManager.createNamedQuery("mailing_list.findPublicListByExternalId");
        query.setParameter("eid", externalId);
        final List<MailingListEntity> found = query.getResultList();

        return found.size() == 1 ? found.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailingListEntity findPrivateMailingList(final String externalId) {
        final Query query = entityManager.createNamedQuery("mailing_list.findPrivateListByExternalId");
        query.setParameter("eid", externalId);
        final List<MailingListEntity> found = query.getResultList();

        return found.size() == 1 ? found.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer clearPublicSubsription(String externalId) {
        final Query query = entityManager.createNamedQuery("mailing_list.clearPublicSubsriptionByExternalId");
        query.setParameter("eid", externalId);
        return query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer clearPrivateSubsription(String externalId) {
        final Query query = entityManager.createNamedQuery("mailing_list.clearPrivateSubsriptionByExternalId");
        query.setParameter("eid", externalId);
        return query.executeUpdate();
    }
}
