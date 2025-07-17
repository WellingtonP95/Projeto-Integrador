package com.mycompany.escola.DAO;

import com.mycompany.escola.model.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlunoDAO {
    
    private final Random random = new Random();
    
    public void inserir(Aluno aluno) {
        Connection conn = Conexao.Conectar();
        String sql = "INSERT INTO alunos (nome, sobrenome, idade, id_turma, matricula) VALUES (?, ?, ? ,? ,?)";
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getSobrenome());
            stmt.setInt(3, aluno.getIdade());
            stmt.setInt(4, aluno.getId_turma());
            stmt.setInt(5, aluno.getMatricula());
            
            stmt.execute();
            
            System.out.println("Aluno inserido com sucesso.");
            
        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
   public void removerPorMatricula(int matricula) {
    Connection conn = Conexao.Conectar();
    String sql = "DELETE FROM alunos WHERE matricula = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, matricula);
        stmt.executeUpdate();
        System.out.println("Aluno removido com sucesso.");
    } catch (SQLException e) {
        System.out.println("Erro ao remover: " + e.getMessage());
    } finally {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
    
    public void atualizar(Aluno a) {
    Connection conn = Conexao.Conectar();
    String sql = "UPDATE alunos SET nome = ?, sobrenome = ?, idade = ?, id_turma = (SELECT id FROM turmas WHERE nome = ?) WHERE matricula = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, a.getNome());
        stmt.setString(2, a.getSobrenome());
        stmt.setInt(3, a.getIdade());
        stmt.setString(4, a.getTurmaNome());
        stmt.setInt(5, a.getMatricula());
        
        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Aluno atualizado com sucesso.");
        } else {
            System.out.println("Nenhum registro foi atualizado.");
        }
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar aluno: " + e.getMessage());
    } finally {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
    
    public int gerarMatriculaUnica() {
        int matricula;
        do {
            matricula = 1000 + random.nextInt(9000); // gera número entre 1000 e 9999
        } while (buscar(matricula) != null); // enquanto a matrícula gerada já existir no banco, gera outra
        return matricula;
    }
    
    public Aluno buscar(int matricula) {
        Connection conn = Conexao.Conectar();
        String sql = "SELECT * FROM alunos WHERE matricula = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, matricula);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setNome(rs.getString("nome"));
                aluno.setSobrenome(rs.getString("sobrenome"));
                aluno.setIdade(rs.getInt("idade"));
                aluno.setId_turma(rs.getInt("id_turma"));
                aluno.setMatricula(rs.getInt("matricula"));
                return aluno;
            } else {
                System.out.println("Aluno não encontrado.");
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    public List<Aluno> listar() {
        List<Aluno> lista = new ArrayList<>();
        
        Connection conn = Conexao.Conectar();
        String sql = """
            SELECT a.nome, a.sobrenome, a.idade, a.matricula, t.nome AS turma_nome
            FROM alunos a
            JOIN turmas t ON a.id_turma = t.id
        """;
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setNome(rs.getString("nome"));
                aluno.setSobrenome(rs.getString("sobrenome"));
                aluno.setIdade(rs.getInt("idade"));
                aluno.setMatricula(rs.getInt("matricula"));
                aluno.setTurmaNome(rs.getString("turma_nome"));

                lista.add(aluno);
        }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        
        return lista;
    }
    
}

    
