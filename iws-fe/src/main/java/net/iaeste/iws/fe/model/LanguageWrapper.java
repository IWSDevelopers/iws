/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.model.Offer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.fe.model;

/**
 * A container for the language level and the level itself
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class LanguageWrapper {
    private Language language;
    private LanguageLevel level = LanguageLevel.EXCELLENT;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LanguageLevel getLevel() {
        return level;
    }

    public void setLevel(LanguageLevel level) {
        this.level = level;
    }
}
