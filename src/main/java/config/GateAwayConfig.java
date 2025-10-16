package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateAwayConfig {

    @Value("${bussines.logic.server.url}")
    private String Url;

}
