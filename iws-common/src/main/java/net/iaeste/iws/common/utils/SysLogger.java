/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IWS (iws-fitnesse)
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * System Logger, used for logging of services, where the normal logging
 * mechanism is unavailable. To ensure that all logs follow the same pattern,
 * the class is implemented as a Singleton. All log records are written to
 * STDOUT.
 *
 * @author  Kim Jensen / last $Author: jensen.kim $
 * @version $Revision: #2 $ / $Date: 2012/01/24 $
 * @since   1.7
 * @noinspection StaticNonFinalField, CallToPrintStackTrace, UseOfSystemOutOrSystemErr, VariableNotUsedInsideIf
 */
public final class SysLogger {

    /** Singleton Instance. */
    private static SysLogger instance = null;
    /** Lock for the intance. */
    private static final Object INSTANCE_LOCK = new Object();

    /** The pattern to use to extract the Class name for a method. */
    private static final Pattern PROFILE_JAVA_FILE_PATTERN = Pattern.compile(".java");

    // The log level settings is numerical, meaning that the logger will try
    // to verify if the current level is the same or higher, than the default.
    /** information relevant while developing. */
    public static final int LOG_LEVEL_TRACE = 1;
    /** Information relevant while verifying, analyzing problems, etc. */
    public static final int LOG_LEVEL_DEBUG = 2;
    /** General purpose information regarding the processing */
    public static final int LOG_LEVEL_INFO = 3;
    /** Recoverable problem, i.e. problem reading a record from the DB, etc. */
    public static final int LOG_LEVEL_WARN = 4;
    /** Irrecoverable problem, i.e. Logical errors, configuration errors, etc. */
    public static final int LOG_LEVEL_ERROR = 5;
    /** External problem, i.e. IO error, DB error, etc. */
    public static final int LOG_LEVEL_FATAL = 6;
    /** No logging. */
    public static final int LOG_LEVEL_NONE = 7;

    // The level to be displayed as part of the log message
    private static final String LOG_START_TAG = "[";
    private static final String LOG_END_TAG = "] ";
    private static final String LOG_MESSAGE_PROFILE = "PROFILE";
    private static final String LOG_MESSAGE_REPORT  = "REPORT ";
    private static final String LOG_MESSAGE_TRACE   = " TRACE ";
    private static final String LOG_MESSAGE_DEBUG   = " DEBUG ";
    private static final String LOG_MESSAGE_INFO    = " INFO  ";
    private static final String LOG_MESSAGE_WARN    = "WARNING";
    private static final String LOG_MESSAGE_ERROR   = " ERROR ";
    private static final String LOG_MESSAGE_FATAL   = " FATAL ";

