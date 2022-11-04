package Controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class switchScreen {
    public void changeScreen(ActionEvent event, String path, int width, int height, String title, String imgPath) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(second);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.setMaxHeight(height);
        window.setMinHeight(height);
        window.setMaxWidth(width);
        window.setMinWidth(width);
        window.setTitle(title);
        window.show();
    }

    public static void Load_Error_Alert(String Window_Name, String Content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("in loading Next Window : " + Window_Name);
        alert.setContentText(Content);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new Image("/img/WARNING_PNG.png"));
        alert.showAndWait();
    }
}
