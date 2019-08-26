package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;

import lombok.val;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.webflow.config.FlowBuilderServicesBuilder;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * This is {@link CasCommandLineShellConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
@Configuration("casCommandLineShellConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CasCommandLineShellConfiguration {

    @Bean
    public PromptProvider shellPromptProvider() {
        return () -> new AttributedString("cas>", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

    /**
     * Mock bean to satisfy autowire without adding webflow module.
     * @return default FlowBuilderServices
     */
    @Lazy(false)
    @Bean
    public FlowBuilderServices builder() {
        val builder = new FlowBuilderServicesBuilder();
        return builder.build();
    }
}
