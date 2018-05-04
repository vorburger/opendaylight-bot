/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.odl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.net.URI;
import org.opendaylight.bot.BotConfiguration;

/**
 * {@link BotConfiguration} for <a href="https://www.opendaylight.org">OpenDaylight.org</a>.
 * @author Michael Vorburger.ch
 */
@SuppressFBWarnings({"URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD"})
public final class OpenDaylightConfigurer {

    private OpenDaylightConfigurer() { }

    public static BotConfiguration getConfiguration() {
        BotConfiguration configuration = new BotConfiguration();
        configuration.gerritBase = URI.create("https://git.opendaylight.org/gerrit/");
        configuration.jiraBase = URI.create("https://jira.opendaylight.org/");
        configuration.jenkinsBase = URI.create("https://jenkins.opendaylight.org/");

        return configuration;
    }
}
