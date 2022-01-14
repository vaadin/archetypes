#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.portlet.annotations.Dependency;
import javax.portlet.annotations.PortletConfiguration;

import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.portal.VaadinLiferayPortlet;
import com.vaadin.flow.theme.Theme;

@PortletConfiguration(
        portletName = "ContactList",
        dependencies = @Dependency(name = "PortletHub", scope = "javax.portlet", version = "3.0.0")
)
public class ${portletClassName} extends VaadinLiferayPortlet<MyPortletContent> {

    @Override
    public String getPortletTag() {
        return "portlet-content";
    }

    @Override
    public WebComponentExporter<MyPortletContent> create() {
        return new MyPortletExporter();
    }

    /**
     * Custom implementation only for styling the shadow DOM
     */
    @Theme(themeFolder = "${theme}")
    private class MyPortletExporter extends PortletWebComponentExporter {
        public MyPortletExporter() {
            super(${portletClassName}.this.getPortletTag());
        }
    }

}
