package BLL;

import DAL.ProdutoDAL;
import Model.Produto;
import javafx.collections.ObservableList;

public class ProdutoBLL {
    public void addProduto(ObservableList<Produto> produtos) {

        ProdutoDAL pdal = new ProdutoDAL();
        pdal.addProduto(produtos);

    }
}
