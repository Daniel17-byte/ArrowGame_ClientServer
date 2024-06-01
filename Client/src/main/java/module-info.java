module org.arrowgame.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires spring.context;
    requires static lombok;
    requires org.json;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    opens org.arrowgame.client.responses to com.fasterxml.jackson.databind;

    opens org.arrowgame.client to javafx.fxml;
    exports org.arrowgame.client;
}