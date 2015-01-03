/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.GenerateDataForTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.common.utils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.enums.Currency;
import net.iaeste.iws.api.enums.GroupType;
import net.iaeste.iws.api.enums.Membership;
import net.iaeste.iws.api.enums.UserStatus;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * This class is used to generate test accounts for various countries.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Ignore("Ignored, while we only need this to generate test data, otherwise it simply just dump garbage out.")
@RunWith(Parameterized.class)
public final class GenerateDataForTest {

    // Control what you want to get generated
    private static final boolean GENERATE_INITIAL_DATA = false;
    private static final boolean GENERATE_OFFER_DATA = true;
    private static final boolean GENERATE_MAILING_LIST_DATA = false;

    private static final String FIRST_NAME = "NS";
    private static final String RESTART_SEQUENCE = "alter sequence %s restart with %d;";
    private static final String COUNTRY_INSERT = "insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('%s', '%s', '%s', '%s', %d, '%s');";
    private static final String GROUP_INSERT = "insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('%s', %d, %s, %d, '%s');";
    private static final String USER_INSERT = "insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
    private static final String USER_GROUP_INSERT = "insert into user_to_group (external_id, user_id, group_id, role_id) values ('%s', %d, %d, %d);";
    private static final String ADDRESS_INSERT = "insert into addresses (street1, street2, postal_code, city, state, pobox, country_id) values ('Karlsplatz 13', '1040 Vienna', 'x', 'x', 'x', 'x', %d);";
    private static final String EMPLOYER_INSERT = "insert into employers (external_id, group_id, name, department, business, working_place, number_of_employees, website, canteen, nearest_airport, nearest_public_transport, weekly_hours, address_id ) values ('%s', %d, 'Vienna University of Technology', 'University', 'University', 'Vienna', 9000, 'www.tuwien.ac.at', FALSE, 'VIE', 'Karlsplatz', 38.5, %d);";
    private static final String OFFER_INSERT = "insert into offers (ref_no, external_id, employer_id, currency, status, exchange_year, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, work_type, study_levels, study_fields, specializations, deduction) values\n" +
            "('%s-2014-000001', '%s', %d, '%s', 'SHARED', 2014, '2014-06-01', '2014-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, CURRENT_DATE + INTERVAL '0-3' YEAR TO MONTH, 'Experience in JAVA', 1250.00, 'MONTHLY', FALSE, 'Working on a project in the field of science to visualize potential threads to economy and counter fight decreasing numbers', 'R', 'B', 'IT|MATHEMATICS', 'BUSINESS_INFORMATICS', 'approx. 30');";
    private static final String SHARE_ALL_OFFER = "insert into offer_to_group (offer_id, group_id, external_id, status) select offers.id, groups.id, offers.id || 'what_ever' || groups.id, 'SHARED' from groups, offers left join employers on offers.employer_id = employers.id where groups.grouptype_id = %d and employers.group_id != groups.id;";
    private static final String MAILING_LIST_INSERT = "insert into mailing_lists (external_id, private, list_address, active) values ('%s', '%b', '%s', '%b');";
    private static final String SUBSCRIBE_USER_TO_LIST = "insert into mailing_list_membership (mailing_list_id, member) values ('%d', '%s');";
    private static final String USER_NOTIFICATION_INSERT = "insert into user_notifications (user_id, notification_type, frequency) values ('%d', '%s', '%s');";

