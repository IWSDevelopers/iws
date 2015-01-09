/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.Folder
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.util.AbstractVerification;
import net.iaeste.iws.api.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class Folder extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    private String folderId = null;
    private String foldername = null;
    private List<Folder> folders = null;
    private List<File> files = null;
    private Date created = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public Folder() {
    }

    /**
     * Copy Constructor.
     *
     * @param file File Object to copy
     */
    public Folder(final Folder folder) {
        if (folder != null) {
            this.folderId = folder.folderId;
            this.foldername = folder.foldername;
            this.folders = folder.folders;
            this.files = folder.files;
            this.created = folder.created;
        }
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setFolderId(final String folderId) {
        this.folderId = folderId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFoldername(final String foldername) {
        this.foldername = foldername;
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFolders(final List<Folder> folders) {
        this.folders = folders;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFiles(final List<File> files) {
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    // =========================================================================
    // DTO required methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "foldername", foldername);

        return validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Folder)) {
            return false;
        }

        final Folder folder = (Folder) obj;

        if (folderId != null ? !folderId.equals(folder.folderId) : folder.folderId != null) {
            return false;
        }
        if (foldername != null ? !foldername.equals(folder.foldername) : folder.foldername != null) {
            return false;
        }
        if (files != null ? !files.equals(folder.files) : folder.files != null) {
            return false;
        }
        if (created != null ? !created.equals(folder.created) : folder.created != null) {
            return false;
        }
        return !(folders != null ? !folders.equals(folder.folders) : folder.folders != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = IWSConstants.HASHCODE_INITIAL_VALUE;

        result = IWSConstants.HASHCODE_MULTIPLIER * result + ((folderId != null) ? folderId.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (foldername != null ? foldername.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (folders != null ? folders.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (files != null ? files.hashCode() : 0);
        result = IWSConstants.HASHCODE_MULTIPLIER * result + (created != null ? created.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Folder{" +
                "folderId='" + folderId + '\'' +
                ", foldername='" + foldername + '\'' +
                ", folders=" + folders +
                ", files=" + files +
                ", created=" + created +
                '}';
    }
}
