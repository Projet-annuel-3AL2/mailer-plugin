package com.agirpourtous.mailer.cli.menus;

import com.agirpourtous.cli.menus.Action;
import com.agirpourtous.cli.menus.MainMenu;
import com.agirpourtous.cli.menus.Menu;
import com.agirpourtous.cli.menus.list.ProjectListSelectionMenu;
import com.agirpourtous.core.models.Project;
import org.pf4j.Extension;

@Extension
public class MailerMenu extends Menu {
    public MailerMenu() {
        super("Menu du plugin d'envoi de mail");
        addAction(new Action("Envoyer un rapport pour un projet") {
            @Override
            public void execute() {
                Project project = (Project) new ProjectListSelectionMenu(launcher).startList();
                if (project != null) {
                    launcher.setActiveMenu(new ProjectMailerMenu(launcher, project));
                }
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