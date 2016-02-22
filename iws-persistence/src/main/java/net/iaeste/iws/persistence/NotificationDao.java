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
package net.iaeste.iws.persistence;

import net.iaeste.iws.common.notification.NotificationType;
import net.iaeste.iws.persistence.entities.notifications.NotificationConsumerEntity;
import net.iaeste.iws.persistence.entities.notifications.NotificationJobEntity;
import net.iaeste.iws.persistence.entities.UserEntity;
import net.iaeste.iws.persistence.entities.UserNotificationEntity;
import net.iaeste.iws.persistence.views.NotificationJobTasksView;

import java.util.Date;
import java.util.List;

/**
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public interface NotificationDao extends BasicDao {

    /**
     * Finds UserNotificationEntity (e.g. user's setting) for specified subject
     * (i.e. event, offer, user, ...)
     *
     * @param user    The User to receive the notification
     * @param type    Type of notification
     * @return        UserNotificationEntity, null if no setting is found, exception if more than 1 setting is found
     */
    UserNotificationEntity findUserNotificationSetting(UserEntity user, NotificationType type);

    /**
     * Finds all NotificationConsumerEntities that are set as active
     *
     * @return List of NotificationConsumerEntity
     */
    List<NotificationConsumerEntity> findActiveNotificationConsumers();

    /**
     * Finds all NotificationJobEntities that are set as processed=false
     *
     * @param date Date how old Jobs will be retrieved
     * @return List of NotificationJobEntity
     */
    List<NotificationJobEntity> findUnprocessedNotificationJobs(Date date);

    /**
     * Finds NotificationConsumerEntity by Id
     *
     * @param id      The consumer id
     * @return        NotificationConsumerEntity
     */
    NotificationConsumerEntity findNotificationConsumerById(Long id);

    /**
     * Finds NotificationJobTaskEntity by ConsumerId
     *
     * @param consumerId    The consumer id
     * @param attemptsLimit Number of attempts when the processing failed, if the value is larger than limit, ignore the task
     * @return            NotificationJobTaskEntity
     */
    List<NotificationJobTasksView> findUnprocessedNotificationJobTaskByConsumerId(Long consumerId, Integer attemptsLimit);

    /**
     * Updates NotificationJobTask entity
     *
     * @param id         NotificationJobTask id
     * @param processed  flag processed/unprocessed
     * @param attempts   number of processing attempts
     */
    void updateNotificationJobTask(Long id, boolean processed, Integer attempts);
}
