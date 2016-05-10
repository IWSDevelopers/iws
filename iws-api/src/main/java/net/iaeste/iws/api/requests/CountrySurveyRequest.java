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
package net.iaeste.iws.api.requests;

import net.iaeste.iws.api.constants.IWSConstants;
import net.iaeste.iws.api.dtos.CountrySurvey;
import net.iaeste.iws.api.enums.Action;
import net.iaeste.iws.api.util.Verifications;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "countrySurveyRequest", propOrder = {"survey", "action"})
public final class CountrySurveyRequest extends Verifications implements Actionable {

    /** {@link IWSConstants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = IWSConstants.SERIAL_VERSION_UID;

    /** Default allowed Actions for the Committee Request. */
    private static final Set<Action> ALLOWED = EnumSet.of(Action.PROCESS);

    @XmlElement(required = true)
    private CountrySurvey survey = null;

    /**
     * <p>Action to perform on a Country Survey, by default we're assuming
     * that it must be processed, i.e. either created or updated.</p>
     */
    @XmlElement(required = true) private Action action = Action.PROCESS;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public CountrySurveyRequest() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Default Constructor,
     *
     * @param survey Survey Of Country Object to process
     */
    public CountrySurveyRequest(final CountrySurvey survey) {
        setSurvey(survey);
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setSurvey(final CountrySurvey survey) {
        ensureNotNullAndVerifiable("survey", survey);
        this.survey = survey;
    }

    public CountrySurvey getSurvey() {
        return survey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction(final Action action) {
        ensureNotNullAndContains("action", action, ALLOWED);
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getAction() {
        return action;
    }

    // =========================================================================
    // Standard Request Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Action> allowedActions() {
        return immutableSet(ALLOWED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> validation = new HashMap<>(1);

        isNotNull(validation, "survey", survey);
        isNotNull(validation, "action", action);

        return validation;
    }
}
