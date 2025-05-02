module m7xed.chess {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens app to javafx.fxml;
    exports app;
}