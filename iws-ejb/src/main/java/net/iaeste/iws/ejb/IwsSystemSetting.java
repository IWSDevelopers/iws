/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.IwsSystemSetting
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 *
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class IwsSystemSetting {
    private static final String IWS_SETTING = "iws-setting";
    private final Properties iwsSetting;

    private static IwsSystemSetting instance = null;
    private static final Object LOCK = new Object();

    private IwsSystemSetting() {
        try {
            final Context context = new InitialContext();
            iwsSetting = (Properties)context.lookup(IWS_SETTING);
            context.close();
        } catch (NamingException e) {
            throw new IWSException(IWSErrors.ERROR, "Queue sender (NotificationEmailSender) initialization failed.", e);
        }
    }

    public static IwsSystemSetting getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new IwsSystemSetting();
            }

            return instance;
        }
    }

    public String getSmtpAddress() {
        return iwsSetting.getProperty("smtpAddress");
    }

    public String getSmtpPort() {
        return iwsSetting.getProperty("smtpPort");
    }

    public String getBaseUrl() {
        return iwsSetting.getProperty("iw4BaseUrl");
    }

    public String getSendingEmailAddress() {
        return iwsSetting.getProperty("sendingEmailAddress");
    }
}
