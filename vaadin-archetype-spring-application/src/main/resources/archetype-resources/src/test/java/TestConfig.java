#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.vaadin.browserless.BrowserlessApplicationContext;
import com.vaadin.browserless.BrowserlessUIContext;
import com.vaadin.browserless.SpringBrowserlessApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    BrowserlessApplicationContext  browserlessApplicationContext(ApplicationContext applicationContext) {
        return SpringBrowserlessApplicationContext.create(applicationContext, Application.class);
    }

    @Bean
    BrowserlessUIContext browserlessUIContext(BrowserlessApplicationContext browserlessApplicationContext) {
        return browserlessApplicationContext.newUser().newWindow();
    }
}
