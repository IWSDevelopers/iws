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
package net.iaeste.iws.ws.client.mappers;

import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.Gender;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.MailReply;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.Permission;
import net.iaeste.iws.api.enums.Privacy;
import net.iaeste.iws.api.enums.SortingField;
import net.iaeste.iws.api.enums.SortingOrder;
import net.iaeste.iws.api.enums.UserStatus;
import net.iaeste.iws.api.enums.UserType;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.StudyLevel;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
class EnumMapper {

    /** Private Constructor, this is a Utility Class. */
    protected EnumMapper() {
    }

    // =========================================================================
    // Conversion of Enums
    // =========================================================================

    protected static net.iaeste.iws.ws.Action map(final Action api) {
        return (api != null) ? net.iaeste.iws.ws.Action.valueOf(api.name()) : null;
    }

    protected static Gender map(final net.iaeste.iws.ws.Gender ws) {
        return (ws != null) ? Gender.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.Gender map(final Gender api) {
        return (api != null) ? net.iaeste.iws.ws.Gender.valueOf(api.name()) : null;
    }

    protected static Privacy map(final net.iaeste.iws.ws.Privacy ws) {
        return (ws != null) ? Privacy.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.Privacy map(final Privacy api) {
        return (api != null) ? net.iaeste.iws.ws.Privacy.valueOf(api.name()) : null;
    }

    protected static UserStatus map(final net.iaeste.iws.ws.UserStatus ws) {
        return (ws != null) ? UserStatus.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.UserStatus map(final UserStatus api) {
        return (api != null) ? net.iaeste.iws.ws.UserStatus.valueOf(api.name()) : null;
    }

    protected static UserType map(final net.iaeste.iws.ws.UserType ws) {
        return (ws != null) ? UserType.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.UserType map(final UserType api) {
        return (api != null) ? net.iaeste.iws.ws.UserType.valueOf(api.name()) : null;
    }

    protected static GroupType map(final net.iaeste.iws.ws.GroupType ws) {
        return (ws != null) ? GroupType.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.GroupType map(final GroupType api) {
        return (api != null) ? net.iaeste.iws.ws.GroupType.valueOf(api.name()) : null;
    }

    protected static Currency map(final net.iaeste.iws.ws.Currency ws) {
        return (ws != null) ? Currency.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.Currency map(final Currency api) {
        return (api != null) ? net.iaeste.iws.ws.Currency.valueOf(api.name()) : null;
    }

    protected static Membership map(final net.iaeste.iws.ws.Membership ws) {
        return (ws != null) ? Membership.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.Membership map(final Membership api) {
        return (api != null) ? net.iaeste.iws.ws.Membership.valueOf(api.name()) : null;
    }

    protected static MailReply map(final net.iaeste.iws.ws.MailReply ws) {
        return MailReply.valueOf(ws.value());
    }

    protected static net.iaeste.iws.ws.MailReply map(final MailReply api) {
        return (api != null) ? net.iaeste.iws.ws.MailReply.valueOf(api.name()) : null;
    }

    protected static MonitoringLevel map(final net.iaeste.iws.ws.MonitoringLevel ws) {
        return MonitoringLevel.valueOf(ws.value());
    }

    protected static net.iaeste.iws.ws.MonitoringLevel map(final MonitoringLevel api) {
        return (api != null) ? net.iaeste.iws.ws.MonitoringLevel.valueOf(api.name()) : null;
    }

    protected static NotificationFrequency map(final net.iaeste.iws.ws.NotificationFrequency ws) {
        return (ws != null) ? NotificationFrequency.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.NotificationFrequency map(final NotificationFrequency api) {
        return (api != null) ? net.iaeste.iws.ws.NotificationFrequency.valueOf(api.name()) : null;
    }

    protected static Permission map(final net.iaeste.iws.ws.Permission ws) {
        return (ws != null) ? Permission.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.Permission map(final Permission api) {
        return (api != null) ? net.iaeste.iws.ws.Permission.valueOf(api.name()) : null;
    }

    protected static net.iaeste.iws.ws.SortingField map(final SortingField api) {
        return (api != null) ? net.iaeste.iws.ws.SortingField.valueOf(api.name()) : null;
    }

    protected static net.iaeste.iws.ws.SortingOrder map(final SortingOrder api) {
        return (api != null) ? net.iaeste.iws.ws.SortingOrder.valueOf(api.name()) : null;
    }

    protected static StudyLevel map(final net.iaeste.iws.ws.StudyLevel ws) {
        return (ws != null) ? StudyLevel.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.StudyLevel map(final StudyLevel api) {
        return (api != null) ? net.iaeste.iws.ws.StudyLevel.valueOf(api.name()) : null;
    }

    protected static Language map(final net.iaeste.iws.ws.Language ws) {
        return (ws != null) ? Language.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.Language map(final Language api) {
        return (api != null) ? net.iaeste.iws.ws.Language.valueOf(api.name()) : null;
    }

    protected static LanguageLevel map(final net.iaeste.iws.ws.LanguageLevel ws) {
        return (ws != null) ? LanguageLevel.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.LanguageLevel map(final LanguageLevel api) {
        return (api != null) ? net.iaeste.iws.ws.LanguageLevel.valueOf(api.name()) : null;
    }

    protected static OfferState map(final net.iaeste.iws.ws.OfferState ws) {
        return (ws != null) ? OfferState.valueOf(ws.value()) : null;
    }

    protected static net.iaeste.iws.ws.OfferState map(final OfferState api) {
        return (api != null) ? net.iaeste.iws.ws.OfferState.valueOf(api.name()) : null;
    }
}
