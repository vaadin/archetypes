package ${package}.client;

import ${package}.${componentClassName};

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(${componentClassName}.class)
public class ${componentClassName}Connector extends AbstractComponentConnector {

    // ServerRpc is used to send events to server. Communication implementation
    // is automatically created here
    ${componentClassName}ServerRpc rpc = RpcProxy.create(${componentClassName}ServerRpc.class, this);

    public ${componentClassName}Connector() {
        
        // To receive RPC events from server, we register ClientRpc implementation 
        registerRpc(${componentClassName}ClientRpc.class, Window::alert);

        // We choose listed for mouse clicks for the widget
        getWidget().addClickHandler(event -> {
                final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
                        .buildMouseEventDetails(event.getNativeEvent(),
                                getWidget().getElement());
                
                // When the widget is clicked, the event is sent to server with ServerRpc
                rpc.clicked(mouseDetails);
            }
        );

    }

    // We must implement getWidget() to cast to correct type 
    // (this will automatically create the correct widget type)
    @Override
    public ${componentClassName}Widget getWidget() {
        return (${componentClassName}Widget) super.getWidget();
    }

    // We must implement getState() to cast to correct type
    @Override
    public ${componentClassName}State getState() {
        return (${componentClassName}State) super.getState();
    }

    // Whenever the state changes in the server-side, this method is called
    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        // State is directly readable in the client after it is set in server
        final String text = getState().text;
        getWidget().setText(text);
    }
}
