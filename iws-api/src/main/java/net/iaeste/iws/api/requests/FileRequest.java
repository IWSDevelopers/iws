/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public final class FileRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /**
     * The File Object to process.
     */
    private File file = null;
    private Boolean deleteFile = false;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FileRequest() {
    }

    /**
     * Default Constructor.
     *
     * @param file Meta data for the newly created file.
     */
    public FileRequest(final File file) {
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
     * Sets the Delete File flag. If the file exists and this flag is set, then
     * the file will be removed from the system. The deleting operation will
     * wipe the data from the system, and later recovery will not be possible.
     * By default, the value of this flag is set to false.<br />
     *   The method will throw an {@code IllegalArgumentException} if the value
     * is set to null.
     *
     * @param deleteFile True if the file should be deleted, otherwise false
     * @throws IllegalArgumentException if the value is null
     */
    public void setDeleteFile(final Boolean deleteFile) throws IllegalArgumentException {
        ensureNotNull("deleteFile", deleteFile);
        this.deleteFile = deleteFile;
    }

    public Boolean getDeleteFile() {
        return deleteFile;
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

        return validation;
    }
}
