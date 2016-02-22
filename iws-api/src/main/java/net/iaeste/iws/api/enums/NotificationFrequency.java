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
 * Notification Frequency - when the user wants to receive notification about an
 * IW action.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "notificationFrequency")
public enum NotificationFrequency implements Descriptable<NotificationFrequency> {

    /**
     * The IWS Notification System will send notifications immediately.
     */
    IMMEDIATELY("Immediately"),

    /**
     * The IWS Notification System will collect all Notifications and only send
     * them once a day in a single message.
     */
    DAILY("Daily"),

    /**
     * The IWS Notification System will collect all Notifications and only send
     * them once a week in a single message.
     */
    WEEKLY("Weekly");

    // =========================================================================
    // Internal Enumeration Functionality
    // =========================================================================

    private final String description;

    /**
     * Internal Constructor, for setting the printable description.
     *
     * @param description Printable description of the Enumeration
     */
    NotificationFrequency(final String description) {
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
