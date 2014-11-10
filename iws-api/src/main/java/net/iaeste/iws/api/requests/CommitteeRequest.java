/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.CommitteeRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.Group;
import net.iaeste.iws.api.dtos.User;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CommitteeRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    // The Id of the Country to create a new Cooperating Institution for
    private String countryId = null;
    // The name of the Institution to use when creating a new Cooperating Institution
    private String institutionName = null;
    // Firstname of the new National Secreraty for a new Cooperating Institution
    private String firstname = null;
    // Lastname of the new National Secreraty for a new Cooperating Institution
    private String lastname = null;
    // Username of the new National Secreraty for a new Cooperating Institution
    private String username = null;
    // National Committee (Staff) to update, upgrade, activate, suspend or delete
    private Group nationalCommittee = null;
    // New National Secretary for a given Committee
    private User nationalSecretary = null;
    // Action to perform on a Committee, by default we're assuming that it must
    // be updated, i.e. that the National Secretary must be set
    private Action action = Action.CHANGE_NS;

    /**
     * The actiona is needed to specify precisely which action is to be taken.
     */
    public enum Action {

        /** Create a new Cooperating Institution. */
        CREATE,

        /** Change the National Secretary. */
        CHANGE_NS,

        /** Upgrades a Committee from Cooperating Institution to Associate Member. */
        MERGE,

        /**
         * Upgrade a Committee from Cooperating Institution to Associate Member,
         * if there is currently only a single Cooperating Institution for the
         * given Country, or upgrades an Associate Member to Full Member.
         */
        UPGRADE,

        /** Activate a currently Suspended Committee. */
        ACTIVATE,

        /** Suspend a currently Active Committee. */
        SUSPEND,

        /** Delete a currently Suspended Committee. */
        DELETE
    }

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public CommitteeRequest() {
    }

    /**
     * Default Constructor, sets the type of Action to perform for the given
     * Committee.
     *
     * @param action Action to perform
     * @throws IllegalArgumentException if the action is null or not allowed
     */
    public CommitteeRequest(final Action action) throws IllegalArgumentException {
        setAction(action);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setCountryId(final String countryId) throws IllegalArgumentException {
        ensureNotNullAndExactLength("countryId", countryId, 2);
        this.countryId = countryId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setInstitutionName(final String institutionName) {
        ensureNotNullOrEmptyOrTooLong("institutionName", institutionName, 50);
        this.institutionName = institutionName;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setFirstname(final String firstname) {
        ensureNotNullOrEmptyOrTooLong("firstname", firstname, CreateUserRequest.USER_MAXIMUM_FIRSTNAME);
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(final String lastname) {
        ensureNotNullOrEmptyOrTooLong("lastname", lastname, CreateUserRequest.USER_MAXIMUM_LASTNAME);
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUsername(final String username) throws IllegalArgumentException {
        ensureNotNullAndValidEmail("username", username);
        ensureNotTooLong("username", username, CreateUserRequest.USER_MAXIMUM_USERNAME);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setNationalCommittee(final Group nationalCommittee) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("nationalCommittee", nationalCommittee);
        if (nationalCommittee.getGroupType() != GroupType.NATIONAL) {
            throw new IllegalArgumentException("Cannot process a Committee which is not having type " + GroupType.NATIONAL.getDescription());
        }
        this.nationalCommittee = nationalCommittee;
    }

    public Group getNationalCommittee() {
        return nationalCommittee;
    }

    public void setNationalSecretary(final User nationalSecretary) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("nationalSecretary", nationalSecretary);
        this.nationalSecretary = nationalSecretary;
    }

    public User getNationalSecretary() {
        return nationalSecretary;
    }

    public void setAction(final Action action) throws IllegalArgumentException {
        ensureNotNull("action", action);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "action", action);
        if (action != null) {
            switch (action) {
                case CREATE:
                    isNotNull(validation, "countryId", countryId);
                    isNotNull(validation, "institutionName", institutionName);
                    isNotNull(validation, "firstname", firstname);
                    isNotNull(validation, "lastname", lastname);
                    isNotNull(validation, "username", username);
                    break;
                case MERGE:
                    isNotNull(validation, "countryId", countryId);
                    isNotNull(validation, "nationalSecretary", nationalSecretary);
                    break;
                case CHANGE_NS:
                    // Updating means changing the current National Secretary,
                    // however doing so means internal checks for an existing
                    // new National Secretary or a potentional new National
                    // Secretary.
                case UPGRADE:
                case ACTIVATE:
                case SUSPEND:
                case DELETE:
                    isNotNull(validation, "nationalCommittee", nationalCommittee);
                    break;
            }
        }

        return validation;
    }
}
