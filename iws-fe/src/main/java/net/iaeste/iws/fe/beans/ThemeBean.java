/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.beans.ThemeBean
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
import net.iaeste.iws.fe.model.base.Theme;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

/**
 * Holds the active PrimeFaces theme per http session.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@Named
@SessionScoped
public class ThemeBean implements Serializable {

    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private Map<String, String> availableThemes;
    private String currentTheme;

    public ThemeBean() {
        currentTheme = Theme.START.getCode();
        availableThemes = Theme.getThemeMap();
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(String code) {
        this.currentTheme = code;
    }

    public Map<String, String> getAvailableThemes() {
        return availableThemes;
    }

}

