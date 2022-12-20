module com.keremyurekli.minecraftservergui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.keremyurekli.minecraftservergui to javafx.fxml;
    exports com.keremyurekli.minecraftservergui;
}