/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.jenkins;

import com.google.common.collect.ImmutableMap;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.JobWithDetails;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.net.URI;

import org.opendaylight.bot.BotConfiguration;

/**
 * Template of Initial Jenkins Client API.
 *
 * @author Prateek Chanda
 */
@SuppressFBWarnings({"UWF_UNWRITTEN_FIELD", "DLS_DEAD_LOCAL_STORE"})
public class Client {

    private String login = System.getenv("MY_UID");
    private String passwd = System.getenv("MY_PASSWD");

    public static BotConfiguration getConfiguration(String admin, String password) {

        BotConfiguration configuration = new BotConfiguration();
        configuration.jenkinsBase = URI.create("https://jenkins.opendaylight.org/");
        configuration.jenkinsLogin = admin;
        configuration.jenkinsPassword = password;
        return configuration;
    }

    void buildJob(String parameterJob, String jobName) throws IOException {

        URI linkname = getConfiguration(login, passwd).jenkinsBase;
        JenkinsServer jenkninsServer = new JenkinsServer(linkname, getConfiguration(login, passwd).jenkinsLogin,
                getConfiguration(login, passwd).jenkinsPassword);
        // Build with Parameters
        ImmutableMap<String, String> params = ImmutableMap.of(parameterJob, "master");
        JobWithDetails job = jenkninsServer.getJob(jobName);
        job.build(params);
        // check for last build
        Build lastBuild = job.getLastBuild();
        // check last successful build
        Build buildLastSuccessful = job.getLastSuccessfulBuild();
    }
}