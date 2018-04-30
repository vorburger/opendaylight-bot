/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Projects ordering.
 *
 * @author Michael Vorburger.ch
 */
public class Projects {

    private final ImmutableList<String> projectsInOrderOfDependencies;

    public Projects(File file) throws IOException {
        this(Files.asCharSource(file, StandardCharsets.US_ASCII).readLines(new LineProcessor<List<String>>() {

            List<String> lines = new ArrayList<>();

            @Override
            public List<String> getResult() {
                return lines;
            }

            @Override
            public boolean processLine(String line) throws IOException {
                if (!line.trim().startsWith("#")) {
                    lines.add(line);
                }
                return true;
            }
        }));
    }

    public Projects(List<String> projectsInOrderOfDependencies) {
        this.projectsInOrderOfDependencies = ImmutableList.copyOf(projectsInOrderOfDependencies);
    }

    public List<String> getProjects() {
        return projectsInOrderOfDependencies;
    }

    public Map<String, List<String>> getNewMultimap() {
        // The Map we return has to be both immutable, and has predictable iteration order based on insertion.
        // We could use a LinkedHashMap, but as ImmutableMap with a Builder also guarantees order, let's use that:
        Builder<String, List<String>> builder = ImmutableMap.<String, List<String>>builder();
        projectsInOrderOfDependencies.forEach(projectName -> builder.put(projectName, new ArrayList<>()));
        return builder.build();
    }

    // public <T> List<T> sort(Collection<T> list, Function<T, String> projectNameFunction) {

}
