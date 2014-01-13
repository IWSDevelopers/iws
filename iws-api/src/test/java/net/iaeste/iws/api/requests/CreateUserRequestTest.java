/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.CreateUserRequestTest
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class CreateUserRequestTest {

    @Test
    public void testClassflow() {
        final String username = "bla@bla.com";
        final String firstname = "bla";
        final String lastname = "abl";
        final String password = "abc123";
        final CreateUserRequest request = new CreateUserRequest(username, password, firstname, lastname);
        request.verify();

        assertThat(request.getUsername(), is(username));
        assertThat(request.getPassword(), is(password));
        assertThat(request.getFirstname(), is(firstname));
        assertThat(request.getLastname(), is(lastname));
        assertThat(request.isStudent(), is(false));
    }

    @Test
    public void testEmptyCostructor() {
        final CreateUserRequest request = new CreateUserRequest();

        assertThat(request.getUsername(), is(nullValue()));
        assertThat(request.getPassword(), is(nullValue()));
        assertThat(request.getFirstname(), is(nullValue()));
        assertThat(request.getLastname(), is(nullValue()));
        assertThat(request.isStudent(), is(false));
    }

    @Test
    public void testMinimalConstructor() {
        final String username = "bla@bla.com";
        final String firstname = "bla";
        final String lastname = "abl";
        final CreateUserRequest request = new CreateUserRequest(username, firstname, lastname);
        request.verify();

        assertThat(request.getUsername(), is(username));
        assertThat(request.getPassword(), is(nullValue()));
        assertThat(request.getFirstname(), is(firstname));
        assertThat(request.getLastname(), is(lastname));
        assertThat(request.isStudent(), is(false));
    }

    @Test
    public void setValidUsername() {
        final String username = "bla@bla.com";
        final CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);

        assertThat(request.getUsername(), is(username));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testSettingNullUsernanme() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setUsername(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyUsername() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setUsername("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSettingInvalidUsername() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setUsername("bla bla");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSettingTooLongUsername() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setUsername("qwertyuiopasdfghjklzxcvbnm1234567890@qwertyuiopasdfghjklzxcvbnm1234567890.qwertyuiopasdfghjklzxc1.com");
    }

    @Test
    public void setValidPassword() {
        final String password = "validPassword";
        final CreateUserRequest request = new CreateUserRequest();
        request.setPassword(password);

        assertThat(request.getPassword(), is(password));
    }

    @Test
    public void setNullPassword() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setPassword(null);

        assertThat(request.getPassword(), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setEmptyPassword() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setPassword("");
    }

    @Test
    public void setValidFirstname() {
        final String firstname = "firstname";
        final CreateUserRequest request = new CreateUserRequest();
        request.setFirstname(firstname);

        assertThat(request.getFirstname(), is(firstname));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullFirstname() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setFirstname(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFirstname() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setFirstname("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLongFirstname() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setFirstname("We only support firstnames that are max 50 characters long.");
    }

    @Test
    public void testValidLastname() {
        final String lastname = "Lastname";
        final CreateUserRequest request = new CreateUserRequest();
        request.setLastname(lastname);

        assertThat(request.getLastname(), is(lastname));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullLastname() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setLastname(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyLastname() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setLastname("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLongLastname() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setLastname("We only support lastnames that are max 50 characters long.");
    }

    @Test
    public void testSetStudentAccount() {
        final CreateUserRequest request = new CreateUserRequest();
        request.setStudentAccount(true);

        assertThat(request.isStudent(), is(true));
    }

    @Test
    public void testValidate() {
        final CreateUserRequest request = new CreateUserRequest();
        final Map<String, String> result = request.validate();

        assertThat(result.size(), is(3));
        assertThat(result.containsKey("username"), is(true));
        assertThat(result.containsKey("firstname"), is(true));
        assertThat(result.containsKey("lastname"), is(true));
        assertThat(result.get("username"), is("The field may not be null."));
        assertThat(result.get("firstname"), is("The field may not be null."));
        assertThat(result.get("lastname"), is("The field may not be null."));
    }
}
