package br.edu.unisep;

import br.edu.unisep.model.vo.MedicaoVO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private DatePicker txtData;

    @FXML private TextField txtSist;
    @FXML private TextField txtDiast;

    @FXML private TableView<MedicaoVO> tabMedicoes;
    @FXML private TableColumn<MedicaoVO, LocalDate> colData;
    @FXML private TableColumn<MedicaoVO, Integer> colSist;
    @FXML private TableColumn<MedicaoVO, Integer> colDiast;
    @FXML private TableColumn<MedicaoVO, String> colResultado;

    private ObservableList<MedicaoVO> medicoes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    }


}
