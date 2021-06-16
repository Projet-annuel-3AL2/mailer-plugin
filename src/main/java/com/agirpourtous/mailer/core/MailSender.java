package com.agirpourtous.mailer.core;

import com.agirpourtous.mailer.MailerPlugin;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailSender {
    private static MailSender instance;
    private final Properties configSMTP = new Properties();

    private MailSender() {
        fetchConfig();
    }

    public static MailSender getInstance() {
        if (instance == null) {
            instance = new MailSender();
        }
        return instance;
    }

    public void refreshConfig() {
        configSMTP.clear();
        fetchConfig();
    }

    private void fetchConfig() {
        try {
            configSMTP.load(MailerPlugin.class.getResourceAsStream("mail.properties"));
        } catch (IOException ex) {
            System.err.println("Cannot open and load SMTP config file.");
        }
    }

    public void sendEmail(String fromEmail, String toEmail, String subject, String body) {
        try {
            Session session = Session.getDefaultInstance(configSMTP, null);
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setFrom(fromEmail);
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException ex) {
            System.err.println("Cannot send email. " + ex);
        }
    }
}
