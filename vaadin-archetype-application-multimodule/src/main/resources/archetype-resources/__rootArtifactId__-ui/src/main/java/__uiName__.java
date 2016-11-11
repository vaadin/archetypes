#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.servlet.annotation.WebServlet;

import ${package}.backend.CrudService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.data.BackEndDataSource;
import com.vaadin.server.data.DataSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("${themeName}")
public class ${uiName} extends UI {

    private CrudService<Person> service = new CrudService<>();
    private DataSource<Person> dataSource = new BackEndDataSource<>(
                    query -> service.findAll().stream(),
                    query -> service.findAll().size());

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        final Button button = new Button("Click Me");
        button.addClickListener(e -> {
            service.save(new Person(name.getValue()));
            dataSource.refreshAll();
        });

        final Grid<Person> grid = new Grid<>();
        grid.addColumn("Name", Person::getName);
        grid.setDataSource(dataSource);
        grid.setSizeFull();

        // This is a component from the ${rootArtifactId}-addon module
        //layout.addComponent(new MyComponent());
        layout.addComponents(name, button, grid);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setExpandRatio(grid, 1.0f);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "${uiName}Servlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ${uiName}.class, productionMode = false)
    public static class ${uiName}Servlet extends VaadinServlet {
    }
}
