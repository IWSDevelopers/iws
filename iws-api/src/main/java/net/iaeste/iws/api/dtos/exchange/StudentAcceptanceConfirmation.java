/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.dtos.exchange.StudentAcceptanceConfirmation
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.dtos.exchange;

import net.iaeste.iws.api.enums.exchange.TransportationType;
import net.iaeste.iws.api.util.Date;

/**
 * Contains a students travel information (N5b) for an application
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class StudentAcceptanceConfirmation {

    // TODO implement

    private String applicationId = null;

    private Date departure = null;
    private TransportationType transportationType = null;

    private String departureFrom = null;
    private String transportNumber = null;

    private Date arrivalDateTime = null;
    private String phoneNumberDuringTravel = null;

    private Date lodgingRequiredFrom = null;
    private Date lodgingRequiredTo = null;

    private String otherInformation = null; // allow up to 5000 characters

    private String insuranceCompany = null;
    private String insuranceReceiptNumber = null;

}
