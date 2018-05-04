/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.templates;

import java.util.Map;
import org.opendaylight.bot.util.Model;

/**
 * Set of multiple sub-templates which all share the same static properties.
 *
 * @author Michael Vorburger.ch
 */
public abstract class MultiTemplate implements Model<Object> {

    @Override
    public abstract Map<String, Object> getProperties();

    public class TemplateWithSameProperties extends Template {

        public TemplateWithSameProperties(String classpathResourceName) {
            super(classpathResourceName);
        }

        @Override
        public Map<String, Object> getProperties() {
            return MultiTemplate.this.getProperties();
        }
    }
}
