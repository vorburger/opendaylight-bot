/*
 * Copyright (c) 2018 Red Hat, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bot.spring.web;

import com.google.common.base.Strings;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opendaylight.bot.Bot;
import org.opendaylight.bot.BotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bot's Servlet.
 *
 * @author Michael Vorburger.ch
 */
@SuppressWarnings("serial")
@SuppressFBWarnings({"SE_NO_SERIALVERSIONID", "SE_BAD_FIELD"})
public class BotServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(BotServlet.class);

    private final Bot bot;

    @Inject
    public BotServlet(Bot bot) {
        this.bot = bot;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String topicName = request.getParameter("topic");

        try {
            if (!Strings.isNullOrEmpty(topicName)) {
                bot.topic(topicName);
            } else {
                out.println("USAGE: /bot?topic={managedTopicName}");
            }
        } catch (BotException e) {
            LOG.error("Bot failed", e);
        }
    }
}
