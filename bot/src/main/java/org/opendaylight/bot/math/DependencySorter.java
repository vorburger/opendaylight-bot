/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.math;

import java.util.List;

/**
 * Sort stuff in a tree of dependencies.
 *
 * @author Michael Vorburger.ch
 */
public final class DependencySorter {

    private DependencySorter() { }

    public static <T> void sort(List<T> list, ParentFunction<T> parentFunction) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (parentFunction.apply(list.get(i), list.get(j))) {
                    swap(list, i, j);
                }
            }
        }
    }

    private static <T> void swap(List<T> list, int p1, int p2) {
        T element = list.get(p1);
        list.set(p1, list.get(p2));
        list.set(p2, element);
    }

}
