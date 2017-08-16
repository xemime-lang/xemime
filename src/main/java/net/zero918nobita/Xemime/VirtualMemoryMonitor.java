package net.zero918nobita.Xemime;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VirtualMemoryMonitor extends Application implements Runnable {

    public void run() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Label label = new Label("Virtual Memory Monitor");
        BorderPane pane = new BorderPane();
        pane.setCenter(label);
        Scene scene = new Scene(pane, 400, 450);
        stage.setTitle("Xemime - Virtual Memory Monitor");
        stage.setScene(scene);
        stage.show();
    }
}
