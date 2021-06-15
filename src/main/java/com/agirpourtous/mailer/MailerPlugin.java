package com.agirpourtous.mailer;

import com.agirpourtous.cli.menus.Action;
import com.agirpourtous.cli.menus.MainMenu;
import com.agirpourtous.cli.menus.Menu;
import com.agirpourtous.cli.menus.list.ProjectListSelectionMenu;
import com.agirpourtous.core.models.Project;
import org.pf4j.Extension;
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
        logger.info("WelcomePlugin.start()");
    }

    @Override
    public void stop() {
        logger.info("WelcomePlugin.stop()");
    }

    @Override
    public void delete() {
        logger.info("WelcomePlugin.delete()");
    }

    @Extension
    public static class MailerMenu extends Menu {
        public MailerMenu() {
            super("Menu du plugin d'envoie de mail");
            addAction(new Action("Envoyer le rapport pour un projet") {
                @Override
                public void execute() {
                    Project project = (Project) new ProjectListSelectionMenu(launcher).startList();

                    launcher.setActiveMenu(new MainMenu(launcher));
                }
            });
            addAction(new Action("Retour au menu principal") {
                @Override
                public void execute() {
                    launcher.setActiveMenu(new MainMenu(launcher));
                }
            });
        }
    }
}
