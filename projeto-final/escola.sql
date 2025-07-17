create database escola;
use escola;

CREATE TABLE turmas (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome CHAR(1) NOT NULL UNIQUE
);

CREATE TABLE alunos (
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    sobrenome VARCHAR(45) NOT NULL,
    idade INT NOT NULL,
	matricula INT NOT NULL UNIQUE,
    id_turma INT NOT NULL,
    FOREIGN KEY (id_turma) REFERENCES turmas(id)
);

CREATE TABLE professores (
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    sobrenome VARCHAR(45) NOT NULL,
    idade INT NOT NULL,
	cpf VARCHAR(12) NOT NULL UNIQUE
);

CREATE TABLE professores_turmas (
    id_professor INT NOT NULL,
    id_turma INT NOT NULL,
    PRIMARY KEY (id_professor, id_turma),
    FOREIGN KEY (id_professor) REFERENCES professores(id) ON DELETE CASCADE,
    FOREIGN KEY (id_turma) REFERENCES turmas(id) ON DELETE CASCADE
);

INSERT INTO turmas (nome) VALUES ('A'), ('B'), ('C');