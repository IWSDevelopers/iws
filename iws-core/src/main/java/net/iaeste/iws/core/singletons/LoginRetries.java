package net.iaeste.iws.core.singletons;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.common.exceptions.AuthenticationException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class serves as a safe-guard against too many login attempts. If more
 * than n attempts were made within a timeframe of x seconds, then the account
 * is locked for y minutes. Meaning, that until such time as the account is
 * again accessible, no further attempts can be made. Any attempt made will be
 * logged with a high log level to emphasize that this may be a potential
 * threat.<br />
 *   This class is made as a Singleton, since all threads should be able to read
 * the same information out. It would make little sense to allow a user to make
 * multiple attempts, if we didn't share the information between the threads.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection StaticNonFinalField
 */
public final class LoginRetries {

    // Singleton Instance Object & Lock Object
    private static final Object INSTANCE_LOCK = new Object();
    private static LoginRetries instance = null;

    // Data Map & Lock
    private final Object lock;
    private final Map<String, Retries> users;

    // Control Variables
    private final int maxRetries;
    private final long blockedPeriod;

    // =========================================================================
    // Object Instantiation Methods
    // =========================================================================

    /**
     * Private Constructor, this is a Singleton class.
     *
     * @param maxRetries    Max number of retries before the account is blocked
     * @param blockedPeriod How long the account is blocked for further retries
     */
    private LoginRetries(final int maxRetries, final long blockedPeriod) {
        this.lock = new Object();
        this.users = new HashMap<>(16);
        this.maxRetries = maxRetries;
        this.blockedPeriod = blockedPeriod;
    }

    /**
     * Prepares and returns the instance for this Singleton.
     *
     * @return Singleton instance of this class
     */
    public static LoginRetries getInstance(final int maxRetries, final long blockedPeriod) {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new LoginRetries(maxRetries, blockedPeriod);
            }

            return instance;
        }
    }

    // =========================================================================
    // Public Methods
    // =========================================================================

    /**
     * Register User login attempt, if the User is already registered, then the
     * attempt will be registered and the first attempt will be marked. If the
     * User is already registered, then the registration will be updated.<br />
     *   If the User have exceeded the maximum allowed retries and the the cool
     * down period is not over, then an Exception is thrown. If the cool down
     * period is over, the registration is reset.
     *
     * @param user User to register
     */
    public void registerUser(final String user) {
        final DateFormat format = new SimpleDateFormat(IWSConstants.DATE_TIME_FORMAT, IWSConstants.DEFAULT_LOCALE);
        final Date when = new Date(new Date().getTime() - blockedPeriod);

        synchronized (lock) {
            if (users.containsKey(user)) {
                final Retries retries = users.get(user);

                // If we have exceeded the max allowed number of login attempts
                if (retries.getAttempts() >= maxRetries) {
                    // then check if our calculated "when" is before the first
                    // login attempt
                    if (retries.getFirstAttempt().before(when)) {
                        // The login attempt is made after the cool down period
                        // is over, hence we'll reset the attempt and allow the
                        // User to try again
                        users.put(user, new Retries());
                    } else {
                        // User have exceeded allowed number of login attempts,
                        // and we're still within the cool down period... User
                        // is blocked!
                        final Date time = new Date(retries.getFirstAttempt().getTime() + blockedPeriod);
                        throw new AuthenticationException("User have attempted to login too many times unsuccessfully," +
                                " the account is being Blocked until " + format.format(time));
                    }
                } else {
                    // User has not exceeded the amount of Login attempts
                    retries.registerAttempt();
                }
            } else {
                users.put(user, new Retries());
            }
        }
    }

    /**
     * Get the time when the User Login attempts were first registered.
     *
     * @param user User to check
     * @return Timestamp for First Attempt
     */
    public Date getFirstAttempt(final String user) {
        final Date firstAttempt;

        synchronized (lock) {
            if (users.containsKey(user)) {
                firstAttempt = users.get(user).getFirstAttempt();
            } else {
                firstAttempt = null;
            }
        }

        return firstAttempt;
    }

    /**
     * Get the time when the time of the Last Login Attempt for the User.
     *
     * @param user User to check
     * @return Timedstamp for Last Attempt
     */
    public Date getLastAttempt(final String user) {
        final Date lastAttempt;

        synchronized (lock) {
            if (users.containsKey(user)) {
                lastAttempt = users.get(user).getLastAttempt();
            } else {
                lastAttempt = null;
            }
        }

        return lastAttempt;
    }

    /**
     * Retrieve the number of Retries for a given User. If the user is not
     * registered, then 0 (zero) is returned.
     *
     * @param user User to check
     * @return Number of retries or zero if unknown
     */
    public int getRetries(final String user) {
        final int retries;

        synchronized (lock) {
            if (users.containsKey(user)) {
                retries = users.get(user).getAttempts();
            } else {
                retries = 0;
            }
        }

        return retries;
    }

    /**
     * Removes a user that has been successfully logged in (Authenticated).
     *
     * @param user User to remove
     */
    public void removeAuthenticatedUser(final String user) {
        synchronized (lock) {
            users.remove(user);
        }
    }

    /**
     * Internal Class to hold the information we need to handle login retries.
     */
    private static final class Retries {

        // First attempt cannot be altered
        private final Date firstAttempt = new Date();
        private Date lastAttempt = new Date();
        private int attempts = 1;

        public void registerAttempt() {
            this.lastAttempt = new Date();
            attempts++;
        }

        public Date getFirstAttempt() {
            return firstAttempt;
        }

        public Date getLastAttempt() {
            return lastAttempt;
        }

        public int getAttempts() {
            return attempts;
        }
    }
}