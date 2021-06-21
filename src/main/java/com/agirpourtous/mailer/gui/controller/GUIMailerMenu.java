package com.agirpourtous.mailer.gui.controller;

import com.agirpourtous.gui.plugin.GuiPluginController;
import com.agirpourtous.mailer.MailerPlugin;
import javafx.scene.image.Image;
import org.pf4j.Extension;

import java.util.Objects;

@Extension
public class GUIMailerMenu extends GuiPluginController {

    public GUIMailerMenu() {
        super(new Image(Objects.requireNonNull(MailerPlugin.class.getResourceAsStream("icon.png"))), "Mailer plugin");
    }

    @Override
    public void start() {
        System.out.println("test start");
    }
}