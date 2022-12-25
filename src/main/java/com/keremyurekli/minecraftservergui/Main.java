package com.keremyurekli.minecraftservergui;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.installer.NgrokV2CDNUrl;
import com.github.alexdlaird.ngrok.installer.NgrokVersion;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
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
    public static String MC_SOFTWARE;
    public static String MC_VERSION;







    public static String NGROK_IP;
    public static String PORT;
    public static boolean NGROK_IS_RUNNING = false;

    public static File NGROK_FILE = new File("NgrokAuthToken.txt");




    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        Locale.setDefault(new Locale("en", "TR"));

        stage.setResizable(false);
        stage.setTitle("ServerGUI v1.0");
        stage.setScene(GUI.createStartupScene(stage));
        stage.show();

        PositionableList ps = new PositionableList();

    }


    @Override
    public void stop(){
        System.out.println("exit.event");
        if(PROCESS != null && PROCESS.isAlive()){
            PROCESS.destroy();
        }
        if(NGROK_IS_RUNNING){
            Util.ngrokSwitch(null,null);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}

