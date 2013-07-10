package net.iaeste.iws.core.singletons;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.core.exceptions.SessionException;

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
 *   Second, the idea behind this logic, is also to add some safe-guars against
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
 * @since   1.7
 * @noinspection StaticNonFinalField
 */
public final class ActiveSessions {

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
     * @param maxActiveTokens Maximum number of concurrently active tokens allowed
     */
    private ActiveSessions(final int maxActiveTokens, final long maxMillisToLive) {
        this.lock = new Object();
        this.tokens = new HashMap<>(maxActiveTokens);
        this.maxActiveTokens = maxActiveTokens;
        this.maxMillisToLive = maxMillisToLive;
    }

    /**
     * Prepares and returns the instance for this Singleton.
     *
     * @param maxSessions Maximum number of concurrently active sessions allowed
     * @return Singleton instance of this class
     */
    public static ActiveSessions getInstance(final int maxSessions, final long maxMillisToLive) {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new ActiveSessions(maxSessions, maxMillisToLive);
            }

            return instance;
        }
    }

    // =========================================================================
    // Public Methods
    // =========================================================================

    /**
     * Reads out how many tokens currently are active in the System.
     *
     * @return Number of Active Tokens (Sessions) now
     */
    public int getNumberOfActiveTokens() {
        synchronized (lock) {
            return tokens.size();
        }
    }

    /**
     * Registers a token on the Token Stack. If the token already exists, then
     * the currently date of registration (last Access), will be updated,
     * otherwise a check is made to see if we have exceeded the currently max
     * allowed tokens and if not, the new token is added. If we have exceeded
     * the currently maximum allowed tokens, an Exception is thrown.
     *
     * @param token Session Token
     */
    public void registerToken(final String token) {
        synchronized (lock) {
            if (tokens.containsKey(token)) {
                tokens.put(token, new Date());
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
     * Reads when a given Token last were accessing the system.
     *
     * @param token Session Token
     * @return Last Access
     */
    public Date getLastAccess(final String token) {
        final Date lastAccess;

        synchronized (lock) {
            if (tokens.containsKey(token)) {
                lastAccess = tokens.get(token);
            } else {
                lastAccess = null;
            }
        }

        return lastAccess;
    }

    /**
     * Removes a token from from the list of currently allowed Sessions.
     *
     * @param token Session Token
     */
    public void removeToken(final String token) {
        synchronized (lock) {
            tokens.remove(token);
        }
    }

    /**
     * This method will crawl through the list of currently active Tokens, and
     * check if someone of these have expired. Meaning, that the time of last
     * access was more than the maximum allowed millis.<br />
     *   The list is returned, and it is then left to the invoking logic to
     * handle closing of them.<br />
     *   All currently active Tokens, which is being found, will also be reomved
     * from the list.
     *
     * @return List of Sessions that have expired
     */
    public List<String> findAndRemoveExpiredTokens() {
        final List<String> expiredTokens = new ArrayList<>(10);
        // Although we always should narrow the scope of variables, the also
        // have to remember that the Synchronized block is a special case, that
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
