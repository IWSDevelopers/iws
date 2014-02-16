/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchFileRequest
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
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class FetchFileRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String fileId = null;
    private String groupId = null;
    private StorageType type = StorageType.OWNER;
    private Boolean readFileData = false;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchFileRequest() {
    }

    /**
     * Default Constructor.
     *
     * @param fileId the Id of the file to read
     * @throws IllegalArgumentException if the fileId is invalid
     */
    public FetchFileRequest(final String fileId) throws IllegalArgumentException {
        setFileId(fileId);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * The FileId is a mandatory field, and must be a proper Id, otherwise the
     * method will throw an {£code IllegalArgumentException}.
     *
     * @param fileId Id of the file to fetch
     * @throws IllegalArgumentException if null or an invalid Id
     */
    public void setFileId(final String fileId) throws IllegalArgumentException {
        ensureNotNullAndValidId("fileId", fileId);
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    /**
     * If the file to be fetched is not owned by the user, but is rather an
     * attachment to a different Object, then it is required that the GroupId
     * is set, otherwise the system will reject the request.<br />
     *   The method will throw an {@code IllegalArgumentException} if the given
     * GroupId is invalid.
     *
     * @param groupId GroupId
     * @throws IllegalArgumentException if the given GroupId is invalid
     */
    public void setGroupId(final String groupId) throws IllegalArgumentException {
        ensureValidId("groupId", groupId);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
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

    /**
     * Determines if the data of the file should also be read out. If not set,
     * then the data is not returned, only the file information.<br />
     *   The method requires that this value is defined. If undefined, i.e.
     * null, then the method will throw an {@code IllegalArgumentException}.
     *
     * @param readFileData Boolean flag to read file data or not
     * @throws IllegalArgumentException if set to null
     */
    public void setReadFileData(final Boolean readFileData) throws IllegalArgumentException {
        ensureNotNull("readFileData", readFileData);
        this.readFileData = readFileData;
    }

    public Boolean getReadFileData() {
        return readFileData;
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

        isNotNull(validation, "fileId", fileId);
        isNotNull(validation, "type", type);

        return validation;
    }
}
