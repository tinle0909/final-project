module test.project.trackinganime {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens test.project.trackinganime to javafx.fxml;
    exports test.project.trackinganime;
}