package net.iaeste.iws.fe.model;

/**
* Possible levels for grading languages
*
* @author Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since 1.7
*/
public enum LanguageLevel {
    EXCELLENT("excellent"),
    GOOD("good"),
    FAIR("fair");

    private String nameProperty;

    LanguageLevel(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
