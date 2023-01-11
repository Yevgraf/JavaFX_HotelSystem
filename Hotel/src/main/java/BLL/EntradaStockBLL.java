package BLL;

import DAL.EntradaStockDAL;
import DAL.ReservaDAL;
import Model.EntradaStock;
import Model.Stock;
import javafx.collections.ObservableList;

public class EntradaStockBLL {
    public void addEntradaStock(ObservableList<EntradaStock> entradaStocks,
                                ObservableList<EntradaStock> fornecedores,
                                ObservableList<Stock> stocks) {

        EntradaStockDAL esdal = new EntradaStockDAL();
        esdal.addEntradaStock(entradaStocks, fornecedores, stocks);

    }

    public Boolean verificaSeExisteEncomendaRepetida(String idEncomenda) {
        EntradaStockDAL entradaDAL = new EntradaStockDAL();
        if (entradaDAL.verificaSeExisteEncomendaRepetida(idEncomenda)) {
            return true;
        } else {
            return false;
        }
    }
}
