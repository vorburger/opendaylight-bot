/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.net.URI;

/**
 * Bot global configuration.
 *
 * @author Michael Vorburger.ch
 */
@SuppressFBWarnings({"URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD"})
public class BotConfiguration {

    public URI gerritBase;
    public URI jiraBase;
    public URI jenkinsBase;

}
