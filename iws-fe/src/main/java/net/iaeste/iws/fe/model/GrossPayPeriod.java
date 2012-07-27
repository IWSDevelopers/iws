package net.iaeste.iws.fe.model;

/**
 * Possible selection of gross pay periods
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public enum GrossPayPeriod {
    MONTHLY("paymentPeriod.monthly"),
    WEEKLY("paymentPeriod.weekly");

    private String nameProperty;

    private GrossPayPeriod(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
