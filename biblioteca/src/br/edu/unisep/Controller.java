package br.edu.unisep;

import br.edu.unisep.model.vo.LivroVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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

    public void abrirNovo(ActionEvent event) throws IOException {

        var loader = new FXMLLoader();
        var root = (Parent) loader.load(getClass().getResource("novo.fxml"));

        var janela = new Stage();
        janela.setScene(new Scene(root));
        janela.initStyle(StageStyle.UTILITY);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.setResizable(false);

        janela.show();
    }

}
