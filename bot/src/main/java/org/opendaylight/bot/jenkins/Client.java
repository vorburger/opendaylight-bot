/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.jenkins;

import com.cdancy.jenkins.rest.JenkinsClient;

import com.google.common.collect.Lists;

/**
* Template of Initial Jenkins Client API.
* @author Prateek Chanda
*/

public class Client {
    
    public String FolderPath = "https://jenkins.opendaylight.org/releng";
    
    // CredentialIds  must take the form of "username:password" or base64 encoded version.
    public void connect(String CredentialIds) {
        JenkinsClient client = JenkinsClient.builder()
            .endPoint(FolderPath)
            .credentials(CredentialIds)
            .build();
    }
    
    public void buildJob(String JobName) {
        
        private String BuildPath = FolderPath + "/job" + JobName  + "/buildWithParameters";
        JenkinsApi jenkinsApi;
        JobsApi api = jenkinsApi.jobsApi();
        IntegerResponse output = api.buildWithParameters(null, BuildPath);
        jenkinsApi.close();        
    }
}
