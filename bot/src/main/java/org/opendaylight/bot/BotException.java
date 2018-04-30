/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

/**
 * Exception for when the Bot unexpectedly falls flat on its face.
 *
 * @author Michael Vorburger.ch
 */
public class BotException extends Exception {

    private static final long serialVersionUID = 1L;

    public BotException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotException(String message) {
        super(message);
    }

}
