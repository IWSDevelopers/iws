/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.primefaces.component.outputlabel.IWOutputLabelRenderer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fe.primefaces.component.outputlabel;

import org.primefaces.component.api.InputHolder;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.outputlabel.OutputLabelRenderer;
import org.primefaces.util.HTML;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * This is a custom implementation of the PrimeFaces OutputLabelRenderer.
 * <p/>
 * This widget uses a custom error class on validation errors which
 * highlights the label text and adds a background. This does not look
 * good on most themes because the actual input field is also
 * highlighted and there is an additional validation message.
 * <p/>
 * Overriding only the CSS would not work because there are different
 * CSS styles for each theme and we would have to override all of them.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @see <a href="http://83-169-37-90.kundenadmin.hosteurope.de/trac/ticket/6">trac ticket 6</a>
 * @since 1.7
 */
public class IWOutputLabelRenderer extends OutputLabelRenderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        OutputLabel label = (OutputLabel) component;
        String clientId = label.getClientId();
        Object value = label.getValue();

        UIInput target = findTarget(context, label);
        String _for = (target instanceof InputHolder) ? ((InputHolder) target).getInputClientId() : target.getClientId(context);

        // always use the default style class
        String defaultStyleClass = OutputLabel.VALID_CLASS;
        String styleClass = label.getStyleClass();
        styleClass = styleClass == null ? defaultStyleClass : defaultStyleClass + " " + styleClass;

        writer.startElement("label", label);
        writer.writeAttribute("id", clientId, "id");
        writer.writeAttribute("class", styleClass, "id");
        writer.writeAttribute("for", _for, "for");
        renderPassThruAttributes(context, label, HTML.LABEL_ATTRS);

        if (value != null) {
            String text = value.toString();
            if (label.isEscape())
                writer.writeText(text, "value");
            else
                writer.write(text);

            //assign label of target
            target.getAttributes().put("label", value);
        }

        if (target.isRequired()) {
            writer.startElement("span", label);
            writer.writeAttribute("class", OutputLabel.REQUIRED_FIELD_INDICATOR_CLASS, null);
            writer.write("*");
            writer.endElement("span");
        }

        writer.endElement("label");
    }
}
