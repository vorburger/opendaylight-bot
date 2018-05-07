/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.math;

import com.google.common.graph.SuccessorsFunction;
import java.util.function.BiFunction;

/**
 * Function which returns if the first value is a dependency parent of the second else.
 * See also Guava's {@link SuccessorsFunction} and <a href=
 * "https://github.com/google/guava/wiki/GraphsExplained">GraphsExplained</a>.
 *
 * @author Michael Vorburger.ch
 */
public interface ParentFunction<T> extends BiFunction<T, T, Boolean> {
}
