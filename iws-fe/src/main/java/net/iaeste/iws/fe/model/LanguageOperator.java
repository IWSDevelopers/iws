package net.iaeste.iws.fe.model;

/**
* Possible choices for selecting if something is required or optional
*
* @author Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since 1.7
*/

public enum LanguageOperator {
    AND("andSelected"),
    OR("orSelected");

    private String nameProperty;

    LanguageOperator(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
