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
package net.iaeste.iws.client.notifications;

import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationField;
import net.iaeste.iws.common.notification.NotificationType;

import java.util.Map;

/**
 * The Notitication Message Object, contains the information required to fill in
 * an actual notification. No templating is used, the pure raw data is used
 * here, to ensure that we can verify the information without unnecessary
 * clutter.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class NotificationMessage {

    private final Notifiable notifiable;
    private final NotificationType type;

    public NotificationMessage(final Notifiable notifiable, final NotificationType type) {
        this.notifiable = notifiable;
        this.type = type;
    }

    public Notifiable getNotifiable() {
        return notifiable;
    }

    public NotificationType getType() {
        return type;
    }

    public Map<NotificationField, String> getFields() {
        return notifiable.prepareNotifiableFields(type);
    }
}
