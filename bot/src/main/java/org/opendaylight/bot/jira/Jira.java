/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Jira client.
 *
 * @author Ivange Larry
 */
public class Jira {

    private final Log log = LogFactory.getLog(Jira.class);

    private String jiraURI;
    private String username;
    private String password;

    private JiraRestClient jiraRestClient;

    public Jira(String jiraURI, String username, String password) {
        this.jiraURI = jiraURI;
        this.username = username;
        this.password = password;
        this.jiraRestClient = getJiraRestClient();
    }

    private JiraRestClient getJiraRestClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(getJiraUri(), username, password);
    }

    private URI getJiraUri() {
        return URI.create(this.jiraURI);
    }

    public List<Issue> search(String jql) {
        SearchRestClient searchRestClient = jiraRestClient.getSearchClient();
        List<Issue> issues = new ArrayList<>();
        searchRestClient.searchJql(jql)
                .done(result -> result.getIssues().forEach(issue -> issues.add(issue)))
                .fail(err -> log.error(err)).claim();
        return issues;
    }
}
