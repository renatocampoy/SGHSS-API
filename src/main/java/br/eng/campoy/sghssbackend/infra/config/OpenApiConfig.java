package br.eng.campoy.sghssbackend.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração da documentação OpenAPI (Swagger) para a API do Sistema de Gestão Hospitalar e de Serviços de Saúde (SGHSS).
 * Inclui segurança via JWT, metadados detalhados e servidores para diferentes ambientes.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Definição dos servidores disponíveis
                .addServersItem(new Server()
                        .url("http://localhost:8088/api")
                        .description("Servidor de Desenvolvimento"))
                .addServersItem(new Server()
                        .url("https://sghss-api-prod.com/api")
                        .description("Servidor de Produção"))
                // Configuração de autenticação via JWT
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Autenticação JWT para acessar a API")))
                // Informações detalhadas da API
                .info(new Info()
                        .title("SGHSS API - Sistema de Gestão Hospitalar e de Serviços de Saúde")
                        .version("1.0.0")
                        .description("API para gestão de hospitais, clínicas e serviços de saúde. "
                                + "Fornece funcionalidades para cadastro de pacientes, agendamentos, "
                                + "telemedicina, prontuários eletrônicos e auditoria. Segue padrões "
                                + "de segurança e compliance com a LGPD.")
                        .contact(new Contact()
                                .name("Renato Campoy")
                                .email("renato@campoy.eng.br")
                                .url("https://campoy.eng.br"))
                        .license(new License()
                                .name("Licença MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}