/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.OfferStatistics
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.constants.IWSConstants;

import java.io.Serializable;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class OfferStatistics implements Serializable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Integer sharedOffers = 0;
    private Integer openOffers = 0;
    private Integer newOffers = 0;
    private Integer atEmployer = 0;
    private Integer closedOffers = 0;
    private Integer applications = 0;
    private Integer applicationRejected = 0;
    private Integer nominations = 0;
    private Integer deletedOffers = 0;
    private Integer completedOffers = 0;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
    * Empty Constructor, to use if the setters are invoked. This is required
    * for WebServices to work properly.
    */
    public OfferStatistics() {
    }

    /**
    * Copy Constructor.
    *
    * @param statistics Offer Statistics Object to copy
    */
    public OfferStatistics(final OfferStatistics statistics) {
        if (statistics != null) {
            sharedOffers = statistics.sharedOffers;
            openOffers = statistics.openOffers;
            newOffers = statistics.newOffers;
            atEmployer = statistics.atEmployer;
            closedOffers = statistics.closedOffers;
            applications = statistics.applications;
            applicationRejected = statistics.applicationRejected;
            nominations = statistics.nominations;
            deletedOffers = statistics.deletedOffers;
            completedOffers = statistics.completedOffers;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setSharedOffers(final Integer sharedOffers) {
        this.sharedOffers = sharedOffers;
    }

    public Integer getSharedOffers() {
        return sharedOffers;
    }

    public void setOpenOffers(final Integer openOffers) {
        this.openOffers = openOffers;
    }

    public Integer getOpenOffers() {
        return openOffers;
    }

    public void setNewOffers(final Integer newOffers) {
        this.newOffers = newOffers;
    }

    public Integer getNewOffers() {
        return newOffers;
    }

    public void setAtEmployer(final Integer atEmployer) {
        this.atEmployer = atEmployer;
    }

    public Integer getAtEmployer() {
        return atEmployer;
    }

    public void setClosedOffers(final Integer closedOffers) {
        this.closedOffers = closedOffers;
    }

    public Integer getClosedOffers() {
        return closedOffers;
    }

    public void setApplications(final Integer applications) {
        this.applications = applications;
    }

    public Integer getApplications() {
        return applications;
    }

    public void setApplicationRejected(final Integer applicationRejected) {
        this.applicationRejected = applicationRejected;
    }

    public Integer getApplicationRejected() {
        return applicationRejected;
    }

    public void setNominations(final Integer nominations) {
        this.nominations = nominations;
    }

    public Integer getNominations() {
        return nominations;
    }

    public void setDeletedOffers(final Integer deletedOffers) {
        this.deletedOffers = deletedOffers;
    }

    public Integer getDeletedOffers() {
        return deletedOffers;
    }

    public void setCompletedOffers(final Integer completedOffers) {
        this.completedOffers = completedOffers;
    }

    public Integer getCompletedOffers() {
        return completedOffers;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OfferStatistics)) {
            return false;
        }

        final OfferStatistics that = (OfferStatistics) obj;

        if (applicationRejected != null ? !applicationRejected.equals(that.applicationRejected) : that.applicationRejected != null) {
            return false;
        }
        if (applications != null ? !applications.equals(that.applications) : that.applications != null) {
            return false;
        }
        if (atEmployer != null ? !atEmployer.equals(that.atEmployer) : that.atEmployer != null) {
            return false;
        }
        if (closedOffers != null ? !closedOffers.equals(that.closedOffers) : that.closedOffers != null) {
            return false;
        }
        if (completedOffers != null ? !completedOffers.equals(that.completedOffers) : that.completedOffers != null) {
            return false;
        }
        if (deletedOffers != null ? !deletedOffers.equals(that.deletedOffers) : that.deletedOffers != null) {
            return false;
        }
        if (newOffers != null ? !newOffers.equals(that.newOffers) : that.newOffers != null) {
            return false;
        }
        if (nominations != null ? !nominations.equals(that.nominations) : that.nominations != null) {
            return false;
        }
        if (openOffers != null ? !openOffers.equals(that.openOffers) : that.openOffers != null) {
            return false;
        }

        return !(sharedOffers != null ? !sharedOffers.equals(that.sharedOffers) : that.sharedOffers != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (sharedOffers != null ? sharedOffers.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (openOffers != null ? openOffers.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (newOffers != null ? newOffers.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (atEmployer != null ? atEmployer.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (closedOffers != null ? closedOffers.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (applications != null ? applications.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (applicationRejected != null ? applicationRejected.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (nominations != null ? nominations.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (deletedOffers != null ? deletedOffers.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (completedOffers != null ? completedOffers.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OfferStatistics{" +
                "sharedOffers=" + sharedOffers +
                ", openOffers=" + openOffers +
                ", newOffers=" + newOffers +
                ", atEmployer=" + atEmployer +
                ", closedOffers=" + closedOffers +
                ", applications=" + applications +
                ", applicationRejected=" + applicationRejected +
                ", nominations=" + nominations +
                ", deletedOffers=" + deletedOffers +
                ", completedOffers=" + completedOffers +
                '}';
    }
}
