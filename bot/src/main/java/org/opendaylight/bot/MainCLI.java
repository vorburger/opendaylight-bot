/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot;

import java.io.IOException;

/**
 * Bot CLI with main method.
 *
 * @author Michael Vorburger.ch
 */
@SuppressWarnings("checkstyle:RegexpSingleLineJava") // duh, yeah; it's main!
public class MainCLI {

    private final Bot bot;

    MainCLI(Bot bot) {
        this.bot = bot;
    }

    public static void main(String[] args) throws BotException, IOException {
        // TODO read Gerrit URL from a BotConfiguration model
        new MainCLI(new Bot()).run(args);
    }

    void run(String[] args) throws BotException {
        if (args.length == 0) {
            printUsage();
            return;
        }
        String command = args[0];
        if (command.equalsIgnoreCase("topics")) {
            // TODO print topic details
            bot.topics();
        } else if (command.equalsIgnoreCase("topic") && args.length == 2) {
            String topicName = args[1];
            System.out.println(bot.topic(topicName));
        } else if (command.equalsIgnoreCase("build") && args.length == 2) {
            String topicName = args[1];
            System.out.println(bot.build(topicName));
        } else {
            printUsage();
        }
    }

    void printUsage() {
        // TODO System.err.println("USAGE: topics");
        System.err.println("USAGE: topic <topic>");
        // TODO System.err.println("USAGE: build <topic>");
    }

}
