/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.persistence.monitoring;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import net.iaeste.iws.api.enums.MonitoringLevel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Monitoring of changes to the content of the IWS Entities, is handled by
 * adding this annotation to first of all, the Entity itself to enable
 * Monitoring, and secondly to the fields as well.<br />
 *   The enum {@code MonitoringLevel} contains details about how the monitoring
 * is applied and interpreted by the {@code MonitoringProcessor}. However, the
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
