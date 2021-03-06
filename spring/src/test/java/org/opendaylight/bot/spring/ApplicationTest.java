/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.spring;

import static com.google.common.truth.Truth.assertThat;

import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Inject TestRestTemplate restTemplate;

    @Test public void contextLoads() {
    }

    @Test public void httpGetRootHomepage() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test public void httpGetSlashBotUsage() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/bot", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).startsWith("USAGE");
    }

    @Test public void httpGetTopic() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/bot?topic=CONTROLLER-1802", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test public void httpGetEmail() {
        ResponseEntity<String> entity = this.restTemplate
                .getForEntity("/email/managed-topic-build-broken/TSC-99/binding-tlc-rpc/lispflowmapping?buildLogURL="
            + "https%3A%2F%2Fjenkins.opendaylight.org%2Freleng%2Fjob%2Fintegration-multipatch-test-fluorine%2F27",
            String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
