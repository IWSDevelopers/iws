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

    private final Properties properties;

    private static final String PROPERTY_ROOT_FILE_PATH = "root.file.path";
    private static final String PROPERTY_MAX_ACTIVE_TOKENS = "max.active.tokens";
    private static final String PROPERTY_MAX_LOGIN_RETRIES = "max.login.retries";
    private static final String PROPERTY_MAX_IDLE_TIME_FOR_SESSIONS = "max.idle.time.for.sessions";
    private static final String PROPERTY_LOGIN_BLOCKED_PERIOD = "login.blocked.period";
    private static final String PROPERTY_PUBLIC_EMAIL_ADDRESS = "public.email.address";
    private static final String PROPERTY_PRIVATE_EMAIL_ADDRESS = "private.email.address";
    private static final String PROPERTY_SENDING_EMAIL_ADDRESS = "sending.email.address";
    private static final String PROPERTY_SMTP_ADDRESS = "smtp.address";
    private static final String PROPERTY_SMTP_PORT = "smtp.port";
    private static final String PROPERTY_BASE_URL = "base.url";
    private static final String PROPERTY_NCS_LIST = "ncs.list";

    /**
     * Default Value Constructor, sets all values to the default, which for most
     * of them is the matching Constant value from the IWS Constant class.
     */
    public Settings() {
        this.properties = new Properties();

        properties.setProperty(PROPERTY_ROOT_FILE_PATH, System.getProperty("java.io.tmpdir"));
        properties.setProperty(PROPERTY_MAX_ACTIVE_TOKENS, String.valueOf(IWSConstants.MAX_ACTIVE_TOKENS));
        properties.setProperty(PROPERTY_MAX_LOGIN_RETRIES, String.valueOf(IWSConstants.MAX_LOGIN_RETRIES));
        properties.setProperty(PROPERTY_MAX_IDLE_TIME_FOR_SESSIONS, String.valueOf(IWSConstants.MAX_SESSION_IDLE_PERIOD));
        properties.setProperty(PROPERTY_LOGIN_BLOCKED_PERIOD, String.valueOf(IWSConstants.LOGIN_BLOCKING_PERIOD));
        properties.setProperty(PROPERTY_PUBLIC_EMAIL_ADDRESS, IWSConstants.PUBLIC_EMAIL_ADDRESS);
        properties.setProperty(PROPERTY_PRIVATE_EMAIL_ADDRESS, IWSConstants.PRIVATE_EMAIL_ADDRESS);
        properties.setProperty(PROPERTY_SENDING_EMAIL_ADDRESS, IWSConstants.IWS_EMAIL_SENDER);
        properties.setProperty(PROPERTY_SMTP_ADDRESS, "");
        properties.setProperty(PROPERTY_SMTP_PORT, "");
        properties.setProperty(PROPERTY_BASE_URL, IWSConstants.BASE_URL);
        properties.setProperty(PROPERTY_NCS_LIST, IWSConstants.NCS_LIST_NAME);
    }

    /**
     * Default Configuration Constructor, reads the settings from the given
     * Properties file.
     *
     * @param properties Properties file to read the values from
     */
    public Settings(final Properties properties) {
        this.properties = properties;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public String getRootFilePath() {
        return properties.getProperty(PROPERTY_ROOT_FILE_PATH);
    }

    public void setMaxActiveTokens(final Integer maxActiveTokens) throws IllegalArgumentException {
        throwIfNull("maxActiveTokens", maxActiveTokens);
        properties.setProperty(PROPERTY_MAX_ACTIVE_TOKENS, String.valueOf(maxActiveTokens));
    }

    public int getMaxActiveTokens() {
        return Integer.valueOf(properties.getProperty(PROPERTY_MAX_ACTIVE_TOKENS));
    }

    public void setMaxLoginRetries(final Integer maxLoginRetries) throws IllegalArgumentException {
        throwIfNull("maxLoginRetries", maxLoginRetries);
        properties.setProperty(PROPERTY_MAX_LOGIN_RETRIES, String.valueOf(maxLoginRetries));
    }

    public int getMaxLoginRetries() {
        return Integer.valueOf(properties.getProperty(PROPERTY_MAX_LOGIN_RETRIES));
    }

    public void setMaxIdleTimeForSessions(final Long maxIdleTimeForSessions) throws IllegalArgumentException {
        throwIfNull("maxIdleTimeForSessions", maxIdleTimeForSessions);
        properties.setProperty(PROPERTY_MAX_IDLE_TIME_FOR_SESSIONS, String.valueOf(maxIdleTimeForSessions));
    }

    public long getMaxIdleTimeForSessions() {
        return Long.valueOf(properties.getProperty(PROPERTY_MAX_IDLE_TIME_FOR_SESSIONS));
    }

    public void setLoginBlockedTime(final Long loginBlockedTime) throws IllegalArgumentException {
        throwIfNull("loginBlockedTime", loginBlockedTime);
        properties.setProperty(PROPERTY_LOGIN_BLOCKED_PERIOD, String.valueOf(loginBlockedTime));
    }

    public long getLoginBlockedTime() {
        return Long.valueOf(properties.getProperty(PROPERTY_LOGIN_BLOCKED_PERIOD));
    }

    public void setPublicMailAddress(final String publicMailAddress) throws IllegalArgumentException {
        throwIfNull("publicMailAddress", publicMailAddress);
        properties.setProperty(PROPERTY_PUBLIC_EMAIL_ADDRESS, publicMailAddress);
    }

    public String getPublicMailAddress() {
        return properties.getProperty(PROPERTY_PUBLIC_EMAIL_ADDRESS);
    }

    public void setPrivateMailAddress(final String privateMailAddress) throws IllegalArgumentException {
        throwIfNull("privateMailAddress", privateMailAddress);
        properties.setProperty(PROPERTY_PRIVATE_EMAIL_ADDRESS, privateMailAddress);
    }

    public String getPrivateMailAddress() {
        return properties.getProperty(PROPERTY_PRIVATE_EMAIL_ADDRESS);
    }

    public void setSendingEmailAddress(final String sendingEmailAddress) {
        throwIfNull("sendingEmailAddress", sendingEmailAddress);
        properties.setProperty(PROPERTY_SENDING_EMAIL_ADDRESS, sendingEmailAddress);
    }

    public String getSendingEmailAddress() {
        return properties.getProperty(PROPERTY_SENDING_EMAIL_ADDRESS);
    }

    public void setSmtpAddress(final String smtpAddress) {
        throwIfNull("smtpAddress", smtpAddress);
        properties.setProperty(PROPERTY_SMTP_ADDRESS, smtpAddress);
    }

    public String getSmtpAddress() {
        return properties.getProperty(PROPERTY_SMTP_ADDRESS);
    }

    public void setSmtpPort(final String smtpPort) {
        throwIfNull("smtpPort", smtpPort);
        properties.setProperty(PROPERTY_SMTP_PORT, smtpPort);
    }

    public String getSmtpPort() {
        return properties.getProperty(PROPERTY_SMTP_PORT);
    }

    public void setBaseUrl(final String baseUrl) {
        throwIfNull("baseUrl", baseUrl);
        properties.setProperty(PROPERTY_BASE_URL, baseUrl);
    }

    public String getBaseUrl() {
        return properties.getProperty(PROPERTY_BASE_URL);
    }

    public void setNcsList(final String ncsList) {
        properties.setProperty(PROPERTY_NCS_LIST, ncsList);
    }

    public String getNcsList() {
        return properties.getProperty(PROPERTY_NCS_LIST);
    }

    private static void throwIfNull(final String name, final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("The value for " + name + " cannot be set to null!");
        }
    }
}
