package Controller;

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
    List<String> lstFile;

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
