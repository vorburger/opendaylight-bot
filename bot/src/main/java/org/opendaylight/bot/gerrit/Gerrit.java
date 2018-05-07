/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.gerrit;

import static com.google.gerrit.extensions.client.ListChangesOption.CURRENT_COMMIT;
import static com.google.gerrit.extensions.client.ListChangesOption.CURRENT_REVISION;

import com.google.common.base.MoreObjects;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.CommitInfo;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.urswolfer.gerrit.client.rest.GerritAuthData;
import com.urswolfer.gerrit.client.rest.GerritRestApi;
import com.urswolfer.gerrit.client.rest.GerritRestApiFactory;
import java.net.URI;
import java.util.List;
import org.opendaylight.bot.BotException;

/**
 * Gerrit client.
 *
 * @author Michael Vorburger.ch
 */
public class Gerrit {

    private final URI gerritBaseURI;
    private final GerritRestApi gerritApi;

    public Gerrit(URI gerritBaseURI) {
        this.gerritBaseURI = gerritBaseURI;
        GerritRestApiFactory gerritRestApiFactory = new GerritRestApiFactory();
        GerritAuthData.Basic authData = new GerritAuthData.Basic(gerritBaseURI.toString());
        // or: authData = new GerritAuthData.Basic("https://example.com/gerrit", "user", "password");
        gerritApi = gerritRestApiFactory.create(authData);
    }

    public List<ChangeInfo> allChangesOnTopic(String topicName) throws BotException {
        String query = "topic:" + topicName;
        try {
            return gerritApi.changes().query(query).withOptions(CURRENT_COMMIT, CURRENT_REVISION).get();
        } catch (RestApiException e) {
            throw new BotException(gerritBaseURI + " query(" + query + ") failed", e);
        }
    }

    public URI getBaseURI() {
        return gerritBaseURI;
    }

    public URI getHumanUI(String query) {
        return URI.create(getBaseURI() + "#/q/" + query);
    }

    public static String getCurrentRevisionReference(ChangeInfo change) {
        return change.revisions.get(change.currentRevision).ref;
    }

    public static String getCurrentCommit(ChangeInfo change) {
        return change.currentRevision;
    }

    public static String getCurrentParentCommit(ChangeInfo change) {
        List<CommitInfo> parents = change.revisions.get(change.currentRevision).commit.parents;
        if (parents.size() == 1) {
            return parents.get(0).commit;
        } else {
            throw new IllegalArgumentException("Change has no or more than 1 parent: " + toString(change));
        }
    }

    public static String toString(ChangeInfo change) {
        return MoreObjects.toStringHelper(change)
                .add("number", change._number).add("subject", change.subject)
                .toString();
    }
}
