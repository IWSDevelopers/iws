package net.iaeste.iws.fe.model;

/**
* Table views that determine the amount of details in the list of offers
*
* @author Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since 1.7
*/
public enum TableView {
    BASIC("basicTableView"),
    COMPACT("compactTableView"),
    DETAILED("detailedTableView");

    private String nameProperty;

    TableView(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
