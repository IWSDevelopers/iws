/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FolderRequest
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
import net.iaeste.iws.api.dtos.Folder;
import net.iaeste.iws.api.util.AbstractVerification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class FolderRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private enum Action { PROCESS, DELETE }

    private Action action = null;

    /** The Folder Object to process. */
    private Folder folder = null;

    /**
     * The Id of the Parent Folder, if nothing given - the Group's root folder
     * is used. This value is only used when creating new Folders.
     */
    private String parentId = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FolderRequest() {
        action = Action.PROCESS;
    }

    /**
     * Default Constructor.
     *
     * @param folder Meta data for the folder
     */
    public FolderRequest(final Folder folder) {
        setFolder(folder);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * Sets the File to be processed. The method will thrown an
     * {@code IllegalArgumentException} if the Object is not verifiable.
     *
     * @param folder Folder Object to be processed
     * @throws IllegalArgumentException if the File Object is not verifiable
     */
    public void setFolder(final Folder folder) throws IllegalArgumentException {
        ensureNotNullAndVerifiable("folder", folder);
        this.folder = new Folder(folder);
    }

    public Folder getFolder() {
        return new Folder(folder);
    }

    public void setAction(final Action action) {
        ensureNotNull("action", action);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
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

        isNotNull(validation, "folder", folder);
        isNotNull(validation, "action", action);

        return validation;
    }
}
