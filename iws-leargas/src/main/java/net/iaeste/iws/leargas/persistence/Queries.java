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
package net.iaeste.iws.leargas.persistence;

/**
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class Queries {

    /** Private Constructor, this is a Constants Class. */
    private Queries() {}

    private static final String RAW_FIELD_LIST =
            "  ref_no," +
            "  deadline," +
            "  comment," +
            "  employer," +
            "  address1," +
            "  address2," +
            "  postbox," +
            "  postalcode," +
            "  city," +
            "  state," +
            "  country," +
            "  website," +
            "  workplace," +
            "  business," +
            "  responsible," +
            "  airport," +
            "  transport," +
            "  employees," +
            "  hoursweekly," +
            "  hoursdaily," +
            "  canteen," +
            "  faculty," +
            "  specialization," +
            "  trainingrequired," +
            "  otherrequirements," +
            "  workkind," +
            "  weeksmin," +
            "  weeksmax," +
            "  from," +
            "  to," +
            "  studycompleted_beginning," +
            "  studycompleted_middle," +
            "  studycompleted_end," +
            "  worktype_p," +
            "  worktype_r," +
            "  worktype_w," +
            "  worktype_n," +
            "  language1," +
            "  language1level," +
            "  language1or," +
            "  language2," +
            "  language2level," +
            "  language2or," +
            "  language3," +
            "  language3level," +
            "  currency," +
            "  payment," +
            "  paymentfrequency," +
            "  deduction," +
            "  lodging," +
            "  lodgingcost," +
            "  lodgingcostfrequency," +
            "  livingcost," +
            "  livingcostfrequency," +
            "  nohardcopies," +
            "  status," +
            "  period2_from," +
            "  period2_to," +
            "  holidays_from," +
            "  holidays_to," +
            "  additional_info," +
            "  shared," +
            "  last_modified," +
            "  nsfirst_name," +
            "  nslast_name";

    public static final String OFFER_FIND =
            "SELECT " + RAW_FIELD_LIST + " FROM test_offer " +
            "WHERE ref_no like '%2015%'";

    public static final String OFFER_INSERT =
            "INSERT INTO test_offer (" + RAW_FIELD_LIST +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String OFFER_UPDATE =
            "UPDATE test_offer SET" +
            "  deadline = ?," +
            "  comment = ?," +
            "  employer = ?," +
            "  address1 = ?," +
            "  address2 = ?," +
            "  postbox = ?," +
            "  postalcode = ?," +
            "  city = ?," +
            "  state = ?," +
            "  country = ?," +
            "  website = ?," +
            "  workplace = ?," +
            "  business = ?," +
            "  responsible = ?," +
            "  airport = ?," +
            "  transport = ?," +
            "  employees = ?," +
            "  hoursweekly = ?," +
            "  hoursdaily = ?," +
            "  canteen = ?," +
            "  faculty = ?," +
            "  specialization = ?," +
            "  trainingrequired = ?," +
            "  otherrequirements = ?," +
            "  workkind = ?," +
            "  weeksmin = ?," +
            "  weeksmax = ?," +
            "  from = ?," +
            "  to = ?," +
            "  studycompleted_beginning = ?," +
            "  studycompleted_middle = ?," +
            "  studycompleted_end = ?," +
            "  worktype_p = ?," +
            "  worktype_r = ?," +
            "  worktype_w = ?," +
            "  worktype_n = ?," +
            "  language1 = ?," +
            "  language1level = ?," +
            "  language1or = ?," +
            "  language2 = ?," +
            "  language2level = ?," +
            "  language2or = ?," +
            "  language3 = ?," +
            "  language3level = ?," +
            "  currency = ?," +
            "  payment = ?," +
            "  paymentfrequency = ?," +
            "  deduction = ?," +
            "  lodging = ?," +
            "  lodgingcost = ?" +
            "  lodgingcostfrequency = ?," +
            "  livingcost = ?" +
            "  livingcostfrequency = ?" +
            "  nohardcopies = ,?" +
            "  status = ?," +
            "  period2_from = ?," +
            "  period2_to = ?," +
            "  holidays_from = ?," +
            "  holidays_to = ?," +
            "  additional_info = ?," +
            "  shared = ?," +
            "  last_modified = ?," +
            "  nsfirst_name = ?," +
            "  nslast_name = ?" +
            ") WHERE ref_no = ?";
}
