package net.zero918nobita.Xemime;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 仮想メモリモニタ
 * インタプリタとは別のスレッドで起動し、インタプリタ側で Xemime コードが実行されている間、
 * フレーム・実体の管理、変数宣言、関数呼び出しを可視化します。(デバッグ用です)
 * @author Kodai Matsumoto
 */

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
