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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * <p>This test class, will verify the correctness of all existing enumerated
 * Exchange types, this is done, to avoid that changes is made lightly, since
 * the enums are part of the public API, and thus any change will have
 * implications for all Clients.</p>
 *
 * <p>Since the enumerated Exchange values is used both by Clients and
 * internally in the database, it is very important that any change is handled
 * in a controlled manner.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public class ExchangeEnumAPITest {

    @Test
    public void testApplicationStatusEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(ApplicationStatus.APPLIED.name(), is("APPLIED"));
        assertThat(ApplicationStatus.NOMINATED.name(), is("NOMINATED"));
        assertThat(ApplicationStatus.FORWARDED_TO_EMPLOYER.name(), is("FORWARDED_TO_EMPLOYER"));
        assertThat(ApplicationStatus.ACCEPTED.name(), is("ACCEPTED"));
        assertThat(ApplicationStatus.REJECTED.name(), is("REJECTED"));
        assertThat(ApplicationStatus.REJECTED_BY_SENDING_COUNTRY.name(), is("REJECTED_BY_SENDING_COUNTRY"));
        assertThat(ApplicationStatus.CANCELLED.name(), is("CANCELLED"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(ApplicationStatus.APPLIED.ordinal(), is(0));
        assertThat(ApplicationStatus.NOMINATED.ordinal(), is(1));
        assertThat(ApplicationStatus.FORWARDED_TO_EMPLOYER.ordinal(), is(2));
        assertThat(ApplicationStatus.ACCEPTED.ordinal(), is(3));
        assertThat(ApplicationStatus.REJECTED.ordinal(), is(4));
        assertThat(ApplicationStatus.REJECTED_BY_SENDING_COUNTRY.ordinal(), is(5));
        assertThat(ApplicationStatus.CANCELLED.ordinal(), is(6));
    }

    @Test
    public void testEmployerFetchTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(EmployerFetchType.ALL.name(), is("ALL"));
        assertThat(EmployerFetchType.NAME.name(), is("NAME"));
        assertThat(EmployerFetchType.ID.name(), is("ID"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(EmployerFetchType.ALL.ordinal(), is(0));
        assertThat(EmployerFetchType.NAME.ordinal(), is(1));
        assertThat(EmployerFetchType.ID.ordinal(), is(2));
    }

    @Test
    public void testExchangeTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(ExchangeType.IW.name(), is("IW"));
        assertThat(ExchangeType.AC.name(), is("AC"));
        assertThat(ExchangeType.COBE.name(), is("COBE"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(ExchangeType.IW.ordinal(), is(0));
        assertThat(ExchangeType.AC.ordinal(), is(1));
        assertThat(ExchangeType.COBE.ordinal(), is(2));

        // Check the descriptable value
        assertThat(ExchangeType.IW.getDescription(), is("IntraWeb"));
        assertThat(ExchangeType.AC.getDescription(), is("Annual Conference"));
        assertThat(ExchangeType.COBE.getDescription(), is("COBE"));
    }

    @Test
    public void testFieldOfStudyEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(FieldOfStudy.AERONAUTIC_ENGINEERING.name(), is("AERONAUTIC_ENGINEERING"));
        assertThat(FieldOfStudy.AGRICULTURE.name(), is("AGRICULTURE"));
        assertThat(FieldOfStudy.APPLIED_ARTS.name(), is("APPLIED_ARTS"));
        assertThat(FieldOfStudy.AQUA_CULTURE.name(), is("AQUA_CULTURE"));
        assertThat(FieldOfStudy.ARCHITECTURE.name(), is("ARCHITECTURE"));
        assertThat(FieldOfStudy.BIOLOGY.name(), is("BIOLOGY"));
        assertThat(FieldOfStudy.BIOMEDICAL_SCIENCE.name(), is("BIOMEDICAL_SCIENCE"));
        assertThat(FieldOfStudy.BIOTECHNOLOGY.name(), is("BIOTECHNOLOGY"));
        assertThat(FieldOfStudy.CHEMISTRY.name(), is("CHEMISTRY"));
        assertThat(FieldOfStudy.CIVIL_ENGINEERING.name(), is("CIVIL_ENGINEERING"));
        assertThat(FieldOfStudy.ECONOMY_AND_MANAGEMENT.name(), is("ECONOMY_AND_MANAGEMENT"));
        assertThat(FieldOfStudy.EDUCATION.name(), is("EDUCATION"));
        assertThat(FieldOfStudy.ELECTRICAL_ENGINEERING.name(), is("ELECTRICAL_ENGINEERING"));
        assertThat(FieldOfStudy.ENERGY_ENGINEERING.name(), is("ENERGY_ENGINEERING"));
        assertThat(FieldOfStudy.ENVIRONMENTAL_ENGINEERING.name(), is("ENVIRONMENTAL_ENGINEERING"));
        assertThat(FieldOfStudy.FOOD_SCIENCE.name(), is("FOOD_SCIENCE"));
        assertThat(FieldOfStudy.GEOSCIENCE.name(), is("GEOSCIENCE"));
        assertThat(FieldOfStudy.INDUSTRIAL_ENGINEERING.name(), is("INDUSTRIAL_ENGINEERING"));
        assertThat(FieldOfStudy.IT.name(), is("IT"));
        assertThat(FieldOfStudy.MATERIAL_SCIENCE.name(), is("MATERIAL_SCIENCE"));
        assertThat(FieldOfStudy.MATHEMATICS.name(), is("MATHEMATICS"));
        assertThat(FieldOfStudy.MECHANICAL_ENGINEERING.name(), is("MECHANICAL_ENGINEERING"));
        assertThat(FieldOfStudy.MEDIA_AND_MARKETING.name(), is("MEDIA_AND_MARKETING"));
        assertThat(FieldOfStudy.PHYSICS.name(), is("PHYSICS"));
        assertThat(FieldOfStudy.VETERINARY_SCIENCE.name(), is("VETERINARY_SCIENCE"));
        assertThat(FieldOfStudy.OTHER.name(), is("OTHER"));
        assertThat(FieldOfStudy.ANY.name(), is("ANY"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(FieldOfStudy.AERONAUTIC_ENGINEERING.ordinal(), is(0));
        assertThat(FieldOfStudy.AGRICULTURE.ordinal(), is(1));
        assertThat(FieldOfStudy.APPLIED_ARTS.ordinal(), is(2));
        assertThat(FieldOfStudy.AQUA_CULTURE.ordinal(), is(3));
        assertThat(FieldOfStudy.ARCHITECTURE.ordinal(), is(4));
        assertThat(FieldOfStudy.BIOLOGY.ordinal(), is(5));
        assertThat(FieldOfStudy.BIOMEDICAL_SCIENCE.ordinal(), is(6));
        assertThat(FieldOfStudy.BIOTECHNOLOGY.ordinal(), is(7));
        assertThat(FieldOfStudy.CHEMISTRY.ordinal(), is(8));
        assertThat(FieldOfStudy.CIVIL_ENGINEERING.ordinal(), is(9));
        assertThat(FieldOfStudy.ECONOMY_AND_MANAGEMENT.ordinal(), is(10));
        assertThat(FieldOfStudy.EDUCATION.ordinal(), is(11));
        assertThat(FieldOfStudy.ELECTRICAL_ENGINEERING.ordinal(), is(12));
        assertThat(FieldOfStudy.ENERGY_ENGINEERING.ordinal(), is(13));
        assertThat(FieldOfStudy.ENVIRONMENTAL_ENGINEERING.ordinal(), is(14));
        assertThat(FieldOfStudy.FOOD_SCIENCE.ordinal(), is(15));
        assertThat(FieldOfStudy.GEOSCIENCE.ordinal(), is(16));
        assertThat(FieldOfStudy.INDUSTRIAL_ENGINEERING.ordinal(), is(17));
        assertThat(FieldOfStudy.IT.ordinal(), is(18));
        assertThat(FieldOfStudy.MATERIAL_SCIENCE.ordinal(), is(19));
        assertThat(FieldOfStudy.MATHEMATICS.ordinal(), is(20));
        assertThat(FieldOfStudy.MECHANICAL_ENGINEERING.ordinal(), is(21));
        assertThat(FieldOfStudy.MEDIA_AND_MARKETING.ordinal(), is(22));
        assertThat(FieldOfStudy.PHYSICS.ordinal(), is(23));
        assertThat(FieldOfStudy.VETERINARY_SCIENCE.ordinal(), is(24));
        assertThat(FieldOfStudy.OTHER.ordinal(), is(25));
        assertThat(FieldOfStudy.ANY.ordinal(), is(26));

        // Check the descriptable value
        assertThat(FieldOfStudy.AERONAUTIC_ENGINEERING.getDescription(), is("Aeronautic Engineering"));
        assertThat(FieldOfStudy.AGRICULTURE.getDescription(), is("Agriculture"));
        assertThat(FieldOfStudy.APPLIED_ARTS.getDescription(), is("Applied Arts"));
        assertThat(FieldOfStudy.AQUA_CULTURE.getDescription(), is("Aquaculture"));
        assertThat(FieldOfStudy.ARCHITECTURE.getDescription(), is("Architecture"));
        assertThat(FieldOfStudy.BIOLOGY.getDescription(), is("Biology"));
        assertThat(FieldOfStudy.BIOMEDICAL_SCIENCE.getDescription(), is("Biomedical Science"));
        assertThat(FieldOfStudy.BIOTECHNOLOGY.getDescription(), is("Biotechnology"));
        assertThat(FieldOfStudy.CHEMISTRY.getDescription(), is("Chemistry"));
        assertThat(FieldOfStudy.CIVIL_ENGINEERING.getDescription(), is("Civil Engineering"));
        assertThat(FieldOfStudy.ECONOMY_AND_MANAGEMENT.getDescription(), is("Economy and Management"));
        assertThat(FieldOfStudy.EDUCATION.getDescription(), is("Education"));
        assertThat(FieldOfStudy.ELECTRICAL_ENGINEERING.getDescription(), is("Electrical Engineering"));
        assertThat(FieldOfStudy.ENERGY_ENGINEERING.getDescription(), is("Energy Engineering"));
        assertThat(FieldOfStudy.ENVIRONMENTAL_ENGINEERING.getDescription(), is("Environmental Science"));
        assertThat(FieldOfStudy.FOOD_SCIENCE.getDescription(), is("Food Science"));
        assertThat(FieldOfStudy.GEOSCIENCE.getDescription(), is("Geoscience"));
        assertThat(FieldOfStudy.INDUSTRIAL_ENGINEERING.getDescription(), is("Industrial Engineering"));
        assertThat(FieldOfStudy.IT.getDescription(), is("IT"));
        assertThat(FieldOfStudy.MATERIAL_SCIENCE.getDescription(), is("Material Science"));
        assertThat(FieldOfStudy.MATHEMATICS.getDescription(), is("Mathematics"));
        assertThat(FieldOfStudy.MECHANICAL_ENGINEERING.getDescription(), is("Mechanical Engineering"));
        assertThat(FieldOfStudy.MEDIA_AND_MARKETING.getDescription(), is("Media and Marketing"));
        assertThat(FieldOfStudy.PHYSICS.getDescription(), is("Physics"));
        assertThat(FieldOfStudy.VETERINARY_SCIENCE.getDescription(), is("Veterinary Sciences"));
        assertThat(FieldOfStudy.OTHER.getDescription(), is("Other"));
        assertThat(FieldOfStudy.ANY.getDescription(), is("Any"));

        // Check the specializations
        assertThat(FieldOfStudy.AERONAUTIC_ENGINEERING.getSpecializations().size(), is(2));
        assertThat(FieldOfStudy.AGRICULTURE.getSpecializations().size(), is(14));
        assertThat(FieldOfStudy.APPLIED_ARTS.getSpecializations().size(), is(4));
        assertThat(FieldOfStudy.AQUA_CULTURE.getSpecializations().size(), is(5));
        assertThat(FieldOfStudy.ARCHITECTURE.getSpecializations().size(), is(4));
        assertThat(FieldOfStudy.BIOLOGY.getSpecializations().size(), is(22));
        assertThat(FieldOfStudy.BIOMEDICAL_SCIENCE.getSpecializations().size(), is(9));
        assertThat(FieldOfStudy.BIOTECHNOLOGY.getSpecializations().size(), is(9));
        assertThat(FieldOfStudy.CHEMISTRY.getSpecializations().size(), is(11));
        assertThat(FieldOfStudy.CIVIL_ENGINEERING.getSpecializations().size(), is(12));
        assertThat(FieldOfStudy.ECONOMY_AND_MANAGEMENT.getSpecializations().size(), is(13));
        assertThat(FieldOfStudy.EDUCATION.getSpecializations().size(), is(2));
        assertThat(FieldOfStudy.ELECTRICAL_ENGINEERING.getSpecializations().size(), is(11));
        assertThat(FieldOfStudy.ENERGY_ENGINEERING.getSpecializations().size(), is(6));
        assertThat(FieldOfStudy.ENVIRONMENTAL_ENGINEERING.getSpecializations().size(), is(10));
        assertThat(FieldOfStudy.FOOD_SCIENCE.getSpecializations().size(), is(8));
        assertThat(FieldOfStudy.GEOSCIENCE.getSpecializations().size(), is(18));
        assertThat(FieldOfStudy.INDUSTRIAL_ENGINEERING.getSpecializations().size(), is(6));
        assertThat(FieldOfStudy.IT.getSpecializations().size(), is(27));
        assertThat(FieldOfStudy.MATERIAL_SCIENCE.getSpecializations().size(), is(11));
        assertThat(FieldOfStudy.MATHEMATICS.getSpecializations().size(), is(8));
        assertThat(FieldOfStudy.MECHANICAL_ENGINEERING.getSpecializations().size(), is(16));
        assertThat(FieldOfStudy.MEDIA_AND_MARKETING.getSpecializations().size(), is(4));
        assertThat(FieldOfStudy.PHYSICS.getSpecializations().size(), is(14));
        assertThat(FieldOfStudy.VETERINARY_SCIENCE.getSpecializations().size(), is(3));
        assertThat(FieldOfStudy.OTHER.getSpecializations().size(), is(0));
        assertThat(FieldOfStudy.ANY.getSpecializations().size(), is(0));
    }

    @Test
    public void testLanguageOperatorEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(LanguageOperator.A.name(), is("A"));
        assertThat(LanguageOperator.O.name(), is("O"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(LanguageOperator.A.ordinal(), is(0));
        assertThat(LanguageOperator.O.ordinal(), is(1));

        // Check the descriptable value
        assertThat(LanguageOperator.A.getDescription(), is("And"));
        assertThat(LanguageOperator.O.getDescription(), is("Or"));
    }

    @Test
    public void testLanguageLevelEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(LanguageLevel.E.name(), is("E"));
        assertThat(LanguageLevel.G.name(), is("G"));
        assertThat(LanguageLevel.F.name(), is("F"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(LanguageLevel.E.ordinal(), is(0));
        assertThat(LanguageLevel.G.ordinal(), is(1));
        assertThat(LanguageLevel.F.ordinal(), is(2));

        // Check the descriptable value
        assertThat(LanguageLevel.E.getDescription(), is("Excellent"));
        assertThat(LanguageLevel.G.getDescription(), is("Good"));
        assertThat(LanguageLevel.F.getDescription(), is("Fair"));
    }

    @Test
    public void testSpecializationEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(OfferFields.REF_NO.name(), is("REF_NO"));
        assertThat(OfferFields.OFFER_TYPE.name(), is("OFFER_TYPE"));
        assertThat(OfferFields.EXCHANGE_TYPE.name(), is("EXCHANGE_TYPE"));
        assertThat(OfferFields.DEADLINE.name(), is("DEADLINE"));
        assertThat(OfferFields.COMMENT.name(), is("COMMENT"));
        assertThat(OfferFields.EMPLOYER.name(), is("EMPLOYER"));
        assertThat(OfferFields.DEPARTMENT.name(), is("DEPARTMENT"));
        assertThat(OfferFields.STREET1.name(), is("STREET1"));
        assertThat(OfferFields.STREET2.name(), is("STREET2"));
        assertThat(OfferFields.POSTBOX.name(), is("POSTBOX"));
        assertThat(OfferFields.POSTAL_CODE.name(), is("POSTAL_CODE"));
        assertThat(OfferFields.CITY.name(), is("CITY"));
        assertThat(OfferFields.STATE.name(), is("STATE"));
        assertThat(OfferFields.COUNTRY.name(), is("COUNTRY"));
        assertThat(OfferFields.WEBSITE.name(), is("WEBSITE"));
        assertThat(OfferFields.WORKPLACE.name(), is("WORKPLACE"));
        assertThat(OfferFields.BUSINESS.name(), is("BUSINESS"));
        assertThat(OfferFields.RESPONSIBLE.name(), is("RESPONSIBLE"));
        assertThat(OfferFields.AIRPORT.name(), is("AIRPORT"));
        assertThat(OfferFields.TRANSPORT.name(), is("TRANSPORT"));
        assertThat(OfferFields.EMPLOYEES.name(), is("EMPLOYEES"));
        assertThat(OfferFields.HOURS_WEEKLY.name(), is("HOURS_WEEKLY"));
        assertThat(OfferFields.HOURS_DAILY.name(), is("HOURS_DAILY"));
        assertThat(OfferFields.CANTEEN.name(), is("CANTEEN"));
        assertThat(OfferFields.FACULTY.name(), is("FACULTY"));
        assertThat(OfferFields.SPECIALIZATION.name(), is("SPECIALIZATION"));
        assertThat(OfferFields.TRAINING_REQUIRED.name(), is("TRAINING_REQUIRED"));
        assertThat(OfferFields.OTHER_REQUIREMENTS.name(), is("OTHER_REQUIREMENTS"));
        assertThat(OfferFields.WORK_KIND.name(), is("WORK_KIND"));
        assertThat(OfferFields.WEEKS_MIN.name(), is("WEEKS_MIN"));
        assertThat(OfferFields.WEEKS_MAX.name(), is("WEEKS_MAX"));
        assertThat(OfferFields.FROM.name(), is("FROM"));
        assertThat(OfferFields.TO.name(), is("TO"));
        assertThat(OfferFields.STUDY_COMPLETED.name(), is("STUDY_COMPLETED"));
        assertThat(OfferFields.STUDY_COMPLETED_BEGINNING.name(), is("STUDY_COMPLETED_BEGINNING"));
        assertThat(OfferFields.STUDY_COMPLETED_MIDDLE.name(), is("STUDY_COMPLETED_MIDDLE"));
        assertThat(OfferFields.STUDY_COMPLETED_END.name(), is("STUDY_COMPLETED_END"));
        assertThat(OfferFields.WORK_TYPE.name(), is("WORK_TYPE"));
        assertThat(OfferFields.WORK_TYPE_P.name(), is("WORK_TYPE_P"));
        assertThat(OfferFields.WORK_TYPE_R.name(), is("WORK_TYPE_R"));
        assertThat(OfferFields.WORK_TYPE_W.name(), is("WORK_TYPE_W"));
        assertThat(OfferFields.WORK_TYPE_N.name(), is("WORK_TYPE_N"));
        assertThat(OfferFields.LANGUAGE_1.name(), is("LANGUAGE_1"));
        assertThat(OfferFields.LANGUAGE_1_LEVEL.name(), is("LANGUAGE_1_LEVEL"));
        assertThat(OfferFields.LANGUAGE_1_OR.name(), is("LANGUAGE_1_OR"));
        assertThat(OfferFields.LANGUAGE_2.name(), is("LANGUAGE_2"));
        assertThat(OfferFields.LANGUAGE_2_LEVEL.name(), is("LANGUAGE_2_LEVEL"));
        assertThat(OfferFields.LANGUAGE_2_OR.name(), is("LANGUAGE_2_OR"));
        assertThat(OfferFields.LANGUAGE_3.name(), is("LANGUAGE_3"));
        assertThat(OfferFields.LANGUAGE_3_LEVEL.name(), is("LANGUAGE_3_LEVEL"));
        assertThat(OfferFields.CURRENCY.name(), is("CURRENCY"));
        assertThat(OfferFields.PAYMENT.name(), is("PAYMENT"));
        assertThat(OfferFields.PAYMENT_FREQUENCY.name(), is("PAYMENT_FREQUENCY"));
        assertThat(OfferFields.DEDUCTION.name(), is("DEDUCTION"));
        assertThat(OfferFields.LODGING.name(), is("LODGING"));
        assertThat(OfferFields.LODGING_COST.name(), is("LODGING_COST"));
        assertThat(OfferFields.LODGING_COST_FREQUENCY.name(), is("LODGING_COST_FREQUENCY"));
        assertThat(OfferFields.LIVING_COST.name(), is("LIVING_COST"));
        assertThat(OfferFields.LIVING_COST_FREQUENCY.name(), is("LIVING_COST_FREQUENCY"));
        assertThat(OfferFields.NO_HARD_COPIES.name(), is("NO_HARD_COPIES"));
        assertThat(OfferFields.STATUS.name(), is("STATUS"));
        assertThat(OfferFields.PERIOD_2_FROM.name(), is("PERIOD_2_FROM"));
        assertThat(OfferFields.PERIOD_2_TO.name(), is("PERIOD_2_TO"));
        assertThat(OfferFields.HOLIDAYS_FROM.name(), is("HOLIDAYS_FROM"));
        assertThat(OfferFields.HOLIDAYS_TO.name(), is("HOLIDAYS_TO"));
        assertThat(OfferFields.ADDITIONAL_INFO.name(), is("ADDITIONAL_INFO"));
        assertThat(OfferFields.SHARED.name(), is("SHARED"));
        assertThat(OfferFields.LAST_MODIFIED.name(), is("LAST_MODIFIED"));
        assertThat(OfferFields.NS_FIRST_NAME.name(), is("NS_FIRST_NAME"));
        assertThat(OfferFields.NS_LAST_NAME.name(), is("NS_LAST_NAME"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(OfferFields.REF_NO.ordinal(), is(0));
        assertThat(OfferFields.OFFER_TYPE.ordinal(), is(1));
        assertThat(OfferFields.EXCHANGE_TYPE.ordinal(), is(2));
        assertThat(OfferFields.DEADLINE.ordinal(), is(3));
        assertThat(OfferFields.COMMENT.ordinal(), is(4));
        assertThat(OfferFields.EMPLOYER.ordinal(), is(5));
        assertThat(OfferFields.DEPARTMENT.ordinal(), is(6));
        assertThat(OfferFields.STREET1.ordinal(), is(7));
        assertThat(OfferFields.STREET2.ordinal(), is(8));
        assertThat(OfferFields.POSTBOX.ordinal(), is(9));
        assertThat(OfferFields.POSTAL_CODE.ordinal(), is(10));
        assertThat(OfferFields.CITY.ordinal(), is(11));
        assertThat(OfferFields.STATE.ordinal(), is(12));
        assertThat(OfferFields.COUNTRY.ordinal(), is(13));
        assertThat(OfferFields.WEBSITE.ordinal(), is(14));
        assertThat(OfferFields.WORKPLACE.ordinal(), is(15));
        assertThat(OfferFields.BUSINESS.ordinal(), is(16));
        assertThat(OfferFields.RESPONSIBLE.ordinal(), is(17));
        assertThat(OfferFields.AIRPORT.ordinal(), is(18));
        assertThat(OfferFields.TRANSPORT.ordinal(), is(19));
        assertThat(OfferFields.EMPLOYEES.ordinal(), is(20));
        assertThat(OfferFields.HOURS_WEEKLY.ordinal(), is(21));
        assertThat(OfferFields.HOURS_DAILY.ordinal(), is(22));
        assertThat(OfferFields.CANTEEN.ordinal(), is(23));
        assertThat(OfferFields.FACULTY.ordinal(), is(24));
        assertThat(OfferFields.SPECIALIZATION.ordinal(), is(25));
        assertThat(OfferFields.TRAINING_REQUIRED.ordinal(), is(26));
        assertThat(OfferFields.OTHER_REQUIREMENTS.ordinal(), is(27));
        assertThat(OfferFields.WORK_KIND.ordinal(), is(28));
        assertThat(OfferFields.WEEKS_MIN.ordinal(), is(29));
        assertThat(OfferFields.WEEKS_MAX.ordinal(), is(30));
        assertThat(OfferFields.FROM.ordinal(), is(31));
        assertThat(OfferFields.TO.ordinal(), is(32));
        assertThat(OfferFields.STUDY_COMPLETED.ordinal(), is(33));
        assertThat(OfferFields.STUDY_COMPLETED_BEGINNING.ordinal(), is(34));
        assertThat(OfferFields.STUDY_COMPLETED_MIDDLE.ordinal(), is(35));
        assertThat(OfferFields.STUDY_COMPLETED_END.ordinal(), is(36));
        assertThat(OfferFields.WORK_TYPE.ordinal(), is(37));
        assertThat(OfferFields.WORK_TYPE_P.ordinal(), is(38));
        assertThat(OfferFields.WORK_TYPE_R.ordinal(), is(39));
        assertThat(OfferFields.WORK_TYPE_W.ordinal(), is(40));
        assertThat(OfferFields.WORK_TYPE_N.ordinal(), is(41));
        assertThat(OfferFields.LANGUAGE_1.ordinal(), is(42));
        assertThat(OfferFields.LANGUAGE_1_LEVEL.ordinal(), is(43));
        assertThat(OfferFields.LANGUAGE_1_OR.ordinal(), is(44));
        assertThat(OfferFields.LANGUAGE_2.ordinal(), is(45));
        assertThat(OfferFields.LANGUAGE_2_LEVEL.ordinal(), is(46));
        assertThat(OfferFields.LANGUAGE_2_OR.ordinal(), is(47));
        assertThat(OfferFields.LANGUAGE_3.ordinal(), is(48));
        assertThat(OfferFields.LANGUAGE_3_LEVEL.ordinal(), is(49));
        assertThat(OfferFields.CURRENCY.ordinal(), is(50));
        assertThat(OfferFields.PAYMENT.ordinal(), is(51));
        assertThat(OfferFields.PAYMENT_FREQUENCY.ordinal(), is(52));
        assertThat(OfferFields.DEDUCTION.ordinal(), is(53));
        assertThat(OfferFields.LODGING.ordinal(), is(54));
        assertThat(OfferFields.LODGING_COST.ordinal(), is(55));
        assertThat(OfferFields.LODGING_COST_FREQUENCY.ordinal(), is(56));
        assertThat(OfferFields.LIVING_COST.ordinal(), is(57));
        assertThat(OfferFields.LIVING_COST_FREQUENCY.ordinal(), is(58));
        assertThat(OfferFields.NO_HARD_COPIES.ordinal(), is(59));
        assertThat(OfferFields.STATUS.ordinal(), is(60));
        assertThat(OfferFields.PERIOD_2_FROM.ordinal(), is(61));
        assertThat(OfferFields.PERIOD_2_TO.ordinal(), is(62));
        assertThat(OfferFields.HOLIDAYS_FROM.ordinal(), is(63));
        assertThat(OfferFields.HOLIDAYS_TO.ordinal(), is(64));
        assertThat(OfferFields.ADDITIONAL_INFO.ordinal(), is(65));
        assertThat(OfferFields.SHARED.ordinal(), is(66));
        assertThat(OfferFields.LAST_MODIFIED.ordinal(), is(67));
        assertThat(OfferFields.NS_FIRST_NAME.ordinal(), is(68));
        assertThat(OfferFields.NS_LAST_NAME.ordinal(), is(69));

        // Testing other fields. Primarily those are also tested as part of the
        // CSV Processing, so here is just a simple verification.
        assertThat(OfferFields.WORK_TYPE.useField(OfferFields.Type.UPLOAD), is(true));
        assertThat(OfferFields.WORK_TYPE.getField(), is("WorkType"));
        assertThat(OfferFields.WORK_TYPE.getMethod(), is("setTypeOfWork"));
        assertThat(OfferFields.WORK_TYPE.getArgumentClass().getSimpleName(), is("TypeOfWork"));
    }

    @Test
    public void testOfferStateEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(OfferState.NEW.name(), is("NEW"));
        assertThat(OfferState.OPEN.name(), is("OPEN"));
        assertThat(OfferState.SHARED.name(), is("SHARED"));
        assertThat(OfferState.APPLICATIONS.name(), is("APPLICATIONS"));
        assertThat(OfferState.NOMINATIONS.name(), is("NOMINATIONS"));
        assertThat(OfferState.DELETED.name(), is("DELETED"));
        assertThat(OfferState.CLOSED.name(), is("CLOSED"));
        assertThat(OfferState.COMPLETED.name(), is("COMPLETED"));
        assertThat(OfferState.AT_EMPLOYER.name(), is("AT_EMPLOYER"));
        assertThat(OfferState.ACCEPTED.name(), is("ACCEPTED"));
        assertThat(OfferState.EXPIRED.name(), is("EXPIRED"));
        assertThat(OfferState.REJECTED.name(), is("REJECTED"));
        assertThat(OfferState.CANCELLED.name(), is("CANCELLED"));
        assertThat(OfferState.EXCHANGED.name(), is("EXCHANGED"));
        assertThat(OfferState.NOMINATION_REJECTED.name(), is("NOMINATION_REJECTED"));
        assertThat(OfferState.NOT_ACCEPTED.name(), is("NOT_ACCEPTED"));
        assertThat(OfferState.DECLINED.name(), is("DECLINED"));
        assertThat(OfferState.SN_COMPLETE.name(), is("SN_COMPLETE"));
        assertThat(OfferState.TAKEN.name(), is("TAKEN"));
        assertThat(OfferState.NOMINATION_ACCEPTED.name(), is("NOMINATION_ACCEPTED"));
        assertThat(OfferState.VIEWED.name(), is("VIEWED"));
        assertThat(OfferState.WAITING_SN.name(), is("WAITING_SN"));
        assertThat(OfferState.AC_EXCHANGED.name(), is("AC_EXCHANGED"));
        assertThat(OfferState.UNKNOWN.name(), is("UNKNOWN"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(OfferState.NEW.ordinal(), is(0));
        assertThat(OfferState.OPEN.ordinal(), is(1));
        assertThat(OfferState.SHARED.ordinal(), is(2));
        assertThat(OfferState.APPLICATIONS.ordinal(), is(3));
        assertThat(OfferState.NOMINATIONS.ordinal(), is(4));
        assertThat(OfferState.DELETED.ordinal(), is(5));
        assertThat(OfferState.CLOSED.ordinal(), is(6));
        assertThat(OfferState.COMPLETED.ordinal(), is(7));
        assertThat(OfferState.AT_EMPLOYER.ordinal(), is(8));
        assertThat(OfferState.ACCEPTED.ordinal(), is(9));
        assertThat(OfferState.EXPIRED.ordinal(), is(10));
        assertThat(OfferState.REJECTED.ordinal(), is(11));
        assertThat(OfferState.CANCELLED.ordinal(), is(12));
        assertThat(OfferState.EXCHANGED.ordinal(), is(13));
        assertThat(OfferState.NOMINATION_REJECTED.ordinal(), is(14));
        assertThat(OfferState.NOT_ACCEPTED.ordinal(), is(15));
        assertThat(OfferState.DECLINED.ordinal(), is(16));
        assertThat(OfferState.SN_COMPLETE.ordinal(), is(17));
        assertThat(OfferState.TAKEN.ordinal(), is(18));
        assertThat(OfferState.NOMINATION_ACCEPTED.ordinal(), is(19));
        assertThat(OfferState.VIEWED.ordinal(), is(20));
        assertThat(OfferState.WAITING_SN.ordinal(), is(21));
        assertThat(OfferState.AC_EXCHANGED.ordinal(), is(22));
        assertThat(OfferState.UNKNOWN.ordinal(), is(23));

        // Check the descriptable value
        assertThat(OfferState.NEW.getDescription(), is("New"));
        assertThat(OfferState.OPEN.getDescription(), is("Open"));
        assertThat(OfferState.SHARED.getDescription(), is("Shared"));
        assertThat(OfferState.APPLICATIONS.getDescription(), is("Applications"));
        assertThat(OfferState.NOMINATIONS.getDescription(), is("Nominations"));
        assertThat(OfferState.DELETED.getDescription(), is("Deleted"));
        assertThat(OfferState.CLOSED.getDescription(), is("Closed"));
        assertThat(OfferState.COMPLETED.getDescription(), is("Completed"));
        assertThat(OfferState.AT_EMPLOYER.getDescription(), is("At Employer"));
        assertThat(OfferState.ACCEPTED.getDescription(), is("Accepted"));
        assertThat(OfferState.EXPIRED.getDescription(), is("Expired"));
        assertThat(OfferState.REJECTED.getDescription(), is("Rejected"));
        assertThat(OfferState.CANCELLED.getDescription(), is("Cancelled"));
        assertThat(OfferState.EXCHANGED.getDescription(), is("Exchanged"));
        assertThat(OfferState.NOMINATION_REJECTED.getDescription(), is("Nomination Rejected"));
        assertThat(OfferState.NOT_ACCEPTED.getDescription(), is("Not Accepted"));
        assertThat(OfferState.DECLINED.getDescription(), is("Declined"));
        assertThat(OfferState.SN_COMPLETE.getDescription(), is("SN Complete"));
        assertThat(OfferState.TAKEN.getDescription(), is("Taken"));
        assertThat(OfferState.NOMINATION_ACCEPTED.getDescription(), is("Nomination Accepted"));
        assertThat(OfferState.VIEWED.getDescription(), is("Viewed"));
        assertThat(OfferState.WAITING_SN.getDescription(), is("Waiting SN"));
        assertThat(OfferState.AC_EXCHANGED.getDescription(), is("AC Exchanged"));
        assertThat(OfferState.UNKNOWN.getDescription(), is("Unknown"));
    }

    @Test
    public void testOfferTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(OfferType.OPEN.name(), is("OPEN"));
        assertThat(OfferType.LIMITED.name(), is("LIMITED"));
        assertThat(OfferType.RESERVED.name(), is("RESERVED"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(OfferType.OPEN.ordinal(), is(0));
        assertThat(OfferType.LIMITED.ordinal(), is(1));
        assertThat(OfferType.RESERVED.ordinal(), is(2));

        // Check the descriptable value
        assertThat(OfferType.OPEN.getDescription(), is("Open"));
        assertThat(OfferType.LIMITED.getDescription(), is("Limited"));
        assertThat(OfferType.RESERVED.getDescription(), is("Reserved"));

        // Check other values
        assertThat(OfferType.OPEN.getType(), is(""));
        assertThat(OfferType.OPEN.getExchangeTypes().size(), is(3));
        assertThat(OfferType.LIMITED.getType(), is("-L"));
        assertThat(OfferType.LIMITED.getExchangeTypes().size(), is(3));
        assertThat(OfferType.RESERVED.getType(), is("-R"));
        assertThat(OfferType.RESERVED.getExchangeTypes().size(), is(2));
    }

    @Test
    public void testPaymentFrequencyEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(PaymentFrequency.DAILY.name(), is("DAILY"));
        assertThat(PaymentFrequency.WEEKLY.name(), is("WEEKLY"));
        assertThat(PaymentFrequency.BIWEEKLY.name(), is("BIWEEKLY"));
        assertThat(PaymentFrequency.MONTHLY.name(), is("MONTHLY"));
        assertThat(PaymentFrequency.YEARLY.name(), is("YEARLY"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(PaymentFrequency.DAILY.ordinal(), is(0));
        assertThat(PaymentFrequency.WEEKLY.ordinal(), is(1));
        assertThat(PaymentFrequency.BIWEEKLY.ordinal(), is(2));
        assertThat(PaymentFrequency.MONTHLY.ordinal(), is(3));
        assertThat(PaymentFrequency.YEARLY.ordinal(), is(4));

        // Check the descriptable value
        assertThat(PaymentFrequency.DAILY.getDescription(), is("Daily"));
        assertThat(PaymentFrequency.WEEKLY.getDescription(), is("Weekly"));
        assertThat(PaymentFrequency.BIWEEKLY.getDescription(), is("Biweekly"));
        assertThat(PaymentFrequency.MONTHLY.getDescription(), is("Monthly"));
        assertThat(PaymentFrequency.YEARLY.getDescription(), is("Yearly"));
    }

    //@Test
    //public void testSpecializationEnumIsCorrect() {
    //    // Name value check, to ensure that they aren't renamed
    //    assertThat(Specialization.REPLY_TO_SENDER.name(), is("REPLY_TO_SENDER"));
    //
    //    // Ordinal value check, to ensure that the order hasn't changed
    //    assertThat(Specialization.REPLY_TO_SENDER.ordinal(), is(0));
    //
    //    // Check the descriptable value
    //    assertThat(Specialization.REPLY_TO_SENDER.getDescription(), is("The Sender is receiving replies."));
    //}

    @Test
    public void testStudyLevelEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(StudyLevel.B.name(), is("B"));
        assertThat(StudyLevel.M.name(), is("M"));
        assertThat(StudyLevel.E.name(), is("E"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(StudyLevel.B.ordinal(), is(0));
        assertThat(StudyLevel.M.ordinal(), is(1));
        assertThat(StudyLevel.E.ordinal(), is(2));

        // Check the descriptable value
        assertThat(StudyLevel.B.getDescription(), is("Begin (1-3 Semesters)"));
        assertThat(StudyLevel.M.getDescription(), is("Middle (4-6 Semesters)"));
        assertThat(StudyLevel.E.getDescription(), is("End (7 or more Semesters)"));
    }

    @Test
    public void testTransportationTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(TransportationType.PLANE.name(), is("PLANE"));
        assertThat(TransportationType.BUS.name(), is("BUS"));
        assertThat(TransportationType.TRAIN.name(), is("TRAIN"));
        assertThat(TransportationType.SHIP.name(), is("SHIP"));
        assertThat(TransportationType.CAR.name(), is("CAR"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(TransportationType.PLANE.ordinal(), is(0));
        assertThat(TransportationType.BUS.ordinal(), is(1));
        assertThat(TransportationType.TRAIN.ordinal(), is(2));
        assertThat(TransportationType.SHIP.ordinal(), is(3));
        assertThat(TransportationType.CAR.ordinal(), is(4));
    }

    @Test
    public void testTypeOfWorkEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(TypeOfWork.R.name(), is("R"));
        assertThat(TypeOfWork.O.name(), is("O"));
        assertThat(TypeOfWork.F.name(), is("F"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(TypeOfWork.R.ordinal(), is(0));
        assertThat(TypeOfWork.O.ordinal(), is(1));
        assertThat(TypeOfWork.F.ordinal(), is(2));

        // Check the descriptable value
        assertThat(TypeOfWork.R.getDescription(), is("Research and Development"));
        assertThat(TypeOfWork.O.getDescription(), is("Office Work"));
        assertThat(TypeOfWork.F.getDescription(), is("Field Work"));
    }

}
