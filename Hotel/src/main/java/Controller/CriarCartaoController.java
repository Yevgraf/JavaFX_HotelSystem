package Controller;

import Model.Cartao;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CriarCartaoController implements Initializable {

  @FXML
  private AnchorPane PainelCartao;

  @FXML
  private ImageView btnBack;

  @FXML
  private ImageView btnCloseApp;

  @FXML
  private ImageView btnDefGestor;

  @FXML
  private ImageView btnLogOut;

  @FXML
  private ImageView btnMinimizateApp;

  @FXML
  private Button btn_add_Cartao;

  @FXML
  private Button btn_remove_Cartao;

  @FXML
  private Button btn_update_Cartao;

  @FXML
  private Button btn_update_Voltar;

  @FXML
  private ComboBox<?> cmb_CartaoMestre;

 @FXML
 private ImageView imgGestorAdionarProd;

 @FXML
 private ImageView imgGestorGestaoProduto;

 @FXML
 private Label lblGestaoProdutos;

 @FXML
  private Label lblSamos;

  @FXML
  private TableColumn<Cartao, Boolean> tbl_cartaoMestre;

  @FXML
  private TableColumn<Cartao, String> tbl_numCartao;

  @FXML
  private TableView<Cartao> tv_cartao;

  @FXML
  private TextField txt_numCartao;

           @FXML
       void onActionCmbCartaoMestre(ActionEvent event) {

       }

       @FXML
       void onActionRemoveCartao(ActionEvent event) {

       }

       @FXML
       void onActionUpdateCartao(ActionEvent event) {

       }

       @FXML
       void onActionVoltar(ActionEvent event) throws IOException {
           FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
           Stage stage = new Stage();
           Stage newStage = (Stage) btn_update_Voltar.getScene().getWindow();
           stage.setTitle("Pagina GestorController");
           newStage.hide();
           stage.setScene(new Scene(fxmlLoader.load()));
           stage.show();
       }

       @Override
       public void initialize(URL location, ResourceBundle resources) {
           initCombos();



       }

       private void initCombos() {



       }

  }

