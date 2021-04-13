package idv.rennnhong.backendstarterkit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(
        exclude = {
                RedisAutoConfiguration.class,
                SecurityAutoConfiguration.class,
                ManagementWebSecurityAutoConfiguration.class}
)
public class BackendStarterkitApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringApplicationBuilder.class);
        springApplication.addInitializers(new CustomApplicationContextInitializer());
        springApplication.run(args);
    }
}
