

package org.opendaylight.bot.jenkins;

import com.cdancy.jenkins.rest.JenkinsClient;

/**
* Initial Jenkins Client API 
*/
public class Client {
  public static void main(String []args) {
    JenkinsClient client = JenkinsClient.builder()
    .endPoint("http://127.0.0.1:8080")
    .credentials("admin:password") 
    .build();
    
  }
  
}
