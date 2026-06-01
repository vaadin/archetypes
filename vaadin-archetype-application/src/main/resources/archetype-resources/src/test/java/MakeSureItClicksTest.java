#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.vaadin.browserless.BrowserlessApplicationContext;
import org.junit.jupiter.api.Test;

public class MakeSureItClicksTest {

    @Test
    void clickAndVerifyParagraph() {
        try(var app = BrowserlessApplicationContext.create(MainView.class)) {
            var uiContext = app.newUser().newWindow();
            uiContext.navigate(MainView.class);
            uiContext.findButton().withText("Click me").click();
            uiContext.findParagraph().withText("Clicked!").ensureComponentIsUsable();
        }
    }

}
