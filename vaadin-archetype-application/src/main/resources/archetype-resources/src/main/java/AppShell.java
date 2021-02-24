#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

/**
 * This class is used to configure the generated html host page used by the app
 */
@PWA(name = "${applicationName}", shortName = "${applicationName}")
public class AppShell implements AppShellConfigurator {
    
}