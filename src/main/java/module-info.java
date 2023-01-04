module com.keremyurekli.minecraftservergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.ngrok;
    requires java.desktop;
    requires commons.io;


    opens com.keremyurekli.minecraftservergui to javafx.fxml;
    exports com.keremyurekli.minecraftservergui;
}