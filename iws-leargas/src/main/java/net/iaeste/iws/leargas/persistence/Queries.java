/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.persistence.Queries
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
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
