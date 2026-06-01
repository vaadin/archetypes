#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.views.MainView;
import com.vaadin.browserless.BrowserlessUIContext;
import com.vaadin.browserless.SpringBrowserlessApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class MakeSureItClicksTest {

    @Test
    void clickAndVerifyParagraph(ApplicationContext context) {
        try(var app = SpringBrowserlessApplicationContext.create(context, Application.class)) {
            BrowserlessUIContext uiContext = app.newUser().newWindow();
            uiContext.navigate(MainView.class);
            uiContext.findButton().withText("Click me").click();
            uiContext.findParagraph().withText("Clicked!").ensureComponentIsUsable();
        }
    }

}
