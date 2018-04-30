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
import org.opendaylight.infrautils.utils.TablePrinter;

/**
 * Bot.
 *
 * @author Michael Vorburger.ch
 */
@SuppressWarnings("checkstyle:RegexpSingleLineJava") // System.out
class Bot {

    private final Gerrit gerrit;

    Bot(Gerrit gerrit) {
        this.gerrit = gerrit;
    }

    // TODO instead of System.out here, return a model, and move TablePrinter into MainCLI
    void topic(String topicName) throws BotException {
        List<ChangeInfo> changes = gerrit.allChangesOnTopic("CONTROLLER-1802");

        TablePrinter tablePrinter = new TablePrinter(0);
        tablePrinter.setTitle("Topic " + topicName);
        tablePrinter.setColumnNames("Status", "Change", "Mergeable?", "Subject", "Project", "Branch");
        for (ChangeInfo change : changes) {
            tablePrinter.addRow(change.status, change._number, change.mergeable, change.subject, change.project,
                    change.branch);
        }
        System.out.println(tablePrinter.toString());
    }

    void build(String topicName) throws BotException {
        // ORDER Topics...
        // TODO Print multi job build String
    }

    // TODO List<Topic>
    void topics() {
    }

}
