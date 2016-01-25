/*
 * =============================================================================
 * Copyright 1998-2016, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.configuration.InternalConstants
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

import java.util.Calendar;

/**
 * The public constants defined in the IWSConstants interface, is all exposed
 * via the API. Certain constants are only for internal IWS usage, so instead of
 * placing them in the published Constant class, they should be placed in this.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class InternalConstants {

    /**
     * The time in hours and minutes, when the IWS State Bean which performs
     * regular cleanups, should run. The format must be HH:MM.
     */
    public static final String RUN_CLEAN_TIME = "02:00";

    /**
     * To ensure that the mail system is updated with the latest information
     * from the Users and Groups, the IWS has a Synchronize Scheduler running,
     * which will handle it. This Scheduler must run at intervals, which is
     * defined via this value.
     */
    public static final String MAIL_SYNCHRONIZE_TIME = "*:00";
    /**
     * When starting the IWS, it is possible to tell the IWS to reset all
     * currently open Sessions, so nothing is hanging. By default, it is set
     * to false.
     */
    public static final String STARTUP_RESET_SESSIONS = "false";

    /**
     * Accounts, which have been created but not activated before this number of
     * days, is considered dead. If the user is unable to activate the account
     * before this time - it is very unlikely that it will ever be activated,
     * and it will be completely removed from the system.<br />
     *   If the user later regrets activating the account, no harm has been done
     * as no data was associated with the account. So it is a simple matter to
     * create a new one.
     */
    public static final long ACCOUNT_UNUSED_REMOVED_DAYS = 91;

    /**
     * <p>Active accounts, which have not been used after this number of days,
     * is considered deprecated, and will be suspended.</p>
     *
     * <p>Suspension of an account simply means that it cannot be used unless it
     * is reactivated. The User account data is still there, but all the account
     * will be removed from the mailing lists and the alias will also be
     * removed. However, all personal data is still present.</p>
     *
     * <p>Since it has been a problem that many National Secretaries have not
     * used their Accounts between Annual Conferences, and therefore have seen
     * them be Suspended, it is being updated to 400 days or 13 months.</p>
     */
    public static final long ACCOUNT_INACTIVE_DAYS = 400;

    /**
     * Accounts, which have been suspended this number of days, will be deleted.
     * Deletion means that the account will change status and all private data
     * will be removed. However, the account will still contain the meta data -
     * so any place where the account was referenced will still have the data
     * present.<br />
     *   Deletion of an account is irreversible, as the username (e-mail used to
     * login) will be replaced with an invalid random value, the password will
     * also be removed.
     */
    public static final long ACCOUNT_SUSPENDED_DAYS = 365;

    /**
     * The maximum number of concurrently active tokens in the system. Meaning
     * that only so many people can be logged in at the same time.<br />
     *   The number is added to prevent that too many simultaneous users may
     * overload the system. The tokens are kept in-memory - since read-only
     * requests cannot update the database.
     */
    public static final int MAX_ACTIVE_TOKENS = 500;

    /**
     * Sessions will time-out after a number of minutes being idle. Meaning that
     * no activity took place using the account. The value is set to 8 hours per
     * default, so inactivity during a normal Office workday shouldn't cause any
     * problems.<br />
     *   Update; The idle period has been proven problematic for many users, as
     * other corrections has been made to ensure that Sessions are better
     * monitored, and updated - the default 8 hours has been reduced to 30
     * minutes.
     */
    public static final long MAX_SESSION_IDLE_PERIOD = 1800000;  // 30 minutes

    /**
     * The maximum number of times a user may attempt to login with incorrect
     * password, before the system will close the account temporarily. The
     * duration for the blocking is specified in {@link #LOGIN_BLOCKING_PERIOD}.
     * Once the duration is over, the count is reset and the user may again
     * attempt at login in.<br />
     *   The maximum retries count is added, to prevent someone from performing
     * Denial Of Server based brute force attacks against the system. All the
     * requests are kept in memory, and nothing is persisted, meaning that only
     * a restart of the system will reset these.
     */
    public static final int MAX_LOGIN_RETRIES = 5;

    /**
     * The amount of minutes that a user account is blocked, if too many invalid
     * requests were made. After this period of time, it is again possible to
     * attempt to login.<br />
     *   The time is specified in milli seconds.
     */
    public static final long LOGIN_BLOCKING_PERIOD = 1800000L; // 30 minutes

    private static final Integer CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    /**
     * <p>The Annual Conference is special for the IAESTE Exchange Year, since
     * this is the time when the Exchange Type AC applies, which means that
     * during this period - only this type applies. However, as the Annual
     * Conference takes place at different dates in January, these values only
     * serves as a guideline to ensure that the IWS can handle it. The real
     * values should be controlled via the IWS configuration file.</p>
     *
     * <p>This is the Start date, i.e. the first day of the Conference, where
     * the an Exchange Session takes place, normally this is Saturday.</p>
     */
    public static final String AC_START = "20-JAN-" + CURRENT_YEAR;
    /**
     * <p>This is the end date for the Annual Conference Exchange Sessions, it
     * is normally on Wednesday.</p>
     */
    public static final String AC_END = "30-JAN-" + CURRENT_YEAR;

    /**
     * <p>Offers exchanged at the Annual Conference, is normally having a fixed
     * deadline, which is March 31st of the current year.</p>
     */
    public static final String AC_DEADLINE = "31-MAR-" + CURRENT_YEAR;

    /**
     * This is the length for generated Passwords. Meaning passwords for
     * accounts where no initial password was given.
     */
    public static final int GENERATED_PASSWORD_LENGTH = 8;

    /**
     * For the automatic password generator, we need a list of characters, that
     * cannot be misinterpreted when read, i.e. l and 1 should not be in the
     * list, since they can too easily be mistaken for each other.
     */
    public static final String ALLOWED_GENERATOR_CHARACTERS = "abcdefghjkmnpqrstuvwxzy23456789";

    /**
     * Whenever one of the Schedulers or any other System Service requires a
     * User for the Authentication information, the System Account should be
     * used.
     */
    public static final long SYSTEM_ACCOUNT = 2553L;

    /** Internal Id for the Owner Role. */
    public static final long ROLE_OWNER = 1L;

    /** Internal Id for the Moderator Role. */
    public static final long ROLE_MODERATOR = 2L;

    /** Internal Id for the Member Role. */
    public static final long ROLE_MEMBER = 3L;

    /** Internal Id for the Student Role. */
    public static final long ROLE_STUDENT = 5L;

    /** Private Constructor, this is a Constants Class. */
    private InternalConstants() {
    }
}
