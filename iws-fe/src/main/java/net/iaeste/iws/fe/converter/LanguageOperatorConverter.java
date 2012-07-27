package net.iaeste.iws.fe.converter;

import net.iaeste.iws.fe.model.LanguageOperator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converting a string into an object using the name of the languageOperator as a key
 *
 * @author Marko Cilimkovic / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@FacesConverter( value = "languageOperatorConverter", forClass = LanguageOperator.class)
public class LanguageOperatorConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        return value != null ? LanguageOperator.valueOf(value) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if (value instanceof LanguageOperator) {
            return ((LanguageOperator)value).name();
        }
        return null;
    }

}
