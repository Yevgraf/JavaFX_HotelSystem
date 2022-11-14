package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


import java.io.File;

public class UploadXML {
    @FXML
    private Button xmlBtn;

    @FXML
    private Text urlText;

    @FXML
    void btnUploadXML(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml" ));
        File f = fc.showOpenDialog(null);
        if (f != null){
            urlText.setText("Ficheiro selecionado: " + f.getAbsolutePath());
        }

    }

}
