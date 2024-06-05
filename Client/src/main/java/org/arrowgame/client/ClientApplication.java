package org.arrowgame.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.arrowgame.client.utils.CustomLocale;
import org.arrowgame.client.utils.LanguageManager;
import org.arrowgame.client.utils.OpenViews;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class ClientApplication extends Application {
    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    public static String path = context.getBean("myProperties", Properties.class).getProperty("path");
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LanguageManager.loadLanguage(CustomLocale.ENGLISH);
        OpenViews.showLogin(primaryStage);
    }
}
