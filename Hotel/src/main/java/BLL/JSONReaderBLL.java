package BLL;

import Model.EntradaStock;
import Model.Fornecedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONReaderBLL {
    public ObservableList<EntradaStock> lerHeader(String path) throws IOException {
        ObservableList<EntradaStock> fornecedores = FXCollections.observableArrayList();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new File(path));

        // access elements of JSON file
        JsonNode orderNode = rootNode.path("Order");
        JsonNode headerNode = orderNode.path("Header");
        String ordemNum = headerNode.path("OrderNumber").asText();
        String ordemData = headerNode.path("OrderDate").asText();
        JsonNode supplierPartyNode = headerNode.path("SupplierParty");
        String idFornecedor = supplierPartyNode.path("PartyIdentifier").asText();
        JsonNode addressNode = supplierPartyNode.path("NameAddress");
        String nomeFornecedor = addressNode.path("Name").asText();
        String moradaFornecedor = addressNode.path("Address1").asText();
        String cidadeFornecedor = addressNode.path("City").asText();
        String codPostalFornecedor = addressNode.path("PostalCode").asText();
        JsonNode countryNode = addressNode.path("Country");
        String paisFornecedor = countryNode.path("Value").asText();





        Fornecedor fornecedor = new Fornecedor(idFornecedor, nomeFornecedor, moradaFornecedor, codPostalFornecedor, paisFornecedor, cidadeFornecedor);
        fornecedores.add(new EntradaStock(ordemNum, ordemData, fornecedor));

        return fornecedores;
    }

    //-----------------------------------------------------------------------------------------------------


    public ObservableList<EntradaStock> lerBody(String path) throws IOException {
        ObservableList<EntradaStock> item = FXCollections.observableArrayList();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new File(path));

        // access elements of JSON file
        JsonNode lineNode = rootNode.path("LineNumber");
        for (JsonNode node : lineNode) {
            JsonNode productNode = node.path("Product");
            JsonNode productIdentifierNode = productNode.path("ProductIdentifier").get(0);
            String idProduto = productIdentifierNode.path("Code").asText();
            String productDescription = productNode.path("ProductDescription").asText();
            JsonNode quantityNode = node.path("Quantity").path("Value");
            String uom = quantityNode.path("UOM").asText();
            int unidades = quantityNode.path("Value").asInt();
            JsonNode informationalQuantityNode = node.path("InformationalQuantity").get(0).path("Value");
            String informationalUom = informationalQuantityNode.path("UOM").asText();
            int informationalValue = informationalQuantityNode.path("Value").asInt();
            JsonNode pricePerUnitNode = node.path("PricePerUnit");
            JsonNode currencyValueNode = pricePerUnitNode.path("CurrencyValue");
            String currencyType = currencyValueNode.path("CurrencyType").asText();
            double currencyValue = currencyValueNode.path("Value").asDouble();
            JsonNode valueNode = pricePerUnitNode.path("Value");
            JsonNode monetaryAdjustmentNode = node.path("MonetaryAdjustment");
            JsonNode monetaryAdjustmentStartAmountNode = monetaryAdjustmentNode.path("MonetaryAdjustmentStartAmount").path("CurrencyValue");
            double precoSemTaxa = monetaryAdjustmentStartAmountNode.path("Value").asDouble();
            JsonNode taxAdjustmentNode = monetaryAdjustmentNode.path("TaxAdjustment");
            Double taxa = taxAdjustmentNode.path("TaxPercent").asDouble();
            JsonNode taxAmountNode = taxAdjustmentNode.path("TaxAmount").path("CurrencyValue");
            double valorTaxa = taxAmountNode.path("Value").asDouble();
            String local = taxAdjustmentNode.path("TaxLocation").asText();
            JsonNode lineBaseAmountNode = node.path("LineBaseAmount").path("CurrencyValue");
            double precoTotal = lineBaseAmountNode.path("Value").asDouble();


            item.add(new EntradaStock(taxa, 0, idProduto, local, precoSemTaxa, unidades, valorTaxa, precoTotal));
        }
        return item;
    }
}

/* "LineNumber": 1,
        "Product": {
          "ProductIdentifier": [
            {
              "Agency": "Supplier",
              "ProductIdentifierType": "PartNumber",
              "Code": "63279196"
            }
          ],
          "ProductDescription": "Croissant Brioche délifrance"
        },
		"Quantity": {
          "Value": {
            "UOM": "Unit",
            "Value": 10000
          }
        },
        "InformationalQuantity": [
          {
            "Value": {
              "UOM": "Kilogram",
              "Value": 600
            }
          }
        ],
         "PricePerUnit": {
            "CurrencyValue": {
              "CurrencyType": "EUR",
              "Value": 0.25
            },
            "Value": {
              "UOM": "Unit",
              "Value": 1
            }
         },
        "MonetaryAdjustment": {
          "AdjustmentType": "Tax",
          "MonetaryAdjustmentLine": 1,
          "MonetaryAdjustmentStartAmount": {
            "CurrencyValue": {
              "CurrencyType": "EUR",
              "Value": 2500
            }
          },
          "TaxAdjustment": {
            "TaxType": "VAT",
            "TaxPercent": 20,
            "TaxAmount": {
              "CurrencyValue": {
                "CurrencyType": "EUR",
                "Value": 500
              }
            },
            "TaxLocation": "FR"
          }
        },

        "LineBaseAmount": {
          "CurrencyValue": {
            "CurrencyType": "EUR",
            "Value": 3000
          }
        }
      }, */

