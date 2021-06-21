package com.agirpourtous.mailer.cli.menus;

import com.agirpourtous.cli.CLILauncher;
import com.agirpourtous.cli.menus.Action;
import com.agirpourtous.cli.menus.MainMenu;
import com.agirpourtous.cli.menus.Menu;
import com.agirpourtous.cli.menus.list.ProjectTicketsListMenu;
import com.agirpourtous.cli.menus.list.UserListMenu;
import com.agirpourtous.core.models.Project;
import com.agirpourtous.core.models.Ticket;
import com.agirpourtous.core.models.User;
import com.agirpourtous.core.pdf.ProjectPdfGenerator;
import com.agirpourtous.mailer.core.MailSender;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.FileInputStream;
import java.io.IOException;

public class ProjectMailerMenu extends Menu {
    public ProjectMailerMenu(CLILauncher launcher, Project project) {
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
                try {
                    MailSender mailSender = MailSender.getInstance();
                    User user = (User) new UserListMenu(launcher).startList();
                    PdfReader reader = new PdfReader(new FileInputStream(new ProjectPdfGenerator(launcher.getClient(), project).generatePdf()));
                    StringBuilder pdfContent = new StringBuilder();
                    PdfReaderContentParser parser = new PdfReaderContentParser(reader);
                    TextExtractionStrategy strategy;
                    for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                        strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                        pdfContent.append(strategy.getResultantText());
                    }
                    mailSender.sendEmail(user.getMail(), "Details du projet - " + project.getName(), pdfContent.toString());
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
