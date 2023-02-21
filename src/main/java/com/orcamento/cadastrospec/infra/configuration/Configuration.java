package com.orcamento.cadastrospec.infra.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import static com.orcamento.cadastrospec.constants.AppConstants.Configuration.PATH_MESSAGES;
import static com.orcamento.cadastrospec.constants.AppConstants.Configuration.UTF_8;

@Component
public class Configuration {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(PATH_MESSAGES);
        messageSource.setDefaultEncoding(UTF_8);
        return messageSource;
    }
}
