/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Projects ordering.
 *
 * @author Michael Vorburger.ch
 */
public class Projects {

    private final ImmutableList<String> projectsInOrderOfDependencies;

    public Projects(File file) throws IOException {
        this(Files.asCharSource(file, StandardCharsets.US_ASCII).readLines());
    }

    public Projects(String... projectsInOrderOfDependencies) {
        this(Arrays.asList(projectsInOrderOfDependencies));
    }

    public Projects(List<String> projectsInOrderOfDependencies) {
        this.projectsInOrderOfDependencies = ImmutableList.copyOf(projectsInOrderOfDependencies.stream()
                .filter(line -> !(line.trim().startsWith("#") || line.trim().isEmpty())).collect(Collectors.toList()));
    }

    public List<String> getProjects() {
        return projectsInOrderOfDependencies;
    }

    // public <T> List<T> sort(Collection<T> list, Function<T, String> projectNameFunction) {

}
