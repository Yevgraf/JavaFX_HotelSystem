package Controller;

import BLL.DBconn;
import BLL.XMLReader;
import Model.EntradaStock;

import com.example.hotel.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CarregarXML implements Initializable {

    private ObservableList<EntradaStock> stock;
    private ObservableList<EntradaStock> head;
    @FXML
    private TableView<EntradaStock> table;
    @FXML
    private TableColumn<EntradaStock, Integer> caixasTable;

    @FXML
    private TableColumn<EntradaStock, String> descricaoTable;

    @FXML
    private TableColumn<EntradaStock, String> identificacaoTable;

    @FXML
    private TableColumn<EntradaStock, String> localTable;

    @FXML
    private TableColumn<EntradaStock, Double> pesoTable;

    @FXML
    private TableColumn<EntradaStock, Double> precoSemTaxaTable;

    @FXML
    private TableColumn<EntradaStock, Double> precoUnidadeTable;

    @FXML
    private TableColumn<EntradaStock, Double> taxaTable;

    @FXML
    private TableColumn<EntradaStock, Integer> unidadesTable;

    @FXML
    private TableColumn<EntradaStock, Double> valorTaxaTable;

    @FXML
    private TableColumn<EntradaStock, Double> precoTotalTable;

    @FXML
    private Text moradaTxt;

    @FXML
    private Text noneFornecedorTxt;

    @FXML
    private Text ordemTxt;

    @FXML
    private Text paisTxt;

    @FXML
    private Text idFornecedorTxt;

    @FXML
    private Text codigoPostalTxt;

    @FXML
    private Text dataTxt;


    @FXML
    private Text urlText;

    @FXML
    private Button xmlBtn;

    XMLReader xmlreader;

    @FXML
    private Text cidadeTxt;

    @FXML
    private Button addItensBtn;

    @FXML
    private Button voltarBtn;

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    List<String> lstFile;

    @FXML
    void clickAddItens(ActionEvent event) {
        clickAddFornecedor();
        clickAddItensStock();
        clickAddItensEntradaStock();
    }

    //----------------------------------- Conexão BD - Stock -----------------------------------

    void clickAddItensStock() {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Stock(identificacao, descricao, peso, precoUnidade, unidades)" +
                    "VALUES (?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < stock.size(); i++) {
                ps2.setString(1, stock.get(i).getIdentificacao());
                ps2.setString(2, stock.get(i).getDescricao());
                ps2.setDouble(3, stock.get(i).getPeso());
                ps2.setDouble(4, stock.get(i).getPrecoUnidade());
                ps2.setInt(5, stock.get(i).getUnidades());
                ps2.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //----------------------------------- Conexão BD - Fornecedor -----------------------------------

    void clickAddFornecedor() {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Fornecedor(id, nome, morada, codigoPostal, pais, cidade)" +
                    "VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < head.size(); i++) {
                ps2.setString(1, head.get(i).getIdFornecedor());
                ps2.setString(2, head.get(i).getNomeFornecedor());
                ps2.setString(3, head.get(i).getMoradaFornecedor());
                ps2.setString(4, head.get(i).getCodPostalFornecedor());
                ps2.setString(5, head.get(i).getPaisFornecedor());
                ps2.setString(6, head.get(i).getCidadeFornecedor());
                ps2.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //----------------------------------- Conexão BD - EntradaStock -----------------------------------

    void clickAddItensEntradaStock() {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO EntradaStock(identificacao, descricao, caixas, peso, unidades," +
                    "precoUnidade, precoSemTaxa, taxa, valorTaxa, localTaxa, precoTotal, ordemNumero, ordemData,idFornecedor)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < stock.size(); i++) {
                ps2.setString(1, stock.get(i).getIdentificacao());
                ps2.setString(2, stock.get(i).getDescricao());
                ps2.setInt(3, stock.get(i).getCaixas());
                ps2.setDouble(4, stock.get(i).getPeso());
                ps2.setInt(5, stock.get(i).getUnidades());
                ps2.setDouble(6, stock.get(i).getPrecoUnidade());
                ps2.setDouble(7, stock.get(i).getPrecoSemTaxa());
                ps2.setDouble(8, stock.get(i).getTaxa());
                ps2.setDouble(9, stock.get(i).getValorTaxa());
                ps2.setString(10, stock.get(i).getLocal());
                ps2.setDouble(11, stock.get(i).getPrecoTotal());
                 for (int j = 0; j < 1; j++) {
                     ps2.setString(12, head.get(j).getOrdemNum());
                     ps2.setString(13, head.get(j).getOrdemData());
                     ps2.setString(14, head.get(j).getIdFornecedor());

                 }
                ps2.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //----------------------------------- Upload Ficheiro XML -----------------------------------

    @FXML
    void clickXmlBtn(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", lstFile));
        File f = fc.showOpenDialog(null);
        if (f != null) {
            urlText.setText("Ficheiro selecionado: " + f.getAbsolutePath());
            String path = f.getAbsolutePath();
            stock = xmlreader.lerXMLBody(path);
            head = xmlreader.lerXMLHeader(path);
            if (stock.isEmpty() && head.isEmpty()) {
                addItensBtn.setDisable(true);
            } else {
                addItensBtn.setDisable(false);
                popularTabela();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        xmlreader = new XMLReader();
        lstFile = new ArrayList<>();
        lstFile.add("*.xml");
        lstFile.add("*.XML");
    }

    //----------------------------------- Popular Tabela -----------------------------------

    private void popularTabela() {

        //------- Popular Rows da Tabela -------

        if (stock != null && stock.size() > 0) {
            identificacaoTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, String>("identificacao"));
            descricaoTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, String>("descricao"));
            taxaTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, Double>("taxa"));
            caixasTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, Integer>("caixas"));
            localTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, String>("local"));
            pesoTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, Double>("peso"));
            precoSemTaxaTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, Double>("precoSemTaxa"));
            precoUnidadeTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, Double>("precoUnidade"));
            unidadesTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, Integer>("unidades"));
            valorTaxaTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, Double>("valorTaxa"));
            precoTotalTable.setCellValueFactory(new PropertyValueFactory<EntradaStock, Double>("precoTotal"));

            table.setItems(stock);

            //------- Popular Header -------

            for (int i = 0; i < head.size(); i++) {
                ordemTxt.setText(head.get(i).getOrdemNum());
                moradaTxt.setText(head.get(i).getMoradaFornecedor());
                noneFornecedorTxt.setText(head.get(i).getNomeFornecedor());
                paisTxt.setText(head.get(i).getPaisFornecedor());
                idFornecedorTxt.setText(head.get(i).getIdFornecedor());
                codigoPostalTxt.setText(head.get(i).getCodPostalFornecedor());
                dataTxt.setText(head.get(i).getOrdemData());
                cidadeTxt.setText(head.get(i).getCidadeFornecedor());

            }
        }
    }

}
