/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FileRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.File;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.util.AbstractActionable;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class FileRequest extends AbstractActionable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The File Object to process.
     */
    private File file = null;
    private StorageType type = StorageType.OWNER;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FileRequest() {
        super(EnumSet.of(Action.Process, Action.Delete));
    }

    /**
     * Default Constructor.
     *
     * @param file Meta data for the newly created file.
     */
    public FileRequest(final File file) {
        super(EnumSet.of(Action.Process, Action.Delete));
        setFile(file);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the File to be processed. The method will thrown an
     * {@code IllegalArgumentException} if the Object is not verifiable.
     *
     * @param file File Object to be processed
     * @throws IllegalArgumentException if the File Object is not verifiable
     */
    public void setFile(final File file) throws IllegalArgumentException {
        ensureVerifiable("file", file);
        this.file = new File(file);
    }

    public File getFile() {
        return new File(file);
    }

    /**
     * Sets the type of Storage that is requested. Most commonly, files are
     * retrieved by their owners (default), but a file can also be attached to
     * other Objects, which means that the system needs to be informed, if the
     * fetching should be for an attached offer.<br />
     *   The field is mandatory, since it is used to determine which mechanism
     * is needed for the lookup, hence it must be defined. If set to null, the
     * method will throw an {@code IllegalArgumentException}.
     *
     * @param type Storage Type
     */
    public void setType(final StorageType type) throws IllegalArgumentException {
        ensureNotNull("type", type);
        this.type = type;
    }

    public StorageType getType() {
        return type;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isNotNull(validation, "file", file);
        isNotNull(validation, "type", type);

        return validation;
    }
}
