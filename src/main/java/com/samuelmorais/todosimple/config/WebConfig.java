package com.samuelmorais.todosimple.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Essa é uma interface uitlizada para configurar a comunicação do front cm o 
// back end em spring
@Configuration  // afirma que esta é uma classe de configuracao
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    // O cors é a configuração que permite que IPs diferentes facam requisicoes à nossa api
    public void addCorsMappings(CorsRegistry registry) {
        // Estamos permitindo a API seja acessada por qualquer caminho
        registry.addMapping("/**");
    }
}