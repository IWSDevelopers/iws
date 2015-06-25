/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.MonitoringLevel
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
 * Whenever an Object is saved or updated, the information may be monitored,
 * using a combined set of information. Meaning, that the Object must allow
 * being monitored. Which not all Objects does - for more information about
 * which Objects allows monitoring, please consult the IDT.<br />
 *   The monitoring is also controllable at Group Level, meaning that each Group
 * may choose to support full monitoring, marking or none at all. By default,
 * the IWS tries to prevent storing any changes that has not been explicitly
 * allowed, so it will not monitor anything.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "MonitoringLevel")
public enum MonitoringLevel implements Descriptable<MonitoringLevel> {

    /**
     * This will effectively disable the monitoring of either the Type (Class)
     * or the Field in question. With this level, no information whatsoever is
     * stored in system regarding changes made.<br />
     *   If used at the Type (Class) level, then it will completely ignore
     * all Field based monitoring settings.<br />
     *   If used on a Field, it will skip this Field, regardless of the level
     * for the Type (Class).
     */
    NONE("No Monitoring"),

    /**
     * With this level, only marking will be performed. Marking means that the
     * actual changes are not mentioned, only that a change took place, together
     * with the user who made the change and the time of the change.<br />
     *   If used at the Type (Class) level, then no information regarding the
     * changes to the fields will be noted.<br />
     *   If used on a Field, it will only note that this Field has been updated.
     */
    MARKED("Marking Monitoring only"),

    /**
     * With this level, all information is stored, i.e. the who and when the
     * update was made, together with the fields and their old -> new
     * values.<br />
     *   If used on the Type (Class) level, then it will include all Field
     * based monitoring settings.<br />
     *   If used on a Field, then it will include the name of the field,
     * together with both the old (original) and new values.
     */
    DETAILED("Detailed Monitoring information");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    MonitoringLevel(final String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