    private static final int ROLE_OWNER = 1;
    private static long currentCountryId = 1;
    private static long currentGroupId = 10;
    private static long currentUserId = 1;
    private static long currentAddressId = 1;
    private static long currentEmployerId = 1;
    private static long currentOfferId = 1;
    private static long currentMailingListId = 1;
    private static long currentListSubscriptionId = 1;

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        final Object[][] parameters = {
                // Country Code, Committee Name, Currency, Member Since, Membership Type
                { "AL", "Albania",              Currency.ALL, 2013, Membership.COOPERATING_INSTITUTION },
                { "AR", "Argentina",            Currency.ARS, 1961, Membership.FULL_MEMBER },
                { "AU", "Australia",            Currency.AUD, 1996, Membership.FULL_MEMBER },
                { "AT", "Austria",              Currency.EUR, 1960, Membership.FULL_MEMBER },
                { "AZ", "Azerbaijan",           Currency.AZN, 2010, Membership.ASSOCIATE_MEMBER },
                { "BD", "Bangladesh.Aviation",  Currency.BDT, 2013, Membership.COOPERATING_INSTITUTION },
                // This script can only create a single committee for a Country
                //{ "BD", "Bangladesh.MoyTree",   Currency.BDT, 2009, Membership.COOPERATING_INSTITUTION },
                { "BY", "Belarus",              Currency.BYR, 2000, Membership.FULL_MEMBER },
                { "BE", "Belgium",              Currency.EUR, 1948, Membership.FULL_MEMBER },
                { "BO", "Bolivia",              Currency.BOB, 2013, Membership.COOPERATING_INSTITUTION },
                { "BA", "BosniaandHerzegovina", Currency.BAM, 2000, Membership.FULL_MEMBER },
                { "BR", "Brazil",               Currency.BRL, 1982, Membership.FULL_MEMBER },
                { "CA", "Canada",               Currency.CAD, 1953, Membership.FULL_MEMBER },
                { "CL", "Chile",                Currency.CLP, 2013, Membership.COOPERATING_INSTITUTION },
                { "CN", "China",                Currency.CNY, 2000, Membership.ASSOCIATE_MEMBER },
                { "CO", "Colombia",             Currency.COP, 1960, Membership.FULL_MEMBER },
                { "HR", "Croatia",              Currency.HRK, 1960, Membership.FULL_MEMBER },
                { "CY", "Cyprus",               Currency.EUR, 1980, Membership.FULL_MEMBER },
                { "CZ", "CzechRepublic",        Currency.CZK, 1965, Membership.FULL_MEMBER },
                { "DK", "Denmark",              Currency.DKK, 1948, Membership.FULL_MEMBER },
                { "EC", "Ecuador",              Currency.USD, 1999, Membership.FULL_MEMBER },
                { "EG", "Egypt",                Currency.EGP, 1961, Membership.FULL_MEMBER },
                { "EE", "Estonia",              Currency.EUR, 2010, Membership.COOPERATING_INSTITUTION },
                { "FI", "Finland",              Currency.EUR, 1960, Membership.FULL_MEMBER },
                { "FR", "France",               Currency.EUR, 1948, Membership.FULL_MEMBER },
                { "GM", "Gambia",               Currency.GMD, 2009, Membership.COOPERATING_INSTITUTION },
                { "DE", "Germany",              Currency.EUR, 1950, Membership.FULL_MEMBER },
                { "GH", "Ghana",                Currency.GHS, 1970, Membership.FULL_MEMBER },
                { "GR", "Greece",               Currency.EUR, 1958, Membership.FULL_MEMBER },
                { "HK", "HongKong",             Currency.HKD, 1997, Membership.ASSOCIATE_MEMBER },
                { "HU", "Hungary",              Currency.HUF, 1983, Membership.FULL_MEMBER },
                { "IC", "Iceland",              Currency.ISK, 1951, Membership.FULL_MEMBER },
                { "IN", "India.KU",             Currency.INR, 2001, Membership.COOPERATING_INSTITUTION },
                // This script can only create a single committee for a Country
                //{ "IN", "India.MIT",            Currency.INR, 2006, Membership.COOPERATING_INSTITUTION },
                { "IR", "Iran",                 Currency.IRR, 2002, Membership.FULL_MEMBER },
                { "IE", "Ireland",              Currency.EUR, 1962, Membership.FULL_MEMBER },
                { "IL", "Israel",               Currency.ILS, 1951, Membership.FULL_MEMBER },
                { "IT", "Italy",                Currency.EUR, 2011, Membership.COOPERATING_INSTITUTION },
                { "JM", "Jamaica",              Currency.JMD, 2006, Membership.COOPERATING_INSTITUTION },
                { "JP", "Japan",                Currency.JPY, 1964, Membership.FULL_MEMBER },
                { "JO", "Jordan",               Currency.JOD, 1960, Membership.FULL_MEMBER },
                { "KZ", "Kazakhstan",           Currency.KZT, 1995, Membership.FULL_MEMBER },
                { "KE", "Kenya",                Currency.KES, 2004, Membership.COOPERATING_INSTITUTION },
                { "KR", "Korea",                Currency.KRW, 2007, Membership.COOPERATING_INSTITUTION },
                { "LV", "Latvia",               Currency.EUR, 2002, Membership.FULL_MEMBER },
                { "LB", "Lebanon",              Currency.LBP, 1966, Membership.FULL_MEMBER },
                { "LR", "Liberia",              Currency.LRD, 2012, Membership.COOPERATING_INSTITUTION },
                { "LT", "Lithuania",            Currency.LTL, 1990, Membership.FULL_MEMBER },
                { "MO", "Macao",                Currency.MOP, 2004, Membership.ASSOCIATE_MEMBER },
                { "MK", "Macedonia",            Currency.MKD, 1994, Membership.FULL_MEMBER },
                { "MY", "Malaysia",             Currency.MYR, 2008, Membership.COOPERATING_INSTITUTION },
                { "MT", "Malta",                Currency.EUR, 1984, Membership.FULL_MEMBER },
                { "MX", "Mexico",               Currency.MXN, 1985, Membership.FULL_MEMBER },
                { "MN", "Mongolia",             Currency.MNT, 2001, Membership.FULL_MEMBER },
                { "ME", "Montenegro",           Currency.EUR, 2007, Membership.ASSOCIATE_MEMBER },
                { "NL", "Netherlands",          Currency.EUR, 2011, Membership.COOPERATING_INSTITUTION },
                { "NG", "Nigeria",              Currency.NGN, 2007, Membership.COOPERATING_INSTITUTION },
                { "NO", "Norway",               Currency.NOK, 1960, Membership.FULL_MEMBER },
                { "OM", "Oman",                 Currency.OMR, 2001, Membership.FULL_MEMBER },
                { "PK", "Pakistan",             Currency.PKR, 1990, Membership.FULL_MEMBER },
                { "PA", "Panama",               Currency.PAB, 2004, Membership.FULL_MEMBER },
                { "PE", "Peru",                 Currency.PEN, 2001, Membership.ASSOCIATE_MEMBER },
                { "PH", "Philippines",          Currency.PHP, 2007, Membership.ASSOCIATE_MEMBER },
                { "PL", "Poland",               Currency.PLN, 1960, Membership.FULL_MEMBER },
                { "PT", "Portugal",             Currency.EUR, 1954, Membership.FULL_MEMBER },
                { "QA", "Qatar",                Currency.QAR, 2011, Membership.ASSOCIATE_MEMBER },
                { "RO", "Romania",              Currency.RON, 1998, Membership.FULL_MEMBER },
                { "RU", "Russia",               Currency.RUB, 1991, Membership.FULL_MEMBER },
                { "RS", "Serbia",               Currency.RSD, 1952, Membership.FULL_MEMBER },
                { "SK", "Slovakia",             Currency.EUR, 1965, Membership.FULL_MEMBER },
                { "SI", "Slovenia",             Currency.EUR, 1993, Membership.FULL_MEMBER },
                { "ES", "Spain",                Currency.EUR, 1951, Membership.FULL_MEMBER },
                { "LK", "SriLanka",             Currency.LKR, 2000, Membership.COOPERATING_INSTITUTION },
                { "SE", "Sweden",               Currency.SEK, 1948, Membership.FULL_MEMBER },
                { "CH", "Switzerland",          Currency.CHF, 1960, Membership.FULL_MEMBER },
                { "SY", "Syria",                Currency.SYP, 1965, Membership.FULL_MEMBER },
                { "TJ", "Tajikistan",           Currency.TJS, 1992, Membership.FULL_MEMBER },
                { "TZ", "Tanzania",             Currency.TZS, 2007, Membership.ASSOCIATE_MEMBER },
                { "TH", "Thailand",             Currency.THB, 1978, Membership.FULL_MEMBER },
                { "TN", "Tunisia",              Currency.TND, 1959, Membership.FULL_MEMBER },
                { "TR", "Turkey",               Currency.TRY, 1955, Membership.FULL_MEMBER },
                { "UA", "Ukraine",              Currency.UAH, 1994, Membership.FULL_MEMBER },
                { "AE", "UnitedArabEmirates",   Currency.AED, 2000, Membership.FULL_MEMBER },
                { "UK", "UnitedKingdom",        Currency.GBP, 1948, Membership.FULL_MEMBER },
                { "US", "USA",                  Currency.USD, 1950, Membership.FULL_MEMBER },
                { "UZ", "Uzbekistan",           Currency.UZS, 1997, Membership.FULL_MEMBER },
                { "VN", "Vietnam",              Currency.VND, 2006, Membership.COOPERATING_INSTITUTION },
                { "WB", "WestBank",             Currency.ILS, 2009, Membership.COOPERATING_INSTITUTION },
        };
        // Firstname is always "NS".
        // Lastname is always "<Committee Name>"
        // E-mail address is always "<Committee Name>@iaeste.<Country Code>"
        // Password is always "<Committee Name>"

