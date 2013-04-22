/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.views.EmployerInformationView
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

import net.iaeste.iws.api.constants.IWSConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection CompareToUsesNonFinalVariable, AssignmentToDateFieldFromParameter
 */
@NamedQueries({
        // TODO 2013-04-20 by Pavel; @Kim: What should be the prefix of the query? Since it returns the whole offer,
        //                                 I kept 'offer'
        @NamedQuery(
                name = "offer.findByGroupAndEmployerName",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.id in (select ei.id from EmployerInformationView ei where ei.employerName = :employerName and ei.groupId = :gid)"),
        // TODO 2013-04-20 by Pavel; @Kim: What should be the prefix of the query? Since it returns the whole offer,
        //                                 I kept 'offer'
        @NamedQuery(
                name = "offer.findByGroupAndLikeEmployerName",
                query = "select o from OfferEntity o " +
                        "where o.group.id = :gid" +
                        "  and o.id in (select ei.id from EmployerInformationView ei where lower(ei.employerName) like :employerName and ei.groupId = :gid)")
})
@Entity
@Table(name = "employer_information")
public class EmployerInformationView extends AbstractView<EmployerInformationView> {

    /** {@see IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @Id
    private Long id = null;

    @Column(name = "employer_name")
    private String employerName = null;

    @Column(name = "group_id")
    private Long groupId = null;

    @Column(name = "changed_on")
    private Date changedOn = null;

    /**
     * Empty Constructor, required by JPA.
     */
    public EmployerInformationView() {
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEmployerName(final String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setChangedOn(final Date changedOn) {
        this.changedOn = changedOn;
    }

    public Date getChangedOn() {
        return changedOn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EmployerInformationView)) {
            return false;
        }

        final EmployerInformationView that = (EmployerInformationView) obj;

        if (changedOn != null ? !changedOn.equals(that.changedOn) : that.changedOn != null) {
            return false;
        }
        if (employerName != null ? !employerName.equals(that.employerName) : that.employerName != null) {
            return false;
        }
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) {
            return false;
        }

        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (employerName != null ? employerName.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (groupId != null ? groupId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (changedOn != null ? changedOn.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final EmployerInformationView o) {
        final int result = id.compareTo(o.id);

        return sortAscending ? result : -result;
    }

}
