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

/**
 * Unit Test for {@link Main}.
 *
 * @author Michael Vorburger.ch
 */
public class MainTest {

    @Test public void testUsageIfNoArguments() {
        checkUsageCalled();
    }

    @Test public void testUsageIfUnknownArguments() {
        checkUsageCalled("yolo");
    }

    @Test public void testUsageIfBuildWithoutArgument() {
        checkUsageCalled("build");
    }

    private static void checkUsageCalled(String... args) {
        AtomicBoolean usageCalled = new AtomicBoolean();

        Main main = new Main(null) {
            @Override
            void printUsage() {
                usageCalled.set(true);
            }
        };

        main.run(new String[0]);

        assertThat(usageCalled.get()).isTrue();
    }

    // TODO add mockito dependency: @Test public void testTopics() {

    // TODO @Test public void testBuild() {
}
