/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.StorageType
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * Files stored in the system can be either owned by someone or shared to
 * someone. In order for the reading of files to work properly, we need to
 * provide the correct context. Hence, this enumerated type.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "StorageType")
public enum StorageType {

    /**
     * By default, the storage type is the Owner.
     */
    OWNER("Owner"),

    /**
     * If reading a file from a public folder, then this Storage Type must be
     * used.
     */
    FOLDER("Folder"),

    /**
     * Attached to an Offer, meaning that the rule that applies is that the
     * provided File Id must be belong to an attachment, which the retrieving
     * person has access to.
     */
    ATTACHED_TO_APPLICATION("Student Application Attachment");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    StorageType(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
