package com.agirpourtous.mailer.core;

import com.agirpourtous.mailer.MailerPlugin;

import javax.mail.*;
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

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            Session session = Session.getDefaultInstance(configSMTP, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(configSMTP.getProperty("mail.smtp.user"), configSMTP.getProperty("mail.smtp.password"));
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setFrom(configSMTP.getProperty("mail.smtp.user"));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException ex) {
            System.err.println("Cannot send email. " + ex);
        }
    }
}
