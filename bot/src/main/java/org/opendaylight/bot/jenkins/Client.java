/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.jenkins;

import com.google.common.collect.Lists;

import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.MockResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cdancy.jenkins.rest.JenkinsClient;

/**
* Template of Initial Jenkins Client API.
* @author Prateek Chanda
*/

public class Client {

    public void connect() {
    
        JenkinsClient client = JenkinsClient.builder()
            .endPoint("http://127.0.0.1:8080")
            .credentials("admin:password") 
            .build();
    }
    
    public void Buildwithparameters() {
   
        MockWebServer server = mockWebServer();

        server.enqueue(
              new MockResponse().setHeader("Location", "http://127.0.1.1:8080/job/item/1/").setResponseCode(201));
        JenkinsApi jenkinsApi = api(server.getUrl("/"));
        JobsApi api = jenkinsApi.jobsApi();
      
        Map<String, List<String>> params = new HashMap<>();
        params.put("Key", Lists.newArrayList("KeyValue"));
        IntegerResponse output = api.buildWithParameters(null, "JobName", params);
      
        jenkinsApi.close();
        server.shutdown();
    
    }
      
}
