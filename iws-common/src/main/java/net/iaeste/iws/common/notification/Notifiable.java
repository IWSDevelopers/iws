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
package net.iaeste.iws.common.notification;

import java.util.Map;

/**
 * Please note, that the current information here is very much incomplete. A
 * possible solution would be to use a standard Template Engine for the
 * information, to be send. This way, the method themselves just have to refer
 * to the name of the Template that holds the required information, and by
 * combining the Object with the Template, it should be possible to generate it
 * all.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Notifiable {

    /**
     * Notifications are templates that needs processing before being sent of.
     * This method will prepare the required fields for a given Notification
     * Type. Since not all types of notifications need the same fields, it is
     * required that only the necessary fields for a given Notification Type
     * should be present.
     *
     * @param type Notification Type
     * @return Map with required fields for the given Notification Type
     */
    Map<NotificationField, String> prepareNotifiableFields(NotificationType type);
}
