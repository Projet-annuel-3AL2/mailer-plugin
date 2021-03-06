package com.agirpourtous.mailer;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailerPlugin extends Plugin {
    private final static Logger logger = LoggerFactory.getLogger("MailerPlugin");

    public MailerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        logger.info("Starting mailer plugin");
    }

    @Override
    public void stop() {
        logger.info("Stopping mailer plugin");
    }

    @Override
    public void delete() {
        logger.info("Deleting mailer plugin");
    }
}
