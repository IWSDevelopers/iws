package net.iaeste.iws.fe.model;

/**
* Levels of study that determine the students preparedness
*
* @author Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since 1.7
*/
public enum StudyLevel {
    BEGINNING("studyLevelBegin"),
    MIDDLE("studyLevelMiddle"),
    END("studyLevelEnd");

    private String nameProperty;

    StudyLevel(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    public String getNameProperty() {
        return nameProperty;
    }
}
