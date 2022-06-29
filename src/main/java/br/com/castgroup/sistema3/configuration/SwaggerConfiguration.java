package br.com.castgroup.sistema3.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
	
	@Bean
	public Docket api () {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.castgroup"))
				.paths(PathSelectors.ant("/**"))
				.build().apiInfo(apiInfo());
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfo("API para controle e cadastro de Alunos, Professores e Turmas", "Sistema de API desenvolvido em SpringBoot com Hibernate e JPA",
				"vesão 1.0", "http://castgroup.com.br", 
				new Contact("Crud Sistema 3 CastGroup", 
						"http://castgroup.com.br", "contato@castgroup.com.br"), "Licença da API", 
				"Armstrong Bezerra",Collections.emptyList());
					
	}
}

