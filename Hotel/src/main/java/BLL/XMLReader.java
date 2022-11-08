package BLL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

public class XMLReader {
    public void lerXML() {

        try {
            java.net.URL url = this.getClass().getClassLoader().getResource("\"..\\..\\..\\lib\\entradaStock.xml\"");
            String filePath = url .getFile();
            //Recebe ficheiro XML
            File fXmlFile = new File(filePath);

            //Define a API que instância o documento XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //Normaliza o XML
            doc.getDocumentElement().normalize();

            //Recebe elemento Root
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            //Grava numa NodeList
            NodeList nList = doc.getElementsByTagName("Line");
            System.out.println("----------------------------");

            //Percorre a lista e faz o Get pelas respetivas Tags
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    System.out.println("Identificacao do produto: "
                            + eElement.getElementsByTagName("ProductIdentifier")
                            .item(0).getTextContent());
                    System.out.println("Descricao do produto: "
                            + eElement.getElementsByTagName("ProductDescription")
                            .item(0).getTextContent());
                    System.out.print("Caixas: "
                            + eElement.getAttribute("Quantity: "));
                    System.out.println(eElement.getElementsByTagName("Value")
                            .item(0).getTextContent());
                    System.out.print("Peso: "
                            + eElement.getAttribute("InformationalQuantity"));
                    System.out.println(eElement.getElementsByTagName("Value")
                            .item(0).getTextContent());
                    System.out.print("Unidades: "
                            + eElement.getAttribute("Quantity: "));
                    System.out.println(eElement.getElementsByTagName("Value")
                            .item(0).getTextContent());
                    System.out.print("Preco por unidade: "
                            + eElement.getAttribute("PricePerUnit"));
                    System.out.println(eElement.getElementsByTagName("Value")
                            .item(0).getTextContent());
                    System.out.println("Preco sem taxa: "
                            + eElement.getElementsByTagName("MonetaryAdjustmentStartAmount")
                            .item(0).getTextContent());
                    System.out.println("Taxa: "
                            + eElement.getElementsByTagName("TaxPercent")
                            .item(0).getTextContent());
                    System.out.println("Valor da taxa: "
                            + eElement.getElementsByTagName("TaxAmount")
                            .item(0).getTextContent());
                    System.out.println("Local da taxa: "
                            + eElement.getElementsByTagName("TaxLocation")
                            .item(0).getTextContent());
                    System.out.println("----------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
