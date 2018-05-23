

package org.opendaylight.bot.jenkins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import com.cdancy.jenkins.rest.JenkinsClient;

import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.MockResponse;


/**
* Initial Jenkins Client API 
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
