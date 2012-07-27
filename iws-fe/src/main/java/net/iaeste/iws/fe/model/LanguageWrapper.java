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
