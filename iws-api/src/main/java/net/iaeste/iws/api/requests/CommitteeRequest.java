/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
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
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.util.AbstractVerification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "committeeRequest", propOrder = { "countryId", "institutionName", "institutionAbbreviation", "firstname", "lastname", "username", "nationalCommittee", "nationalSecretary", "action" })
public final class CommitteeRequest extends AbstractVerification implements Actionable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Default allowed Actions for the Committee Request. */
    private static final Set<Action> ALLOWED = EnumSet.of(Action.CREATE, Action.CHANGE_NS, Action.UPDATE, Action.MERGE, Action.UPGRADE, Action.ACTIVATE, Action.SUSPEND, Action.DELETE);

    /** The Id of the Country to create a new Cooperating Institution for. */
    @XmlElement(required = true, nillable = true) private String countryId = null;
    /** The name of the Institution to use when creating a new Cooperating Institution. */
    @XmlElement(required = true, nillable = true) private String institutionName = null;
    /** The official Abbreviation for the Institution, for creating & updating. */
    @XmlElement(required = true, nillable = true) private String institutionAbbreviation = null;
    /** Firstname of the new National Secretary for a new Cooperating Institution. */
    @XmlElement(required = true, nillable = true) private String firstname = null;
    /** Lastname of the new National Secretary for a new Cooperating Institution. */
    @XmlElement(required = true, nillable = true) private String lastname = null;
    /** Username of the new National Secretary for a new Cooperating Institution. */
    @XmlElement(required = true, nillable = true) private String username = null;
    /** National Committee (Staff) to update, upgrade, activate, suspend or delete. */
    @XmlElement(required = true, nillable = true) private Group nationalCommittee = null;
    /** New National Secretary for a given Committee. */
    @XmlElement(required = true, nillable = true) private User nationalSecretary = null;

    /**
     * Action to perform on a Committee, by default we're assuming that it must
     * be updated, i.e. that the National Secretary must be set.
     */
    @XmlElement(required = true, nillable = false) private Action action = Action.CHANGE_NS;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Action> allowedActions() {
        return ALLOWED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction(final Action action) throws IllegalArgumentException {
        ensureNotNullAndContains("action", action, ALLOWED);
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getAction() {
        return action;
    }

    /**
     * Sets the CountryId, or CountryCode, which is the standard two-letter code
     * for all Countries, defined by the UN. The Code is used by several of the
     * request variations, and if needed, it must be set.<br />
     *   The method will throw an IllegalArgument Exception, if the CountryId
     * is set to null or is not exactly 2 characters long.
     *
     * @param countryId Two-letter Country Code
     * @throws IllegalArgumentException if null or not exactly 2 characters long
     */
    public void setCountryId(final String countryId) throws IllegalArgumentException {
        ensureNotNullAndExactLength("countryId", countryId, 2);
        this.countryId = countryId;
    }

    public String getCountryId() {
        return countryId;
    }

    /**
     * Sets the Institution Name, which is the name of the Cooperating
     * Institition, to either create or update. The name is most often the name
     * of the University or Department, for which a Cooperating Institution is
     * to be added.<br />
     *   The method will throw an IllegalArgument Exception, if the name is set
     * to null, empty or too long. The max length is 50 characters.
     *
     * @param institutionName The Institution Name
     * @throws IllegalArgumentException if not valid, i.e. null, empty or longer than 50 characters
     */
    public void setInstitutionName(final String institutionName) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("institutionName", institutionName, 50);
        this.institutionName = institutionName;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    /**
     * Sets the Abbreviation for the Cooperating Institution, which is used for
     * the official IAESTE Committee Name and also official mailing list. The
     * abbreviation is allowed to be max 5 characters long. If longer, then the
     * method will throw an IllegalArgument Exception.
     *
     * @param institutionAbbreviation Institution Abbreviation
     * @throws IllegalArgumentException if null, empty or longer than 5 characters
     */
    public void setInstitutionAbbreviation(final String institutionAbbreviation) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("institutionAbbreviation", institutionAbbreviation, 5);
        this.institutionAbbreviation = institutionAbbreviation;
    }

    public String getInstitutionAbbreviation() {
        return institutionAbbreviation;
    }

    public void setFirstname(final String firstname) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("firstname", firstname, CreateUserRequest.USER_MAXIMUM_FIRSTNAME);
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param lastname Lastname or Family name of the new National Secretary
     * @throws IllegalArgumentException
     */
    public void setLastname(final String lastname) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("lastname", lastname, CreateUserRequest.USER_MAXIMUM_LASTNAME);
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the Usenrame for creating a new National Secretary, which is done
     * when creating a new Committee or can optionaly be used when setting a new
     * National Secretary for an existing Committee.<br />
     *   The username must be a valid e-mail address, otherwise the method will
     * throw an IllegalArgument Exception.
     *
     * @param username National Secretary Username
     * @throws IllegalArgumentException if not a valid e-mail address
     */
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
                    isNotNull(validation, "institutionAbbreviation", institutionAbbreviation);
                    isNotNull(validation, "firstname", firstname);
                    isNotNull(validation, "lastname", lastname);
                    isNotNull(validation, "username", username);
                    break;
                case UPDATE:
                    isNotNull(validation, "nationalCommittee", nationalCommittee);
                    isNotNull(validation, "institutionName", institutionName);
                    isNotNull(validation, "institutionAbbreviation", institutionAbbreviation);
                    break;
                case MERGE:
                    isNotNull(validation, "countryId", countryId);
                    isNotNull(validation, "nationalSecretary", nationalSecretary);
                    break;
                case CHANGE_NS:
                    // Updating means changing the current National Secretary,
                    // however doing so means internal checks for an existing
                    // new National Secretary or a potential new National
                    // Secretary.
                case UPGRADE:
                case ACTIVATE:
                case SUSPEND:
                case DELETE:
                    isNotNull(validation, "nationalCommittee", nationalCommittee);
                    break;
                default:
                    validation.put("action", "The Action '" + action + "' is not allowed");
            }
        }

        return validation;
    }
}
