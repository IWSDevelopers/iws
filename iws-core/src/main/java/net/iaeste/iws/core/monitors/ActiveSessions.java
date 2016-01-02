/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-core) - net.iaeste.iws.core.monitors.ActiveSessions
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.core.monitors;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.dtos.AuthenticationToken;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.core.exceptions.SessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * There is a number of issues that has to be resolved for our Session handling,
 * this includes expiring of Sessions, avoiding that too many sessions currently
 * are active (since it may overload the system), and we also wish to have
 * certain requests made read-only.<br />
 *   First, an inactive User should be kicked out when no longer using the
 * system, these sessions are considered expired. The main reason for doing so,
 * is simply to avoid that someone can misuse an open Session.<br />
 *   Second, the idea behind this logic, is also to add some safe-guards against
 * DDOS attacks, by not invoking the database needlessly. Hence the map is
 * purely living in-memory. Combined with the multi-login attempt prevention, it
 * may hopefully keep the system from going down.
 *   Third, some of the requests are made purely read-only. This basically
 * applies to all requests called "fetch...". Since these only need to read
 * content from the database, and can thus be invoked using database
 * views.<br />
 *   This class is made as a Singleton, since all threads should be able to read
 * the same information out. It would make little sense to add a Session safe
 * guard, if different threads were not sharing the information.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class ActiveSessions {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveSessions.class);

    // Singleton Instance Object & Lock Object
    private static final Object INSTANCE_LOCK = new Object();
    private static ActiveSessions instance = null;

    // Data Map & Lock
    private final Object lock;
    private final Map<String, Date> tokens;

    // Control Variables
    private final int maxActiveTokens;
    private final long maxMillisToLive;

    // =========================================================================
    // Object Instantiation Methods
    // =========================================================================

    /**
     * Private Constructor, this is a Singleton class.
     *
     * @param settings System Settings
     */
    private ActiveSessions(final Settings settings) {
        this.lock = new Object();
        this.tokens = new HashMap<>(settings.getMaxActiveTokens());

        maxActiveTokens = settings.getMaxActiveTokens();
        maxMillisToLive = settings.getMaxIdleTimeForSessions();
    }

    /**
     * Prepares and returns the instance for this Singleton.
     *
     * @param settings System Settings
     * @return Singleton instance of this class
     */
    public static ActiveSessions getInstance(final Settings settings) {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new ActiveSessions(settings);
            }

            return instance;
        }
    }

    // =========================================================================
    // Public Methods
    // =========================================================================

    public boolean hasMaximumRegisteredSessions() {
        synchronized (lock) {
            return tokens.size() >= maxActiveTokens;
        }
    }

    /**
     * Registers a token on the Token Stack. If the token already exists, then
     * the currently date of registration (last Access), will be updated,
     * otherwise a check is made to see if we have exceeded the currently max
     * allowed tokens and if not, the new token is added. If we have exceeded
     * the currently maximum allowed tokens, an Exception is thrown.
     *
     * @param token     Session Token
     * @param timestamp The Token creation timestamp
     */
    public void registerToken(final String token, final Date timestamp) {
        LOG.debug("{} Registering Token in the Active Session Monitor.", readTrace(token));
        synchronized (lock) {
            if (tokens.containsKey(token)) {
                tokens.put(token, timestamp);
            } else {
                if (tokens.size() < maxActiveTokens) {
                    tokens.put(token, new Date());
                } else {
                    throw new SessionException(IWSErrors.TOO_MANY_ACTIVE_SESSIONS, "Too many active sessions exists.");
                }
            }
        }
    }

    /**
     * Reads when a given Token last were accessing the system. If no active
     * Session exists, then a null is returned.
     *
     * @param token Session Token
     * @return Last Access or null if no active session exists
     */
    public Date getLastAccess(final String token) {
        Date lastAccess = null;

        synchronized (lock) {
            if (tokens.containsKey(token)) {
                lastAccess = tokens.get(token);
            }
        }

        LOG.trace("{} The token was last used {}.", readTrace(token), lastAccess);
        return lastAccess;
    }

    /**
     * Removes a token from from the list of currently allowed Sessions.
     *
     * @param token Session Token
     */
    public void removeToken(final String token) {
        LOG.debug("{} Removing token from the Active Session Monitor.", readTrace(token));
        synchronized (lock) {
            tokens.remove(token);
        }
    }

    /**
     * Returns true, if the token exists in the listing, and the timestamp is
     * not expired.
     *
     * @param token Token
     * @return True, if token is valid, otherwise false
     */
    public boolean isActive(final String token) {
        final Date mustBeAfter = new Date(new Date().getTime() - maxMillisToLive);
        final boolean result;

        final Date lastAccess = getLastAccess(token);
        if (lastAccess != null) {
            result = lastAccess.after(mustBeAfter);
        } else {
            LOG.info("{} Token has expired, it is {} ms since last access.", readTrace(token), maxMillisToLive);
            result = false;
        }

        return result;
    }

    /**
     * Updates the token entry with a new timestamp, to indicate when it was
     * last used.
     *
     * @param token Token to update
     */
    public void updateToken(final String token) {
        LOG.trace("{} Updating the last access for the token to now.", readTrace(token));
        synchronized (lock) {
            if (tokens.containsKey(token)) {
                tokens.put(token, new Date());
            }
        }
    }

    /**
     * This method will crawl through the list of currently active Tokens, and
     * check if someone of these have expired. Meaning, that the time of last
     * access was more than the maximum allowed millis.<br />
     *   The list is returned, and it is then left to the invoking logic to
     * handle closing of them.<br />
     *   All currently active Tokens, which is being found, will also be removed
     * from the list.
     *
     * @return List of Sessions that have expired
     */
    public List<String> findAndRemoveExpiredTokens() {
        LOG.debug("Attempting to find and remove expired Tokens.");
        final List<String> expiredTokens = new ArrayList<>(10);
        // Although we always should narrow the scope of variables, we also have
        // to remember that the Synchronized block is a special case, that
        // should be kept to a minimum at all times. Hence, we're finding the
        // current timestamp outside of the Synchronized block.
        //   Here, we create a new Date Object to compare with. Since the max
        // allowed time is given, we simply create a new Date Object, which we
        // can then compare against
        final Date when = new Date(new Date().getTime() - maxMillisToLive);

        // We start by iterating over the list, to find those Tokens to expire,
        // we're doing so by making a simple comparison with the current
        synchronized (lock) {
            for (final Map.Entry<String, Date> token : tokens.entrySet()) {
                if (token.getValue().before(when)) {
                    expiredTokens.add(token.getKey());
                }
            }
        }

        // Now, remove the tokens. If we did this in the same loop as iterating
        // over the tokens, the iterator would get confused, and we would get a
        // ConcurrentModificationException
        removeTokens(expiredTokens);
        return expiredTokens;
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static String readTrace(final String token) {
        final String trace;

        if (token != null) {
            trace = String.format("[traceId = %s]", new AuthenticationToken(token).getTraceId());
        } else {
            trace = "";
        }

        return trace;
    }

    /**
     * Removes a list of Tokens from the Session map.
     *
     * @param expiredTokens List of expired Tokens to remove
     */
    private void removeTokens(final List<String> expiredTokens) {
        synchronized (lock) {
            for (final String token : expiredTokens) {
                tokens.remove(token);
            }
        }
    }
}
