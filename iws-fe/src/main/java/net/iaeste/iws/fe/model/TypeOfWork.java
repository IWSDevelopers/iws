package net.iaeste.iws.fe.model;

/**
* All types of work that can be selected in the system
*
* @author Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since 1.7
*/
public enum TypeOfWork {
    RESEARCHANDDEVELOPMENT("researchAndDevelopment"),
    PROFESSIONAL("professional"),
    WORKINGENVIRONMENT("workingEnvironment"),
    NONSPECIFIC("nonSpecific");

    private String nameProperty;

    TypeOfWork(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
