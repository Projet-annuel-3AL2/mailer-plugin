package com.agirpourtous.mailer.cli.menus;

import com.agirpourtous.cli.CLILauncher;
import com.agirpourtous.cli.menus.Action;
import com.agirpourtous.cli.menus.MainMenu;
import com.agirpourtous.cli.menus.Menu;
import com.agirpourtous.cli.menus.list.UserListMenu;
import com.agirpourtous.core.models.Project;
import com.agirpourtous.core.models.User;
import com.agirpourtous.mailer.core.MailSender;
import com.agirpourtous.mailer.core.PdfParser;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

public class ProjectMailerMenu extends Menu {
    public ProjectMailerMenu(CLILauncher launcher, Project project) {
        super(launcher, "Mailing menu for project");
        addAction(new Action("Envoyer le rapport du projet " + project.getName()) {
            @Override
            public void execute() {
                try {
                    MailSender mailSender = MailSender.getInstance();
                    User user = (User) new UserListMenu(launcher).startList();
                    mailSender.sendEmail(user.getMail(), "Details du projet - " + project.getName(), new PdfParser().parseProjectPdf(project, launcher.getClient()));
                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                }
                launcher.setActiveMenu(new MainMenu(launcher));
            }
        });

        addAction(new Action("Retour au menu du plugin") {
            @Override
            public void execute() {
                launcher.setActiveMenu(new MailerMenu().pluginBuild(launcher));
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
