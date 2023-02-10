package com.keremyurekli.minecraftservergui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.fxmisc.richtext.CodeArea;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import static com.keremyurekli.minecraftservergui.Main.*;

public class PropertyViewer {

    PropertyViewer(File file) throws FileNotFoundException {

        StackPane p = new StackPane();

//        TextArea textArea = new TextArea();
//        textArea.setFont(new Font("Consolas", 12));
//
//
//            StringBuilder bl = new StringBuilder();
//            Scanner myReader = new Scanner(file);
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                bl.append(data + "\n");
//            }
//            myReader.close();
//            String string = bl.toString();
//            textArea.setText(string);


        //
        CodeArea codeArea= new CodeArea();


        StringBuilder bl = new StringBuilder();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            bl.append(data + "\n");
        }
        myReader.close();
        String string = bl.toString();
        codeArea.replaceText(string);


        String[] lines1 = codeArea.getText().split("\n");



        for (String line : lines1) {
            if (line.contains("=")) {
                String[] parts = line.split("=");

                int startIndex = string.indexOf(parts[0]);
                int endIndex = startIndex + parts[0].length();
                codeArea.setStyle(startIndex, endIndex, Collections.singleton("purple"));

                if (parts.length == 2) {
                    startIndex = string.indexOf(parts[1], endIndex);
                    endIndex = startIndex + parts[1].length();
                    codeArea.setStyle(startIndex, endIndex, Collections.singleton("blue"));
                }
            }
        }

        //

        codeArea.setOnKeyTyped(keyEvent -> {
            codeArea.clearStyle(0, codeArea.getLength());
            String[] lines = codeArea.getText().split("\n");
            for (String line : lines) {
                if (line.contains("=")) {
                    String[] parts = line.split("=");

                    int startIndex = codeArea.getText().indexOf(parts[0]);
                    int endIndex = startIndex + parts[0].length();
                    codeArea.setStyle(startIndex, endIndex, Collections.singleton("purple"));

                    if (parts.length == 2) {
                        startIndex = codeArea.getText().indexOf(parts[1], endIndex);
                        endIndex = startIndex + parts[1].length();
                        codeArea.setStyle(startIndex, endIndex, Collections.singleton("blue"));
                    }
                }
            }
        });




        p.getChildren().addAll(codeArea);

        Scene sc = new Scene(p,1280,720);
        Stage secondStage = new Stage();
        sc.getStylesheets().add("com/keremyurekli/minecraftservergui/editor.css");
        secondStage.setTitle(file.getName() + " / " + "CTRL+S for save, or just close the window");
        secondStage.setScene(sc);
        secondStage.show();



        secondStage.setOnCloseRequest(windowEvent -> {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(codeArea.getText());
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        secondStage.getScene().getAccelerators().put(
                KeyCombination.keyCombination("CTRL+S"),
                new Runnable() {
                    @Override
                    public void run() {
                        SavedScreen(p);
                        BufferedWriter writer = null;
                        try {
                            writer = new BufferedWriter(new FileWriter(file));
                            writer.write(codeArea.getText());
                            writer.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );



    }



    private static void SavedScreen(Pane pan){

        StackPane p = new StackPane();
        pan.getChildren().addAll(p);
        p.setStyle("-fx-background-color: white;");
        p.setOpacity(0);



        Text text = new Text("Saved!");
        text.setFont(new Font("", 20));

        p.getChildren().add(text);


        // Create a Timeline to animate the opacity of the Pane
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(p.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(p.opacityProperty(), 0.3)),
                new KeyFrame(Duration.seconds(0.6), new KeyValue(p.opacityProperty(), 0))
        );
        timeline.setCycleCount(1);
        timeline.play();

        timeline.setOnFinished(actionEvent -> {
            pan.getChildren().remove(p);
        });




    }
}
