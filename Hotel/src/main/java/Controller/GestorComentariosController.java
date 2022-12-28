package Controller;

import BLL.ComentarioBLL;
import BLL.UtilizadorBLL;
import DAL.ComentarioDAL;
import DAL.ProdutoDAL;
import Model.Comentario;
import Model.Produto;
import com.example.hotel.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestorComentariosController implements Initializable {

    @FXML
    private Button Submeter;

    @FXML
    private Button Voltar;

    @FXML
    private TableColumn<Comentario, String> comentario;

    @FXML
    private TextArea comentarioArea;

    @FXML
    private TableView<Comentario> comentarioTable;

    @FXML
    private TableColumn<Comentario, Integer> idCliente;

    @FXML
    private TableColumn<Comentario, Integer> idTable;

    @FXML
    void VoltarClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) Voltar.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void comentarioTableClick(MouseEvent event) throws SQLException {
        ComentarioDAL cdal = new ComentarioDAL();
        Comentario selectedID = comentarioTable.getSelectionModel().getSelectedItem();
        if (selectedID != null) {
            comentarioArea.setText(String.valueOf(cdal.getComentario(selectedID)));
        }
    }

    private void initTable() {
        idTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        comentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));
        comentarioTable.setItems(ComentarioBLL.getAllComentarios());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}
