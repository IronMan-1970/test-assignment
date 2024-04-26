package org.antihetman.testassignment.configuration;/*
  @author   antihetman
  @project   test-assignment
  @class  SwaggerConfiguration
  @version  1.0.0 
  @since 4/26/24 - 20.02
*/

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info
                (
                        title = "Documentation for test assignment",
                        description = "Swagger Module",
                        version = "1.0",
                        contact = @Contact(
                                name ="Pavlo-Maksymyliano",
                                email = "umaks6859@gmail.com",
                                url = "https://www.linkedin.com/in/maksym-havryliuk-a2805b2a2/"
                        )
                )
)
public class SwaggerConfiguration {
}
