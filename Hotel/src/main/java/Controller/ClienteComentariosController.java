package Controller;

import BLL.ComentarioBLL;
import BLL.QuartoBLL;
import BLL.UtilizadorPreferences;
import Model.Cartao;
import Model.Comentario;
import Model.MessageBoxes;
import Model.Quarto;
import com.example.hotel.Main;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClienteComentariosController implements Initializable {


    @FXML
    private Text numLetras;

    @FXML
    private Button Submeter;

    @FXML
    private Button Voltar;

    @FXML
    private TextArea campoComentario;

    @FXML
    private ComboBox<String> tipoComentario;

    private ComentarioBLL comentarioBLL = new ComentarioBLL();

    @FXML
    void SubmeterClick(ActionEvent event) {
        try {
            if (campoComentario.getText() == null || campoComentario.getText().trim().isEmpty()) {
                MessageBoxes.ShowMessage(Alert.AlertType.WARNING, "O campo de mensagem não pode ser vazio.", "Erro no formulário.");
                return;
            }

            Comentario comentario = new Comentario(
                    null,
                    UtilizadorPreferences.utilizadorId(),
                    campoComentario.getText(),
                    verificaTipoComentario());

            comentarioBLL.addComentario(comentario);
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Comentário inserido com sucesso", "Informação!");
            campoComentario.setText("");
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Não foi possível inserir o comentátrio.", "Erro!");
            throw new RuntimeException(e);
        }
    }

    private void contaLetras() {
        int tamanhoTexto = 300;
        campoComentario.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > tamanhoTexto) campoComentario.setText(oldValue);
                }
        );
        numLetras.textProperty().bind(Bindings.length(campoComentario.textProperty())
                .asString("%d"));
    }


    @FXML
    void VoltarClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCliente.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) Voltar.getScene().getWindow();
        stage.setTitle("Pagina Cliente");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    private String verificaTipoComentario() {
        String comentario;
        switch (tipoComentario.getItems().toString()) {
            case "Sugestão" -> {
                comentario = "sugestao";
                return comentario;
            }
            case "Queixa" -> {
                comentario = "queixa";
                return comentario;
            }
            default -> MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Escolha um tipo de comentário", "Aviso:");
        }
        return null;
    }

    private void initCombos() {
        tipoComentario.getItems().add("Sugestão");
        tipoComentario.getItems().add("Queixa");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contaLetras();
        initCombos();
    }
}
