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

import org.junit.Ignore;
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

    private static String op = System.getProperty("os.name").toLowerCase();

    @Test(expected = IllegalArgumentException.class)
    public void testTemplateMissingResourceFails() {
        new Template("missing.html") {
            @Override
            public Map<String, Object> getProperties() {
                return Collections.emptyMap();
            }
        };
    }

    @Test
    public void testTemplateEngine() {
        TestTemplate testTemplate = new TestTemplate();
        testTemplate.name = "world";
        if (op.contains("windows")) {
            assertThat(getTemplater().run(testTemplate)).isEqualTo("hello, world\r\n");
        } else {
            assertThat(getTemplater().run(testTemplate)).isEqualTo("hello, world\n");
        }

    }

    @Ignore // TODO how to make this fail?
    @Test
    public void testBrokenTemplate() {
        getTemplater().run(new Template("templates/test/test-template-missing-property.txt") {
            @Override
            public Map<String, Object> getProperties() {
                return Collections.emptyMap();
            }
        });
    }

    private static Templater getTemplater() {
        ClassLoaderTemplateResolver thymeleafTemplateResolver = new ClassLoaderTemplateResolver();
        thymeleafTemplateResolver.setTemplateMode(TemplateMode.TEXT);

        TemplateEngine thymeleafTemplateEngine = new TemplateEngine();
        thymeleafTemplateEngine.setTemplateResolver(thymeleafTemplateResolver);

        return new ThymeleafTemplater(thymeleafTemplateEngine);
    }
}
