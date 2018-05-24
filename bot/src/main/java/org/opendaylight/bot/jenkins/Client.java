/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.jenkins;

import com.cdancy.jenkins.rest.JenkinsClient;

import java.net.URI;

import org.opendaylight.bot.BotConfiguration;

/**
* Template of Initial Jenkins Client API.
* @author Prateek Chanda
*/

public class Client {

    private final BotConfiguration configuration;
    // credentialIds  must take the form of "username:password" or base64 encoded version.
    
    public static void buildJob(String credentialsId, String jobName) {

        configuration.jenkinsBase = URI.create("https://jenkins.opendaylight.org/");
        configuration.jobName = jobName;
        configuration.credentialsId = credentialsId;

        String buildPath = configuration.jenkinsBase.toString() + "/job" + configuration.jobName  
            + "/buildWithParameters";

        JenkinsClient client = JenkinsClient.builder()
                .endPoint(configuration.jenkinsBase)
                .credentials(configuration.credentialsId)
                .build();

        JenkinsApi jenkinsApi;
        JobsApi api = jenkinsApi.jobsApi();
        IntegerResponse output = api.buildWithParameters(null, buildPath);
        jenkinsApi.close();
    }
}
