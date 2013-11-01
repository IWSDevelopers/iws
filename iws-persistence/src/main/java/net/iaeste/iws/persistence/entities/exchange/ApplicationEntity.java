package net.iaeste.iws.persistence.entities.exchange;

import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.persistence.Externable;
import net.iaeste.iws.persistence.entities.AbstractUpdateable;
import net.iaeste.iws.persistence.entities.GroupEntity;
import net.iaeste.iws.persistence.entities.UserEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class ApplicationEntity extends AbstractUpdateable<ApplicationEntity> implements Externable<ApplicationEntity> {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "offer_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id = null;

    @Column(name = "external_id", length = 36, unique = true, nullable = false, updatable = false)
    private String externalId = null;

    @ManyToOne(targetEntity = OfferEntity.class)
    @JoinColumn(name = "offer_id", nullable = false)
    private OfferEntity offer = null;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(nullable = true, name = "group_id")
    private GroupEntity group = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "modified_by", nullable = false)
    private UserEntity modifiedBy = null;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy = null;

    /**
     * Last time the Entity was modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modified = new Date();

    /**
     * Timestamp when the Entity was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created = new Date();

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExternalId() {
        return externalId;
    }

    public void setOffer(final OfferEntity offer) {
        this.offer = offer;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public void setGroup(final GroupEntity group) {
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(final Date modified) {
        this.modified = modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModified() {
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // Standard Entity Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diff(final ApplicationEntity obj) {
        int changes = 0;

        //changes += different("aaa", "bbb");

        return changes == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final ApplicationEntity obj) {
        // don't merge if objects are not the same entity
        if ((id != null) && (obj != null) && externalId.equals(obj.externalId)) {
            //merge fields
        }
    }
}