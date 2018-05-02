/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import com.google.gerrit.extensions.common.ChangeInfo;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
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

    Bot(Gerrit gerrit, Projects projects) {
        // this.projects = projects;
        this.gerrit = gerrit;
        this.multipatchJob = new MultipatchJob(projects);
    }

    public Bot() throws IOException {
        this(new Gerrit(URI.create("https://git.opendaylight.org/gerrit/")), new Projects(new File("projects.txt")));
    }

    String topic(String topicName) throws BotException {
        return getChangesAsTable(gerrit.allChangesOnTopic(topicName),
                gerrit.getBaseURI() + "#/q/topic:" + topicName + " ");
    }

    private static String getChangesAsTable(List<ChangeInfo> changes, String title) {
        TablePrinter tablePrinter = new TablePrinter(0);
        tablePrinter.setTitle(title);
        // TODO add V[erified] vote information
        tablePrinter.setColumnNames("Status", "Change", "Mergeable?", "Subject", "Project", "Branch",
                "Current Rev Ref", "Commit", "Parent/s");
        for (ChangeInfo change : changes) {
            tablePrinter.addRow(change.status, change._number, change.mergeable, change.subject, change.project,
                    change.branch, Gerrit.getCurrentRevisionReference(change), Gerrit.getCurrentCommit(change),
                    Gerrit.getCurrentParentCommit(change));
        }
        return tablePrinter.toString();
    }

    String build(String topicName) throws BotException {
        List<ChangeInfo> changes = gerrit.allChangesOnTopic(topicName);
        return getChangesAsTable(changes, "Build " + gerrit.getBaseURI() + "#/q/topic:" + topicName + " ") + "\n"
                + multipatchJob.getPatchesToBuildString(changes);
    }

    // TODO List<Topic>
    void topics() {
    }

}
