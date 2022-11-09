package BLL;

import Controller.CarregarXML;
import Model.EntradaStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

public class XMLReader {

    public ObservableList<EntradaStock> lerXML(String path) {
        ObservableList<EntradaStock> item = FXCollections.observableArrayList();

        try {
            File fXmlFile = new File(path);

            //Define a API que instancia o documento XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //Normaliza o XML
            doc.getDocumentElement().normalize();

            //Grava numa NodeList
            NodeList nList = doc.getElementsByTagName("Line");

            //Percorre a lista e faz o Get pelas respetivas Tags
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    // Line
                    Element lineElement = (Element) nNode;

                    // Product
                    Element productElement = FindInChildren(lineElement, "Product");
                    Element productIdentifierEl = FindInChildren(productElement, "ProductIdentifier");
                    Element productDescriptionEl = FindInChildren(productElement, "ProductDescription");
                    String identificacao = productIdentifierEl.getTextContent();
                    String descricao = productDescriptionEl.getTextContent();

                    // Quantity
                    Element elQuantity = FindInChildren(lineElement, "Quantity");
                    Element elQuantityValue = FindInChildren(elQuantity, "Value");
                    Integer caixas = Integer.valueOf(elQuantityValue.getTextContent());

                    // InformationalQuantity
                    Element iqWeightElement = FindInChildren(lineElement, "InformationalQuantity");
                    Element iQWeightValueElement = FindInChildren(iqWeightElement, "Value");
                    double peso = Double.parseDouble(iQWeightValueElement.getTextContent());

                    Element iqUnitsElement = FindInChildren(lineElement, "InformationalQuantity", 10);
                    Integer unidades = 0;
                    if (iqUnitsElement != null) {
                        Element iQUnitsValueElement = FindInChildren(iqUnitsElement, "Value");
                        unidades = Integer.valueOf(iQUnitsValueElement.getTextContent());
                    }

                    // PricePerUnit
                    Element ppuElement = FindInChildren(lineElement, "PricePerUnit");
                    Element ppuCurrencyValueElement = FindInChildren(ppuElement, "CurrencyValue");
                    Element ppuValueElement = FindInChildren(ppuElement, "Value");
                    //Integer unidades = Integer.valueOf(ppuValueElement.getTextContent());
                    double precoUnidade = Double.parseDouble(ppuCurrencyValueElement.getTextContent());

                    // MonetaryAdjustment
                    Element monAdjElement = FindInChildren(lineElement, "MonetaryAdjustment");
                    Element monAdjStartElement = FindInChildren(monAdjElement, "MonetaryAdjustmentStartAmount");
                    Element monAdjStartValueElement = FindInChildren(monAdjStartElement, "CurrencyValue");
                    Element monAdjTaxElement = FindInChildren(monAdjElement, "TaxAdjustment");
                    Element monAdjTaxPercentElement = FindInChildren(monAdjTaxElement, "TaxPercent");
                    Element monAdjTaxLocationElement = FindInChildren(monAdjTaxElement, "TaxLocation");
                    Element monAdjTaxAmountElement = FindInChildren(monAdjTaxElement, "TaxAmount");
                    Element monAdjTaxAmountValueElement = FindInChildren(monAdjTaxAmountElement, "CurrencyValue");
                    double precoSemTaxa = Double.parseDouble(monAdjStartValueElement.getTextContent());
                    double taxa = Double.parseDouble(monAdjTaxPercentElement.getTextContent());
                    String local = monAdjTaxLocationElement.getTextContent();
                    double valorTaxa = Double.parseDouble(monAdjTaxAmountValueElement.getTextContent());

                    // LineBaseAmount
                    Element lineBaseAmountElementElement = FindInChildren(lineElement, "LineBaseAmount");
                    Element CurrencyValueEl = FindInChildren(lineBaseAmountElementElement, "CurrencyValue");
                    double precoTotal = Double.parseDouble(CurrencyValueEl.getTextContent());

                    item.add(new EntradaStock(taxa, caixas, descricao, identificacao, local, peso, precoSemTaxa, precoUnidade, unidades, valorTaxa, precoTotal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private Element FindInChildren(Element parent, String elementToGet) {
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node nNode = list.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.getNodeName() == elementToGet) {
                return (Element) nNode;
            }
        }
        return null;
    }

    private Element FindInChildren(Element parent, String elementToGet, int elementCount) {
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node nNode = list.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE
                    && nNode.getNodeName() == elementToGet
                    && i + 1 == elementCount) {
                return (Element) nNode;
            }
        }
        return null;
    }
}
