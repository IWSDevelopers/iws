/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FetchGroupResponse
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.UserGroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FetchGroupResponse", propOrder = { "group", "members", "students", "subGroups" })
public final class FetchGroupResponse extends FallibleResponse {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true) private Group group = null;
    @XmlElement(required = true, nillable = true) private List<UserGroup> members = null;
    @XmlElement(required = true, nillable = true) private List<UserGroup> students = null;
    @XmlElement(required = true, nillable = true) private List<Group> subGroups = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchGroupResponse() {
    }

    /**
     * Default Constructor, for creating this Object with a Group and all known
     * Users and Subgroups.
     *
     * @param group      Requested Group
     * @param members List of Users belonging to the Group, with relation details
     * @param subGroups  List of SubGroups belonging to the Group
     */
    public FetchGroupResponse(final Group group, final List<UserGroup> members, final List<Group> subGroups) {
        this.group = group;
        this.members = members;
        this.subGroups = subGroups;
    }

    /**
     * Error Constructor.
     *
     * @param error    IWS Error Object
     * @param message  Error Message
     */
    public FetchGroupResponse(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setGroup(final Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return new Group(group);
    }

    public void setMembers(final List<UserGroup> members) {
        this.members = members;
    }

    public List<UserGroup> getMembers() {
        return members;
    }

    public void setStudents(final List<UserGroup> students) {
        this.students = students;
    }

    public List<UserGroup> getStudents() {
        return students;
    }

    public void setSubGroups(final List<Group> subGroups) {
        this.subGroups = subGroups;
    }

    public List<Group> getSubGroups() {
        return subGroups;
    }

    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FetchGroupResponse)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final FetchGroupResponse that = (FetchGroupResponse) obj;

        if ((group != null) ? !group.equals(that.group) : (that.group != null)) {
            return false;
        }
        if ((members != null) ? !members.equals(that.members) : (that.members != null)) {
            return false;
        }
        if ((students != null) ? !students.equals(that.students) : (that.students != null)) {
            return false;
        }
        return !((subGroups != null) ? !subGroups.equals(that.subGroups) : (that.subGroups != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((group != null) ? group.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((members != null) ? members.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((students != null) ? students.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((subGroups != null) ? subGroups.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchGroupResponse{" +
                "group=" + group +
                ", members=" + members +
                ", students=" + students +
                ", subGroups=" + subGroups +
                '}';
    }
}
