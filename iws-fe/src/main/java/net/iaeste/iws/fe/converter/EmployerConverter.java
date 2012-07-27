package net.iaeste.iws.fe.converter;

import net.iaeste.iws.fe.model.Employer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converting a string into an object using the name of the employer as a key
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@FacesConverter(value = "employerConverter", forClass = Employer.class)
public class EmployerConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        for (Employer e : Employer.getDummyEmployers()) {
            if (e.nameMatches(value)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if (value instanceof Employer) {
            return ((Employer) value).getName();
        }
        return null;
    }
}
