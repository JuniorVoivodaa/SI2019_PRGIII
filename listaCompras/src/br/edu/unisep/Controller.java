package br.edu.unisep;

import br.edu.unisep.model.dao.ProdutoDAO;
import br.edu.unisep.model.vo.ProdutoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.text.DecimalFormat;
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

    @FXML private Label lblTotal;

    private ObservableList<ProdutoVO> produtos;
    private ProdutoVO produto = new ProdutoVO();
    private ProdutoDAO dao = new ProdutoDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        produtos = FXCollections.observableArrayList();
        tabProdutos.setItems(produtos);

        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        colMercadoA.setCellValueFactory(new PropertyValueFactory<>("mercadoA"));
        colMercadoB.setCellValueFactory(new PropertyValueFactory<>("mercadoB"));
        colMercadoC.setCellValueFactory(new PropertyValueFactory<>("mercadoC"));

        colMercadoA.setCellFactory( col -> formatCell() );
        colMercadoB.setCellFactory( col -> formatCell() );
        colMercadoC.setCellFactory( col -> formatCell() );

        listar();
    }

    private TableCell<ProdutoVO, Double> formatCell() {
        var cell = new TableCell<ProdutoVO, Double>() {

            @Override
            protected void updateItem(Double valor, boolean vazio) {
                super.updateItem(valor, vazio);

                if (valor == null || vazio) {
                    setText("");
                    setStyle(null);
                } else {
                    setText(formatarValor(valor));
                    setAlignment(Pos.CENTER_RIGHT);

                    var prod = getTableRow().getItem();

                    if (prod != null) {
                        if (valor == prod.getMaior()) {
                            setStyle("-fx-background-color: #ef9a9a");
                        } else if (valor == prod.getMenor()) {
                            setStyle("-fx-background-color: #a5d6a7");
                        } else {
                            setStyle(null);
                        }
                    }
                }
            }

        };

        return cell;
    }

    private String formatarValor(Double v) {
        var df = new DecimalFormat("#,##0.00");
        return "R$ " + df.format(v);
    }

    public void adicionar(ActionEvent event) {
        produto.setDescricao(txtDescricao.getText());
        produto.setMercadoA(Double.valueOf(txtMercadoA.getText()));
        produto.setMercadoB(Double.valueOf(txtMercadoB.getText()));
        produto.setMercadoC(Double.valueOf(txtMercadoC.getText()));

        if (produto.getId() == null) {
            dao.salvar(produto);
        } else {
            dao.alterar(produto);
        }

        limpar();

        listar();
        totalizar();
    }

    public void excluir(ActionEvent event) {
        var p = tabProdutos.getSelectionModel().getSelectedItem();

        if (p != null)  {
            dao.excluir(p.getId());

            listar();
            totalizar();
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

    private void totalizar() {
        var total = produtos.stream().mapToDouble(p -> p.getMenor()).sum();
        lblTotal.setText(formatarValor(total));
    }

    private void listar() {
        var lista = dao.listar();
        produtos.setAll(lista);
    }

    public void selecionarItemLista(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY &&
            event.getClickCount() == 2) {

            produto = tabProdutos.getSelectionModel().getSelectedItem();
            txtDescricao.setText(produto.getDescricao());
            txtMercadoA.setText(produto.getMercadoA().toString());
            txtMercadoB.setText(produto.getMercadoB().toString());
            txtMercadoC.setText(produto.getMercadoC().toString());

            txtDescricao.requestFocus();
        }
    }

}