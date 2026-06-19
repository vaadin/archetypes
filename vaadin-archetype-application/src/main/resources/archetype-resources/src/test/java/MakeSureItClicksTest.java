#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.vaadin.browserless.BrowserlessTest;
import com.vaadin.browserless.ViewPackages;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ViewPackages(classes = {MainView.class})
public class MakeSureItClicksTest extends BrowserlessTest {

    @BeforeEach
    void setup() {
        navigate(MainView.class);
    }

    @Test
    void clickAndVerifyParagraph() {
        assertFalse(${symbol_dollar}view(Paragraph.class).exists());
        ${symbol_dollar}view(Button.class).withText("Click me").first().click();
        assertTrue(${symbol_dollar}view(Paragraph.class).withText("Clicked!").exists());
    }
}
