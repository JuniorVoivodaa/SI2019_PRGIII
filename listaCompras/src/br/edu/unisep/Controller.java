package br.edu.unisep;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private TextField txtDescricao;

    @FXML private TextField txtMercadoA;
    @FXML private TextField txtMercadoB;
    @FXML private TextField txtMercadoC;

    @FXML private TableView<ProdutoVO> tabProdutos;
    @FXML private TableColumn<ProdutoVO, String> colDescricao;
    @FXML private TableColumn<ProdutoVO, Double> colMercadoA;
    @FXML private TableColumn<ProdutoVO, Double> colMercadoB;
    @FXML private TableColumn<ProdutoVO, Double> colMercadoC;

    private ObservableList<ProdutoVO> produtos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        produtos = FXCollections.observableArrayList();
        tabProdutos.setItems(produtos);

        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colMercadoA.setCellValueFactory(new PropertyValueFactory<>("mercadoA"));
        colMercadoB.setCellValueFactory(new PropertyValueFactory<>("mercadoB"));
        colMercadoC.setCellValueFactory(new PropertyValueFactory<>("mercadoC"));

    }

    public void adicionar(ActionEvent event) {
        var p = new ProdutoVO();
        p.setDescricao(txtDescricao.getText());
        p.setMercadoA(Double.valueOf(txtMercadoA.getText()));
        p.setMercadoB(Double.valueOf(txtMercadoB.getText()));
        p.setMercadoC(Double.valueOf(txtMercadoC.getText()));

        produtos.add(p);

        limpar();
    }

    public void excluir(ActionEvent event) {
        var p = tabProdutos.getSelectionModel().getSelectedItem();

        if (p != null)  {
            produtos.remove(p);
        } else {
            var msg = new Alert(Alert.AlertType.WARNING, "Selecione um produto para excluir");
            msg.show();
        }
    }

    private void limpar() {
        txtDescricao.clear();
        txtMercadoA.clear();
        txtMercadoB.clear();
        txtMercadoC.clear();
    }

}
