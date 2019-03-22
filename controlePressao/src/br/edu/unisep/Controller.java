package br.edu.unisep;

import br.edu.unisep.model.dao.MedicaoDAO;
import br.edu.unisep.model.vo.MedicaoVO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.format.DateTimeFormatter;
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

    private MedicaoDAO dao;
    private MedicaoVO medicao = new MedicaoVO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializa a lista de medições vazia
        medicoes = FXCollections.observableArrayList();

        // Associar a lista de medições ao TableView de medições da tela
        // Desta forma, os objetos que forem adicionados na lista
        // aparecerão no TableView da tela
        tabMedicoes.setItems(medicoes);

        // Define a largura das colunas. O valor multiplicado por Integer.MAX_VALUE
        // indica o percentual de largura da coluna
        colData.setMaxWidth(Integer.MAX_VALUE * 30.0);
        colSist.setMaxWidth(Integer.MAX_VALUE * 20.0);
        colDiast.setMaxWidth(Integer.MAX_VALUE * 20.0);
        colResultado.setMaxWidth(Integer.MAX_VALUE * 30.0);

        // Define qual atributo do objeto MedicaoVO será exibido
        // em cada uma das colunas que compõem o TableView
        //colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colSist.setCellValueFactory(new PropertyValueFactory<>("sistolica"));
        colDiast.setCellValueFactory(new PropertyValueFactory<>("diastolica"));

        var dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        colData.setCellValueFactory( (cell) -> {
            var data = cell.getValue().getData();
            var dataFmt = new SimpleStringProperty(dtf.format(data));
            return dataFmt;
        });

        colResultado.setCellValueFactory( (cell) -> {
            var tipo = cell.getValue().getResultado();
            var resultado = "";

            if (tipo == 1) {
                resultado = "Normal";
            } else if (tipo == 2) {
                resultado = "Pré-Hipertenso";
            } else if (tipo == 3) {
                resultado = "Hipertenso I";
            } else if (tipo == 4) {
                resultado = "Hipertenso II";
            } else {
                resultado = "Crítico";
            }

            return new SimpleStringProperty(resultado);
        });

        this.dao = new MedicaoDAO();

        listar();
    }

    public void salvar(ActionEvent event) {

        medicao.setData(txtData.getValue());
        medicao.setSistolica(Integer.valueOf(txtSist.getText()));
        medicao.setDiastolica(Integer.valueOf(txtDiast.getText()));

        if (medicao.getSistolica() <= 120 && medicao.getDiastolica() <= 80) {
            medicao.setResultado(1);
        } else if (medicao.getSistolica() < 140 || medicao.getDiastolica() < 90) {
            medicao.setResultado(2);
        } else if (medicao.getSistolica() < 160 || medicao.getDiastolica() < 100) {
            medicao.setResultado(3);
        } else if (medicao.getSistolica() < 180 || medicao.getDiastolica() < 110) {
            medicao.setResultado(4);
        } else {
            medicao.setResultado(5);
        }

        if (medicao.getId() == null) {
            dao.salvar(medicao);
        } else {
            dao.alterar(medicao);
            medicao = new MedicaoVO();
        }

        limpar(event);
        listar();
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
            dao.excluir(med.getId());
            listar();
        } else {
            var msg = new Alert(Alert.AlertType.WARNING,
                    "Selecione um item para excluir");
            msg.show();
        }
    }

    private void listar() {
        var lista = dao.listar();
        medicoes.setAll(lista);
    }

    public void selecionarItemLista(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY &&
                event.getClickCount() == 2) {

            // Obtém o objeto selecionado na lista e atribui ao objeto medicao
            medicao = tabMedicoes.getSelectionModel().getSelectedItem();

            txtData.setValue(medicao.getData());
            txtSist.setText(medicao.getSistolica().toString());
            txtDiast.setText(medicao.getDiastolica().toString());

            txtData.requestFocus();
        }
    }

}
