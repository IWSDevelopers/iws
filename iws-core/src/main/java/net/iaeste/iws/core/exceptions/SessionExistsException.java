/*
 */
package net.iaeste.iws.core.exceptions;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;

/**
 * @author Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class SessionExistsException extends IWSException {

    /** {@link net.iaeste.iws.api.constants.IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * Default Constructor, for the case where an error condition has arisen,
     * and the only information available is the type of error, and a message
     * describing the error.
     *
     * @param message  Specific message, regarding the problem
     */
    public SessionExistsException(final String message) {
        super(IWSErrors.SESSION_EXISTS, message);
    }
}
