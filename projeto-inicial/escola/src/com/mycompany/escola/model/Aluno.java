package com.mycompany.escola.model;


public class Aluno extends Pessoa{
    
    private int matricula = 0;
    private char turma;
    
    public Aluno(String nome, String sobrenome, int idade, char turma) {
        super(nome, sobrenome, idade);
        this.turma = turma;
    }

    public int getMatricula() {
        return matricula;
    }

    public char getTurma() {
        return turma;
    }
    
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setTurma(char turma) {
        this.turma = turma;
    }
    
    
}
