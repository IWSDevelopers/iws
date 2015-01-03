/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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

import net.iaeste.iws.api.enums.exchange.OfferState;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "view.findForeignApplicationsByGroupAndExternalId",
                query = "select v from ApplicationView v " +
                        "where v.offerGroupId = :gid" +
                        "  and v.offerExternalId = :eoid"),
        @NamedQuery(name = "view.findDomesticApplicationByGroupAndExternalId",
                query = "select v from ApplicationView v " +
                        "where v.applicationGroupId = :gid" +
                        "  and v.offerExternalId = :eoid"),
})
@Table(name = "application_view")
public class ApplicationView extends AbstractView<ApplicationView> {

    @Id
    @Column(name = "application_id", insertable = false, updatable = false)
    private Long id = null;

    @Column(name = "application_group_id", insertable = false, updatable = false)
    private Long applicationGroupId = null;

    @Column(name = "offer_group_id", insertable = false, updatable = false)
    private Long offerGroupId = null;

    @Column(name = "offer_external_id", insertable = false, updatable = false)
    private String offerExternalId = null;

    @Column(name = "offer_ref_no", insertable = false, updatable = false)
    private String offerRefNo = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_status", insertable = false, updatable = false)
    private OfferState offerState = null;

    @Embedded
    private EmbeddedApplication application = null;

    @Embedded
    private EmbeddedHomeAddress homeAddress = null;

    @Embedded
    private EmbeddedTermAddress termsAddress = null;

    @Embedded
    private EmbeddedCountry nationality = null;

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

    public void setApplicationGroupId(final Long applicationGroupId) {
        this.applicationGroupId = applicationGroupId;
    }

    public Long getApplicationGroupId() {
        return applicationGroupId;
    }

    public void setOfferGroupId(final Long offerGroupId) {
        this.offerGroupId = offerGroupId;
    }

    public Long getOfferGroupId() {
        return offerGroupId;
    }

    public void setOfferExternalId(final String offerExternalId) {
        this.offerExternalId = offerExternalId;
    }

    public String getOfferExternalId() {
        return offerExternalId;
    }

    public void setOfferRefNo(final String offerRefNo) {
        this.offerRefNo = offerRefNo;
    }

    public String getOfferRefNo() {
        return offerRefNo;
    }

    public void setOfferState(final OfferState offerState) {
        this.offerState = offerState;
    }

    public OfferState getOfferState() {
        return offerState;
    }

    public void setApplication(final EmbeddedApplication application) {
        this.application = application;
    }

    public EmbeddedApplication getApplication() {
        return application;
    }

    public void setHomeAddress(final EmbeddedHomeAddress homeAddress) {
        this.homeAddress = homeAddress;
    }

    public EmbeddedHomeAddress getHomeAddress() {
        return homeAddress;
    }

    public void setTermsAddress(final EmbeddedTermAddress termsAddress) {
        this.termsAddress = termsAddress;
    }

    public EmbeddedTermAddress getTermsAddress() {
        return termsAddress;
    }

    public void setNationality(final EmbeddedCountry nationality) {
        this.nationality = nationality;
    }

    public EmbeddedCountry getNationality() {
        return nationality;
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
    public int compareTo(final ApplicationView o) {
        final int result = id.compareTo(o.id);

        return sortAscending ? result : -result;
    }
}
