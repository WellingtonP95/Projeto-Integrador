package com.mycompany.escola.DAO;

import com.mycompany.escola.model.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    public void inserir(Professor prof) {
        Connection conn = Conexao.Conectar();
        String sql = "INSERT INTO professores (nome, sobrenome, idade, cpf) VALUES (?, ?, ? ,?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, prof.getNome());
            stmt.setString(2, prof.getSobrenome());
            stmt.setInt(3, prof.getIdade());
            stmt.setString(4, prof.getCpf());
            
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            int idProfessor = -1;
            if (rs.next()) {
                idProfessor = rs.getInt(1);
            }
            
            if (idProfessor != -1) {
                String[] turmas = prof.getTurmas().split("\\s*,\\s*");
                for (String turmaNome : turmas) {
                    int idTurma = switch (turmaNome.trim().toUpperCase()) {
                        case "A" -> 1;
                        case "B" -> 2;
                        case "C" -> 3;
                        default -> -1;
                    };
                    if (idTurma != -1) {
                        String insertTurmaSql = "INSERT INTO professores_turmas (id_professor, id_turma) VALUES (?, ?)";
                        PreparedStatement turmaStmt = conn.prepareStatement(insertTurmaSql);
                        turmaStmt.setInt(1, idProfessor);
                        turmaStmt.setInt(2, idTurma);
                        turmaStmt.executeUpdate();
                        turmaStmt.close();
                    } else {
                        System.out.println("Turma inválida: " + turmaNome);
                    }
                }
            }

            System.out.println("Professor inserido com sucesso.");

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
    
    public void atualizar(Professor p, String cpfOriginal) {
        Connection conn = Conexao.Conectar();
    String sql = "UPDATE professores SET nome = ?, sobrenome = ?, idade = ?, cpf = ? WHERE cpf = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, p.getNome());
        stmt.setString(2, p.getSobrenome());
        stmt.setInt(3, p.getIdade());
        stmt.setString(4, p.getCpf()); // Novo CPF
        stmt.setString(5, cpfOriginal); // CPF antigo para localizar o registro
        
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            // Atualizar as turmas também: remova todas e re-insira
            String deleteSql = "DELETE FROM professores_turmas WHERE id_professor = (SELECT id FROM professores WHERE cpf = ?)";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setString(1, p.getCpf());
                deleteStmt.executeUpdate();
            }
            String[] turmas = p.getTurmas().split("\\s*,\\s*");
            for (String turmaNome : turmas) {
                int idTurma = switch (turmaNome.trim().toUpperCase()) {
                    case "A" -> 1;
                    case "B" -> 2;
                    case "C" -> 3;
                    default -> -1;
                };
                if (idTurma != -1) {
                    String insertSql = "INSERT INTO professores_turmas (id_professor, id_turma) VALUES ((SELECT id FROM professores WHERE cpf = ?), ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setString(1, p.getCpf());
                        insertStmt.setInt(2, idTurma);
                        insertStmt.executeUpdate();
                    }
                }
            }
            System.out.println("Professor atualizado com sucesso.");
        } else {
            System.out.println("Nenhum registro foi atualizado.");
        }
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar professor: " + e.getMessage());
    } finally {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
    }   
    
    public void removerPorCpf(String cpf) {
        Connection conn = Conexao.Conectar();
        String sql = "DELETE FROM professores WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Professor removido com sucesso.");
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
    
    public Professor buscar(String cpf) {
        Connection conn = Conexao.Conectar();
        String sql = "SELECT * FROM professores WHERE cpf = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Professor prof = new Professor();
                prof.setNome(rs.getString("nome"));
                prof.setSobrenome(rs.getString("sobrenome"));
                prof.setIdade(rs.getInt("idade"));
                prof.setCpf(rs.getString("cpf"));
                return prof;
            } else {
                System.out.println("Professor não encontrado.");
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar prof: " + e.getMessage());
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    public List<Professor> listar() {
        List<Professor> lista = new ArrayList<>();
        
        Connection conn = Conexao.Conectar();
        String sql = """
                SELECT p.nome, p.sobrenome, p.idade, p.cpf,
                    GROUP_CONCAT(t.nome ORDER BY t.nome SEPARATOR ', ') AS turmas
                FROM professores p
                JOIN professores_turmas pt ON p.id = pt.id_professor
                JOIN turmas t ON pt.id_turma = t.id
                GROUP BY p.id
        """;
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Professor prof = new Professor();
                prof.setNome(rs.getString("nome"));
                prof.setSobrenome(rs.getString("sobrenome"));
                prof.setIdade(rs.getInt("idade"));
                prof.setTurmas(rs.getString("turmas"));
                prof.setCpf(rs.getString("cpf"));
                
                lista.add(prof);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar professores: " + e.getMessage());
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
