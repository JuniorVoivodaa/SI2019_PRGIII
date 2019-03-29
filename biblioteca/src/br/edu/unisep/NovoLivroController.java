package br.edu.unisep;

import br.edu.unisep.model.dao.AutorDAO;
import br.edu.unisep.model.vo.AutorVO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NovoLivroController implements Initializable {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtEditora;
    @FXML private TextField txtPaginas;

    @FXML private TextArea txtSinopse;

    @FXML private ChoiceBox<AutorVO> cmbAutor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listarAutores();
    }


    private void listarAutores() {
        var dao = new AutorDAO();
        var lista = dao.listar();

        cmbAutor.setItems(FXCollections.observableList(lista));
    }

}
