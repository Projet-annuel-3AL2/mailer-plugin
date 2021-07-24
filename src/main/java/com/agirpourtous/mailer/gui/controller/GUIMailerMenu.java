package com.agirpourtous.mailer.gui.controller;

import com.agirpourtous.core.models.Project;
import com.agirpourtous.core.models.User;
import com.agirpourtous.gui.controllers.ProjectDetailsController;
import com.agirpourtous.gui.plugin.GuiPluginController;
import com.agirpourtous.mailer.MailerPlugin;
import com.agirpourtous.mailer.core.MailSender;
import com.agirpourtous.mailer.core.PdfParser;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import org.pf4j.Extension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Extension
public class GUIMailerMenu extends GuiPluginController {
    private Project project;

    public GUIMailerMenu() {
        super(new Image(Objects.requireNonNull(MailerPlugin.class.getResourceAsStream("icon.png"))), "Mailer plugin");
    }

    @Override
    public void start() {
        this.project = ((ProjectDetailsController) getController()).getProject();
        List<User> users = getController().getClient()
                .getProjectService()
                .getMembers(project.getId())
                .collect(Collectors.toList())
                .block();
        if (users == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Une erreur s'est produite, impossible de trouver un destinataire");
            alert.showAndWait();
            return;
        }
        ChoiceDialog<User> dialog = new ChoiceDialog<>(users.get(0), users);
        dialog.setTitle("Envoyer un rapport par mail");
        dialog.setHeaderText("Choisissez le destinataire");
        Optional<User> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return;
        }
        sendMail(result.get());
    }

    private void sendMail(User user) {
        Task<Object> task = new Task<>() {
            @Override
            protected Object call() {
                try{
                    MailSender mailSender = MailSender.getInstance();
                    mailSender.sendEmail(user.getMail(), "Details du projet - " + project.getName(), new PdfParser().parseProjectPdf(project, getController().getClient()));
                }catch (Exception e ){
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}