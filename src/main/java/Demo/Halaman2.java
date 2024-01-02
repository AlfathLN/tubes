package Demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Halaman2 extends Application {

    public static Scene createScene() {
        VBox layout = new VBox(10);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);



        return new Scene(layout, 300, 200);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Halaman 2");

        // Membuat objek Scene menggunakan metode createScene
        Scene scene = createScene();

        // Menetapkan Scene ke primaryStage
        primaryStage.setScene(scene);

        // Menampilkan primaryStage
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
