package br.edu.unisep;

import br.edu.unisep.model.vo.LivroVO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private TableView<LivroVO> tabLivros;

    @FXML private TableColumn<LivroVO, String> colTitulo;
    @FXML private TableColumn<LivroVO, String> colAutor;
    @FXML private TableColumn<LivroVO, String> colEditora;
    @FXML private TableColumn<LivroVO, Integer> colPaginas;
    @FXML private TableColumn<LivroVO, String> colStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
