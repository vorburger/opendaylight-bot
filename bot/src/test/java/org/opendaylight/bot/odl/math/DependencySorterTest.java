/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.odl.math;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.truth.Truth.assertThat;
import static java.util.Collections.emptyList;

import com.google.errorprone.annotations.Var;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.opendaylight.bot.math.DependencySorter;
import org.opendaylight.bot.math.ParentFunction;

/**
 * Unit Test for {@link DependencySorter}.
 *
 * @author Michael Vorburger.ch
 */
public class DependencySorterTest {

    @Test public void testSortEmpty() {
        List<Object> emptyList = emptyList();
        DependencySorter.sort(emptyList, (first, second) -> false);
        assertThat(emptyList).isEmpty();
    }

    @Test public void testIsSorted() {
        List<Thing> emptyList = newArrayList(new Thing("A"), new Thing("B", "A"));
        DependencySorter.sort(emptyList, THING_PARENT_FUNCTION);
        assertThat(emptyList).containsExactly(new Thing("A"), new Thing("B", "A")).inOrder();
    }

    @Test public void testSort2() {
        List<Thing> emptyList = newArrayList(new Thing("B", "A"), new Thing("A"));
        DependencySorter.sort(emptyList, THING_PARENT_FUNCTION);
        assertThat(emptyList).containsExactly(new Thing("A"), new Thing("B", "A")).inOrder();
    }

    @Test public void testSortMore() {
        List<Thing> emptyList = newArrayList(new Thing("D", "B"), new Thing("B", "A"), new Thing("A"),
                new Thing("E"), new Thing("C", "A"));
        DependencySorter.sort(emptyList, THING_PARENT_FUNCTION);
        assertThat(emptyList).containsExactly(new Thing("A"), new Thing("B", "A"),
                new Thing("D", "B"), new Thing("E"), new Thing("C", "A")).inOrder();
    }

    @Test public void testSortEvenMore() {
        List<Thing> emptyList = newArrayList(new Thing("C", "A"), new Thing("D", "B"), new Thing("B", "A"),
                new Thing("A"), new Thing("E"));
        DependencySorter.sort(emptyList, THING_PARENT_FUNCTION);
        assertThat(emptyList).containsExactly(new Thing("A"), new Thing("B", "A"),
                new Thing("D", "B"), new Thing("C", "A"), new Thing("E")).inOrder();
    }

    @Test public void testParentFunction() {
        assertThat(THING_PARENT_FUNCTION.apply(new Thing("A"), new Thing("B", "A"))).isFalse();
        assertThat(THING_PARENT_FUNCTION.apply(new Thing("B", "A"), new Thing("A"))).isTrue();

        assertThat(THING_PARENT_FUNCTION.apply(new Thing("A", "C"), new Thing("B", "A"))).isFalse();
        assertThat(THING_PARENT_FUNCTION.apply(new Thing("B", "A"), new Thing("A", "C"))).isTrue();

        assertThat(THING_PARENT_FUNCTION.apply(new Thing("A"), new Thing("B"))).isFalse();
        assertThat(THING_PARENT_FUNCTION.apply(new Thing("B"), new Thing("A"))).isFalse();
    }

    private static class Thing {
        private final String thing;
        private final Optional<String> parent;

        Thing(String thing, String parent) {
            this.thing = thing;
            this.parent = Optional.of(parent);
        }

        Thing(String thing) {
            this.thing = thing;
            this.parent = Optional.empty();
        }

        @Override
        public String toString() {
            if (parent.isPresent()) {
                return thing + "{" + parent.get() + "}";
            } else {
                return thing;
            }
        }

        // equals() and hashCode() required by assertThat() but not DependencySorter

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Thing other = (Thing) obj;
            if (parent == null) {
                if (other.parent != null) {
                    return false;
                }
            } else if (!parent.equals(other.parent)) {
                return false;
            }
            if (thing == null) {
                if (other.thing != null) {
                    return false;
                }
            } else if (!thing.equals(other.thing)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int prime = 31;
            @Var int result = 1;
            result = prime * result + (parent == null ? 0 : parent.hashCode());
            result = prime * result + (thing == null ? 0 : thing.hashCode());
            return result;
        }
    }

    private static final ParentFunction<Thing> THING_PARENT_FUNCTION =
        (first, second) -> first.parent.isPresent()
                && first.parent.get().equals(second.thing);

}
