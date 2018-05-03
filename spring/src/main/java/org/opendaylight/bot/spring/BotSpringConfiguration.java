/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.spring;

import org.opendaylight.bot.Bot;
import org.opendaylight.bot.spring.web.TopicServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring beans configuration.
 *
 * @author Michael Vorburger.ch
 */
@Configuration
public class BotSpringConfiguration {

    @Bean
    public Bot bot() throws Exception {
        return new Bot();
    }

    @Bean
    public ServletRegistrationBean<TopicServlet> exampleServletBean(Bot bot) {
        return new ServletRegistrationBean<>(new TopicServlet(bot), "/bot/*");
    }
}
