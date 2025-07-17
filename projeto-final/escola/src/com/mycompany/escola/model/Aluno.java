package com.mycompany.escola.model;


public class Aluno extends Pessoa{
    
    private int matricula = 0;
    
    private int id;
    private int id_turma;
    private String turmaNome;
    
    public Aluno(String nome, String sobrenome, int idade, String turmaNome) {
        super(nome, sobrenome, idade);
        this.turmaNome = turmaNome;
        this.id_turma = switch (turmaNome.toUpperCase()) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            default -> -1;
        };
    }

    public Aluno() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public int getMatricula() {
        return matricula;
    }

    public int getId_turma() {
        return id_turma;
    }

    public void setId_turma(int id_turma) {
        this.id_turma = id_turma;
        this.turmaNome = switch (id_turma) {
            case 1 -> "A";
            case 2 -> "B";
            case 3 -> "C";
            default -> "Desconhecido";
        };
    }

    public String getTurmaNome() {
        return turmaNome;
    }

    public void setTurmaNome(String turmaNome) {
        this.turmaNome = turmaNome;
        this.id_turma = switch (turmaNome.toUpperCase()) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            default -> -1;
        };
}
    
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
}
