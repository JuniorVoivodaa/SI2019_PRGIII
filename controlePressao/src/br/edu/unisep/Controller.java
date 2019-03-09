package br.edu.unisep;

import br.edu.unisep.model.vo.MedicaoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private DatePicker txtData;

    @FXML private TextField txtSist;
    @FXML private TextField txtDiast;

    @FXML private TableView<MedicaoVO> tabMedicoes;
    @FXML private TableColumn<MedicaoVO, String> colData;
    @FXML private TableColumn<MedicaoVO, Integer> colSist;
    @FXML private TableColumn<MedicaoVO, Integer> colDiast;
    @FXML private TableColumn<MedicaoVO, String> colResultado;

    private ObservableList<MedicaoVO> medicoes;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializa a lista de medições vazia
        medicoes = FXCollections.observableArrayList();

        // Associar a lista de medições ao TableView de medições da tela
        // Desta forma, os objetos que forem adicionados na lista
        // aparecerão no TableView da tela
        tabMedicoes.setItems(medicoes);

        // Define qual atributo do objeto MedicaoVO será exibido
        // em cada uma das colunas que compõem o TableView
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colSist.setCellValueFactory(new PropertyValueFactory<>("sistolica"));
        colDiast.setCellValueFactory(new PropertyValueFactory<>("diastolica"));
        colResultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));
    }

    public void salvar(ActionEvent event) {

        var med = new MedicaoVO();
        med.setData(txtData.getValue());
        med.setSistolica(Integer.valueOf(txtSist.getText()));
        med.setDiastolica(Integer.valueOf(txtDiast.getText()));

        if (med.getSistolica() <= 120 && med.getDiastolica() <= 80) {
            med.setResultado("Normal");
        } else if (med.getSistolica() < 140 || med.getDiastolica() < 90) {
            med.setResultado("Pré-Hipertenso");
        } else if (med.getSistolica() < 160 || med.getDiastolica() < 100) {
            med.setResultado("Hipertenso I");
        } else if (med.getSistolica() < 180 || med.getDiastolica() < 110) {
            med.setResultado("Hipertenso II");
        } else {
            med.setResultado("Crítico");
        }

        // Adiciona o objeto MedicaoVO criado na lista.
        medicoes.add(med);

        limpar(event);
    }

    public void limpar(ActionEvent event) {
        txtData.setValue(null);
        txtSist.clear();
        txtDiast.clear();

        txtData.requestFocus();
    }


    public void excluir(ActionEvent event) {
        var med = tabMedicoes.getSelectionModel().getSelectedItem();

        // Verifica se o usuário selecionou um item para excluir
        if (med != null) {
            medicoes.remove(med);
        } else {
            var msg = new Alert(Alert.AlertType.WARNING,
                    "Selecione um item para excluir");
            msg.show();
        }
    }

}
