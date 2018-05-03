/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.templates;

import static java.util.Locale.ENGLISH;

import java.util.Locale;

/**
 * Service which renders {@link Template}s.
 *
 * @author Michael Vorburger.ch
 */
public interface Templater {

    String run(Template template, Locale locale);

    default String run(Template template) {
        return run(template, ENGLISH);
    }

}
