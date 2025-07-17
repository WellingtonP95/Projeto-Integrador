package com.mycompany.escola.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Gerenciador {

    private final List<Pessoa> pessoas;  // Lista que pode armazenar tanto Alunos quanto Professores

    public Gerenciador() {
        this.pessoas = new ArrayList<>();
    }

    public boolean adicionarPessoa(Pessoa pessoa) {
        switch (pessoa) {
            case Aluno aluno -> {
                int matricula;
                boolean matriculaUnica;
                
                // Gere matrícula, repita o código até que a matrícula seja única para o aluno
                do {
                    matriculaUnica = true;
                    matricula = ThreadLocalRandom.current().nextInt(1000, 9999);
                    
                    for (Pessoa p : pessoas) {
                        if (p instanceof Aluno && ((Aluno) p).getMatricula() == matricula) {
                            matriculaUnica = false;
                            break;
                        }
                    }
                } while (!matriculaUnica);
                
                aluno.setMatricula(matricula);
                pessoas.add(aluno); // Adiciona na lista de pessoas
                System.out.println("\nAluno adicionado com sucesso: " + aluno.getNome());
                
            }
            case Professor professor -> {
                // Verifica se o CPF do professor já está cadastrado
                for (Pessoa p : pessoas) {
                    if (p instanceof Professor && ((Professor) p).getCpf().equals(professor.getCpf())) {
                        System.out.println("\nErro: CPF já cadastrado para o professor.\n");
                        return false;
                    }
                }
                
                // Adiciona o professor na lista de pessoas
                pessoas.add(professor);
                System.out.println("\nProfessor adicionado com sucesso: " + professor.getNome());
                
            }       default -> {
                System.out.println("\nTipo de pessoa desconhecido.");
                return false;
            }   }
        return true;
    }

    // Remove uma pessoa (Aluno ou Professor)
    public boolean removerPessoa(Pessoa pessoa) {
        for (Pessoa p : pessoas) {
            if (p instanceof Aluno && pessoa instanceof Aluno && ((Aluno) p).getMatricula() == ((Aluno) pessoa).getMatricula()) {
                pessoas.remove(p);
                System.out.println("\nAluno removido com sucesso: " + pessoa.getNome());
                return true;
            } else if (p instanceof Professor && pessoa instanceof Professor && ((Professor) p).getCpf().equals(((Professor) pessoa).getCpf())) {
                pessoas.remove(p);
                System.out.println("\nProfessor removido com sucesso: " + pessoa.getNome());
                return true;
            }
        }
        System.out.println("\nPessoa não encontrada.");
        return false;
    }
    
    public void listarPessoas() {
        System.out.println("\n----------- Listagem ----------");
        for (Pessoa pessoa : pessoas) {
            switch (pessoa) {
                case Aluno aluno -> System.out.println("Aluno: " + aluno.getNome() + " " + aluno.getSobrenome() + " | "
                        + "Idade: " + aluno.getIdade() + " | Turma: " + " | Matrícula: " + aluno.getMatricula());
                case Professor professor -> System.out.println("Professor: " + professor.getNome() + " " + professor.getSobrenome() + " | "
                        + "Idade: " + professor.getIdade() + " | CPF: " + professor.getCpf() + " | Turmas: " + professor.getTurmas());
                default -> {
                }
            }
        }
}

    // Retorna a lista de pessoas
    public List<Pessoa> getPessoas() {
        return pessoas;
    }
}