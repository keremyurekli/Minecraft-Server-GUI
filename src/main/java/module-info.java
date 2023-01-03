module com.keremyurekli.minecraftservergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.management;
    requires com.google.gson;
    requires java.ngrok;
    requires java.desktop;


    opens com.keremyurekli.minecraftservergui to javafx.fxml;
    exports com.keremyurekli.minecraftservergui;
}