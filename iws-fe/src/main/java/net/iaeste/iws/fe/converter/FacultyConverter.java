package net.iaeste.iws.fe.converter;

import net.iaeste.iws.fe.model.Faculty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converting a string into an object using the name of the faculty as a key
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@FacesConverter(value = "facultyConverter", forClass = Faculty.class)
public class FacultyConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        for (Faculty f: Faculty.getDummyFaculties()) {
            if (f.nameMatches(value)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if (value instanceof Faculty) {
            return ((Faculty)value).getName();
        }
        return null;
    }
}
