/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.io.Resources;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

/**
 * Unit test for {@link Projects} helper.
 *
 * @author Michael Vorburger.ch
 */
public class ProjectsTest {

    @Test
    public void testDefaultProjectListOnClasspath() throws IOException {
        Projects projects = new Projects(
                Resources.readLines(Resources.getResource("test-projects.txt"), StandardCharsets.US_ASCII));
        assertThat(projects.getProjects()).hasSize(2);
    }


}
