/*
Initial Jenkins Client API 
*/

package org.opendaylight.bot.jenkins;

import com.cdancy.jenkins.rest.JenkinsClient;

public class Client {

  JenkinsClient client = JenkinsClient.builder()
  .endPoint("http://127.0.0.1:8080")
  .credentials("admin:password") 
  .build();
  
}
