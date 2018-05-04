/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import static java.util.Objects.requireNonNull;
import static org.opendaylight.bot.util.Validate.requireNonNullOrEmpty;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.opendaylight.bot.email.EmailTemplate;

/**
 * Email template for a build failure on a managed Gerrit topic.
 *
 * @author Michael Vorburger.ch
 */
public class ManagedTopicBuildFailureEmailTemplate extends EmailTemplate {

    public BotConfiguration botConfiguration;

    public String gerritTopicName;
    public String mainJiraID;
    public String projectName;
    public String buildLogURL;
    // TODO private String buildLogFailure;

    public ManagedTopicBuildFailureEmailTemplate() {
        super("managed-topic-build-broken");
    }

    @Override
    public Map<String, Object> getProperties() {
        return ImmutableMap.of("mainJiraID", requireNonNullOrEmpty(mainJiraID, "mainJiraID"),
                               "gerritTopicName", requireNonNullOrEmpty(gerritTopicName, "gerritTopicName"),
                               "projectName", requireNonNullOrEmpty(projectName, "projectName"),
                               "buildLogURL", requireNonNullOrEmpty(buildLogURL, "buildLogURL"),
                               "botConfiguration", requireNonNull(botConfiguration, "botConfiguration")
                               // TODO "buildLogFailure", requireNonNullOrEmpty(buildLogFailure, "buildLogFailure")
                               );
    }

}
