/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.constants.IWSExchangeConstants
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.api.constants;

import java.util.regex.Pattern;

/**
 * Exchange specific constants for the IW Services.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public interface IWSExchangeConstants {

    String REFNO_FORMAT = "[A-Z]{2}-\\d{4}-\\d{4}(-[A-Z0-9]{2})?";
    Pattern REFNO_PATTERN = Pattern.compile(REFNO_FORMAT);

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.FieldOfStudy}
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MAX_OFFER_FIELDS_OF_STUDY = 7;

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.Specialization}
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MAX_OFFER_SPECIALIZATIONS = 7;

    /**
     * Defines the maximum number of {@link net.iaeste.iws.api.enums.Language}
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MAX_OFFER_LANGUAGES = 3;

    /**
     * Defines the minimum length of offer description
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MIN_OFFER_WORK_DESCRIPTION_SIZE = 1;

    /**
     * Defines the maximum length of offer description
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MAX_OFFER_WORK_DESCRIPTION_SIZE = 1000;

    /**
     * Defines the minimum length of other requirements
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MIN_OFFER_OTHER_REQUIREMENTS_SIZE = 0;
    /**
     * Defines the maximum length of other requirements
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MAX_OFFER_OTHER_REQUIREMENTS_SIZE = 500;

    /**
     * Defines the minimum number of weeks required
     * for an {@link net.iaeste.iws.api.dtos.Offer}
     */
    int MIN_OFFER_MINIMUM_WEEKS = 1;
}
