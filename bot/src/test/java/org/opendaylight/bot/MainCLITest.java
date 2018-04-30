/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import static com.google.common.truth.Truth.assertThat;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit Test for {@link MainCLI}.
 *
 * @author Michael Vorburger.ch
 */
public class MainCLITest {

    @Test public void testUsageIfNoArguments() {
        checkUsageCalled();
    }

    @Test public void testUsageIfUnknownArguments() {
        checkUsageCalled("yolo");
    }

    @Test public void testUsageIfBuildWithoutArgument() {
        checkUsageCalled("build");
    }

    @Test public void testUsageIfTopicWithoutArgument() {
        checkUsageCalled("topic");
    }

    private static void checkUsageCalled(String... args) {
        AtomicBoolean usageCalled = new AtomicBoolean();

        MainCLI cli = new MainCLI(null) {
            @Override
            void printUsage() {
                usageCalled.set(true);
            }
        };

        cli.run(new String[0]);

        assertThat(usageCalled.get()).isTrue();
    }

    @Test public void testTopic() {
        Bot bot = Mockito.mock(Bot.class);
        MainCLI cli = new MainCLI(bot);
    }

    // TODO add mockito dependency: @Test public void testTopics() {

    // TODO @Test public void testBuild() {
}
