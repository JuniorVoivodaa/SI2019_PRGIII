package br.edu.unisep.model.dao;

import br.edu.unisep.model.vo.LivroVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public List<LivroVO> listar() {

        var livros = new ArrayList<LivroVO>();

        try {

            var con = Conexao.obterConexao();

            var sql = new StringBuffer();
            sql.append("select l.id_livro, ");
            sql.append("l.ds_titulo, ");
            sql.append("l.ds_sinopse, ");
            sql.append("l.ds_editora, ");
            sql.append("l.nr_paginas, ");
            sql.append("l.tp_status, ");
            sql.append("a.id_autor, ");
            sql.append("a.ds_autor ");
            sql.append("from livro l ");
            sql.append("inner join autor a ");
            sql.append("on l.id_autor = a.id_autor");

            var ps = con.prepareStatement(sql.toString());


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }

}
