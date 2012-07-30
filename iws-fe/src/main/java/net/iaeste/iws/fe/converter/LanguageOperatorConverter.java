/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.model.Offer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

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
