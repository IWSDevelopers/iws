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
import java.util.EnumSet;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlType(name = "offerType")
public enum OfferType implements Descriptable<OfferType> {

    OPEN("Open", "", EnumSet.of(ExchangeType.AC, ExchangeType.IW, ExchangeType.COBE)),
    LIMITED("Limited", "-L", EnumSet.of(ExchangeType.AC, ExchangeType.IW, ExchangeType.COBE)),
    RESERVED("Reserved", "-R", EnumSet.of(ExchangeType.AC, ExchangeType.IW));

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;
    private final String type;
    private final Set<ExchangeType> exchangeTypes;

    OfferType(final String description, final String type, final Set<ExchangeType> exchangeTypes) {
        this.description = description;
        this.type = type;
        this.exchangeTypes = exchangeTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Set<ExchangeType> getExchangeTypes() {
        return exchangeTypes;
    }
}
