package BLL;

import DAL.FornecedorDAL;
import Model.EntradaStock;
import javafx.collections.ObservableList;

public class FornecedorBLL {
    public void addFornecedor(ObservableList<EntradaStock> fornecedores) {

        FornecedorDAL fdal = new FornecedorDAL();
        fdal.addFornecedor(fornecedores);

    }
}
