package com.keremyurekli.minecraftservergui;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.installer.NgrokVersion;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;

import static com.keremyurekli.minecraftservergui.Main.*;

public class GUI {

    public static Timeline wonder = null;
    public static Stage stage = null;
    private static Button startbutton;
    private static TextField inputfield;
    private static Label lbl1;



    public static TextField ramSlot;
    public static TextField javapathSlot;
    public static TextField portSlot;

    public static StackPane createTopMenu() throws IOException {
        Font.loadFont("com/keremyurekli/minecraftservergui/Minecraft.ttf", 12);


        StackPane p1 = new StackPane();
        p1.setId("topmenu");
        Label lbl = new Label();
        p1.getChildren().addAll(lbl);
        lbl.setText("Can't get information from server!");

        if(wonder == null){
            wonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (PROCESS != null){
                        buttonUpdate();
                    }
                    if (PROCESS != null && PROCESS.isAlive()) {
                        try {
                            SERVER_ACCESSIBLE = ServerCommunication.isOnline(LOCAL_IP,PORT == null ? 25565 : Integer.parseInt(PORT));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if(SERVER_ACCESSIBLE){
                            ServerCommunication handshake = new ServerCommunication();
                            InetSocketAddress inet = new InetSocketAddress(LOCAL_IP, PORT == null ? 25565 : Integer.parseInt(PORT));
                            handshake.setAddress(inet);
                            JsonObject response= null;
                            try {
                                response = handshake.fetchData();
                            } catch (IOException e) {
                                System.out.println();
                            }

                            JsonObject description;
                            JsonObject players;
                            JsonObject version;
                            JsonObject securechat;

                            if(response == null){
                                lbl.setText("Can't get information from server!");
                            }else{
//                            if (JsonParser.parseString(String.valueOf(response.get("enforcesSecureChat"))) != null) {
//                                securechat = (JsonObject) JsonParser.parseString(String.valueOf(response.get("enforcesSecureChat")));
//                                MC_SECURE_CHAT = Boolean.parseBoolean(securechat.toString());
//                            }
                                if (JsonParser.parseString(String.valueOf(response.get("description"))) != null && !JsonParser.parseString(String.valueOf(response.get("description"))).isJsonNull()) {
                                    description = (JsonObject) JsonParser.parseString(String.valueOf(response.get("description")));
                                    MC_MOTD = String.valueOf(description.get("text"));
                                }
                                if (JsonParser.parseString(String.valueOf(response.get("players"))) != null && !JsonParser.parseString(String.valueOf(response.get("players"))).isJsonNull()) {
                                    players = (JsonObject) JsonParser.parseString(String.valueOf(response.get("players")));
                                    MC_ONLINE_PLAYERS = Integer.parseInt(String.valueOf(players.get("online")));
                                    MC_PLAYERS_MAX = Integer.parseInt(String.valueOf(players.get("max")));
                                }
                                if (JsonParser.parseString(String.valueOf(response.get("version"))) != null && !JsonParser.parseString(String.valueOf(response.get("version"))).isJsonNull()) {
                                    version = (JsonObject) JsonParser.parseString(String.valueOf(response.get("version")));
                                    MC_VERSION = String.valueOf(version.get("name"));
                                    MC_PROTOCOL_VERSION = Integer.parseInt(String.valueOf(version.get("protocol")));
                                }
                                lbl.setText("MOTD: " + MC_MOTD + "\nOnline Players: " + MC_ONLINE_PLAYERS + " / " + MC_PLAYERS_MAX + "\n" + "Server Version: " + MC_VERSION);
                            }
                        }


                    }else{
                        lbl.setText("Can't get information from server!");
                    }
//                    if (NGROK_IS_RUNNING) {
//                        try {
//                            Util.httpHandle();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        lbl.setText("MOTD: " + MC_MOTD + "\nOnline Players: " + MC_ONLINE_PLAYERS + " / " + MC_PLAYERS_MAX + "\n" + "Server Version: " + MC_VERSION);
//
//                    } else {
//                    lbl.setText("You must enable portforwarding to view server information!");
//                    }


                }

            }));
            wonder.setCycleCount(Timeline.INDEFINITE);
            wonder.play();
        }
        return p1;
    }

    private static void labelFix() {
        Platform.runLater(() -> {
            lbl1.setText("DEBUG MODE");
        });
    }

    public static StackPane createLeftMenu() throws UnknownHostException, FileNotFoundException {

        StackPane p1 = new StackPane();
        p1.setId("leftmenu");

        Label lbl1 = new Label();
        if (Main.SERVER_JAR != null) {
            lbl1.setText(Main.SERVER_JAR.getName());
        } else {
            lbl1.setText("DEBUG MODE");
        }
        GUI.lbl1 = lbl1;


        Button b1 = new Button("START SERVER");
        b1.setPrefWidth(240);
        startbutton = b1;


        Label lbl2 = new Label("Ram Amount(MB):");

        TextField tf1 = new TextField();
        tf1.setAlignment(Pos.CENTER);
        if(Saves.getValueFor("ram").isBlank()){
            tf1.setPromptText("4096 by default");
        }else{
            tf1.setText(Saves.getValueFor("ram"));
        }
        tf1.setMaxWidth(240);
        ramSlot = tf1;

        Label lbl3 = new Label("Server Port:");


        TextField tf2 = new TextField();
        tf2.setAlignment(Pos.CENTER);

        if(Saves.getValueFor("port").isBlank()){
            tf2.setPromptText("25565 by default");
        }else{
            tf2.setText(Saves.getValueFor("port"));
        }
        tf2.setMaxWidth(240);
        portSlot = tf2;


        Label lbl4 = new Label("Custom Javapath:");
///POINT
        TextField tf3 = new TextField();
        tf3.setAlignment(Pos.CENTER);
        if(Saves.getValueFor("javapath").isBlank()){
            tf3.setPromptText("Java installed on the system by default");
        }else{
            tf3.setText(Saves.getValueFor("javapath"));
        }
        tf3.setMaxWidth(240);
        javapathSlot = tf3;

        Button b2 = new Button("Open server.properties");
        b2.setPrefWidth(240);
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                File temp = new File(Main.SERVER_PARENT_PATH + "\\server.properties");

                if (temp.exists()) {
                    try {

                        PropertyViewer propertyViewer = new PropertyViewer(temp);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        Button b3 = new Button("Open server folder");
        b3.setPrefWidth(240);

        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();

                if (Main.SERVER_PARENT.exists()) {
                    try {
                        desktop.open(Main.SERVER_PARENT);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        Label lbl10 = new Label("Local Ip Address:");

        TextField tf6 = new TextField();
        tf6.setAlignment(Pos.CENTER);
        tf6.setText(LOCAL_IP);
        tf6.setEditable(false);
        tf6.setMaxWidth(240);


        Label lbl5 = new Label("Portforwarding Service:");

        ComboBox<String> cmb1 = new ComboBox<>();
        cmb1.setDisable(true);
        cmb1.getItems().addAll("ngrok (default)");
        cmb1.setValue("ngrok (default)");

        Label lbl6 = new Label("Ngrok Auth Token:");

        TextField tf4 = new TextField();
        tf4.setAlignment(Pos.CENTER);
        tf4.setPromptText("ENTER YOUR NGROK AUTH TOKEN");
        tf4.setMaxWidth(240);
        if (Util.readNgrok() != null) {
            if (!Util.readNgrok().isEmpty() || !Util.readNgrok().isBlank()) {
                tf4.setText(Util.readNgrok());
            }
        }

        Button b4 = new Button("START PORTFORWARDING");
        b4.setPrefWidth(240);


        Label lbl7 = new Label("Public Ip Address:");

        TextField tf5 = new TextField();
        tf5.setAlignment(Pos.CENTER);
        tf5.setText("PORTFORWARDING IS NOT ENABLED");
        tf5.setEditable(false);
        tf5.setMaxWidth(240);

        b4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (cmb1.getValue().equals("ngrok (default)")) {

                    if (Main.PROCESS != null) {
                        Main.PORT = "25565";
                        if (!tf2.getText().isEmpty()) {
                            PORT = tf2.getText();
                        }
                        if (tf4.getText().isEmpty()) {
                            tf4.requestFocus();
                            return;
                        }
                        if (NGROK_IS_RUNNING) {

                            b4.setText("START PORTFORWARDING");

                        } else {

                            b4.setText("STOP PORTFORWARDING");

                        }
                        String str = Util.ngrokSwitch(PORT, tf4.getText());
                        tf5.setText(Objects.requireNonNullElse(str, "PORTFORWARDING IS NOT ENABLED"));
                    } else {
                        b1.requestFocus();
                    }
                }

            }
        });


        StackPane p2 = new StackPane();
        Button b5 = new Button("<-- Go back to server selection");
        p2.setId("littlemenu");
        b5.setTranslateY(95);
        b5.setTranslateX(5);

        Label lbl8 = new Label("This app made by keremyurekli in 2022");
        lbl8.setTranslateY(50);

        Hyperlink lbl9 = new Hyperlink("Github page of the project");
        lbl9.setTranslateY(70);

        lbl9.setOnAction(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URL("https://github.com/keremyurekli/MinecraftServerGui").toURI());
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        b5.setOnAction(event -> {
            try {
                Saves.putDefaults();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Main.forceStop();
                PROCESS = null;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.hide();
            try {
                stage.setScene(createStartupScene());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            stage.centerOnScreen();
            stage.show();

        });


        StackPane.setAlignment(lbl9, Pos.TOP_CENTER);
        StackPane.setAlignment(lbl8, Pos.TOP_CENTER);
        StackPane.setAlignment(b5, Pos.TOP_LEFT);
        p2.getChildren().addAll(b5, lbl8, lbl9);

        p1.getChildren().addAll(lbl1, b1, lbl2, tf1, lbl4, tf3, lbl3, tf2, b2, b3, lbl10, tf6, lbl5, cmb1, b4, lbl6, tf4, lbl7, tf5, p2);
        for (Node n : p1.getChildren()) {
            StackPane.setAlignment(n, Pos.TOP_CENTER);
        }
        StackPane.setAlignment(p2, Pos.BOTTOM_CENTER);


        PositionableList positionableList = new PositionableList();
        Collections.addAll(positionableList, lbl1, b1, lbl2, tf1, lbl4, tf3, lbl3, tf2, b2, b3, lbl5, cmb1, b4, lbl6, tf4, lbl7, tf5, lbl10, tf6, p2);
        positionableList.reposition(20, 30, 5);


        b1.setOnAction(e -> {
            Main.logger.setScrollTop(Double.MAX_VALUE);

            int ram = 4096;
            if (!tf1.getText().isEmpty()) {
                ram = Integer.parseInt(tf1.getText());
            }
            String javapath = null;
            if (!tf3.getText().isEmpty()) {
                javapath = tf3.getText();
            }
            Main.PORT = "25565";
            if (!tf2.getText().isEmpty()) {
                PORT = tf2.getText();
            }


            if (Main.PROCESS == null || !Main.PROCESS.isAlive()) {
                try {
                    Saves.changePort(Integer.parseInt(PORT));
                    Main.PROCESS = Util.initProcess(ram, javapath);
                    b1.setText("STOP SERVER");
                    inputfield.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            } else {
                try {
                    Util.outputHandler(Main.outputStream, "stop\n");
                    b1.setText("START SERVER");
                    inputfield.setDisable(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        return p1;

    }


    public static void buttonUpdate() {
        Platform.runLater(() -> {
                String msg;
                if(SERVER_ACCESSIBLE && (PROCESS.isAlive() && PROCESS != null)){
                    msg = "STOP SERVER";
                    inputfield.setDisable(false);
                }else{
                    msg = "START SERVER";
                    inputfield.setDisable(true);
                }
                if(PROCESS != null){
                    if(!SERVER_ACCESSIBLE && (!PROCESS.isAlive() || PROCESS != null)){
                        msg = "Preparing...";
                    }
                }
                startbutton.setText(msg);

        });
    }

    public static StackPane createConsoleMenu() throws InterruptedException {

        AtomicBoolean useless = new AtomicBoolean(true);
        String prefix = "> ";
        StackPane p1 = new StackPane();
        p1.setId("consolemenu");

        TextField tf = new TextField(prefix);
        tf.setDisable(true);
        tf.setFont(new Font("Consolas", 12));
        tf.setId("input");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            TextFormatter.Change rtrn = null;
            if (useless.get()) {
                if (c.getCaretPosition() < prefix.length() || c.getAnchor() < prefix.length()) {
                    rtrn = null;
                } else {
                    rtrn = c;
                }
            } else {
                rtrn = c;
            }

            return rtrn;

        };

        tf.setTextFormatter(new TextFormatter<String>(filter));

        tf.positionCaret(prefix.length());

        StackPane.setAlignment(tf, Pos.BOTTOM_CENTER);
        tf.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {

                    String txt = tf.getText().substring(2) + "\n";
                    if (txt.isBlank()) {
                        return;
                    }
                    Main.logger.appendText(txt);
                    if (Main.outputStream != null) {
                        Util.outputHandler(Main.outputStream, txt);
                    }
                    useless.set(false);
                    tf.setText(prefix);
                    tf.positionCaret(prefix.length());
                    useless.set(true);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        inputfield = tf;

        TextArea ta = new TextArea();
        ta.setWrapText(true);
        StackPane.setAlignment(ta, Pos.TOP_CENTER);
        ta.setFont(new Font("Consolas", 12));
        ta.setEditable(false);
        Main.logger = ta;
        Main.logger.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                Main.logger.setScrollTop(Double.MAX_VALUE);
            }
        });

        p1.getChildren().addAll(ta, tf);

        return p1;


    }


    public static Scene createStartupScene() throws FileNotFoundException {
        Scene toReturn = null;
        if(Saves.getValueFor("serverpath").isBlank()){
            exithandler = false;
            StackPane p1 = new StackPane();
            p1.setId("startupscene");
            stage.setTitle("ServerGUI");
            Label lb = new Label("You must choose the JAR file of your server");
            p1.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.F12) {
                    stage.hide();
                    stage.setTitle("MinecraftServerGUI v1.0 opened with debug mode");
                    try {
                        stage.setScene(createMainScene());
                        labelFix();
                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.show();
                }
            });

            Button b1 = new Button("CHOOSE");
            b1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Choose a JAR file");
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JAR Files (*.jar)", "*.jar");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showOpenDialog(stage);

                    if (file != null) {
                        File parent = file.getParentFile();
                        if (parent.isDirectory()) {
                            Main.SERVER_JAR = file;
                            Main.SERVER_JAR_PATH = file.getAbsolutePath();

                            Main.SERVER_PARENT = parent;
                            Main.SERVER_PARENT_PATH = parent.getAbsolutePath();
                            try {
                                stage.hide();
                                stage.setTitle("MinecraftServerGUI v1.0 opened with " + Main.SERVER_JAR_PATH);
                                stage.setScene(createMainScene());
                                stage.show();
                            } catch (InterruptedException | IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            });

            Button b2 = new Button("NGROK SETUP");
            b2.setOnAction(event -> {
                JavaNgrokConfig javaNgrokConfig = new JavaNgrokConfig.Builder().withNgrokVersion(NgrokVersion.V3).build();
                NgrokClient ngrk = new NgrokClient.Builder()
                        .withJavaNgrokConfig(javaNgrokConfig)
                        .build();
            });
            b2.setTranslateX(5);
            b2.setTranslateY(-5);

            StackPane.setAlignment(b2, Pos.BOTTOM_LEFT);
            StackPane.setAlignment(lb, Pos.CENTER);
            StackPane.setAlignment(b1, Pos.CENTER);
            lb.setTranslateY(-34);


            p1.getChildren().addAll(lb, b1, b2);

            Scene scene = new Scene(p1, Main.SCREEN_WIDTH / 5, Main.SCREEN_HEIGHT / 3);
            scene.getStylesheets().add("com/keremyurekli/minecraftservergui/stylesheet.css");
            toReturn = scene;
        }else{
            exithandler = true;
            File a = new File(Saves.getValueFor("serverpath"));
            File parent = a.getParentFile();
            Main.SERVER_JAR = a;
            Main.SERVER_JAR_PATH = a.getAbsolutePath();

            Main.SERVER_PARENT = parent;
            Main.SERVER_PARENT_PATH = parent.getAbsolutePath();


            try {
                stage.hide();
                toReturn = createMainScene();
                stage.setTitle("MinecraftServerGUI v1.0 opened with " + Main.SERVER_JAR_PATH);
            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }



        }


        return toReturn;

    }

    public static Scene createMainScene() throws InterruptedException, IOException {
        exithandler = true;
        SplitPane splt1 = new SplitPane();
        splt1.setOrientation(Orientation.HORIZONTAL);
        splt1.setDividerPositions(0.215);


        SplitPane splt2 = new SplitPane();
        splt2.setOrientation(Orientation.VERTICAL);
        splt2.setDividerPositions(0.2);
        splt2.getItems().addAll(createTopMenu(), createConsoleMenu());
        splt1.getItems().addAll(createLeftMenu(), splt2);


        Scene scene = new Scene(splt1, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add("com/keremyurekli/minecraftservergui/stylesheet.css");
        return scene;
    }
}
