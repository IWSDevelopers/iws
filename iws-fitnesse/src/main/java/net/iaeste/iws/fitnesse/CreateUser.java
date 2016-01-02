/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.CreateUser
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fitnesse;

import net.iaeste.iws.api.Administration;
import net.iaeste.iws.api.requests.CreateUserRequest;
import net.iaeste.iws.api.util.Fallible;
import net.iaeste.iws.client.notifications.NotificationMessage;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;
import net.iaeste.iws.common.notification.NotificationField;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class CreateUser extends AbstractFixture<Fallible> {

    private final Administration administration = new AdministrationCaller();
    private CreateUserRequest request = new CreateUserRequest();
    private NotificationMessage notificationMessage = null;

    public void setUsernameAndPassword(final String username, final String password) {
        setUsername(username);
        setPassword(password);
    }

    public void setNewFirstnameAndLastname(final String firstName, final String lastName) {
        request.setFirstname(firstName);
        request.setLastname(lastName);
    }

    public void setNewUsernameAndPassword(final String username, final String password) {
        request.setUsername(username);
        request.setPassword(password);
    }

    public void setStudentAccount(final boolean studentAccount) {
        request.setStudentAccount(studentAccount);
    }

    public void createUser() {
        execute();
    }

    public void readNotificationFromQueue() {
        notificationMessage = getNextNotification();
    }

    public String getActivationCode() {
        return notificationMessage.getFields().get(NotificationField.CODE);
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(administration.createUser(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
