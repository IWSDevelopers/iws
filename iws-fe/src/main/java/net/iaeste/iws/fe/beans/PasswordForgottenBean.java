/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.beans.StoreEmail
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.beans;

import net.iaeste.iws.api.constants.IWSConstants;
import org.hibernate.validator.constraints.Email;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Getters, setters and validation of the email if a user forgot his password
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Named
@RequestScoped
public class PasswordForgottenBean {

    @Email(regexp = IWSConstants.EMAIL_REGEX)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // TODO implement password forgotten in API, CORE, FE
}


