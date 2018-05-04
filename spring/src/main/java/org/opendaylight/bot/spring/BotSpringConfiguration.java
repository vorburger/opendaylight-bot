/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.spring;

import org.opendaylight.bot.Bot;
import org.opendaylight.bot.BotConfiguration;
import org.opendaylight.bot.odl.OpenDaylightConfigurer;
import org.opendaylight.bot.spring.web.TopicServlet;
import org.opendaylight.bot.templates.Templater;
import org.opendaylight.bot.templates.impl.ThymeleafTemplater;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Spring beans configuration.
 *
 * @author Michael Vorburger.ch
 */
@Configuration
public class BotSpringConfiguration {

    @Bean public BotConfiguration configuration() {
        return OpenDaylightConfigurer.getConfiguration();
    }

    @Bean public Bot bot(BotConfiguration config) throws Exception {
        return new Bot(config);
    }

    @Bean public Templater templater() {
        return new ThymeleafTemplater(newTemplateEngine());
    }

    private static ITemplateEngine newTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Resolver for TEXT emails
        templateEngine.addTemplateResolver(textTemplateResolver());
        return templateEngine;
    }

    private static ITemplateResolver textTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(1));
//        templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
//        templateResolver.setPrefix("/mail/");
//        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public ServletRegistrationBean<TopicServlet> exampleServletBean(Bot bot) {
        return new ServletRegistrationBean<>(new TopicServlet(bot), "/bot/*");
    }
}
