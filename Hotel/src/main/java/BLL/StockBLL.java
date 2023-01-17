package BLL;

import DAL.StockDAL;

public class StockBLL {

    public boolean verificaSeProdutoTemQuantidadeSuficiente(String idProduto, int quantDesejada) {
        StockDAL sDAL = new StockDAL();
        if (sDAL.verificaSeProdutoTemQuantidadeSuficiente(idProduto, quantDesejada) != null) {
            return true;
        } else {
            return false;
        }
    }
}
