/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

/**
 * This Object contains those values that have a default value defined as a
 * Constant, but needs a system specific override. The values are using the
 * Constant values per default, so it will always work.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class Settings {

    private int maxActiveTokens = IWSConstants.MAX_ACTIVE_TOKENS;
    private int maxLoginRetries = IWSConstants.MAX_LOGIN_RETRIES;
    private long maxIdleTimeForSessions = IWSConstants.MAX_SESSION_IDLE_PERIOD;
    private long loginBlockedTime = IWSConstants.LOGIN_BLOCKING_PERIOD;
    private String publicMailAddress = IWSConstants.PUBLIC_EMAIL_ADDRESS;
    private String privateMailAddress = IWSConstants.PRIVATE_EMAIL_ADDRESS;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

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

    private static void throwIfNull(final String name, final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("The value for " + name + " cannot be set to null!");
        }
    }
}
