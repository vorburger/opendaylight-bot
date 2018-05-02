/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.odl;

import com.google.gerrit.extensions.client.ChangeStatus;
import com.google.gerrit.extensions.common.ChangeInfo;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.opendaylight.bot.BotException;
import org.opendaylight.bot.Projects;
import org.opendaylight.bot.gerrit.Gerrit;

/**
 * Utilities related to the OpenDaylight integration-multipatch-test job.
 *
 * @author Michael Vorburger.ch
 */
public class MultipatchJob {

    private static final String REF_CHANGES_PREFIX = "refs/changes/";

    private final Projects projects;

    public MultipatchJob(Projects projects) {
        this.projects = projects;
    }

    /**
     * Constructs the String used in the PATCHES_TO_BUILD parameter of an
     * OpenDaylight integration-multipatch-test job.
     */
    public String getPatchesToBuildString(Collection<ChangeInfo> changes) throws BotException {
        // TODO why NPE for topicName = CONTROLLER-1802 with 3x MERGED and 2x NEW?
        // if (changes.stream().filter(change -> !change.mergeable).findFirst().isPresent()) {
        //    throw new BotException("Changes with conflicts which need to be manually resolved!");
        // }

        // TODO check for +1 Verified Vote, and abort if not present

        Map<String, List<String>> map1 = projects.getNewMultimap();

        List<String> unknownProjects = changes.stream()
                .filter(change -> !map1.containsKey(change.project))
                .map(change -> change.project).distinct().collect(Collectors.toList());
        if (!unknownProjects.isEmpty()) {
            throw new BotException("Unknown projects: " + unknownProjects);
        }

        changes.stream()
            .filter(change -> change.status.equals(ChangeStatus.NEW))
            .forEach(change -> map1.get(change.project).add(Gerrit.getCurrentRevisionReference(change)));

        Map<String, List<String>> map2 = new LinkedHashMap<>(map1); // must preserve order!
        for (Map.Entry<String, List<String>> entry : map1.entrySet()) {
            if (entry.getValue().isEmpty()) {
                map2.remove(entry.getKey());
            } else {
                break;
            }
        }

        StringBuilder patchesToBuild = new StringBuilder();
        map2.forEach((project, refs) -> {
            if (patchesToBuild.length() > 0) {
                patchesToBuild.append(",");
            }
            patchesToBuild.append(project);
            if (!refs.isEmpty()) {
                patchesToBuild.append(':');
                Iterator<String> iterator = refs.iterator();
                patchesToBuild.append(iterator.next().substring(REF_CHANGES_PREFIX.length()));
                while (iterator.hasNext()) {
                    patchesToBuild.append(':');
                    patchesToBuild.append(iterator.next().substring(REF_CHANGES_PREFIX.length()));
                }
            }
        });

        return patchesToBuild.toString();
    }

}
