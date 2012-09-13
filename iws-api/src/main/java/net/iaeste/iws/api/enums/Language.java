/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.Language
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

/**
 * All languages that can be selected in the system
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public enum Language {
    ENGLISH("English"),
    ARABIC("Arabic"),
    BELARUSIAN("Belarusian"),
    BULGARIAN("Bulgarian"),
    CANTONESE_YUE_CHINESE("Cantonese (Yue) Chinese"),
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
    UKRAINIAN("Ukrainian"),
    ANY("Any");


    private final String description;

    Language(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Same as valueOf but for when an argument is null then null is returned.
     *
     * @param language String which should be value of TypeOfWork
     * @return null or enum value
     */
    public static Language toValue(final String language) {
        return language == null ? null : Language.valueOf(language);
    }

}
