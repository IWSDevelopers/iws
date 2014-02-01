/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.monitoring.Monitored
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.monitoring;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Monitoring of changes to the content of the IWS Entities, is handled by
 * adding this annotation to first of all, the Entity itself to enable
 * Monitoring, and secondly to the fields as well.<br />
 *   The enum {@code MonitoringLevel} contains details about how the monitoring
 * is applied and interpretated by the {@code MonitoringProcessor}. However, the
 * processor will only look at Types (Classes) and Fields which have this
 * annotation added - everything else is skipped.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Documented
@Target({TYPE, FIELD})
@Retention(RUNTIME)
public @interface Monitored {

    /**
     * The level of monitoring to use.
     *
     * @return Monitoring Level
     */
    MonitoringLevel level();

    /**
     * To ensure that the name of the Table to be used for storing can easily be
     * identified, and also the fields themselves, a name is needed. The name
     * of the table is only used internally, whereas the name of the field, is
     * also returned when a list of changes is retrieved.
     *
     * @return Name for the Type (Class) / Field
     */
    String name();
}
