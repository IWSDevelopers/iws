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
package net.iaeste.iws.core.services;

import net.iaeste.iws.api.enums.exchange.OfferFields;
import net.iaeste.iws.api.requests.exchange.OfferCSVUploadRequest;
import net.iaeste.iws.common.configuration.Settings;
import net.iaeste.iws.persistence.BasicDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
class CommonCSVService<T extends BasicDao> extends CommonService<T> {

    protected static final OfferCSVUploadRequest.FieldDelimiter DELIMITER = OfferCSVUploadRequest.FieldDelimiter.COMMA;

    CommonCSVService(final Settings settings, final T dao) {
        super(settings, dao);
    }

    protected static List<String> createFirstRow(final OfferFields.Type type) {
        final List<String> result = new ArrayList<>();

        addField(result, OfferFields.REF_NO, type);
        addField(result, OfferFields.OFFER_TYPE, type);
        addField(result, OfferFields.EXCHANGE_TYPE, type);
        addField(result, OfferFields.DEADLINE, type);
        addField(result, OfferFields.COMMENT, type);
        addField(result, OfferFields.EMPLOYER, type);
        addField(result, OfferFields.DEPARTMENT, type);
        addField(result, OfferFields.STREET1, type);
        addField(result, OfferFields.STREET2, type);
        addField(result, OfferFields.POSTBOX, type);
        addField(result, OfferFields.POSTAL_CODE, type);
        addField(result, OfferFields.CITY, type);
        addField(result, OfferFields.STATE, type);
        addField(result, OfferFields.COUNTRY, type);
        addField(result, OfferFields.WEBSITE, type);
        addField(result, OfferFields.WORKPLACE, type);
        addField(result, OfferFields.BUSINESS, type);
        addField(result, OfferFields.RESPONSIBLE, type);
        addField(result, OfferFields.AIRPORT, type);
        addField(result, OfferFields.TRANSPORT, type);
        addField(result, OfferFields.EMPLOYEES, type);
        addField(result, OfferFields.HOURS_WEEKLY, type);
        addField(result, OfferFields.HOURS_DAILY, type);
        addField(result, OfferFields.CANTEEN, type);
        addField(result, OfferFields.FACULTY, type);
        addField(result, OfferFields.SPECIALIZATION, type);
        addField(result, OfferFields.TRAINING_REQUIRED, type);
        addField(result, OfferFields.OTHER_REQUIREMENTS, type);
        addField(result, OfferFields.WORK_KIND, type);
        addField(result, OfferFields.WEEKS_MIN, type);
        addField(result, OfferFields.WEEKS_MAX, type);
        addField(result, OfferFields.FROM, type);
        addField(result, OfferFields.TO, type);
        addField(result, OfferFields.STUDY_COMPLETED_BEGINNING, type);
        addField(result, OfferFields.STUDY_COMPLETED_MIDDLE, type);
        addField(result, OfferFields.STUDY_COMPLETED_END, type);
        addField(result, OfferFields.WORK_TYPE_P, type);
        addField(result, OfferFields.WORK_TYPE_R, type);
        addField(result, OfferFields.WORK_TYPE_W, type);
        addField(result, OfferFields.WORK_TYPE_N, type);
        addField(result, OfferFields.LANGUAGE_1, type);
        addField(result, OfferFields.LANGUAGE_1_LEVEL, type);
        addField(result, OfferFields.LANGUAGE_1_OR, type);
        addField(result, OfferFields.LANGUAGE_2, type);
        addField(result, OfferFields.LANGUAGE_2_LEVEL, type);
        addField(result, OfferFields.LANGUAGE_2_OR, type);
        addField(result, OfferFields.LANGUAGE_3, type);
        addField(result, OfferFields.LANGUAGE_3_LEVEL, type);
        addField(result, OfferFields.CURRENCY, type);
        addField(result, OfferFields.PAYMENT, type);
        addField(result, OfferFields.PAYMENT_FREQUENCY, type);
        addField(result, OfferFields.DEDUCTION, type);
        addField(result, OfferFields.LODGING, type);
        addField(result, OfferFields.LODGING_COST, type);
        addField(result, OfferFields.LODGING_COST_FREQUENCY, type);
        addField(result, OfferFields.LIVING_COST, type);
        addField(result, OfferFields.LIVING_COST_FREQUENCY, type);
        addField(result, OfferFields.NO_HARD_COPIES, type);
        addField(result, OfferFields.STATUS, type);
        addField(result, OfferFields.PERIOD_2_FROM, type);
        addField(result, OfferFields.PERIOD_2_TO, type);
        addField(result, OfferFields.HOLIDAYS_FROM, type);
        addField(result, OfferFields.HOLIDAYS_TO, type);
        addField(result, OfferFields.ADDITIONAL_INFO, type);
        addField(result, OfferFields.SHARED, type);
        addField(result, OfferFields.LAST_MODIFIED, type);
        addField(result, OfferFields.NS_FIRST_NAME, type);
        addField(result, OfferFields.NS_LAST_NAME, type);

        return result;
    }

    private static void addField(final List<String> row, final OfferFields field, final OfferFields.Type type) {
        if (field.useField(type)) {
            row.add(field.getField());
        }
    }
}
