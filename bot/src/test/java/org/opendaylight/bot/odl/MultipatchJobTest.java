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
import com.google.gerrit.extensions.common.RevisionInfo;
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
        MultipatchJob multipatchJob = new MultipatchJob(new Projects("p1", "p2", "p3"));
        assertThat(multipatchJob.getPatchesToBuildString(newArrayList(
                newChange("p3", NEW, "refs/changes/62/69362/30"),
                newChange("p2", NEW, "refs/changes/30/23973/48"),
                newChange("p3", NEW, "refs/changes/40/38973/60"))))
            .isEqualTo("p1,p2:30/23973/48,p3:62/69362/30:40/38973/60");
    }

    private static ChangeInfo newChange(String projectName, ChangeStatus status, String ref) {
        ChangeInfo change = new ChangeInfo();
        change.project = projectName;
        change.status = status;

        RevisionInfo revInfo = new RevisionInfo();
        revInfo.ref = ref;
        change.revisions = ImmutableMap.of(ref, revInfo);
        change.currentRevision = ref;

        return change;
    }
}
