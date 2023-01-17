package BLL;

import Model.MessageBoxes;
import javafx.scene.control.Alert;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class ValidaXML {
    private static final String userDirectory = System.getProperty("user.dir");
    private static final String filePath = userDirectory + "/src/main/resources/lib/HotelAgencyPT.xsd";
    public static Boolean validarXML(File xmlFile) {
        try {
            // Create a SchemaFactory capable of understanding WXS schemas.
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Load the schema file
            File schemaLocation = new File(filePath);
            Schema schema = factory.newSchema(schemaLocation);

            // Create a Validator object, which can be used to validate an instance document.
            Validator validator = schema.newValidator();

            // Validate the XML file
            StreamSource source = new StreamSource(xmlFile);
            validator.validate(source);
            return true;
        } catch (Exception e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Ficheiro XML inválido!", "Erro:");
            return false;
        }
    }
}


