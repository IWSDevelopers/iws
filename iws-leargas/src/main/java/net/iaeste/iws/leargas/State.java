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
package net.iaeste.iws.leargas;

/**
 * For keeping track of how many Offers were Created, Updated or Deleted, we're
 * using this simple State Object.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class State {

    private int createdOffers = 0;
    private int updatedOffers = 0;
    private int deletedOffers = 0;

    public void incCreatedOffers() {
        createdOffers++;
    }

    public void incUpdatedOffers() {
        updatedOffers++;
    }

    public void incDeletedOffers() {
        deletedOffers++;
    }

    public String toString() {
        return "created " + createdOffers +
               ", updated " + updatedOffers +
               ", deleted " + deletedOffers +
               " of " + createdOffers + updatedOffers + deletedOffers +
               " Offers";
    }
}
