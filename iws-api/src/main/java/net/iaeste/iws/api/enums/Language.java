/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
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
 * @author  Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public enum Language {

    ENGLISH,
    ARABIC,
    BELARUSIAN,
    BULGARIAN,
    CANTONESE_YUE_CHINESE,
    CROATIAN,
    CZECH,
    DANISH,
    DUTCH,
    ESTONIAN,
    FINNISH,
    FRENCH,
    GERMAN,
    GREEK,
    HUNGARIAN,
    INDONESIAN,
    ITALIAN,
    JAPANESE,
    KOREAN,
    LATVIAN,
    LITHUANIAN,
    MANDARIN_CHINESE,
    NORWEGIAN,
    PERSIAN,
    POLISH,
    PORTUGUESE,
    ROMANIAN,
    RUSSIAN,
    SERBIAN,
    SLOVAKIAN,
    SLOVENIAN,
    SPANISH,
    SWEDISH,
    TAJIK,
    THAI,
    TURKISH,
    VIETNAMESE,
    UKRAINIAN,
    ANY;


    public String stringCSV(){
        return this.name().charAt(0) + this.name().toLowerCase().replace('_', ' ').substring(1);
    }
}
