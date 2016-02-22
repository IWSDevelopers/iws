/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
@XmlType(name = "committeeRequest", propOrder = { "countryCode", "institutionName", "institutionAbbreviation", "firstname", "lastname", "username", "nationalCommittee", "nationalSecretary", "action" })
public final class CommitteeRequest extends AbstractVerification implements Actionable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Default allowed Actions for the Committee Request. */
    private static final Set<Action> ALLOWED = EnumSet.of(Action.CREATE, Action.CHANGE_NS, Action.UPDATE, Action.MERGE, Action.UPGRADE, Action.ACTIVATE, Action.SUSPEND, Action.DELETE);

    /** The Id of the Country to create a new Cooperating Institution for. */
    @XmlElement(required = true, nillable = true) private String countryCode = null;
    /** The name of the Institution to use when creating a new Cooperating Institution. */
    @XmlElement(required = true, nillable = true) private String institutionName = null;
    /** The official Abbreviation for the Institution, for creating &amp; updating. */
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
     * <p>Action to perform on a Committee, by default we're assuming that it
     * must be updated, i.e. that the National Secretary must be set.</p>
     */
    @XmlElement(required = true, nillable = false) private Action action = Action.CHANGE_NS;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public CommitteeRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Default Constructor, sets the type of Action to perform for the given
     * Committee.</p>
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
     * <p>Sets the CountryId, or CountryCode, which is the standard two-letter
     * code for all Countries, defined by the UN. The Code is used by several
     * of the request variations, and if needed, it must be set.</p>
     *
     * <p>The method will throw an IllegalArgument Exception, if the CountryId
     * is set to null or is not exactly 2 characters long.</p>
     *
     * @param countryCode Two-letter Country Code
     * @throws IllegalArgumentException if null or not exactly 2 characters long
     */
    public void setCountryCode(final String countryCode) throws IllegalArgumentException {
        ensureNotNullAndExactLength("countryCode", countryCode, 2);
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    /**
     * <p>Sets the Institution Name, which is the name of the Cooperating
     * Institution, to either create or update. The name is most often the name
     * of the University or Department, for which a Cooperating Institution is
     * to be added.</p>
     *
     * <p>The method will throw an IllegalArgument Exception, if the name is set
     * to null, empty or too long. The max length is 50 characters.</p>
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
     * <p>Sets the Abbreviation for the Cooperating Institution, which is used
     * for the official IAESTE Committee Name and also official mailing list.
     * The abbreviation is allowed to be max 5 characters long. If longer, then
     * the method will throw an IllegalArgument Exception.</p>
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
     * @throws IllegalArgumentException in value is invalid
     */
    public void setLastname(final String lastname) throws IllegalArgumentException {
        ensureNotNullOrEmptyOrTooLong("lastname", lastname, CreateUserRequest.USER_MAXIMUM_LASTNAME);
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    /**
     * <p>Sets the Username for creating a new National Secretary, which is done
     * when creating a new Committee or can optionally be used when setting a
     * new National Secretary for an existing Committee.</p>
     *
     * <p>The username must be a valid e-mail address, otherwise the method will
     * throw an IllegalArgument Exception.</p>
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
                    isNotNull(validation, "countryCode", countryCode);
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
                    isNotNull(validation, "countryCode", countryCode);
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
