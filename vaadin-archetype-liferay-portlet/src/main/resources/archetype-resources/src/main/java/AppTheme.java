package ${package};

import org.osgi.service.component.annotations.Component;

import com.vaadin.osgi.resources.OSGiVaadinTheme;
import com.vaadin.ui.themes.ValoTheme;

@Component(immediate = true, service = OSGiVaadinTheme.class)
public class AppTheme extends ValoTheme implements OSGiVaadinTheme {
    @Override
    public String getName() {
        return "${theme}";
    }

}
