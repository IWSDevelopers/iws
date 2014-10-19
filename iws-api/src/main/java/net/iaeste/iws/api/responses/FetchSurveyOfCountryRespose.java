/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.responses.FetchSurveyOfCountryRespose
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.responses;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.constants.IWSError;
import net.iaeste.iws.api.dtos.SurveyOfCountry;
import net.iaeste.iws.api.util.AbstractFallible;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class FetchSurveyOfCountryRespose extends AbstractFallible {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** The Survey of Countries, matching the request. */
    private SurveyOfCountry survey = null;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public FetchSurveyOfCountryRespose() {
    }

    /**
     * Default Constructor.
     *
     * @param survey Survey Of Country Object
     */
    public FetchSurveyOfCountryRespose(final SurveyOfCountry survey) {
        this.survey = survey;
    }

    /**
     * Error Constructor.
     *
     * @param error   IWS Error Object
     * @param message Error Message
     */
    public FetchSurveyOfCountryRespose(final IWSError error, final String message) {
        super(error, message);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setSurvey(final SurveyOfCountry survey) {
        this.survey = survey;
    }

    public SurveyOfCountry getSurvey() {
        return survey;
    }


    // =========================================================================
    // Standard Response Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FetchSurveyOfCountryRespose)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final FetchSurveyOfCountryRespose that = (FetchSurveyOfCountryRespose) obj;

        return !(survey != null ? !survey.equals(that.survey) : that.survey != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = IWSConstants.HASHCODE_MULTIPLIER * result + (survey != null ? survey.hashCode() : 0);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FetchSurveyOfCountryRespose{" +
                "survey=" + survey +
                '}';
    }
}
