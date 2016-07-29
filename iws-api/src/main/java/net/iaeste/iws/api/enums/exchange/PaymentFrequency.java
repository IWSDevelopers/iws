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
 * PaymentFrequency Object for the Exchange Module.
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "paymentFrequency")
public enum PaymentFrequency implements Descriptable<PaymentFrequency> {

    /**
     * When the Payment frequency is daily.
     */
    DAILY("Daily"),

    /**
     * When the Payment frequency is weekly.
     */
    WEEKLY("Weekly"),

    /**
     * When the Payment frequency is every second week.
     */
    BIWEEKLY("Biweekly"),

    /**
     * When the Payment frequency is monthly.
     */
    MONTHLY("Monthly"),

    /**
     * When the Payment frequency is yearly.
     */
    YEARLY("Yearly");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    PaymentFrequency(final String description) {
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
