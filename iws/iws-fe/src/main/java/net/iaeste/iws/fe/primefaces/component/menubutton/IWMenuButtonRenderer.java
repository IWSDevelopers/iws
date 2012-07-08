package net.iaeste.iws.fe.primefaces.component.menubutton;

import org.primefaces.component.menubutton.MenuButton;
import org.primefaces.component.menubutton.MenuButtonRenderer;
import org.primefaces.util.HTML;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * This is a custom implementation of the PrimeFaces MenuButtonRenderer.
 * The purpose of this renderer is to put the button icon on the right side.
 * The standard renderer puts the button icon on the left side of the button text
 * but in SplitButtons (that we also use extensively) the icon is on the right side.
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @see MenuButtonRenderer
 * @since 1.7
 */
public class IWMenuButtonRenderer extends MenuButtonRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encodeButton(FacesContext context, MenuButton button, String buttonId, boolean disabled) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String value = button.getValue();
        String buttonClass = disabled ? HTML.BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS + " ui-state-disabled" : HTML.BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS;

        writer.startElement("button", null);
        writer.writeAttribute("id", buttonId, null);
        writer.writeAttribute("name", buttonId, null);
        writer.writeAttribute("type", "button", null);
        writer.writeAttribute("class", buttonClass, buttonId);
        if (button.isDisabled()) {
            writer.writeAttribute("disabled", "disabled", null);
        }

        //text
        writer.startElement("span", null);
        writer.writeAttribute("class", HTML.BUTTON_TEXT_CLASS, null);

        if (value == null)
            writer.write("ui-button");
        else
            writer.writeText(value, "value");

        writer.endElement("span");

        //button icon
        String iconClass = HTML.BUTTON_RIGHT_ICON_CLASS + " ui-icon-triangle-1-s";
        writer.startElement("span", null);
        writer.writeAttribute("class", iconClass, null);
        writer.endElement("span");

        writer.endElement("button");
    }
}
