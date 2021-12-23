package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;
import Database.DatabaseConnection;

// JavaDocs Located at project_root/JavaDocs

public class Main extends Application {

    /**
     *
     * @param primaryStage primary stage
     * @throws Exception exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Login.fxml")));
        primaryStage.setScene(new Scene(root, 310, 240));
        primaryStage.show();
    }

    /**
     *
     * @param args args
     */
    public static void main(String[] args) {
        DatabaseConnection.connect();
        launch(args);
    }
}
