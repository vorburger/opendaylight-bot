/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.jenkins;

import com.cdancy.jenkins.rest.JenkinsClient;

/**
* Template of Initial Jenkins Client API.
* @author Prateek Chanda
*/

public class Client {

    public String folderPath = "https://jenkins.opendaylight.org/releng";

    // credentialIds  must take the form of "username:password" or base64 encoded version.
    public void connect(String credentialIds) {

        JenkinsClient client = JenkinsClient.builder()
            .endPoint(folderPath)
            .credentials(credentialIds)
            .build();
    }

    public void buildJob(String jobName) {

        private String buildPath = folderPath + "/job" + jobName  + "/buildWithParameters";
        JenkinsApi jenkinsApi;
        JobsApi api = jenkinsApi.jobsApi();
        IntegerResponse output = api.buildWithParameters(null, buildPath);
        jenkinsApi.close();
    }
}
