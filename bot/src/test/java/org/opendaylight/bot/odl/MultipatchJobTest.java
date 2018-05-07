/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.odl;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.truth.Truth.assertThat;
import static com.google.gerrit.extensions.client.ChangeStatus.NEW;

import com.google.common.collect.ImmutableMap;
import com.google.gerrit.extensions.client.ChangeStatus;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.CommitInfo;
import com.google.gerrit.extensions.common.RevisionInfo;
import java.util.Collections;
import org.junit.Ignore;
import org.junit.Test;
import org.opendaylight.bot.BotException;
import org.opendaylight.bot.Projects;

/**
 * Unit test for {@link MultipatchJob}.
 *
 * @author Michael Vorburger.ch
 */
public class MultipatchJobTest {

    // TODO @Test public void testNoChanges() etc. ...

    @Test public void testFormat() throws BotException {
        MultipatchJob multipatchJob = new MultipatchJob(new Projects("p1", "p2", "p3", "p4"));
        assertThat(multipatchJob.getPatchesToBuildString(newArrayList(
                newChange("p4", NEW, "refs/changes/62/69362/30", "commit-sha1"),
                newChange("p2", NEW, "refs/changes/30/23973/48", "commit-sha2 ;)"),
                newChange("p4", NEW, "refs/changes/40/38973/60", "commit-sha3 ;)"))))
            .isEqualTo("p2:30/23973/48,p3,p4:62/69362/30:40/38973/60");
    }

    @Ignore // TODO https://jira.opendaylight.org/browse/RELENG-106
    @Test public void testParentDependencyOrdering() throws BotException {
        MultipatchJob multipatchJob = new MultipatchJob(new Projects("p1", "p2", "p3", "p4"));
        assertThat(multipatchJob.getPatchesToBuildString(newArrayList(
                newChange("p4", NEW, "refs/changes/62/69362/30", "d0d555ce4da5b908bb8a9571e8121376faac8662",
                        "bbc288cd2204da844b94e21fcc6e532d5c74f529"),
                newChange("p2", NEW, "refs/changes/30/23973/48", "7e27ffff30e05b9afa61e248f117d6c4f21c340a"),
                newChange("p4", NEW, "refs/changes/40/38973/60", "eec23848695fdac7c068e22ddce0ca781a215045",
                        "d0d555ce4da5b908bb8a9571e8121376faac8662"))))
            .isEqualTo("p2:30/23973/48,p3,p4:40/38973/60:62/69362/30");
    }

    private static ChangeInfo newChange(String projectName, ChangeStatus status, String ref, String commit) {
        ChangeInfo change = new ChangeInfo();
        change.project = projectName;
        change.status = status;

        // matching Gerrit.getCurrentRevisionReference()
        RevisionInfo revInfo = new RevisionInfo();
        revInfo.ref = ref;
        change.revisions = ImmutableMap.of(commit, revInfo);
        change.currentRevision = commit;

        return change;
    }

    private static ChangeInfo newChange(String projectName, ChangeStatus status, String ref,
            String commit, String parentCommit) {
        ChangeInfo newChange = newChange(projectName, status, ref, commit);
        newChange.currentRevision = commit; // matching in Gerrit.getCurrentCommit()
        // matching Gerrit.getCurrentParentCommit()
        CommitInfo currentCommitInfo = new CommitInfo();
        newChange.revisions.get(newChange.currentRevision).commit = currentCommitInfo;
        CommitInfo parentCommitInfo = new CommitInfo();
        currentCommitInfo.parents = Collections.singletonList(parentCommitInfo);
        parentCommitInfo.commit = parentCommit;
        return newChange;
    }

}
