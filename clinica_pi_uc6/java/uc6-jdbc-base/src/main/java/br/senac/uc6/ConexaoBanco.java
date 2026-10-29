package br.senac.uc6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexaoBanco {

    private ConexaoBanco() {
    }

    public static Connection abrir() throws SQLException {
        Connection conexao = DriverManager.getConnection(
            ConfigBanco.URL,
            ConfigBanco.USUARIO,
            ConfigBanco.SENHA
        );

        try (Statement comando = conexao.createStatement()) {
            comando.execute("SET search_path TO " + ConfigBanco.SCHEMA + ", public");
        }

        return conexao;
    }
}
