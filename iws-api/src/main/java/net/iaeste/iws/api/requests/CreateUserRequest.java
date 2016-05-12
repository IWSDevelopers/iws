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
import net.iaeste.iws.api.util.Verifications;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This is the Request Object for creating new User Accounts in IWS. The
 * account requires some data, which is included here. By default, the user must
 * provide a valid e-mail address - as the IWS Account ActivationCode is sent to
 * this e-mail address. The user account cannot be used until it has been
 * activated. Accounts that has not been activated within 14 days will be
 * considered dead and be wiped from the system.</p>
 *
 * <p>Additionally to the username (e-mail address), a password must be chosen.
 * The system makes no checks against the strength of the password, nor will the
 * system enforce regular changes. However, the user should pick a strong
 * password. Besides this, the users first and last names must also be
 * provided.</p>
 *
 * <p>It is important to note, that the users names (first, last) cannot be
 * altered, unless a DBA (Database Administrator) directly intervenes and makes
 * this change. It is done so, since the IWS is a multi-user &amp; multi-group
 * system, and the user should not give accounts to others, but rather create
 * and delete accounts.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createUserRequest", propOrder = { "username", "password", "firstname", "lastname", "studentAccount" })
public final class CreateUserRequest extends Verifications {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The maximum length for the username (e-mail address) allowed. */
    public static final int USER_MAXIMUM_USERNAME = 100;
    /** The maximum length for a users firstname that is allowed. */
    public static final int USER_MAXIMUM_FIRSTNAME = 50;
    /** The maximum length for a users lastname that is allowed. */
    public static final int USER_MAXIMUM_LASTNAME = 50;

    @XmlElement(required = true, nillable = false) private String username = null;
    @XmlElement(required = true, nillable = true)  private String password = null;
    @XmlElement(required = true, nillable = false) private String firstname = null;
    @XmlElement(required = true, nillable = false) private String lastname = null;
    @XmlElement(required = true, nillable = true)  private boolean studentAccount = false;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * <p>Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.</p>
     */
    public CreateUserRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * <p>Default Constructor for creating users without a pre-defined Password.
     * However, the username and the first/last names must be set. The System
     * will automatically generate a password, and set it in the e-mail
     * delivered.</p>
     *
     * @param username  The users e-mail address, is used as username in IWS
     * @param firstname The users given name, can only be altered by the DBA's
     * @param lastname  The users Family name, can only be altered by the DBA's
     */
    public CreateUserRequest(final String username, final String firstname, final String lastname) {
        setUsername(username);
        setFirstname(firstname);
        setLastname(lastname);
    }

    /**
     * <p>Default Constructor. All users generated must have this information
     * set.</p>
     *
     * @param username  The users e-mail address, is used as username in IWS
     * @param password  Chosen Password in clear-text
     * @param firstname The users given name, can only be altered by the DBA's
     * @param lastname  The users Family name, can only be altered by the DBA's
     */
    public CreateUserRequest(final String username, final String password, final String firstname, final String lastname) {
        setUsername(username);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * <p>Sets the Users Username (e-mail address), the Username may not be
     * null, empty or longer than 100 chars long, if so an
     * {@code IllegalArgumentException} is thrown.</p>
     *
     * @param username The Users Username (e-mail address)
     * @throws IllegalArgumentException if the Username is invalid
     * @see #USER_MAXIMUM_USERNAME
     * @see IWSConstants#EMAIL_PATTERN
     */
    public void setUsername(final String username) {
        ensureNotNullOrEmptyOrTooLong("username", username, USER_MAXIMUM_USERNAME);

        if (!IWSConstants.EMAIL_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("invalid e-mail address.");
        }

        this.username = username;
    }

    /**
     * <p>Retrieves the Users Username (private e-mail address).</p>
     *
     * @return The Users Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>Sets the Users Password, the Password may not be empty, if so then an
     * {@code IllegalArgumentException} is thrown. As for the length, then
     * there are no limits, as the system only stores the cryptographic
     * hash value of the Password.</p>
     *
     * <p>Note, that if no password is provided, i.e. if the value is null. Then
     * the system will generate a standard password for the user, which will
     * then be send to the user via e-mail.</p>
     *
     * @param password The Users Password or null
     * @throws IllegalArgumentException if the Password is invalid
     */
    public void setPassword(final String password) {
        ensureNotEmpty("password", password);
        this.password = password;
    }

    /**
     * Retrieves the Users Password.
     *
     * @return The Users Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>Sets the Users Firstname, the Firstname may not be null, empty or
     * longer than 50 chars long, if so an {@code IllegalArgumentException} is
     * thrown.</p>
     *
     * @param firstname The Users Firstname
     * @throws IllegalArgumentException if the Firstname is invalid
     * @see #USER_MAXIMUM_FIRSTNAME
     */
    public void setFirstname(final String firstname) {
        ensureNotNullOrEmptyOrTooLong("firstname", firstname, USER_MAXIMUM_FIRSTNAME);
        this.firstname = firstname;
    }

    /**
     * <p>Retrieves the Users Firstname.</p>
     *
     * @return The Users Firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * <p>Sets the Users Lastname, the Lastname may not be null, empty or longer
     * than 50 chars long, if so an {@code IllegalArgumentException} is
     * thrown.</p>
     *
     * @param lastname The Users Lastname
     * @throws IllegalArgumentException if the Lastname is invalid
     * @see #USER_MAXIMUM_LASTNAME
     */
    public void setLastname(final String lastname) {
        ensureNotNullOrEmptyOrTooLong("lastname", lastname, USER_MAXIMUM_LASTNAME);
        this.lastname = lastname;
    }

    /**
     * <p>Retrieves the Users Lastname.</p>
     *
     * @return The Users Lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * <p>Changes the type of an Account from being a normal User to a Student
     * Account. A Student Account will act as a normal Account, but the User is
     * given the role as a "Student" in the National Members group, and instead
     * of a Private Group, the user is assigned to the National Student
     * group.</p>
     *
     * @param studentAccount  True if a student, otherwise false (default)
     */
    public void setStudentAccount(final boolean studentAccount) {
        this.studentAccount = studentAccount;
    }

    /**
     * <p>Returns true if this is suppose to be a Student Account, otherwise
     * false.</p>
     *
     * @return True if Student Account, otherwise false
     */
    public boolean isStudent() {
        return studentAccount;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(5);

        isNotNull(validation, "username", username);
        isNotNull(validation, "firstname", firstname);
        isNotNull(validation, "lastname", lastname);

        return validation;
    }
}
