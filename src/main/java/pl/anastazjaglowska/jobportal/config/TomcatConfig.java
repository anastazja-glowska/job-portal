package pl.anastazjaglowska.jobportal.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            // 20 MB limit (rozmiar)
            connector.setMaxPostSize(20 * 1024 * 1024);

            // Liczba multipart parts (to najważniejsze!)
            connector.setProperty("maxParameterCount", "10000");
        });
    }
}
