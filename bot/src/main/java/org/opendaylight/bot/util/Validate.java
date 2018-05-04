/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.util;

import com.google.common.base.Strings;

/**
 * Utility methods for validating.
 *
 * @author Michael Vorburger.ch
 */
public final class Validate {

    private Validate() { }

    public static String requireNonNullOrEmpty(String input, String name) {
        if (!Strings.isNullOrEmpty(name)) {
            if (!Strings.isNullOrEmpty(input)) {
                return input;
            } else {
                throw new IllegalArgumentException(name + " input is empty or null");
            }
        } else {
            throw new IllegalArgumentException("name is empty or null");
        }
    }

}
