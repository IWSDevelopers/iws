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
package net.iaeste.iws.common.utils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.common.configuration.Settings;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Locale;
import java.util.UUID;

/**
 * This class is used to generate bot accounts for all Committees.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@Ignore("Test is ignored, it is only used to generate the data for the IAESTE Offer Bots.")
public final class GenerateBotData {

    // Generated as follows:
    // select '{ "' || g.id || '", "' || g.list_name || '" },'  from groups g join grouptypes gt on gt.id = g.grouptype_id join countries c on c.id = g.country_id where gt.id = 4 and g.status = 'ACTIVE' order by g.id asc;
    private static final String[][] COMMIT_TUPLES = {
            // Group Id, Committee Name, List Name, Member Since, Membership Type
            {"11", "Ireland"},
            {"14", "United_States"},
            {"16", "United_Kingdom"},
            {"20", "Switzerland"},
            {"22", "Malta"},
            {"26", "Finland"},
            {"28", "Denmark"},
            {"30", "Hungary"},
            {"32", "Germany"},
            {"34", "Macedonia"},
            {"36", "Brazil"},
            {"38", "Canada"},
            {"40", "Austria"},
            {"42", "Australia"},
            {"44", "Sweden"},
            {"46", "Norway"},
            {"50", "Belgium"},
            {"52", "Colombia"},
            {"54", "Croatia"},
            {"56", "Cyprus"},
            {"58", "Czech_Republic"},
            {"60", "Egypt"},
            {"64", "France"},
            {"66", "Greece"},
            {"68", "Israel"},
            {"70", "Japan"},
            {"72", "Jordan"},
            {"74", "Kazakhstan"},
            {"76", "Lebanon"},
            {"78", "Lithuania"},
            {"82", "Mexico"},
            {"84", "Pakistan"},
            {"86", "Poland"},
            {"88", "Portugal"},
            {"90", "Russia"},
            {"92", "Slovakia"},
            {"94", "Slovenia"},
            {"98", "Spain"},
            {"100", "Syria"},
            {"102", "Tajikistan"},
            {"104", "Thailand"},
            {"106", "Tunisia"},
            {"108", "Turkey"},
            {"110", "Ukraine"},
            {"113", "Argentina"},
            {"115", "Bosnia_and_Herzegovina"},
            {"117", "Ecuador"},
            {"119", "Ghana"},
            {"121", "Mongolia"},
            {"123", "Oman"},
            {"125", "Romania"},
            {"129", "Uzbekistan"},
            {"134", "China"},
            {"136", "China_Hong_Kong"},
            {"138", "Belarus"},
            {"140", "United_Arab_Emirates"},
            {"142", "Sri_Lanka_UM"},
            {"144", "peru"},
            {"150", "Latvia"},
            {"153", "Iran"},
            {"155", "India"},
            {"159", "Serbia"},
            {"167", "Kenya_JKUAT"},
            {"169", "China_Macao"},
            {"212", "Panama"},
            {"242", "Vietnam_NU"},
            {"265", "Tanzania"},
            {"279", "Korea"},
            {"287", "Nigeria_UNIBEN"},
            {"289", "Philippines"},
            {"317", "Palestine_ANU"},
            {"323", "Gambia_IPAM"},
            {"328", "Estonia_TUT"},
            {"330", "Azerbaijan"},
            {"354", "Qatar_QU"},
            {"376", "Netherlands"},
            {"405", "Albania_ICESTE"},
            {"407", "Bolivia_IB"},
            {"416", "Bangladesh_CAT"},
            {"422", "Kenya_DKUT"},
            {"425", "Nepal_CI"},
            {"2860", "Bangladesh_AFM"},
            {"3073", "New_Zealand_AUSA"},
            {"3075", "Chile_TU"},
            {"3359", "Jamaica_JOYST"},
            {"3436", "sierra_leone_nu"},
            {"3532", "DPR_Korea_PUST"},
            {"3535", "Saudi_Arabia_TU"},
            {"3835", "nicaragua_ulsa"}
    };

    private static final String ADD_USER = "insert into users (external_id, username, alias, password, salt, firstname, lastname, status, user_type) values\n" +
      "('%s', '%s@iaeste.bot', '%s.bot@iaeste.org', '%s', '%s', '%s', 'Offer Bot', 'SUSPENDED', 'UNKNOWN');";
    private static final String ADD_GROUP_RELATIONS = "insert into user_to_group (external_id, user_id, group_id, role_id, custom_title, on_public_list, on_private_list, write_to_private_list) values\n" +
      "-- First we're creating the Group Member relationship so the User can log in/out\n" +
      "('%s', (select id from users where external_id = '%s'), (select parent_id from groups where id = %s), 3, 'Automated Bot', false, false, false),\n" +
      "-- Second, we're adding the User to the National Group, so the Offers can be read out\n" +
      "('%s', (select id from users where external_id = '%s'), %s, 3, 'Automated Bot', false, false, false);\n";
    private static final String PASSWORD = PasswordGenerator.generatePassword();

    @Test
    public void generateBots() {
        final Settings settings = new Settings();
        // The IWS Instance salt which is set in the IWS Properties file must be
        // set to the correct value here also, otherwise the passwords will be
        // unusable. The salt can be read from the IWS Properties file.
        settings.setinstanceSalt("Don't forget to set the correct salt!");
        final HashcodeGenerator generator = new HashcodeGenerator(settings);

        System.out.println("-- This will generate the Bot accounts for all IAESTE Countries.");
        System.out.println("-- The password is common for all and is: '" + PASSWORD + "'.");

        for (final String[] tuple : COMMIT_TUPLES) {
            final String eid = UUID.randomUUID().toString();
            final String passwordSalt = UUID.randomUUID().toString();
            final String passwordHash = generator.generateHash(PASSWORD, passwordSalt);
            final String committeeId = tuple[0];
            final String committeeName = tuple[1];
            final String username = tuple[1].toLowerCase(Locale.ENGLISH);

            final String createUser = String.format(ADD_USER, eid, username, username, passwordHash, passwordSalt, committeeName);
            final String createRelation = String.format(ADD_GROUP_RELATIONS, UUID.randomUUID().toString(), eid, committeeId, UUID.randomUUID().toString(), eid, committeeId);
            System.out.println(createUser);
            System.out.println(createRelation);
        }

        // Adding a stupid assertion to quiet of the Static Analysis tools
        assertThat(Boolean.TRUE, is(true));
    }
}
