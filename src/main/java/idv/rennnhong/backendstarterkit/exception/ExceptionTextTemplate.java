package idv.rennnhong.backendstarterkit.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by Arpit Khandelwal.
 */
@Component
@PropertySource("classpath:exception-messages.properties")
class ExceptionTextTemplate {
    @Autowired
    private Environment env;

    public String getContent(String configKey) {
        return env.getProperty(configKey);
    }
}
