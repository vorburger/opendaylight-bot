/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.spring.web;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Locale;
import javax.inject.Inject;
import org.opendaylight.bot.BotConfiguration;
import org.opendaylight.bot.ManagedTopicBuildFailureEmailTemplate;
import org.opendaylight.bot.templates.Templater;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Bot's Servlet.
 *
 * @author Michael Vorburger.ch
 */
@RestController
public class ManagedTopicBuildFailureEmailController {

    // private static final Logger LOG = LoggerFactory.getLogger(ManagedTopicBuildFailureEmailController.class);

    private final Templater templater;
    private final BotConfiguration botConfiguration;

    @Inject
    public ManagedTopicBuildFailureEmailController(BotConfiguration botConfiguration, Templater templater) {
        this.botConfiguration = botConfiguration;
        this.templater = templater;
    }

    @RequestMapping(value = "/email/managed-topic-build-broken/{mainJiraID}/"
            + "{gerritTopicName}/{projectName}", produces = "text/plain")
    // NB: buildLogURL is an (encoded) ?buildLogURL=https%3A%2F%2Fjenkins... request parameter, NOT a @PathVariabl
    String handle(Locale locale, @PathVariable String gerritTopicName, @PathVariable String mainJiraID,
            @PathVariable String projectName, URI buildLogURL) {
        // TODO pass through the Locale
        ManagedTopicBuildFailureEmailTemplate template = new ManagedTopicBuildFailureEmailTemplate();
        template.botConfiguration = botConfiguration;
        template.gerritTopicName = gerritTopicName;
        template.mainJiraID = mainJiraID;
        template.projectName = projectName;
        template.buildLogURL = buildLogURL.toString();

        StringBuilder sb = new StringBuilder();
        sb.append("TO: ");
        sb.append(template.getTo(templater));
        sb.append("SUBJECT: ");
        sb.append(template.getSubject(templater));
        sb.append("\n");
        sb.append(template.getBodyAsText(templater));
        return sb.toString();
    }

    // used in index.html because FORM can't compose nice REST URL
    @RequestMapping("/email/managed-topic-build-broken")
    RedirectView redirect(String gerritTopicName, String mainJiraID, String projectName, String buildLogURL)
            throws UnsupportedEncodingException {
        return new RedirectView("/email/managed-topic-build-broken/" + mainJiraID + "/" + gerritTopicName + "/"
                + projectName + "?buildLogURL=" + URLEncoder.encode(buildLogURL, "UTF-8"));
    }

}
