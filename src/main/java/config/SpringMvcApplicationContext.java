package config;

import ga.rugal.demo.springmvc.controller.PackageInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Java based Web context configuration class. Including argument resolution, message converter,
 * view resolution etc.,
 *
 * @author Rugal Bernstein
 * @since 0.2
 */
@ComponentScan(basePackageClasses = PackageInfo.class)
@Configuration
@EnableWebMvc
public class SpringMvcApplicationContext implements WebMvcConfigurer {

  private static final String SWAGGER_BASE_PATH = "/blueprints";

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController(SWAGGER_BASE_PATH + "/v2/api-docs",
                                       "/v2/api-docs");
    registry.addRedirectViewController(SWAGGER_BASE_PATH + "/configuration/ui",
                                       "/configuration/ui");
    registry.addRedirectViewController(SWAGGER_BASE_PATH + "/configuration/security",
                                       "/configuration/security");
    registry.addRedirectViewController(SWAGGER_BASE_PATH + "/swagger-resources",
                                       "/swagger-resources");
    registry.addRedirectViewController(SWAGGER_BASE_PATH,
                                       SWAGGER_BASE_PATH + "/swagger-ui.html");
    registry.addRedirectViewController(SWAGGER_BASE_PATH + "/",
                                       SWAGGER_BASE_PATH + "/swagger-ui.html");
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**", SWAGGER_BASE_PATH + "/**")
            .addResourceLocations("classpath:/static/",
                                  "classpath:/META-INF/resources/",
                                  "classpath:/resources/ ",
                                  "classpath:/public/");
  }

  @Override
  public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false).favorParameter(false);
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }

  /**
   * Handler mapping.
   *
   * @return handler mapping object
   */
  @Bean
  public AbstractHandlerMapping defaultAnnotationHandlerMapping() {
    final RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
    mapping.setUseSuffixPatternMatch(false);
    return mapping;
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*");
  }
}
