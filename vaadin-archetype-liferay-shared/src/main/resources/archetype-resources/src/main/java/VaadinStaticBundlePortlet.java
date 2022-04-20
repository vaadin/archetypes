#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.IOException;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Static Resources Portlet sharing common Vaadin resources between multiple Vaadin portlets in container.
 */
public class VaadinStaticBundlePortlet extends GenericPortlet {
    private static final String BUNDLE_PORTLET_ERROR_MESSAGE = "This portlet provides common Vaadin client side dependencies and should not be placed on portal pages.";

    @Override
    protected void doDispatch(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        response.getWriter().println(BUNDLE_PORTLET_ERROR_MESSAGE);
    }
}
