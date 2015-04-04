/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.FetchFolderRequest
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
import net.iaeste.iws.api.util.AbstractVerification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FetchFolderRequest", propOrder = { "folderId" })
public final class FetchFolderRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = true)
    private String folderId = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchFolderRequest() {
    }

    /**
     * Default Constructor.
     *
     * @param folderId the Id of the folder to read
     * @throws IllegalArgumentException if the fileId is invalid
     */
    public FetchFolderRequest(final String folderId) throws IllegalArgumentException {
        setFolderId(folderId);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * The FolderId is a mandatory field, and must be a proper Id, otherwise the
     * method will throw an {@code IllegalArgumentException}.
     *
     * @param folderId Id of the folder to fetch
     * @throws IllegalArgumentException if not an valid Id
     */
    public void setFolderId(final String folderId) throws IllegalArgumentException {
        ensureValidId("folderId", folderId);
        this.folderId = folderId;
    }

    public String getFolderId() {
        return folderId;
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

        return validation;
    }
}
