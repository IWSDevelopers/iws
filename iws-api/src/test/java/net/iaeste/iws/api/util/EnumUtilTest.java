/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.util.EnumUtilTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.enums.ContactsType;
import net.iaeste.iws.api.enums.CountryType;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Language;
import net.iaeste.iws.api.enums.MonitoringLevel;
import net.iaeste.iws.api.enums.NotificationFrequency;
import net.iaeste.iws.api.enums.StorageType;
import net.iaeste.iws.api.enums.UserType;
import net.iaeste.iws.api.enums.exchange.ExchangeType;
import net.iaeste.iws.api.enums.exchange.FieldOfStudy;
import net.iaeste.iws.api.enums.exchange.LanguageLevel;
import net.iaeste.iws.api.enums.exchange.LanguageOperator;
import net.iaeste.iws.api.enums.exchange.OfferState;
import net.iaeste.iws.api.enums.exchange.OfferType;
import net.iaeste.iws.api.enums.exchange.PaymentFrequency;
import net.iaeste.iws.api.enums.exchange.Specialization;
import net.iaeste.iws.api.enums.exchange.StudyLevel;
import net.iaeste.iws.api.enums.exchange.TypeOfWork;
import org.junit.Test;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
public final class EnumUtilTest {

    @Test
    public void testEnumUtilOnContactsType() {
        for (final ContactsType type : ContactsType.values()) {
            assertThat(EnumUtil.valueOf(ContactsType.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(ContactsType.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnCountryType() {
        for (final CountryType type : CountryType.values()) {
            assertThat(EnumUtil.valueOf(CountryType.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(CountryType.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnCurrency() {
        for (final Currency type : Currency.values()) {
            assertThat(EnumUtil.valueOf(Currency.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(Currency.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnExchangeType() {
        for (final ExchangeType type : ExchangeType.values()) {
            assertThat(EnumUtil.valueOf(ExchangeType.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnFieldOfStudy() {
        for (final FieldOfStudy type : FieldOfStudy.values()) {
            assertThat(EnumUtil.valueOf(FieldOfStudy.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(FieldOfStudy.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnGroupType() {
        for (final GroupType type : GroupType.values()) {
            assertThat(EnumUtil.valueOf(GroupType.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(GroupType.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnLanguage() {
        for (final Language type : Language.values()) {
            assertThat(EnumUtil.valueOf(Language.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(Language.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnLanguageLevel() {
        for (final LanguageLevel type : LanguageLevel.values()) {
            assertThat(EnumUtil.valueOf(LanguageLevel.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(LanguageLevel.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnLanguageOperator() {
        for (final LanguageOperator type : LanguageOperator.values()) {
            assertThat(EnumUtil.valueOf(LanguageOperator.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(LanguageOperator.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnMonitoringLevel() {
        for (final MonitoringLevel type : MonitoringLevel.values()) {
            assertThat(EnumUtil.valueOf(MonitoringLevel.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(MonitoringLevel.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnNotificationFrequency() {
        for (final NotificationFrequency type : NotificationFrequency.values()) {
            assertThat(EnumUtil.valueOf(NotificationFrequency.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(NotificationFrequency.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnOfferState() {
        for (final OfferState type : OfferState.values()) {
            assertThat(EnumUtil.valueOf(OfferState.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(OfferState.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnOfferType() {
        for (final OfferType type : OfferType.values()) {
            assertThat(EnumUtil.valueOf(OfferType.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(OfferType.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnPaymentFrequency() {
        for (final PaymentFrequency type : PaymentFrequency.values()) {
            assertThat(EnumUtil.valueOf(PaymentFrequency.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(PaymentFrequency.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnSpecialization() {
        for (final Specialization type : Specialization.values()) {
            assertThat(EnumUtil.valueOf(Specialization.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(Specialization.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnStorageType() {
        for (final StorageType type : StorageType.values()) {
            assertThat(EnumUtil.valueOf(StorageType.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(StorageType.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnStudyLevel() {
        for (final StudyLevel type : StudyLevel.values()) {
            assertThat(EnumUtil.valueOf(StudyLevel.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(StudyLevel.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnTypeOfWork() {
        for (final TypeOfWork type : TypeOfWork.values()) {
            assertThat(EnumUtil.valueOf(TypeOfWork.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(TypeOfWork.class, type.getDescription()), is(type));
        }
    }

    @Test
    public void testEnumUtilOnUserType() {
        for (final UserType type : UserType.values()) {
            assertThat(EnumUtil.valueOf(UserType.class, type.name()), is(type));
            assertThat(EnumUtil.valueOf(UserType.class, type.getDescription()), is(type));
        }
    }
}
