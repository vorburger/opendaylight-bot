/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import com.google.gerrit.extensions.client.ChangeStatus;
import com.google.gerrit.extensions.common.ChangeInfo;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.opendaylight.bot.gerrit.Gerrit;
import org.opendaylight.infrautils.utils.TablePrinter;

/**
 * Bot.
 *
 * @author Michael Vorburger.ch
 */
@SuppressWarnings("checkstyle:RegexpSingleLineJava") // System out
class Bot {

    private final Projects projects;
    private final Gerrit gerrit;

    Bot(Gerrit gerrit, Projects projects) {
        this.projects = projects;
        this.gerrit = gerrit;
    }

    void topic(String topicName) throws BotException {
        printChanges(gerrit.allChangesOnTopic(topicName), gerrit.getBaseURI() + "#/q/topic:" + topicName + " ");
    }

    private void printChanges(List<ChangeInfo> changes, String title) {
        TablePrinter tablePrinter = new TablePrinter(0);
        tablePrinter.setTitle(title);
        // TODO add V[erified] vote information
        tablePrinter.setColumnNames("Status", "Change", "Mergeable?", "Subject", "Project", "Branch",
                "Current Rev Ref");
        for (ChangeInfo change : changes) {
            tablePrinter.addRow(change.status, change._number, change.mergeable, change.subject, change.project,
                    change.branch, getCurrentRevisionReference(change));
        }
        System.out.println(tablePrinter.toString());
    }

    void build(String topicName) throws BotException {
        List<ChangeInfo> changes = gerrit.allChangesOnTopic(topicName);
        printChanges(changes, "Topic to be built " + topicName);

        // TODO why NPE for topicName = CONTROLLER-1802 with 3x MERGED and 2x NEW?
        // if (changes.stream().filter(change -> !change.mergeable).findFirst().isPresent()) {
        //    System.err.println("There are un-mergeable changes with conflicts which need to be manually resolved!");
        // }

        // TODO check for +1 Verified Vote, and abort if not present

        Map<String, List<String>> map = projects.getNewMultimap();

        List<String> unknownProjects = changes.stream()
                .filter(change -> !map.containsKey(change.project))
                .map(change -> change.project).distinct().collect(Collectors.toList());
        if (!unknownProjects.isEmpty()) {
            throw new BotException("Unknown projects: " + unknownProjects);
        }

        changes.stream()
            .filter(change -> change.status.equals(ChangeStatus.NEW))
            .forEach(change -> map.get(change.project).add(getCurrentRevisionReference(change)));

        StringBuilder patchesToBuild = new StringBuilder();
        map.forEach((project, refs) -> {
            if (patchesToBuild.length() > 0) {
                patchesToBuild.append(",");
            }
            patchesToBuild.append(project);
            if (!refs.isEmpty()) {
                patchesToBuild.append(':');
                Iterator<String> iterator = refs.iterator();
                patchesToBuild.append(iterator.next());
                while (iterator.hasNext()) {
                    patchesToBuild.append(",");
                    patchesToBuild.append(project);
                    patchesToBuild.append(':');
                    patchesToBuild.append(iterator.next());
                }
            }
        });
        System.out.println(patchesToBuild.toString());
    }

    // TODO List<Topic>
    void topics() {
    }

    private String getCurrentRevisionReference(ChangeInfo change) {
        return change.revisions.get(change.currentRevision).ref;
    }

}
