package com.example.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pagina Gestor");
        stage.setScene(scene);
        stage.show();
        //"PainelCriarFuncionario.fxml"
    }

    public static void main(String[] args) {
        launch();
    }

    //DBconn conexao = new DBconn();
    // conexao.TestConnection();

    //XMLReader teste = new XMLReader();
    //teste.lerXML();

}
