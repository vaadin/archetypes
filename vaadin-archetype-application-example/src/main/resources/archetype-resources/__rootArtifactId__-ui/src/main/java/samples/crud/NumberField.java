#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.samples.crud;

import ${package}.samples.AttributeExtension;

import com.vaadin.ui.TextField;

/**
 * A field for entering numbers. On touch devices, a numeric keyboard is shown
 * instead of the normal one.
 */
public class NumberField extends TextField {
    public NumberField() {
        // Mark the field as numeric.
        // This affects the virtual keyboard shown on mobile devices.
        AttributeExtension ae = new AttributeExtension();
        ae.extend(this);
        ae.setAttribute("type", "number");
    }

    public NumberField(String caption) {
        this();
        setCaption(caption);
    }
}
