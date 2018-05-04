/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.templates.test;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.opendaylight.bot.templates.Template;

public class TestTemplate extends Template {

    public String name;

    public TestTemplate() {
        super("templates/test/test-template.txt");
    }

    @Override
    public Map<String, Object> getProperties() {
        return ImmutableMap.of("name", name);
    }

}
