package com.example;

import java.sql.DriverManager;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class PrimaryController {

    @FXML TextField txtConteudo;
    @FXML TextField txtPrazo;
    @FXML Button adicionar;
    @FXML Button apagar;
    @FXML ListView<Estudo> listaEstudos; //depois de criar o records

    ArrayList<Estudo> controleEstudos = new ArrayList<>();

    public static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    public static final String USER = "RM99711";
    public static final String PASS = "290204";

    public void adicionar(){
        String conteudo = txtConteudo.getText();
        String prazo = txtPrazo.getText();
        Estudo estudo = new Estudo(conteudo, prazo);

        try {
            var conexao = DriverManager.getConnection(URL, USER, PASS);
            var sql = "INSERT INTO TBL_CONTROLE_ESTUDOS_RM99711 (CONTEUDO, PRAZO) VALUES (?,?)";
            var comando = conexao.prepareStatement(sql);
            
            comando.setString(1, estudo.conteudo());
            comando.setString(2, estudo.prazo());

            comando.executeUpdate(); // para alterar registros

            Alert alertaErro = new Alert(AlertType.INFORMATION);
            alertaErro.setTitle("Sucesso");
            alertaErro.setContentText("Estudo adicionado com sucesso");
            alertaErro.show();

            conexao.close();

        } catch (Exception erro) {
            Alert alertaErro = new Alert (AlertType.ERROR);
            alertaErro.setTitle("Erro ao conectar");
            alertaErro.setContentText(erro.getLocalizedMessage());
            alertaErro.show();
        }
        controleEstudos.add(estudo);
        controleEstudos.sort((o1, o2) -> o1.prazo().compareTo(o2.prazo()));
        mostrarEstudos();
    }

    public void mostrarEstudos(){
        listaEstudos.getItems().clear();
        listaEstudos.getItems().addAll(controleEstudos);
    }

    public void apagar(){
       Estudo estudo = listaEstudos.getSelectionModel().getSelectedItem();
        if(estudo != null){
            controleEstudos.remove(estudo);
            mostrarEstudos(); 

            Alert mensagem = new Alert(AlertType.INFORMATION);
            mensagem.setHeaderText("Apagado");
            mensagem.setContentText("Estudo apagado com sucesso");
            mensagem.show();
        }else{
            Alert mensagem = new Alert(AlertType.ERROR);
            mensagem.setHeaderText("ERRO");
            mensagem.setContentText("Não foi possível remover");
            mensagem.show();
        }
    }
} 
