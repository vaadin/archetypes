#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.portal.PortletView;
import com.vaadin.flow.portal.PortletViewContext;
import com.vaadin.flow.portal.VaadinPortlet;

public class ${portletContentClassName} extends VerticalLayout implements PortletView {

    private PortletViewContext portletViewContext;

    public ${portletContentClassName}() {
        VaadinPortlet portlet = VaadinPortlet.getCurrent();
        String name = portlet.getPortletName();
        String serverInfo = portlet.getPortletContext().getServerInfo();
        Button button = new Button("Click me", event -> Notification.show(
                "Hello from " + name + " running in " + serverInfo + "!"));
        add(button);
    }

    @Override
    public void onPortletViewContextInit(PortletViewContext context) {
        portletViewContext = context;

        // configure portlet mode, state and event listeners
    }
}
