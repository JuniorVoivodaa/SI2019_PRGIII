package br.edu.unisep;

import br.edu.unisep.fx.annotation.ColValueMap;
import br.edu.unisep.fx.annotation.FXColumn;
import br.edu.unisep.fx.annotation.IntToString;
import br.edu.unisep.fx.controller.AppController;
import br.edu.unisep.model.dao.LivroDAO;
import br.edu.unisep.model.vo.LivroVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Controller extends AppController {

    @FXML private TableView<LivroVO> tabLivros;

    @FXColumn(property = "titulo", percentWidth = 35)
    @FXML private TableColumn<LivroVO, String> colTitulo;

    @FXColumn(property = "autor", percentWidth = 25)
    @FXML private TableColumn<LivroVO, String> colAutor;

    @FXColumn(property = "editora", percentWidth = 20)
    @FXML private TableColumn<LivroVO, String> colEditora;

    @FXColumn(property = "paginas", percentWidth = 10)
    @FXML private TableColumn<LivroVO, Integer> colPaginas;

    @FXColumn(property = "status", percentWidth = 10)
    @ColValueMap({
            @IntToString(from = 1, to="Não lido"),
            @IntToString(from = 2, to="Lendo"),
            @IntToString(from = 3, to="Lido")
    })
    @FXML private TableColumn<LivroVO, String> colStatus;

    @FXML private Button btnLendo;
    @FXML private Button btnTerminei;

    private ObservableList<LivroVO> livros;
    private LivroDAO dao;

    @Override
    public void onInit() {
        livros = FXCollections.observableArrayList();
        tabLivros.setItems(livros);

        dao = new LivroDAO();
        listar();

        configurarSelecaoGrid();
    }


    private void configurarSelecaoGrid() {
        tabLivros.getSelectionModel().selectedItemProperty().addListener(
            (obsValue, anterior, novo) -> {
                btnLendo.setDisable(true);
                btnTerminei.setDisable(true);

                if (novo != null) {
                    if (novo.getStatus() == 1) {
                        btnLendo.setDisable(false);
                    } else if (novo.getStatus() == 2) {
                        btnTerminei.setDisable(false);
                    }
                }
            }
        );
    }


    public void listar() {
        var lst = dao.listar();
        livros.setAll(lst);

        tabLivros.getSelectionModel().clearSelection();
    }

    public void abrirNovo(ActionEvent event) throws IOException {
        openModal("novo.fxml", this::listar);
    }

    public void selecionarLivro(MouseEvent event) throws IOException{
        if (event.getButton() == MouseButton.PRIMARY &&
            event.getClickCount() == 2) {

            var livroSel = tabLivros.getSelectionModel().getSelectedItem();
            openModal("novo.fxml", this::listar, livroSel);
        }
    }

    public void alterarStatus(ActionEvent event) {
        var livro = tabLivros.getSelectionModel().getSelectedItem();
        livro.setStatus( livro.getStatus() + 1 );

        dao.alterar(livro);

        listar();
    }

}
