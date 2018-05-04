/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.templates.impl;

import java.util.Locale;
import java.util.Map;
import org.opendaylight.bot.templates.Template;
import org.opendaylight.bot.templates.Templater;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

/**
 * Templater implementation using
 * <a href="https://www.thymeleaf.org">Thymeleaf</a>.
 *
 * @author Michael Vorburger.ch
 */
public class ThymeleafTemplater implements Templater {

    private final ITemplateEngine templateEngine;

    public ThymeleafTemplater(ITemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String run(Template template, Locale locale) {
        String templateName = template.getResourceName();
        Map<String, Object> variables = template.getProperties();
        IContext context = new Context(locale, variables);
        return templateEngine.process(templateName, context);
    }

}
