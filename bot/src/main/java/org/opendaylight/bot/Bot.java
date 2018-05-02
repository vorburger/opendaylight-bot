/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import com.google.gerrit.extensions.common.ChangeInfo;
import java.util.List;
import org.opendaylight.bot.gerrit.Gerrit;
import org.opendaylight.bot.odl.MultipatchJob;
import org.opendaylight.infrautils.utils.TablePrinter;

/**
 * Bot.
 *
 * @author Michael Vorburger.ch
 */
@SuppressWarnings("checkstyle:RegexpSingleLineJava") // System out
class Bot {

    // private final Projects projects;
    private final Gerrit gerrit;
    private final MultipatchJob multipatchJob;

    Bot(Gerrit gerrit, Projects projects) {
        // this.projects = projects;
        this.gerrit = gerrit;
        this.multipatchJob = new MultipatchJob(projects);
    }

    void topic(String topicName) throws BotException {
        printChanges(gerrit.allChangesOnTopic(topicName), gerrit.getBaseURI() + "#/q/topic:" + topicName + " ");
    }

    private static void printChanges(List<ChangeInfo> changes, String title) {
        TablePrinter tablePrinter = new TablePrinter(0);
        tablePrinter.setTitle(title);
        // TODO add V[erified] vote information
        tablePrinter.setColumnNames("Status", "Change", "Mergeable?", "Subject", "Project", "Branch",
                "Current Rev Ref");
        for (ChangeInfo change : changes) {
            tablePrinter.addRow(change.status, change._number, change.mergeable, change.subject, change.project,
                    change.branch, Gerrit.getCurrentRevisionReference(change));
        }
        System.out.println(tablePrinter.toString());
    }

    void build(String topicName) throws BotException {
        List<ChangeInfo> changes = gerrit.allChangesOnTopic(topicName);
        printChanges(changes, "Build " + gerrit.getBaseURI() + "#/q/topic:" + topicName + " ");
        System.out.println(multipatchJob.getPatchesToBuildString(changes));
    }

    // TODO List<Topic>
    void topics() {
    }

}
