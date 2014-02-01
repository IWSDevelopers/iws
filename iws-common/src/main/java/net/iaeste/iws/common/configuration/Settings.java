/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.configuration.Settings
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.configuration;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * This Object contains those values that have a default value defined as a
 * Constant, but needs a system specific override. The values are using the
 * Constant values per default, so it will always work.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class Settings {

    private static final String SETTING_JNDI = "iws-setting";

    private boolean doJndiLookup = true;
    private boolean lookupDone = false;

    /**
     * The File Root Path is not set via the Constants Class, since this is an
     * internal information that we do not wish to disclose.
     */
    private String rootFilePath = "/data/iws";
    private int maxActiveTokens = IWSConstants.MAX_ACTIVE_TOKENS;
    private int maxLoginRetries = IWSConstants.MAX_LOGIN_RETRIES;
    private long maxIdleTimeForSessions = IWSConstants.MAX_SESSION_IDLE_PERIOD;
    private long loginBlockedTime = IWSConstants.LOGIN_BLOCKING_PERIOD;
    private String publicMailAddress = IWSConstants.PUBLIC_EMAIL_ADDRESS;
    private String privateMailAddress = IWSConstants.PRIVATE_EMAIL_ADDRESS;
    private String smtpAddress = "";
    private String smtpPort = "";
    private String baseUrl = IWSConstants.BASE_URL;
    private String sendingEmailAddress = IWSConstants.IWS_EMAIL_SENDER;
    private String ncsList = "";

    public void init() {
        if (!lookupDone) {
            try {
                final Context context = new InitialContext();
                final Properties properties = (Properties) context.lookup(SETTING_JNDI);
                context.close();

                smtpAddress = properties.getProperty("smtpAddress");
                smtpPort = properties.getProperty("smtpPort");
                baseUrl = properties.getProperty("iw4BaseUrl");
                sendingEmailAddress = properties.getProperty("sendingEmailAddress");
                publicMailAddress = properties.getProperty("publicMailAddress");
                privateMailAddress = properties.getProperty("privateMailAddress");
                rootFilePath = properties.getProperty("rootFilePath");

                ncsList = IWSConstants.NCS_LIST_NAME + '@' + privateMailAddress;

                lookupDone = true;
            } catch (NamingException e) {
                throw new IWSException(IWSErrors.ERROR, "Lookup for System settings failed.", e);
            }
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setDoJndiLookup(final boolean doJndiLookup) {
        this.doJndiLookup = doJndiLookup;
    }

    public boolean getDoJndiLookup() {
        return doJndiLookup;
    }

    public void setRootFilePath(final String rootFilePath) {
        this.rootFilePath = rootFilePath;
    }

    public String getRootFilePath() {
        return rootFilePath;
    }

    public void setMaxActiveTokens(final Integer maxActiveTokens) throws IllegalArgumentException {
        throwIfNull("maxActiveTokens", maxActiveTokens);
        this.maxActiveTokens = maxActiveTokens;
    }

    public int getMaxActiveTokens() {
        return maxActiveTokens;
    }

    public void setMaxLoginRetries(final Integer maxLoginRetries) throws IllegalArgumentException {
        throwIfNull("maxLoginRetries", maxLoginRetries);
        this.maxLoginRetries = maxLoginRetries;
    }

    public int getMaxLoginRetries() {
        return maxLoginRetries;
    }

    public void setMaxIdleTimeForSessions(final Long maxIdleTimeForSessions) throws IllegalArgumentException {
        throwIfNull("maxIdleTimeForSessions", maxIdleTimeForSessions);
        this.maxIdleTimeForSessions = maxIdleTimeForSessions;
    }

    public long getMaxIdleTimeForSessions() {
        return maxIdleTimeForSessions;
    }

    public void setLoginBlockedTime(final Long loginBlockedTime) throws IllegalArgumentException {
        throwIfNull("loginBlockedTime", loginBlockedTime);
        this.loginBlockedTime = loginBlockedTime;
    }

    public long getLoginBlockedTime() {
        return loginBlockedTime;
    }

    public void setPublicMailAddress(final String publicMailAddress) throws IllegalArgumentException {
        throwIfNull("publicMailAddress", publicMailAddress);
        this.publicMailAddress = publicMailAddress;
    }

    public String getPublicMailAddress() {
        return publicMailAddress;
    }

    public void setPrivateMailAddress(final String privateMailAddress) throws IllegalArgumentException {
        throwIfNull("privateMailAddress", privateMailAddress);
        this.privateMailAddress = privateMailAddress;
    }

    public String getPrivateMailAddress() {
        return privateMailAddress;
    }

    public void setSmtpAddress(final String smtpAddress) {
        throwIfNull("smtpAddress", smtpAddress);
        this.smtpAddress = smtpAddress;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpPort(final String smtpPort) {
        throwIfNull("smtpPort", smtpPort);
        this.smtpPort = smtpPort;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setBaseUrl(final String baseUrl) {
        throwIfNull("baseUrl", baseUrl);
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setSendingEmailAddress(final String sendingEmailAddress) {
        throwIfNull("sendingEmailAddress", sendingEmailAddress);
        this.sendingEmailAddress = sendingEmailAddress;
    }

    public String getSendingEmailAddress() {
        return sendingEmailAddress;
    }

    public void setNcsList(final String ncsList) {
        this.ncsList = ncsList;
    }

    public String getNcsList() {
        return ncsList;
    }

    private static void throwIfNull(final String name, final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("The value for " + name + " cannot be set to null!");
        }
    }
}
