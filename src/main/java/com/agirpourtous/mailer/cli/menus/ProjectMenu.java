package com.agirpourtous.mailer.cli.menus;

import com.agirpourtous.cli.CLILauncher;
import com.agirpourtous.cli.menus.Action;
import com.agirpourtous.cli.menus.MainMenu;
import com.agirpourtous.cli.menus.Menu;
import com.agirpourtous.cli.menus.list.ProjectTicketsListMenu;
import com.agirpourtous.core.models.Project;
import com.agirpourtous.core.models.Ticket;
import com.agirpourtous.mailer.MailerPlugin;

public class ProjectMenu extends Menu {
    public ProjectMenu(CLILauncher launcher, Project project) {
        super(launcher, "Mailing menu for project");
        addAction(new Action("Envoyer le rapport pour un projet") {
            @Override
            public void execute() {
                Ticket ticket = (Ticket) new ProjectTicketsListMenu(launcher, project).startList();

                launcher.setActiveMenu(new MainMenu(launcher));
            }
        });

        addAction(new Action("Envoyer le rapport du projet " + project.getName()) {
            @Override
            public void execute() {
                System.out.println(project.getName());
                launcher.setActiveMenu(new MainMenu(launcher));
            }
        });

        addAction(new Action("Retour au menu du plugin") {
            @Override
            public void execute() {
                launcher.setActiveMenu(new MailerPlugin.MailerMenu().pluginBuild(launcher));
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
