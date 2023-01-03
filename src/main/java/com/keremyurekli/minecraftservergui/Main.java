package com.keremyurekli.minecraftservergui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;


public class Main extends Application {

    public static final double SCREEN_WIDTH = 1280.0;
    public static final double SCREEN_HEIGHT = 720.0;

    public static TextArea logger;

    public static OutputStream outputStream;
    public static InputStream inputStream;


    public static File SERVER_JAR;
    public static String SERVER_JAR_PATH;
    public static File SERVER_PARENT;
    public static String SERVER_PARENT_PATH;


    public static Process PROCESS = null;


    public static int MC_ONLINE_PLAYERS;
    public static int MC_PLAYERS_MAX;
    public static String MC_VERSION;

    public static String MC_MOTD;


    public static String NGROK_IP;
    public static String PORT;
    public static boolean NGROK_IS_RUNNING = false;




    public static void manualStop() throws IOException {
        if (PROCESS != null && PROCESS.isAlive()) {
            PROCESS.destroy();
            PROCESS = null;
            outputStream.close();
            inputStream.close();
            outputStream = null;
            inputStream = null;
            GUI.wonder.stop();
            Platform.runLater(() -> {
                Main.logger.appendText("[STOPPING THE SERVER]");
            });

        }
        if (PROCESS == null) {
            GUI.buttonUpdate();
        }
        if (NGROK_IS_RUNNING) {
            Util.ngrokSwitch(null, null);
            NGROK_IS_RUNNING = false;
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        Locale.setDefault(new Locale("en", Locale.getDefault().getCountry()));


        GUI.stage = stage;

        stage.setResizable(false);
        stage.setTitle("ServerGUI");
        stage.setScene(GUI.createStartupScene());
        stage.show();


    }

    @Override
    public void stop() throws IOException, InterruptedException {


        if (PROCESS != null && PROCESS.isAlive()) {
            PROCESS.destroy();
            PROCESS = null;
            outputStream.close();
            inputStream.close();
            outputStream = null;
            inputStream = null;
            GUI.wonder.stop();
            Platform.runLater(() -> {
                Main.logger.appendText("[STOPPING THE SERVER]");
            });

        }
        if (PROCESS == null) {
            GUI.buttonUpdate();
        }
        if (NGROK_IS_RUNNING) {
            Util.ngrokSwitch(null, null);
            NGROK_IS_RUNNING = false;
        }


        if (PROCESS != null && PROCESS.isAlive()) {
            PROCESS.waitFor();
        }
        Platform.exit();
        System.exit(0);

    }
}

