/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.ApplicationView
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.persistence.views;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findForeignApplicationsByOfferExternalId",
                query = "select v from ApplicationView v " +
                        "where v.offerOwnerGroupId = :offerOwnerId " +
                        "  and v.offer.externalId = :oeid "),
        @NamedQuery(name = "view.findDomesticApplicationsByOfferExternalId",
                query = "select v from ApplicationView v " +
                        "where v.applicationOwnerGroupId = :applicationOwnerId " +
                        "  and v.offer.externalId = :oeid "),
})
@Table(name = "application_view")
public class ApplicationView extends AbstractView<ApplicationView> {

    @Id
    @Column(name = "application_id", insertable = false, updatable = false)
    private Long id = null;

    @Column(name = "application_owner_group_id", insertable = false, updatable = false)
    private Long applicationOwnerGroupId = null;

    @Column(name = "offer_owner_group_id", insertable = false, updatable = false)
    private Long offerOwnerGroupId = null;

    @Embedded
    private EmbeddedApplication application = null;

    @Embedded
    private EmbeddedAddress homeAddress = null;

    @Embedded
    private EmbeddedAddress2 termsAddress = null;

    @Embedded
    private EmbeddedCountry nationality = null;

    @Embedded
    private EmbeddedOffer offer = null;

    @Embedded
    private EmbeddedStudent student = null;

    @Embedded
    private EmbeddedUser user = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setApplicationOwnerGroupId(final Long applicationOwnerGroupId) {
        this.applicationOwnerGroupId = applicationOwnerGroupId;
    }

    public Long getApplicationOwnerGroupId() {
        return applicationOwnerGroupId;
    }

    public void setOfferOwnerGroupId(final Long offerOwnerGroupId) {
        this.offerOwnerGroupId = offerOwnerGroupId;
    }

    public Long getOfferOwnerGroupId() {
        return offerOwnerGroupId;
    }

    public void setApplication(final EmbeddedApplication application) {
        this.application = application;
    }

    public EmbeddedApplication getApplication() {
        return application;
    }

    public void setHomeAddress(final EmbeddedAddress homeAddress) {
        this.homeAddress = homeAddress;
    }

    public EmbeddedAddress getHomeAddress() {
        return homeAddress;
    }

    public void setTermsAddress(final EmbeddedAddress2 termsAddress) {
        this.termsAddress = termsAddress;
    }

    public EmbeddedAddress2 getTermsAddress() {
        return termsAddress;
    }

    public void setNationality(final EmbeddedCountry nationality) {
        this.nationality = nationality;
    }

    public EmbeddedCountry getNationality() {
        return nationality;
    }

    public void setOffer(final EmbeddedOffer offer) {
        this.offer = offer;
    }

    public EmbeddedOffer getOffer() {
        return offer;
    }

    public void setStudent(final EmbeddedStudent student) {
        this.student = student;
    }

    public EmbeddedStudent getStudent() {
        return student;
    }

    public void setUser(final EmbeddedUser user) {
        this.user = user;
    }

    public EmbeddedUser getUser() {
        return user;
    }

    // =========================================================================
    // Standard View Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ApplicationView)) {
            return false;
        }

        // As the view is reading from the current data model, and the Id is
        // always unique. It is sufficient to compare against this field.
        final ApplicationView that = (ApplicationView) obj;
        return id.equals(that.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final ApplicationView a) {
        final int result = id.compareTo(a.id);

        return sortAscending ? result : -result;
    }
}
