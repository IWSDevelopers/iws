package net.iaeste.iws.fe.model;

/**
* Possible gender selections
*
* @author Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since 1.7
*/
public enum Gender {
    EITHER("eitherGender"),
    MALE("maleGender"),
    FEMALE("femaleGender");

    private String nameProperty;

    Gender(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
