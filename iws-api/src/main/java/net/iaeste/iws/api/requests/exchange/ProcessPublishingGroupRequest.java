/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.requests.exchange.ProcessPublishingGroupRequest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.requests.exchange;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.exchange.PublishingGroup;
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
 * @since   IWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processPublishingGroupRequest", propOrder = { "publishingGroup", "deletePublishingGroup" })
public final class ProcessPublishingGroupRequest extends AbstractVerification {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    @XmlElement(required = true, nillable = false) private PublishingGroup publishingGroup = null;
    @XmlElement(required = true, nillable = false) private Boolean deletePublishingGroup = false;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ProcessPublishingGroupRequest() {
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setPublishingGroup(final PublishingGroup publishingGroup) {
        ensureNotNullAndVerifiable("list", publishingGroup);
        this.publishingGroup = new PublishingGroup(publishingGroup);
    }

    public PublishingGroup getPublishingGroup() {
        return publishingGroup;
    }

    public void setDeletePublishingGroup(final Boolean deletePublishingGroup) {
        ensureNotNull("deletePublishingGroup", deletePublishingGroup);
        this.deletePublishingGroup = deletePublishingGroup;
    }

    public Boolean getDeletePublishingGroup() {
        return deletePublishingGroup;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(0);

        isNotNull(validation, "publishingGroup", publishingGroup);
        isNotNull(validation, "deletePublishingGroup", deletePublishingGroup);
        if (publishingGroup != null) {
            validation.putAll(publishingGroup.validate());
        }

        return validation;
    }
}
