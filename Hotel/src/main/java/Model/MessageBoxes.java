package Model;

import javafx.scene.control.Alert;

public class MessageBoxes {
    public static void ShowMessage(Alert.AlertType type, String msg, String header) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
