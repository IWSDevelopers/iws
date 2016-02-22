/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
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

    private static final String PROPERTY_RUN_CLEAN_TIME = "run.clean.time";
    private static final String PROPERTY_MAIL_SYNCHRONIZE_TIME = "mail.synchronize.time";
    private static final String PROPERTY_STARTUP_RESET_SESSIONS = "startup.reset.sessions";
    private static final String PROPERTY_ROOT_FILE_PATH = "root.file.path";
    private static final String PROPERTY_PASSWORD_SALT = "iws.password.salt";
    private static final String PROPERTY_MAX_ACTIVE_TOKENS = "max.active.tokens";
    private static final String PROPERTY_MAX_LOGIN_RETRIES = "max.login.retries";
    private static final String PROPERTY_MAX_IDLE_TIME_FOR_SESSIONS = "max.idle.time.for.sessions";
    private static final String PROPERTY_LOGIN_BLOCKED_PERIOD = "login.blocked.period";
    private static final String PROPERTY_ANNUAL_CONFERENCE_START = "annual.conference.start";
    private static final String PROPERTY_ANNUAL_CONFERENCE_END = "annual.conference.end";
    private static final String PROPERTY_ANNUAL_CONFERENCE_DEADLINE = "annual.conference.nomination.deadline";
    private static final String PROPERTY_ACCOUNT_UNUSED_REMOVED_DAYS = "account.unused.removed.days";
    private static final String PROPERTY_ACCOUNT_INACTIVE_DAYS = "account.inactive.days";
    private static final String PROPERTY_ACCOUNT_SUSPENDED_DAYS = "account.suspended.days";
    private static final String PROPERTY_PUBLIC_EMAIL_ADDRESS = "public.email.address";
    private static final String PROPERTY_PRIVATE_EMAIL_ADDRESS = "private.email.address";
    private static final String PROPERTY_SENDING_EMAIL_ADDRESS = "sending.email.address";
    private static final String PROPERTY_SMTP_ADDRESS = "smtp.address";
    private static final String PROPERTY_SMTP_PORT = "smtp.port";
    private static final String PROPERTY_BASE_URL = "base.url";
    private static final String PROPERTY_LIST_NCS = "virtual.mailing.list.ncs";
    private static final String PROPERTY_LIST_ANNOUNCE = "virtual.mailing.list.announce";

    /**
     * Default Value Constructor, sets all values to the default, which for most
     * of them is the matching Constant value from the IWS Constant class.
     */
    public Settings() {
        this.properties = new Properties();

        properties.setProperty(PROPERTY_RUN_CLEAN_TIME, InternalConstants.RUN_CLEAN_TIME);
        properties.setProperty(PROPERTY_MAIL_SYNCHRONIZE_TIME, InternalConstants.MAIL_SYNCHRONIZE_TIME);
        properties.setProperty(PROPERTY_ROOT_FILE_PATH, System.getProperty("java.io.tmpdir"));
        properties.setProperty(PROPERTY_STARTUP_RESET_SESSIONS, InternalConstants.STARTUP_RESET_SESSIONS);
        properties.setProperty(PROPERTY_PASSWORD_SALT, "");
        properties.setProperty(PROPERTY_MAX_ACTIVE_TOKENS, String.valueOf(InternalConstants.MAX_ACTIVE_TOKENS));
        properties.setProperty(PROPERTY_MAX_LOGIN_RETRIES, String.valueOf(InternalConstants.MAX_LOGIN_RETRIES));
        properties.setProperty(PROPERTY_MAX_IDLE_TIME_FOR_SESSIONS, String.valueOf(InternalConstants.MAX_SESSION_IDLE_PERIOD));
        properties.setProperty(PROPERTY_LOGIN_BLOCKED_PERIOD, String.valueOf(InternalConstants.LOGIN_BLOCKING_PERIOD));
        properties.setProperty(PROPERTY_ANNUAL_CONFERENCE_START, InternalConstants.AC_START);
        properties.setProperty(PROPERTY_ANNUAL_CONFERENCE_END, InternalConstants.AC_END);
        properties.setProperty(PROPERTY_ANNUAL_CONFERENCE_DEADLINE, InternalConstants.AC_DEADLINE);
        properties.setProperty(PROPERTY_ACCOUNT_UNUSED_REMOVED_DAYS, String.valueOf(InternalConstants.ACCOUNT_UNUSED_REMOVED_DAYS));
        properties.setProperty(PROPERTY_ACCOUNT_INACTIVE_DAYS, String.valueOf(InternalConstants.ACCOUNT_INACTIVE_DAYS));
        properties.setProperty(PROPERTY_ACCOUNT_SUSPENDED_DAYS, String.valueOf(InternalConstants.ACCOUNT_SUSPENDED_DAYS));
        properties.setProperty(PROPERTY_PUBLIC_EMAIL_ADDRESS, IWSConstants.PUBLIC_EMAIL_ADDRESS);
        properties.setProperty(PROPERTY_PRIVATE_EMAIL_ADDRESS, IWSConstants.PRIVATE_EMAIL_ADDRESS);
        properties.setProperty(PROPERTY_SENDING_EMAIL_ADDRESS, IWSConstants.IWS_EMAIL_SENDER);
        properties.setProperty(PROPERTY_SMTP_ADDRESS, "");
        properties.setProperty(PROPERTY_SMTP_PORT, "");
        properties.setProperty(PROPERTY_BASE_URL, IWSConstants.BASE_URL);
        properties.setProperty(PROPERTY_LIST_NCS, IWSConstants.MAILING_LIST_NCS_NAME);
        properties.setProperty(PROPERTY_LIST_ANNOUNCE, IWSConstants.MAILING_LIST_ANNOUNCE);
    }

    /**
     * Default Configuration Constructor, reads the settings from the given
     * Properties file.
     *
     * @param properties Properties file to read the values from
     */
    public Settings(final Properties properties) {
        this.properties = new Properties(properties);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public String getRunCleanTime() {
        return properties.getProperty(PROPERTY_RUN_CLEAN_TIME);
    }

    public String getMailSynchronizeTime() {
        return properties.getProperty(PROPERTY_MAIL_SYNCHRONIZE_TIME);
    }

    public boolean resetSessionsAtStartup() {
        return Boolean.valueOf(properties.getProperty(PROPERTY_STARTUP_RESET_SESSIONS));
    }

    public String getRootFilePath() {
        return properties.getProperty(PROPERTY_ROOT_FILE_PATH);
    }

    public void setPasswordSalt(final String salt) {
        properties.setProperty(PROPERTY_PASSWORD_SALT, salt);
    }

    public String getPasswordSalt() {
        return properties.getProperty(PROPERTY_PASSWORD_SALT);
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

    public void setAnnualConferenceStart(final String start) {
        properties.setProperty(PROPERTY_ANNUAL_CONFERENCE_START, start);
    }

    public String getAnnualConferenceStart() {
        return properties.getProperty(PROPERTY_ANNUAL_CONFERENCE_START);
    }

    public void setAnnualConferenceEnd(final String end) {
        properties.setProperty(PROPERTY_ANNUAL_CONFERENCE_END, end);
    }

    public String getAnnualConferenceEnd() {
        return properties.getProperty(PROPERTY_ANNUAL_CONFERENCE_END);
    }

    public void setAnnualConferenceDeadline(final String deadline) {
        properties.setProperty(PROPERTY_ANNUAL_CONFERENCE_END, deadline);
    }

    public String getAnnualConferenceDeadline() {
        return properties.getProperty(PROPERTY_ANNUAL_CONFERENCE_DEADLINE);
    }

    public Long getAccountUnusedRemovedDays() {
        return Long.valueOf(properties.getProperty(PROPERTY_ACCOUNT_UNUSED_REMOVED_DAYS));
    }

    public Long getAccountInactiveDays() {
        return Long.valueOf(properties.getProperty(PROPERTY_ACCOUNT_INACTIVE_DAYS));
    }

    public Long getAccountSuspendedDays() {
        return Long.valueOf(properties.getProperty(PROPERTY_ACCOUNT_SUSPENDED_DAYS));
    }

    public String getPublicMailAddress() {
        return properties.getProperty(PROPERTY_PUBLIC_EMAIL_ADDRESS);
    }

    public String getPrivateMailAddress() {
        return properties.getProperty(PROPERTY_PRIVATE_EMAIL_ADDRESS);
    }

    public String getSendingEmailAddress() {
        return properties.getProperty(PROPERTY_SENDING_EMAIL_ADDRESS);
    }

    public String getSmtpAddress() {
        return properties.getProperty(PROPERTY_SMTP_ADDRESS);
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

    public String getNcsList() {
        return properties.getProperty(PROPERTY_LIST_NCS);
    }

    public String getAnnounceList() {
        return properties.getProperty(PROPERTY_LIST_ANNOUNCE);
    }

    private static void throwIfNull(final String name, final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("The value for " + name + " cannot be set to null!");
        }
    }
}
