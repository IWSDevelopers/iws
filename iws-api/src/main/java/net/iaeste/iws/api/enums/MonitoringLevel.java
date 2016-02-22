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
package net.iaeste.iws.api.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * <p>Whenever an Object is saved or updated, the information may be monitored,
 * using a combined set of information. Meaning, that the Object must allow
 * being monitored. Which not all Objects does - for more information about
 * which Objects allows monitoring, please consult the IDT.</p>
 *
 * <p>The monitoring is also controllable at Group Level, meaning that each
 * Group may choose to support full monitoring, marking or none at all. By
 * default, the IWS tries to prevent storing any changes that has not been
 * explicitly allowed, so it will not monitor anything.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "monitoringLevel")
public enum MonitoringLevel implements Descriptable<MonitoringLevel> {

    /**
     * <p>This will effectively disable the monitoring of either the Type
     * (Class) or the Field in question. With this level, no information
     * whatsoever is stored in system regarding changes made.</p>
     *
     * <p>If used at the Type (Class) level, then it will completely ignore
     * all Field based monitoring settings.</p>
     *
     * <p>If used on a Field, it will skip this Field, regardless of the level
     * for the Type (Class).</p>
     */
    NONE("No Monitoring"),

    /**
     * <p>With this level, only marking will be performed. Marking means that
     * the actual changes are not mentioned, only that a change took place,
     * together with the user who made the change and the time of the
     * change.</p>
     *
     * <p>If used at the Type (Class) level, then no information regarding the
     * changes to the fields will be noted.</p>
     *
     * <p>If used on a Field, it will only note that this Field has been
     * updated.</p>
     */
    MARKED("Marking Monitoring only"),

    /**
     * <p>With this level, all information is stored, i.e. the who and when the
     * update was made, together with the fields and their old -&gt; new
     * values.</p>
     *
     * <p>If used on the Type (Class) level, then it will include all Field
     * based monitoring settings.</p>
     *
     * <p>If used on a Field, then it will include the name of the field,
     * together with both the old (original) and new values.</p>
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