        return Arrays.asList(parameters);
    }

   /**
    * We start by printing some house-holding information, to help reset the
    * system. This way, existing data is dropped so new data can be properly
    * added.
    */
    static {
        print("-- ============================================================================");
        print("-- Preparing to create test data for users.");
        print("-- ============================================================================");
        print("\n-- First reset the existing tables & sequences, regardlessly!");
        if (GENERATE_OFFER_DATA) {
            print("delete from offers;");
            print("delete from employers;");
            print("delete from addresses;");
        }
        if (GENERATE_INITIAL_DATA) {
            print("delete from user_to_group;");
            print("delete from user_notifications;");
            print("delete from sessions;");
            print("delete from history;");
            print("delete from users;");
            print("delete from groups where id>= 10;");
            print("delete from countries;");
        }

        if (GENERATE_MAILING_LIST_DATA) {
            print("delete from mailing_lists;");
            print("delete from mailing_list_membership;");
        }

        if (GENERATE_INITIAL_DATA) {
            print(RESTART_SEQUENCE, "country_sequence", currentCountryId);
            print(RESTART_SEQUENCE, "group_sequence", currentGroupId);
            print(RESTART_SEQUENCE, "user_sequence", currentUserId);
        }

        if (GENERATE_OFFER_DATA) {
            print(RESTART_SEQUENCE, "address_sequence", currentAddressId);
            print(RESTART_SEQUENCE, "employer_sequence", currentEmployerId);
            print(RESTART_SEQUENCE, "offer_sequence", currentOfferId);
        }

        if (GENERATE_MAILING_LIST_DATA) {
            print(RESTART_SEQUENCE, "mailing_list_sequence", currentMailingListId);
            print(RESTART_SEQUENCE, "mailing_list_membership_sequence", currentListSubscriptionId);
        }
    }

    // Write everything to the buffer and then print it at once. With dual core
    // CPUs and threads, the output will otherwise get messed up
    private static final StringBuffer sb = new StringBuffer();

    /**
     * Default Constructor for the Parameterized Test, it will take a series of
     * arguments, that are defined above, and run a new instance of the class
     * with them.
     *
     * @param countryCode   Country Code
     * @param committeeName Committee Name
     * @param currency      Currency
     * @param memberSince   Country has been an IAESTE member since
     * @param memberShip    Current membership
     */
    public GenerateDataForTest(
            final String countryCode,
            final String committeeName,
            final Currency currency,
            final int memberSince,
            final Membership memberShip) {
        // First a little heads-up regarding which country data is being created
        print(sb, "\n-- Generating Test data for %s", committeeName);
        final String password = committeeName.toLowerCase(IWSConstants.DEFAULT_LOCALE);

        if (GENERATE_INITIAL_DATA) {
            // Generate the SQL for the Country Table
            print(sb, COUNTRY_INSERT, countryCode, committeeName, committeeName, currency.name(), memberSince, memberShip.name());

            // Generate the Group SQL, we need both a Member & National Group
            final String memberExternalId = generateExternalId();
            final String nationalExternalId = generateExternalId();
            print(sb, GROUP_INSERT, memberExternalId, GroupType.MEMBER.ordinal(), "null", currentCountryId, committeeName);
            print(sb, GROUP_INSERT, nationalExternalId, GroupType.NATIONAL.ordinal(), String.valueOf(currentGroupId), currentCountryId, committeeName);

            // Generate the User SQL
            final String salt = generateExternalId();
            final String username = password + "@iaeste." + countryCode.toLowerCase(IWSConstants.DEFAULT_LOCALE);
            print(sb, USER_INSERT, generateExternalId(), UserStatus.ACTIVE, username, generateAlias(FIRST_NAME, committeeName), generateHashedPassword(password, salt), salt, FIRST_NAME, committeeName);

            // Generate the User Group relations
            print(sb, USER_GROUP_INSERT, generateExternalId(), currentUserId, currentGroupId, ROLE_OWNER);
            print(sb, USER_GROUP_INSERT, generateExternalId(), currentUserId, currentGroupId + 1, ROLE_OWNER);

            // Generate the User Notification settings
            print(sb, USER_NOTIFICATION_INSERT, currentUserId, "ACTIVATE_USER", "IMMEDIATELY");
            print(sb, USER_NOTIFICATION_INSERT, currentUserId, "RESET_PASSWORD", "IMMEDIATELY");
            print(sb, USER_NOTIFICATION_INSERT, currentUserId, "UPDATE_USERNAME", "IMMEDIATELY");
            print(sb, USER_NOTIFICATION_INSERT, currentUserId, "RESET_SESSION", "IMMEDIATELY");

            if (GENERATE_MAILING_LIST_DATA) {
                print(sb, "\n-- Generating Mailing Test data for %s, move to Mailing SQL file", committeeName);
                //Generate the Mailing list
                final String memberPrivateListAddress = StringUtils.convertToAsciiMailAlias(committeeName) + '@' + IWSConstants.PRIVATE_EMAIL_ADDRESS;
                final String nationalPublicListAddress = StringUtils.convertToAsciiMailAlias(committeeName) + '@' + IWSConstants.PUBLIC_EMAIL_ADDRESS;
                final String nationalPrivateListAddress = StringUtils.convertToAsciiMailAlias(committeeName) + ".staff" + '@' + IWSConstants.PRIVATE_EMAIL_ADDRESS;
                print(sb, MAILING_LIST_INSERT, memberExternalId, true, memberPrivateListAddress, true);
                print(sb, MAILING_LIST_INSERT, nationalExternalId, false, nationalPublicListAddress, true);
                print(sb, MAILING_LIST_INSERT, nationalExternalId, true, nationalPrivateListAddress, true);

                //Subscribe the User to Group's mailing list
                //TODO - this user probably has not working email address, subscribe anyway?
            }

        }

        if (GENERATE_OFFER_DATA) {
            print(sb, "\n-- Generating Offer Test data for %s", committeeName);
            // Generate the Offer
            print(sb, ADDRESS_INSERT, currentCountryId);
            print(sb, EMPLOYER_INSERT, generateExternalId(), currentGroupId + 1, currentAddressId);
            print(sb, OFFER_INSERT, countryCode, generateExternalId(), currentEmployerId, currency, currentGroupId + 1);
        }

        // Update our Id's, so we're ready for next country
        currentCountryId++;
        currentGroupId += 2;
        currentUserId++;
        currentAddressId++;
        currentEmployerId++;
        currentOfferId++;
        currentMailingListId += 3;
        currentListSubscriptionId++;

        // And done :-)
        //print("-- Completed generating test data for %s", committeeName);
    }

    @Test
    public void test() {
        // Constructor is invoked before, so we just have to sit tight here :-)
        assertThat(Boolean.TRUE, is(true));
    }

    @AfterClass
    public static void testDown() {
        print(sb, "-- Now share all offers with all national groups");
        print(sb, SHARE_ALL_OFFER, GroupType.NATIONAL.ordinal());

        System.out.print(sb);
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    private static String generateExternalId() {
        return UUID.randomUUID().toString();
    }

    private static String generateHashedPassword(final String password, final String salt) {
        return HashcodeGenerator.generateSHA256(password, salt);
    }

    private static String generateAlias(final String firstname, final String lastname) {
        return firstname + '.' + lastname + '@' + IWSConstants.PUBLIC_EMAIL_ADDRESS;
    }

    private static void print(final String query, final Object... args) {
        System.out.println(String.format(query, args));
    }

    private static void print(StringBuffer sb, final String query, final Object... args) {
        sb.append(String.format(query + "\n", args));
    }
}
