package HelperFunctions;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Window {

    /**
     *
     * @param event show window
     * @param windowName name of window to show
     */
    public static void show(ActionEvent event, String windowName) {
        try {
            Parent loader = FXMLLoader.load(Objects.requireNonNull(Window.class.getResource(windowName)));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}