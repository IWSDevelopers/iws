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
package net.iaeste.iws.core.notifications;

import net.iaeste.iws.common.utils.Observable;
import net.iaeste.iws.persistence.Authentication;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.common.notification.Notifiable;
import net.iaeste.iws.common.notification.NotificationType;

/**
 * Classes to be observed, should extend the Notifiable interface.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface Notifications extends Observable {

    /**
     * For (almost) all notifications. this method should be invoked, since it
     * will ensure that the correct notification is persisted and send to the
     * notification queue.
     *
     * @param authentication Authentication information (user + group)
     * @param obj            Instance to notify about changes for
     * @param type           Type of Notification Message to send
     */
    void notify(Authentication authentication, Notifiable obj, NotificationType type);

    /**
     * For the Forgot password functionality, we only have the {@code UserEntity}
     * Object at hand.
     *
     * @param user {@code UserEntity} Object
     */
    void notify(UserEntity user);

    /**
     * Notify methods prepare jobs to be processed. The processing is invoked by this method
     * and creates tasks for consumers. It also trigger consumers
     */
    void processJobs();
}
