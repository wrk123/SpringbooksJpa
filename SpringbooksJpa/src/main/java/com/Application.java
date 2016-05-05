package com;


import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
      MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
      HibernateAwareObjectMapper hibernateAwareObjectMapper = new HibernateAwareObjectMapper();
      jsonConverter.setObjectMapper(hibernateAwareObjectMapper);
      return jsonConverter;
  }

  //@Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      converters.add(customJackson2HttpMessageConverter());
      //super.addDefaultHttpMessageConverters(converters);
  }
}
