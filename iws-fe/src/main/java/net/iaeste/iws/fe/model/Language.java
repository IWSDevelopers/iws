package net.iaeste.iws.fe.model;

/**
* All languages that can be selected in the system
*
* @author Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since 1.7
*/
public enum Language {
    ENGLISH("englishLanguage"),
    GERMAN("germanLanguage"),
    CROATIAN("croatianLanguage");

    private String nameProperty;

    Language(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
