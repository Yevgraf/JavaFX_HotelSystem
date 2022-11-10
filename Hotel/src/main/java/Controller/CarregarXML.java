package Controller;

import BLL.DBconn;
import BLL.XMLReader;
import Model.EntradaStock;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CarregarXML implements Initializable {

    private ObservableList<EntradaStock> stock;
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
    private Text urlText;

    @FXML
    private Button xmlBtn;

    XMLReader xmlreader;

    @FXML
    private Button addItensBtn;

    List<String> lstFile;

   //identificacao VARCHAR (100),
   //descricao VARCHAR (30),
   //peso DECIMAL (4, 2),
   //precoUnidade DECIMAL (7, 2),
   //unidades INT
    @FXML
    void clickAddItens(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Stock(identificacao, descricao, peso, precoUnidade, unidades) VALUES (?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i <stock.size(); i++) {
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

    @FXML
    void clickXmlBtn(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", lstFile));
        File f = fc.showOpenDialog(null);
        if (f != null) {
            urlText.setText("Ficheiro selecionado: " + f.getAbsolutePath());
            String path = f.getAbsolutePath();
            stock = xmlreader.lerXML(path);
            popularTabela();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        xmlreader = new XMLReader();
        lstFile = new ArrayList<>();
        lstFile.add("*.xml");
        lstFile.add("*.XML");
    }

    private void popularTabela() {
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
        }
    }

}
