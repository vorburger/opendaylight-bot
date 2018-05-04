/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.email;

import org.opendaylight.bot.templates.MultiTemplate;
import org.opendaylight.bot.templates.Template;
import org.opendaylight.bot.templates.Templater;

/**
 * Template of a specific outgoing email type.
 *
 * @author Michael Vorburger.ch
 */
public abstract class EmailTemplate extends MultiTemplate {

    private final Template toTemplate;
    private final Template subjectTemplate;
    private final Template bodyTxtTemplate;
    // TODO bodyHtmlTemplate

    protected EmailTemplate(String emailMultiTemplateName) {
        toTemplate = new TemplateWithSameProperties("templates/email/" + emailMultiTemplateName + "/To");
        subjectTemplate = new TemplateWithSameProperties("templates/email/" + emailMultiTemplateName + "/Subject");
        bodyTxtTemplate = new TemplateWithSameProperties("templates/email/" + emailMultiTemplateName + "/Body.txt");
    }

    public String getTo(Templater templater) {
        return templater.run(toTemplate);
    }

    public String getSubject(Templater templater) {
        return templater.run(subjectTemplate);
    }

    public String getBodyAsText(Templater templater) {
        return templater.run(bodyTxtTemplate);
    }

    // TODO getBodyAsHTML
}
