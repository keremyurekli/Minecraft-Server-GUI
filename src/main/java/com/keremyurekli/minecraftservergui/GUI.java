package com.keremyurekli.minecraftservergui;

import com.github.alexdlaird.ngrok.NgrokClient;
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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

import static com.keremyurekli.minecraftservergui.Main.*;

public class GUI {

    private static Button startbutton;
    public static StackPane createTopMenu() throws IOException {

        StackPane p1 = new StackPane();
        p1.setStyle("-fx-background-color: GREEN;");
        Label lbl = new Label();
        p1.getChildren().addAll(lbl);
        lbl.setText("Cant get information from server!");
        Timeline wonder = new Timeline(
                new KeyFrame(Duration.seconds(20),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                if(NGROK_IS_RUNNING){
                                    try {
                                        Util.httpHandle();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    lbl.setText("Online Players: " + MC_ONLINE_PLAYERS +" / "+ MC_PLAYERS_MAX+"\n" +
                                            "Server Version: "+ MC_VERSION+"\n" +
                                            "Server Software: " + MC_SOFTWARE);
                                }else{
                                    lbl.setText("Cant get information from server!");
                                }

                            }
                        }));
        wonder.setCycleCount(Timeline.INDEFINITE);
        wonder.play();
        return p1;
    }


    public static StackPane createLittleMenu(){
        StackPane p1 = new StackPane();
        p1.setStyle("-fx-background-color: PURPLE;");
        return p1;
    }

    public static StackPane createLeftMenu(){

        StackPane p1 = new StackPane();
        p1.setStyle("-fx-background-color: RED;");

        Separator separator1 = new Separator();

        separator1.setMaxWidth(500);
        separator1.setScaleY(2);
        separator1.setTranslateY(20);

        Label lbl1 = new Label(Main.SERVER_JAR.getName());
        lbl1.setTranslateY(30);
        lbl1.setTranslateX(5);

        Button b1 = new Button("START SERVER");
        b1.setPrefWidth(240);
        b1.setTranslateY(60);
        startbutton = b1;


        Label lbl2 = new Label("Ram Amount(MB):");
        lbl2.setTranslateX(5);
        lbl2.setTranslateY(100);

        TextField tf1 = new TextField();
        tf1.setAlignment(Pos.CENTER);
        tf1.setPromptText("4096 by default");
        tf1.setMaxWidth(240);
        tf1.setTranslateY(130);

        Label lbl3 = new Label("Server Port:");
        lbl3.setTranslateX(5);
        lbl3.setTranslateY(170);




        TextField tf2 = new TextField();
        tf2.setAlignment(Pos.CENTER);
        tf2.setPromptText("25565 by default");
        tf2.setMaxWidth(240);
        tf2.setTranslateY(200);


        Label lbl4 = new Label("Custom Javapath:");
        lbl4.setTranslateX(5);
        lbl4.setTranslateY(240);

        TextField tf3 = new TextField();
        tf3.setAlignment(Pos.CENTER);
        tf3.setPromptText("Java installed on the system by default");
        tf3.setMaxWidth(240);
        tf3.setTranslateY(270);

        Button b2 = new Button("Open server.properties");
        b2.setPrefWidth(240);
        b2.setTranslateY(310);
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Desktop desktop = Desktop.getDesktop();
                File temp = new File(Main.SERVER_PARENT_PATH+"\\server.properties");

                if(temp.exists()) {
                    try {
                        desktop.open(temp);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        Button b3 = new Button("Open server folder");
        b3.setPrefWidth(240);
        b3.setTranslateY(340);

        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();

                if(Main.SERVER_PARENT.exists()) {
                    try {
                        desktop.open(Main.SERVER_PARENT);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });





        Separator separator2 = new Separator();

        separator2.setMaxWidth(500);
        separator2.setScaleY(2);
        separator2.setTranslateY(380);

        Label lbl5 = new Label("Portforwarding Service:");
        lbl5.setTranslateX(5);
        lbl5.setTranslateY(410);

        ComboBox cmb1 = new ComboBox();
        cmb1.setTranslateY(440);
        cmb1.getItems().addAll("ngrok (default)");
        cmb1.setValue("ngrok (default)");

        TextField tf4= new TextField();
        tf4.setAlignment(Pos.CENTER);
        tf4.setPromptText("ENTER YOUR NGROK AUTH TOKEN");
        tf4.setMaxWidth(240);
        tf4.setTranslateY(470);
        if(!Util.readNgrok().isEmpty() || !Util.readNgrok().isBlank()){
            tf4.setText(Util.readNgrok());
        }

        Button b4 = new Button("START PORTFORWARDING");
        b4.setPrefWidth(240);
        b4.setTranslateY(510);


        Label lbl6 = new Label("Public Ip Address:");
        lbl6.setTranslateX(5);
        lbl6.setTranslateY(540);

        TextField tf5= new TextField();
        tf5.setAlignment(Pos.CENTER);
        tf5.setText("PORTFORWARDING IS NOT ENABLED");
        tf5.setEditable(false);
        tf5.setMaxWidth(240);
        tf5.setTranslateY(570);

        StackPane littleMenu = createLittleMenu();
        littleMenu.setTranslateY(600);


        b4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(cmb1.getValue().equals("ngrok (default)")) {

                    if (Main.PROCESS != null) {
                        String port = "25565";
                        if(!tf2.getText().isEmpty()){
                            port = tf2.getText();
                        }
                        if(tf4.getText().isEmpty()){
                            tf4.requestFocus();
                            return;
                        }
                        if (NGROK_IS_RUNNING) {

                            b4.setText("START PORTFORWARDING");

                        } else {

                            b4.setText("STOP PORTFORWARDING");

                        }
                        String str = Util.ngrokSwitch(port, tf4.getText());
                        if(str == null){
                            tf5.setText("PORTFORWARDING IS NOT ENABLED");
                        }else{
                            tf5.setText(str);
                        }
                    }else{
                        b1.requestFocus();
                    }
                }

            }
        });





        p1.getChildren().addAll(separator1,lbl1,b1,lbl2,tf1,lbl3,tf2,b2,b3,separator2,lbl4,lbl5,tf3,cmb1,b4,lbl6,tf4,tf5,littleMenu);
        for(Node n : p1.getChildren()){
            p1.setAlignment(n,Pos.TOP_CENTER);
        }



        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Main.logger.setScrollTop(Double.MAX_VALUE);

                int ram = 4096;
                if(!tf1.getText().isEmpty()){
                    ram = Integer.parseInt(tf1.getText());
                }
                String javapath = null;
                if(!tf3.getText().isEmpty()){
                    javapath = tf3.getText();
                }

                if(Main.PROCESS == null || !Main.PROCESS.isAlive()){
                    try {
                        Main.PROCESS = Util.initProcess(ram,javapath);
                        b1.setText("STOP SERVER");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                }else{
                    try {
                        Util.outputHandler(Main.outputStream, "stop\n");
                        b1.setText("START SERVER");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });



        return p1;

    }

    public static void buttonUpdate(){
        Platform.runLater(() -> {
            startbutton.setText("START SERVER");
        });
    }

    public static StackPane createConsoleMenu() throws InterruptedException {

        StackPane p1 = new StackPane();

        TextField tf = new TextField();
        p1.setAlignment(tf, Pos.BOTTOM_CENTER);
        tf.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    String txt = tf.getText()+"\n";

                    Main.logger.appendText(txt);
                    if(Main.outputStream!=null){
                        Util.outputHandler(Main.outputStream, txt);
                    }
                    tf.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        TextArea ta = new TextArea();
        ta.setFont(new Font("Consolas",12));
        ta.setStyle("-fx-control-inner-background: #2B2B2B;");
        ta.setEditable(false);
        Main.logger = ta;
        Main.logger.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                Main.logger.setScrollTop(Double.MAX_VALUE);
            }
        });

        p1.getChildren().addAll(ta, tf);

        return p1;


    }


    public static Scene createStartupScene(Stage stage){
        StackPane p1 = new StackPane();
        Label lb = new Label("You must choose the JAR file of your server");
        p1.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.F12){
                stage.hide();
                stage.setTitle("ServerGUI v1.0 opened with debug mode");
                try {
                    stage.setScene(createMainScene());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.show();
            }
        });

        Button b1 = new Button("CHOOSE");
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose a JAR file");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("JAR Files (*.jar)", "*.jar");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(stage);

                if (file != null) {
                    File parent = file.getParentFile();
                    if(parent.isDirectory()){
                        Main.SERVER_JAR = file;
                        Main.SERVER_JAR_PATH = file.getAbsolutePath();

                        Main.SERVER_PARENT = parent;
                        Main.SERVER_PARENT_PATH = parent.getAbsolutePath();
                        try {
                            stage.hide();
                            stage.setTitle("ServerGUI v1.0 opened with "+ Main.SERVER_JAR_PATH);
                            stage.setScene(createMainScene());
                            stage.show();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        p1.setAlignment(lb, Pos.CENTER);
        lb.setTranslateY(-34);
        p1.setAlignment(b1,Pos.CENTER);


        p1.getChildren().addAll(lb,b1);

        Scene scene = new Scene(p1, Main.SCREEN_WIDTH/5, Main.SCREEN_HEIGHT/3);
        return scene;
    }

    public static Scene createMainScene() throws InterruptedException, IOException {

        SplitPane splt1 = new SplitPane();
        splt1.setOrientation(Orientation.HORIZONTAL);
        splt1.setDividerPositions(0.2);



        SplitPane splt2 = new SplitPane();
        splt2.setOrientation(Orientation.VERTICAL);
        splt2.setDividerPositions(0.2);
        splt2.getItems().addAll(createTopMenu(),createConsoleMenu());
        splt1.getItems().addAll(createLeftMenu(),splt2);


        Scene scene = new Scene(splt1, SCREEN_WIDTH, SCREEN_HEIGHT);
        return scene;
    }
}
