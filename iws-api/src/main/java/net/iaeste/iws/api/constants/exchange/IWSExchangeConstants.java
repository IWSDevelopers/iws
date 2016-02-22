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
package net.iaeste.iws.api.constants.exchange;

import java.util.regex.Pattern;

/**
 * Exchange specific constants for the IW Services.
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
public final class IWSExchangeConstants {

    /**
     * Following a Board decision in the Autumn, 2014 - all Reference Numbers
     * must apply to the following regex. The format was updated as part of Trac
     * ticket #930, which supersede the format ticket #414. The actual decision
     * points from the Board minutes is still pending.
     */
    public static final String REFNO_FORMAT = "[A-Z]{2}-[0-9]{4}-[A-Z0-9\\-]{1,8}";
    public static final Pattern REFNO_PATTERN = Pattern.compile(REFNO_FORMAT);

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.exchange.FieldOfStudy}
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    public static final int MAX_OFFER_FIELDS_OF_STUDY = 3;

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.exchange.Specialization}
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}. Note, that the
     * original value was 3, although it should have been 7. But, it seems that
     * we have Offers with up to 12 in the DB. So rather than enforcing a lower
     * amount of records, we're raising the bar to make sure that existing
     * Offers will work.
     */
    public static final int MAX_OFFER_SPECIALIZATIONS = 12;

    /**
     * Sets are converted into Strings internally, using this constant as
     * delimiter. For this reason, it is not allowed to use this in the String
     * given as it will cause errors.
     */
    public static final String SET_DELIMITER = "|";

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.Language}
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    public static final int MAX_OFFER_LANGUAGES = 3;

    /**
     * Defines the maximum length of offer description
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    public static final int MAX_OFFER_WORK_DESCRIPTION_SIZE = 3000;

    /**
     * Defines the maximum length of other requirements
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    public static final int MAX_OFFER_OTHER_REQUIREMENTS_SIZE = 4000;

    /**
     * Defines the minimum number of weeks required
     * for an {@link net.iaeste.iws.api.dtos.exchange.Offer}
     */
    public static final int MIN_OFFER_MINIMUM_WEEKS = 4;

    /**
     * Defines the maximum number of additional documents
     * allowed to attach to a {@link net.iaeste.iws.api.dtos.exchange.StudentApplication}
     */
    public static final int MAX_NUMBER_OF_ADDITIONAL_DOCUMENTS = 10;

    /** Private Constructor, this is a Constants Class. */
    private IWSExchangeConstants() {
    }
}
