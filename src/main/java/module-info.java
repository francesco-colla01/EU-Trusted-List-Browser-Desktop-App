module it.markovii.graphics.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.json;
    requires com.google.common;

    opens project.graphics.demo to javafx.fxml;
    exports project.graphics.demo;
}