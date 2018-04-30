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

    @Test public void testUsageIfNoArguments() throws BotException {
        checkUsageCalled();
    }

    @Test public void testUsageIfUnknownArguments() throws BotException {
        checkUsageCalled("yolo");
    }

    @Test public void testUsageIfBuildWithoutArgument() throws BotException {
        checkUsageCalled("build");
    }

    @Test public void testUsageIfTopicWithoutArgument() throws BotException {
        checkUsageCalled("topic");
    }

    private static void checkUsageCalled(String... args) throws BotException {
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

    @Test public void testTopic() throws BotException {
        Bot bot = Mockito.mock(Bot.class);
        MainCLI cli = new MainCLI(bot);
        cli.run(new String[] { "topic", "CONTROLLER-1802" });
        Mockito.verify(bot).topic("CONTROLLER-1802");
    }

    @Test public void testBuild() throws BotException {
        Bot bot = Mockito.mock(Bot.class);
        MainCLI cli = new MainCLI(bot);
        cli.run(new String[] { "build", "CONTROLLER-1802" });
        Mockito.verify(bot).build("CONTROLLER-1802");
    }

    @Test public void testTopics() throws BotException {
        Bot bot = Mockito.mock(Bot.class);
        MainCLI cli = new MainCLI(bot);
        cli.run(new String[] { "topics" });
        Mockito.verify(bot).topics();
    }
}
