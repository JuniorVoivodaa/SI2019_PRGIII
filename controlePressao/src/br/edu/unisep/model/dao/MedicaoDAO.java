package br.edu.unisep.model.dao;

import br.edu.unisep.model.vo.MedicaoVO;

public class MedicaoDAO {

    public void salvar(MedicaoVO med) {

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

        }
    }

}
