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
                { "CO", "Columbia",    "columbia@iaeste.co",    "NS", "Columbia",    "columbia",    "COP", 1960, Membership.FULL_MEMBER },
                { "CH", "Switzerland", "switzerland@iaeste.ch", "NS", "Switzerland", "switzerland", "CHF", 1960, Membership.FULL_MEMBER },
                { "FI", "Finland",     "finland@iaeste.dk",     "NS", "Finland",     "finland",     "EUR", 1960, Membership.FULL_MEMBER },
                { "DE", "Germany",     "germany@iaeste.de",     "NS", "Germany",     "germany",     "EUR", 1960, Membership.FULL_MEMBER },
                { "AT", "Austria",     "austria@iaeste.at",     "NS", "Austria",     "austria",     "EUR", 1960, Membership.FULL_MEMBER },
                { "HR", "Croatia",     "croatia@iaeste.hr",     "NS", "Croatia",     "croatia",     "HRK", 1960, Membership.FULL_MEMBER },
                { "JO", "Jordan",      "jordan@iaeste.jo",      "NS", "Jordan",      "jordan",      "JOD", 1960, Membership.FULL_MEMBER },
                { "PL", "Poland",      "poland@iaeste.pl",      "NS", "Poland",      "poland",      "PLN", 1960, Membership.FULL_MEMBER },
                { "NO", "Norway",      "norway@iaeste.de",      "NS", "Norway",      "norway",      "NOK", 1960, Membership.FULL_MEMBER },
        };

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
     * @param countryCode CountryCode
     * @param countryName CountryName
     * @param username    Username
     * @param firstname   Firstname
     * @param lastname    Lastname
     * @param password    Password
     * @param memberSince Country has been an IAESTE member since
     * @param memberShip  Current membership
     */
    public GenerateDataForTest(
            final String countryCode,
            final String countryName,
            final String username,
            final String firstname,
            final String lastname,
            final String password,
            final String currency,
            final int memberSince,
            final Membership memberShip) {
        // First a little heads-up regarding which country data is being created
        print("\n-- Generating Test data for %s", countryName);

        // Generate the SQL for the Country Table
        print(COUNTRY_INSERT, countryCode, countryName, countryName, currency, memberSince, memberShip.name());

        // Generate the Group SQL, we need both a Member & National Group
        print(GROUP_INSERT, generateExternalId(), GroupType.MEMBER.ordinal(), "null", currentCountryId, countryName);
        print(GROUP_INSERT, generateExternalId(), GroupType.NATIONAL.ordinal(), String.valueOf(currentGroupId), currentCountryId, countryName);

        // Generate the User SQL
        final String salt = generateExternalId();
        print(USER_INSERT, generateExternalId(), UserStatus.ACTIVE, username, generateAlias(firstname, lastname), generateHashedPassword(password, salt), salt, firstname, lastname);

        // Generate the User Group relations
        print(USER_GROUP_INSERT, generateExternalId(), currentUserId, currentGroupId, ROLE_OWNER);
        print(USER_GROUP_INSERT, generateExternalId(), currentUserId, currentGroupId + 1, ROLE_OWNER);

        // Update our Id's, so we're ready for next country
        currentCountryId++;
        currentGroupId += 2;
        currentUserId++;

        // And done :-)
        print("-- Completed generating test data for %s", countryName);
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
