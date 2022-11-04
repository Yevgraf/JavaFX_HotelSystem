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
           File fXmlFile = new File("C:\\Repos\\LP3\\grupo4\\Hotel\\src\\main\\resources\\lib\\entradaStock.xml");
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           Document doc = dBuilder.parse(fXmlFile);
           doc.getDocumentElement().normalize();

           System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
           NodeList nList = doc.getElementsByTagName("Line");
           System.out.println("----------------------------");

           for (int temp = 0; temp < nList.getLength(); temp++) {
               Node nNode = nList.item(temp);
               System.out.println("\nProduct :" + nNode.getNodeName());
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element eElement = (Element) nNode;
                   System.out.println("Indentificacao do produto: "
                           + eElement.getAttribute("Product"));
                   System.out.println("Descricao do produto: "
                           + eElement.getElementsByTagName("ProductDescription")
                           .item(0).getTextContent());
                   System.out.println("Quantiidade: "
                           + eElement.getElementsByTagName("Quantity")
                           .item(0).getTextContent());
                   System.out.println("Peso: "
                           + eElement.getElementsByTagName("InformationalQuantity")
                           .item(0).getTextContent() + "Kg");
                   System.out.println("Preco por unidade: "
                           + eElement.getElementsByTagName("PricePerUnit")
                           .item(0).getTextContent());
                   System.out.println("Ajuste monetario: "
                           + eElement.getElementsByTagName("MonetaryAdjustmentStartAmount")
                           .item(0).getTextContent());
                   System.out.println("IVA: "
                           + eElement.getElementsByTagName("TaxPercent")
                           .item(0).getTextContent());
                   System.out.println("Preco unitario com IVA: "
                           + eElement.getElementsByTagName("TaxAmount")
                           .item(0).getTextContent());
                   System.out.println("Local da taxa: "
                           + eElement.getElementsByTagName("TaxLocation")
                           .item(0).getTextContent());
                   System.out.println("Preco total com IVA: "
                           + eElement.getElementsByTagName("LineBaseAmount")
                           .item(0).getTextContent());
                   System.out.println("----------------------------------------------");
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
