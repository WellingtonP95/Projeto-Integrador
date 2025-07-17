package com.mycompany.escola.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    public static Connection Conectar() {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/escola", "root", "");
            
            System.out.println("Conectado com sucesso.");
            
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }
}
