/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;
import com.google.gerrit.extensions.common.ChangeInfo;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.inject.Inject;
import org.opendaylight.bot.gerrit.Gerrit;
import org.opendaylight.bot.odl.MultipatchJob;
import org.opendaylight.infrautils.utils.TablePrinter;

/**
 * Bot.
 *
 * @author Michael Vorburger.ch
 */
public class Bot {

    // private final Projects projects;
    private final Gerrit gerrit;
    private final MultipatchJob multipatchJob;

    @VisibleForTesting
    Bot(Gerrit gerrit, Projects projects) {
        // this.projects = projects;
        this.gerrit = gerrit;
        this.multipatchJob = new MultipatchJob(projects);
    }

    @Inject
    public Bot(BotConfiguration configuration) throws IOException {
        this(new Gerrit(configuration.gerritBase),
             // TODO remove hard-coded ODL project list and make configurable like Gerrit URL
             new Projects(Resources.readLines(Resources.getResource("odl/projects.txt"), StandardCharsets.US_ASCII)));
    }

    public String topic(String topicName) throws BotException {
        return getChangesAsTable(gerrit.allChangesOnTopic(topicName), gerrit.getHumanUI("topic:" + topicName) + " ");
    }

    private static String getChangesAsTable(List<ChangeInfo> changes, String title) {
        TablePrinter tablePrinter = new TablePrinter(0);
        tablePrinter.setTitle(title);
        // TODO add V[erified] vote information
        tablePrinter.setColumnNames("Status", "Change", "Mergeable?", "Subject", "Project", "Branch",
                "Current Rev Ref", "Commit", "Parent Commit");
        for (ChangeInfo change : changes) {
            tablePrinter.addRow(change.status, change._number, change.mergeable, change.subject, change.project,
                    change.branch, Gerrit.getCurrentRevisionReference(change),
                    Gerrit.getCurrentCommit(change), Gerrit.getCurrentParentCommit(change));
        }
        return tablePrinter.toString();
    }

    public String build(String topicName) throws BotException {
        List<ChangeInfo> changes = gerrit.allChangesOnTopic(topicName);
        ResultWithWarnings<String, String> build = multipatchJob.getPatchesToBuildString(changes);
        return getChangesAsTable(changes, "Build " + gerrit.getHumanUI("topic:" + topicName) + " ") + "\n"
                + build.getResult() + "\nWarnings: " + build.getWarnings() + "\ntopic=" + topicName;
    }

    // TODO List<Topic>
    void topics() {
    }

}
