/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fitnesse) - net.iaeste.iws.fitnesse.FetchUser
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
import net.iaeste.iws.api.requests.FetchUserRequest;
import net.iaeste.iws.api.responses.FetchUserResponse;
import net.iaeste.iws.fitnesse.callers.AdministrationCaller;
import net.iaeste.iws.fitnesse.exceptions.StopTestException;

/**
 * @author  Martin Eisfeld / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class FetchUser extends AbstractFixture<FetchUserResponse> {

    private final Administration administration = new AdministrationCaller();
    private FetchUserRequest request = new FetchUserRequest();

    public void setUserId(final String userId) {
        request.setUserId(userId);
    }

    public String getUserId() {
        return getResponse().getUser().getUserId();
    }

    public String getUsername() {
        return getResponse().getUser().getUsername();
    }

    public String getAlias() {
        return getResponse().getUser().getAlias();
    }

    public String getFirstname() {
        return getResponse().getUser().getFirstname();
    }

    public String getLastname() {
        return getResponse().getUser().getFirstname();
    }

    public String getStatus() {
        return getResponse().getUser().getStatus().toString();
    }

    public String getPrivacy() {
        return getResponse().getUser().getPrivacy().toString();
    }

    public String getAlternateEmail() {
        return getResponse().getUser().getPerson().getAlternateEmail();
    }

    public String printUser() {
        final String retVal;

        if (getResponse() == null) {
            retVal = "no response";
        } else {
            retVal = getResponse().getUser().toString();
        }

        return retVal;
    }

    public void fetchUser() {
        execute();
    }

    @Override
    public void execute() throws StopTestException {
        createSession();
        setResponse(administration.fetchUser(getToken(), request));
    }

    @Override
    public void reset() {
        // Resets the Response Object
        super.reset();

        request = null;
    }
}
