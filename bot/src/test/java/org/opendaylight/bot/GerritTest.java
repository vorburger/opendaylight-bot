/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import static com.google.common.truth.Truth.assertThat;

import com.google.gerrit.extensions.common.ChangeInfo;
import java.net.URI;
import java.util.List;
import org.junit.Test;
import org.opendaylight.bot.gerrit.Gerrit;
import org.opendaylight.infrautils.utils.TablePrinter;

/**
 * Integration test {@link Gerrit} client.
 *
 * @author Michael Vorburger.ch
 */
@SuppressWarnings("checkstyle:RegexpSingleLineJava") // System.out
public class GerritTest {

    Gerrit gerrit = new Gerrit(URI.create("https://git.opendaylight.org/gerrit/"));

    @Test
    public void testChangesOnTopic() throws Exception {
        List<ChangeInfo> changes = gerrit.allChangesOnTopic("CONTROLLER-1802");
        assertThat(changes).isNotEmpty();

        TablePrinter tablePrinter = new TablePrinter(0);
        tablePrinter.setColumnNames("Status", "Change", "Mergeable?", "Subject", "Project", "Branch");
        for (ChangeInfo change : changes) {
            tablePrinter.addRow(change.status, change._number, change.mergeable, change.subject, change.project,
                    change.branch);
        }
        System.out.println(tablePrinter.toString());
    }

}
