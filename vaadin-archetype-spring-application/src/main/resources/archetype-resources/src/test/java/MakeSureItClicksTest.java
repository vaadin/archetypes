#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.views.MainView;
import com.vaadin.browserless.BrowserlessUIContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestConfig.class)
public class MakeSureItClicksTest {

    @Test
    void clickAndVerifyParagraph(@Autowired BrowserlessUIContext uiContext) {
        uiContext.navigate(MainView.class);
        uiContext.findButton().withText("Click me").click();
        uiContext.findParagraph().withText("Clicked!").ensureComponentIsUsable();
    }

}
