/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.templates.test;

import static com.google.common.truth.Truth.assertThat;

import java.util.Collections;
import java.util.Map;
import org.junit.Test;
import org.opendaylight.bot.templates.Template;
import org.opendaylight.bot.templates.Templater;
import org.opendaylight.bot.templates.impl.ThymeleafTemplater;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Unit test templates.
 *
 * @author Michael Vorburger.ch
 */
public class TemplatesTest {

    @Test(expected = IllegalArgumentException.class)
    public void testTemplateMissingResourceFails() {
        new Template("missing.html") {
            @Override
            public Map<String, Object> getVariablesMap() {
                return Collections.emptyMap();
            }
        };
    }

    @Test public void testTemplateEngine() {
        TestTemplate testTemplate = new TestTemplate();
        testTemplate.name = "world";

        ClassLoaderTemplateResolver thymeleafTemplateResolver = new ClassLoaderTemplateResolver();
        thymeleafTemplateResolver.setTemplateMode(TemplateMode.TEXT);

        TemplateEngine thymeleafTemplateEngine = new TemplateEngine();
        thymeleafTemplateEngine.setTemplateResolver(thymeleafTemplateResolver);

        Templater templater = new ThymeleafTemplater(thymeleafTemplateEngine);
        assertThat(templater.run(testTemplate)).isEqualTo("hello, world\n");
    }
}
