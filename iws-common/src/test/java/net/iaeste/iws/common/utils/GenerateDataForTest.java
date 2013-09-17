/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-common) - net.iaeste.iws.common.utils.GenerateTestData
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
 * @since   1.7
 * @noinspection StaticNonFinalField, NonFinalStaticVariableUsedInClassInitialization, ConstructorWithTooManyParameters, AssignmentToStaticFieldFromInstanceMethod, UseOfSystemOutOrSystemErr
 */
@RunWith(Parameterized.class)
public final class GenerateDataForTest {

    private static final String FIRST_NAME = "NS";
    private static final String RESTART_SEQUENCE = "alter sequence %s restart with %d;";
    private static final String COUNTRY_INSERT = "insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('%s', '%s', '%s', '%s', %d, '%s');";
    private static final String GROUP_INSERT = "insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('%s', %d, %s, %d, '%s');";
    private static final String USER_INSERT = "insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
    private static final String USER_GROUP_INSERT = "insert into user_to_group (external_id, user_id, group_id, role_id) values ('%s', %d, %d, %d);";
    private static final int ROLE_OWNER = 1;
    private static long currentCountryId = 1;
    private static long currentGroupId = 10;
    private static long currentUserId = 1;

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
                { "CO", "Columbia",             Currency.COP, 1960, Membership.FULL_MEMBER },
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
                { "DE", "Germany",              Currency.EUR, 1960, Membership.FULL_MEMBER },
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
                { "LV", "Latvia",               Currency.LVL, 2002, Membership.FULL_MEMBER },
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
                { "SL", "Slovenia",             Currency.EUR, 1993, Membership.FULL_MEMBER },
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
        print("delete from user_to_group;");
        print("delete from users;");
        print("delete from groups where id>= 10;");
        print("delete from countries;");
        print(RESTART_SEQUENCE, "country_sequence", currentCountryId);
        print(RESTART_SEQUENCE, "group_sequence", currentGroupId);
        print(RESTART_SEQUENCE, "user_sequence", currentUserId);
    }

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
        print("\n-- Generating Test data for %s", committeeName);
        final String password = committeeName.toLowerCase(IWSConstants.DEFAULT_LOCALE);

        // Generate the SQL for the Country Table
        print(COUNTRY_INSERT, countryCode, committeeName, committeeName, currency.name(), memberSince, memberShip.name());

        // Generate the Group SQL, we need both a Member & National Group
        print(GROUP_INSERT, generateExternalId(), GroupType.MEMBER.ordinal(), "null", currentCountryId, committeeName);
        print(GROUP_INSERT, generateExternalId(), GroupType.NATIONAL.ordinal(), String.valueOf(currentGroupId), currentCountryId, committeeName);

        // Generate the User SQL
        final String salt = generateExternalId();
        final String username = password + "@iaeste." + countryCode.toLowerCase(IWSConstants.DEFAULT_LOCALE);
        print(USER_INSERT, generateExternalId(), UserStatus.ACTIVE, username, generateAlias(FIRST_NAME, committeeName), generateHashedPassword(password, salt), salt, FIRST_NAME, committeeName);

        // Generate the User Group relations
        print(USER_GROUP_INSERT, generateExternalId(), currentUserId, currentGroupId, ROLE_OWNER);
        print(USER_GROUP_INSERT, generateExternalId(), currentUserId, currentGroupId + 1, ROLE_OWNER);

        // Update our Id's, so we're ready for next country
        currentCountryId++;
        currentGroupId += 2;
        currentUserId++;

        // And done :-)
        print("-- Completed generating test data for %s", committeeName);
    }

    @Test
    public void test() {
        // Constructor is invoked before, so we just have to sit tight here :-)
        assertThat(Boolean.TRUE, is(true));
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
}
