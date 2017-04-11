package ${package};

import org.osgi.service.component.annotations.Component;

import com.vaadin.osgi.resources.OSGiVaadinWidgetset;

@Component(immediate = true, service = OSGiVaadinWidgetset.class)
public class AppWidgetSet implements OSGiVaadinWidgetset {

    @Override
    public String getName() {
        return "${package}.AppWidgetSet";
    }

}
