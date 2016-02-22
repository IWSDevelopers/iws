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
package net.iaeste.iws.api.enums.exchange;

import net.iaeste.iws.api.enums.Descriptable;

import javax.xml.bind.annotation.XmlType;

/**
 * Defines the possible states for an Offer
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "offerState")
public enum OfferState implements Descriptable<OfferState> {

    /**
     * In an offer is not shared
     */
    NEW("New"),

    /**
     * IW3 is referring to Offers as Open, and have been viewed.
     */
    OPEN("Open"),

    /**
     * If an offer is shared to multiple countries
     */
    SHARED("Shared"),

    /**
     * If an offer has student applications
     */
    APPLICATIONS("Applications"),

    /**
     * If an offer has student nominations
     */
    NOMINATIONS("Nominations"),

    /**
     * In IW3, some offers were stored with status 'D' as in deleted.
     */
    DELETED("Deleted"),

    //offer status
    CLOSED("Closed"),
    COMPLETED("Completed"),
    AT_EMPLOYER("At Employer"),
    ACCEPTED("Accepted"),
    EXPIRED("Expired"),
    REJECTED("Rejected"),

    // Following is the missing values from IW3. They are added here, so the
    // API contain both old and new values in an attempt at correcting the
    // Exchange Process.
    CANCELLED("Cancelled"),
    EXCHANGED("Exchanged"),
    NOMINATION_REJECTED("Nomination Rejected"),
    NOT_ACCEPTED("Not Accepted"),
    DECLINED("Declined"),
    SN_COMPLETE("SN Complete"),
    TAKEN("Taken"),
    NOMINATION_ACCEPTED("Nomination Accepted"),
    VIEWED("Viewed"),
    WAITING_SN("Waiting"),
    AC_EXCHANGED("AC Exchanged"),
    UNKNOWN("Unknown");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    OfferState(final String description) {
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
