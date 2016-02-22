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
package net.iaeste.iws.api.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * All languages that can be selected in the system
 *
 * @author  Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "language")
public enum Language implements Descriptable<Language> {

    ENGLISH("English"),
    ARABIC("Arabic"),
    BELARUSIAN("Belarusian"),
    BULGARIAN("Bulgarian"),
    CANTONESE_YUE_CHINESE("Cantonese Yue Chinese"),
    CROATIAN("Croatian"),
    CZECH("Czech"),
    DANISH("Danish"),
    DUTCH("Dutch"),
    ESTONIAN("Estonian"),
    FINNISH("Finnish"),
    FRENCH("French"),
    GERMAN("German"),
    GREEK("Greek"),
    HUNGARIAN("Hungarian"),
    INDONESIAN("Indonesian"),
    ITALIAN("Italian"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    LATVIAN("Latvian"),
    LITHUANIAN("Lithuanian"),
    MANDARIN_CHINESE("Mandarin Chinese"),
    NORWEGIAN("Norwegian"),
    PERSIAN("Persian"),
    POLISH("Polish"),
    PORTUGUESE("Portuguese"),
    ROMANIAN("Romanian"),
    RUSSIAN("Russian"),
    SERBIAN("Serbian"),
    SLOVAKIAN("Slovakian"),
    SLOVENIAN("Slovenian"),
    SPANISH("Spanish"),
    SWEDISH("Swedish"),
    TAJIK("Tajik"),
    THAI("Thai"),
    TURKISH("Turkish"),
    VIETNAMESE("Vietnamese"),
    UKRAINIAN("Ukrainian");

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;

    Language(final String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
