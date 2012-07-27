package net.iaeste.iws.fe.model;

/**
*  All currencies that can be used in the system
*
* @author Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since 1.7
*/
public enum Currency {
    EUR("currencyEURO"),
    HRK("currencyHRK"),
    AUD("currencyAUD");

    private String nameProperty;

    Currency(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
