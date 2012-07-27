package net.iaeste.iws.fe.model;

/**
 * All periods that determine whether something needs to be payed weekly or monthly
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public enum PaymentPeriod {
    MONTHLY("paymentPeriod.monthly"),
    WEEKLY("paymentPeriod.weekly");

    private String nameProperty;

    private PaymentPeriod(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
