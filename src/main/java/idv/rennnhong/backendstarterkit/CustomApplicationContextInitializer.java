package idv.rennnhong.backendstarterkit;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class CustomApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        //預防誤用auto-ddl的create、create-drop
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        String hibernateDDLAuto = env.getProperty("spring.jpa.hibernate.ddl-auto");
        System.out.println(hibernateDDLAuto);

        if ("create".equals(hibernateDDLAuto) || "create-drop".equals(hibernateDDLAuto)) {
            System.exit(0);
        }
    }
}
