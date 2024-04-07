package br.com.tiviati.sct.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Admin Tivia",
                        email = "admin@tiviati.com.br",
                        url = "http://www.tiviati.com.br/"
                ),
                description = "Todos valores aqui são fictícios, a fim apenas de demonstrar como documentar API's.",
                title = "OpenApi Tivia TI",
                version = "1.0",
                license = @License(
                        name = "Todos direitos reservados.",
                        url = "http://www.tiviati.com.br/"
                ),
                termsOfService = "Termos do serviço"
        ),
        servers = {
                @Server(
                        description = "Local - Provas de conceito (PoC).",
                        url = "http://localhost:8080/tiviati/"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://fakeapi.foo.tiviati.com.br:8080/tiviati/"
                )
        }
)
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "X-API-KEY", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}