    // Internal variables
    private String prefix = "";
    private int logLevel = LOG_LEVEL_INFO;
    private boolean profiling = false;
    private boolean printPrefix = false;
    private boolean printStackTrace = false;
    private boolean printTimestamp = true;
    private boolean printJavaInfo = false;
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);

    // =========================================================================
    // =====  Instantiation methods
    // =========================================================================

    /**
     * Singleton Constructor.
     */
    private SysLogger() {
    }

    /**
     * Getting an instantiated version of the Object. If already instantiated
     * it will return the current instance, otherwise it will create a new
     * instance.
     *
     * @return SysLogger instance
     */
    public static SysLogger getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new SysLogger();
            }

            return instance;
        }
    }

    // =========================================================================
    // =====  Profiling methods
    // =========================================================================

    /**
     * Check to see if the profiling is enabled or not, similar to
     * isTraceEnabled and isDebugEnabled.
     *
     * @return True if profiling is enabled, otherwise false
     */
    public boolean isProfilingEnabled() {
        return profiling;
    }

    /**
     * For profiling purposes, shows a message based on the time since the
     * 'before' time. The method can be enabled or disabled via the profile
     * flag, which by default should be set to false.<br />
     *   To use the method, simply create a new Date object at the top of the
     * code that you wish to profile, and call this method with the date object
     * at the end.<br />
     *   If the caller invoking this method is called 'myProfile', then this
     * method is omitted in the log message, and the log message will instead
     * refer to the earlier method on the call stack.
     *
     * @param before  The timestamp to start this profile from
     */
    public void profile(final Date before) {
        if (profiling && (before != null)) {
            final long duration = new Date().getTime() - before.getTime();

            final StackTraceElement[] ste = new Throwable().getStackTrace();
            final StackTraceElement element = ste[1];
            final String file = PROFILE_JAVA_FILE_PATTERN.matcher(element.getFileName()).replaceFirst("");
            String method = element.getMethodName();

            if ("myProfile".equals(method)) {
                method = ste[2].getMethodName();
            }

            final String message = file + '.' + method + "(): " + duration + " ms";
            writer(LOG_MESSAGE_PROFILE, message, null);
        }
    }

    // =========================================================================
    // =====  Logging methods
    // =========================================================================

    /**
     * Log the information, regardless of the loglevel.
     *
     * @param message   The message to log
     */
    public void report(final String message) {
        writer(LOG_MESSAGE_REPORT, message, null);
    }

    /**
     * Log the information, regardless of the loglevel.
     *
     * @param message   The message to log
     * @param throwable Exception thrown
     */
    public void report(final String message, final Throwable throwable) {
        writer(LOG_MESSAGE_REPORT, message, throwable);
    }

    /**
     * Returns true, if the log level 'Trace' is enabled.
     *
     * @return True if 'Trace' is enabled, otherwise false
     */
    public boolean isTraceEnabled() {
        return logLevel <= LOG_LEVEL_TRACE;
    }

    /**
     * Logs debugging information, information relevant for either development
     * or determining the source of a runtime problem, i.e. a problem which
     * prevents the program from completing with the expected results.
     *
     * @param message   The message to log
     */
    public void trace(final String message) {
        if (logLevel <= LOG_LEVEL_TRACE) {
            writer(LOG_MESSAGE_TRACE, message, null);
        }
    }

    /**
     * Logs debugging information, information relevant for either development
     * or determining the source of a runtime problem, i.e. a problem which
     * prevents the program from completing with the expected results.
     *
     * @param message   The message to log
     * @param throwable Exception thrown
     */
    public void trace(final String message, final Throwable throwable) {
        if (logLevel <= LOG_LEVEL_TRACE) {
            writer(LOG_MESSAGE_TRACE, message, throwable);
        }
    }

    /**
     * Returns true, if the log level 'Debug' is enabled.
     *
     * @return True if 'Debug' is enabled, otherwise false
     */
    public boolean isDebugEnabled() {
        return logLevel <= LOG_LEVEL_DEBUG;
    }

    /**
     * Logs debugging information, information relevant for either development
     * or determining the source of a runtime problem, i.e. a problem which
     * prevents the program from completing with the expected results.
     *
     * @param message   The message to log
     */
    public void debug(final String message) {
        if (logLevel <= LOG_LEVEL_DEBUG) {
            writer(LOG_MESSAGE_DEBUG, message, null);
        }
    }

    /**
     * Logs debugging information, information relevant for either development
     * or determining the source of a runtime problem, i.e. a problem which
     * prevents the program from completing with the expected results.
     *
     * @param message   The message to log
     * @param throwable Exception thrown
     */
    public void debug(final String message, final Throwable throwable) {
        if (logLevel <= LOG_LEVEL_DEBUG) {
            writer(LOG_MESSAGE_DEBUG, message, throwable);
        }
    }

    /**
     * Logs information, this is for general purpose information, which the
     * developer believes is in the interest of the user.
     *
     * @param message   The message to log
     */
    public void info(final String message) {
        if (logLevel <= LOG_LEVEL_INFO) {
            writer(LOG_MESSAGE_INFO, message, null);
        }
    }

    /**
     * Logs information, this is for general purpose information, which the
     * developer believes is in the interest of the user.
     *
     * @param message   The message to log
     * @param throwable Exception thrown
     */
    public void info(final String message, final Throwable throwable) {
        if (logLevel <= LOG_LEVEL_INFO) {
            writer(LOG_MESSAGE_INFO, message, throwable);
        }
    }

    /**
     * Logs warning messages, warnings are problems which are of a recoverable
     * status, i.e. temporary problems or problems related to user provided
     * information. The program should do it's best to circumvent such problems
     * and ensure that the program will continue properly.
     *
     * @param message   The message to log
     */
    public void warn(final String message) {
        if (logLevel <= LOG_LEVEL_WARN) {
            writer(LOG_MESSAGE_WARN, message, null);
        }
    }

    /**
     * Logs warning messages, warnings are problems which are of a recoverable
     * status, i.e. temporary problems or problems related to user provided
     * information. The program should do it's best to circumvent such problems
     * and ensure that the program will continue properly.
     *
     * @param message   The message to log
     * @param throwable Exception thrown
     */
    public void warn(final String message, final Throwable throwable) {
        if (logLevel <= LOG_LEVEL_WARN) {
            writer(LOG_MESSAGE_WARN, message, throwable);
        }
    }

    /**
     * Logs error problems, an error is defined as a problem which occurs within
     * the program, i.e. configuration error, invalid queries, etc. The problems
     * will normally prevent the program from completing normally, and thus it
     * should terminate.
     *
     * @param message   The message to log
     */
    public void error(final String message) {
        if (logLevel <= LOG_LEVEL_ERROR) {
            writer(LOG_MESSAGE_ERROR, message, null);
        }
    }

    /**
     * Logs error problems, an error is defined as a problem which occurs within
     * the program, i.e. configuration error, invalid queries, etc. The problems
     * will normally prevent the program from completing normally, and thus it
     * should terminate.
     *
     * @param message   The message to log
     * @param throwable Exception thrown
     */
    public void error(final String message, final Throwable throwable) {
        if (logLevel <= LOG_LEVEL_ERROR) {
            writer(LOG_MESSAGE_ERROR, message, throwable);
        }
    }

    /**
     * Logs fatal problems, a fatal problem is defined as a problem which has
     * external roots, i.e. disk/net I/O, DB connection problems, etc. Problems
     * of a nature which is beyond the control of the programmer! Normally, when
     * such a problem arises, the program should tell that it is incapable of
     * working properly!
     *
     * @param message   The message to log
     */
    public void fatal(final String message) {
        if (logLevel <= LOG_LEVEL_FATAL) {
            writer(LOG_MESSAGE_FATAL, message, null);
        }
    }

    /**
     * Logs fatal problems, a fatal problem is defined as a problem which has
     * external roots, i.e. disk/net I/O, DB connection problems, etc. Problems
     * of a nature which is beyond the control of the programmer! Normally, when
     * such a problem arises, the program should tell that it is incapable of
     * working properly!
     *
     * @param message   The message to log
     * @param throwable Exception thrown
     */
    public void fatal(final String message, final Throwable throwable) {
        if (logLevel <= LOG_LEVEL_FATAL) {
            writer(LOG_MESSAGE_FATAL, message, throwable);
        }
    }

    // =========================================================================
    // =====  Setters and getters
    // =========================================================================

    /**
     * By overwriting the current loglevel, at which messages will be written,
     * it is possible to runtime set the system into debug mode, or to minimize
     * logging information.<br />
     * The level provided must be greater than 0, otherwise the method will not
     * update the internal loglevel setting. If the given level is greater than
     * the highest defined loglevel, it means that nothing will be logged! The
     * highest defined loglevel is "fatal".
     *
     * @param  level  The new log level
     */
    public void setLogLevel(final int level) {
        if ((level >= LOG_LEVEL_TRACE) && (level <= LOG_LEVEL_NONE)) {
            logLevel = level;
        }
    }

    /**
     * Sets the logging prefix, which all logentries will have. This will make
     * it easier to identify the log entries in the logfile.<br />
     * If the given prefix value is either null or empty, then the method will
     * not update the prefix settings.
     *
     * @param  prefix  The log prefix
     */
    public void setPrefix(final String prefix) {
        if (prefix != null && !prefix.isEmpty()) {
            this.prefix = prefix;
        }
    }

    /**
     * Defines the Timestamp format.
     *
     * @param  timestamp  The string to use to format the timestamps
     * @see    java.text.SimpleDateFormat
     */
    public void setTimestampFormat(final String timestamp) {
        try {
            // Let's validate the new format, and store the result if valid
            final SimpleDateFormat mySDF = new SimpleDateFormat(timestamp, Locale.GERMAN);

            dateFormat = timestamp;
            sdf = mySDF;
        } catch (Exception e) {
            // In case of no data (NullPointer Exception), or invalid format
            // (IllegalArgumentException), we better log it!
            warn("Invalid input", e);
        }
    }

    /**
     * Profiling is a way to time how long a piece of code takes. By enabling
     * it within the SysLogger, the method {@code profile} will be enabled.
     */
    public void enableProfiling() {
        profiling = true;
    }

    /**
     * Profiling is a way to time how long a piece of code takes. By disabling
     * it within the SysLogger, the method {@code profile} will be disabled.
     */
    public void disableProfiling() {
        profiling = false;
    }

    /**
     * Enables showing the prefix, when writing a log entry. This is disabled
     * by default.
     */
    public void enablePrefix() {
        printPrefix = true;
    }

    /**
     * Disabled showing the prefix, when writing a log entry. This is disabled
     * by default.
     */
    public void disablePrefix() {
        printPrefix = false;
    }

    /**
     * Enables showing Stack Traces, when handling exceptions. This is disabled
     * by default.
     */
    public void enableStackTrace() {
        printStackTrace = true;
    }

    /**
     * Disables showing Stack Traces, when handling exceptions. This is disabled
     * by default.
     */
    public void disableStackTrace() {
        printStackTrace = false;
    }

    /**
     * Enables timestamps in the log entries. This is disabled by default
     */
    public void enableTimestamp() {
        printTimestamp = true;
    }

    /**
     * Disables timestamps in the log entries. This is disabled by default.
     */
    public void disableTimestamp() {
        printTimestamp = false;
    }

    /**
     * Enables displaying the Java Source file and line number. This is disabled
     * by default.
     */
    public void enableJavaInformation() {
        printJavaInfo = true;
    }

    /**
     * Disables displaying the Java Source file and line number. This is
     * disabled by default.
     */
    public void disableJavaInformation() {
        printJavaInfo = false;
    }

    // =========================================================================
    // =====  Internal methods
    // =========================================================================

    /**
     * Writes the log message to STDOUT, so it can be caught by the Application
     * Server and written in the correct log file.
     *
     * @param info      The log type information, eg. DEBUG, INFO, etc.
     * @param message   The actual message, provided by the caller
     * @param throwable The exception information to display (if any)
     */
    private void writer(final String info, final String message, final Throwable throwable) {
        // Generate the logmessage information
        final String msgExc = throwable != null ? " { " + throwable.toString() + " } " : "";
        final String toLog = info + ' ' + extractMethodName(throwable) + message + msgExc;

        // Write the logentry to STDOUT, in case of different destination,
        // please update this line!
        System.out.println(generateLogTimestamp() + extractPrefix() + toLog);

        // If we have received an exception let's see if we should be display it
        if ((throwable != null) && printStackTrace) {
            throwable.printStackTrace();
        }
    }

    /**
     * Extract the prefix, for the log message, and returns either this or an
     * empty string, depending upon the settings.
     *
     * @return Prefix or empty string
     */
    private String extractPrefix() {
        final String thePrefix;

        if (printPrefix && !prefix.isEmpty()) {
            thePrefix = LOG_START_TAG + prefix + LOG_END_TAG;
        } else {
            thePrefix = "";
        }

        return thePrefix;
    }

    /**
     * This method will extract the method calling the log method, from the
     * call* stack. The method will return either the caller, or an empty
     * string, depending upon the settings.<br />
     *   If the Throwable object is defined, i.e. not null, then the method
     * name to be logged is taken from the previous place in the call stack.
     *
     * @param  throwable  Used to determine the index in the call stack
     * @return Calling method, or empty string
     */
    private String extractMethodName(final Throwable throwable) {
        final String caller;

        if (printJavaInfo) {
            final int index = throwable != null ? 4 : 3;
            final StackTraceElement[] ste = new Throwable().getStackTrace();
            final StackTraceElement element = ste[index]; // => calling method
            final String method = element.getFileName() + ':' + element.getLineNumber();

            caller = LOG_START_TAG + method + LOG_END_TAG;
        } else {
            caller = "";
        }

        return caller;
    }

    /**
     * If we should print the timestamp, then this method will generate the
     * requested timestamp information, and return it, otherwise it will return
     * an empty string.
     *
     * @return Timestamp or empty string
     */
    private String generateLogTimestamp() {
        final String timestamp;

        if (printTimestamp) {
            final Date date = new Date();

            timestamp = LOG_START_TAG + this.sdf.format(date) + LOG_END_TAG;
        } else {
            timestamp = "";
        }

        return timestamp;
    }
}
