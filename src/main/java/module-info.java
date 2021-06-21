module com.agirpourtous.mailer {
    requires org.pf4j;
    requires slf4j.api;
    requires com.agirpourtous;
    requires java.mail;
    requires javafx.graphics;
    requires itextpdf;

    opens com.agirpourtous.mailer;
    opens com.agirpourtous.mailer.gui.controller;
    exports com.agirpourtous.mailer;
    exports com.agirpourtous.mailer.gui.controller;
}