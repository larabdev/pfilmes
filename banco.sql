--  CineList — Script de criação do banco de dados

CREATE DATABASE IF NOT EXISTS filmes_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE filmes_db;

-- Tabela de filmes
CREATE TABLE IF NOT EXISTS filmes (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    titulo          VARCHAR(200) NOT NULL,
    ano_lancamento  INT          NOT NULL,
    diretor         VARCHAR(150),
    genero          VARCHAR(60),
    sinopse         TEXT
);

-- Tabela de categorias de avaliação
CREATE TABLE IF NOT EXISTS categorias (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Categorias padrão
INSERT INTO categorias (nome) VALUES
    ('Melhor Roteiro'),
    ('Melhor Trilha Sonora'),
    ('Melhor Fotografia'),
    ('Melhor Direção'),
    ('Melhor Elenco');

-- Tabela de avaliações
CREATE TABLE IF NOT EXISTS avaliacoes (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    filme_id     INT NOT NULL,
    categoria_id INT NOT NULL,
    nota         TINYINT NOT NULL CHECK (nota BETWEEN 1 AND 5),
    comentario   TEXT,
    FOREIGN KEY (filme_id)     REFERENCES filmes(id)     ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE
);

--Tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(32)  NOT NULL   -- MD5 hash
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO categorias (nome) VALUES ('Ação'), ('Comédia'), ('Drama'), ('Terror'), ('Ficção Científica');

-- Usuário padrão: admin@filmes.com / senha: 123456
-- MD5("123456") = e10adc3949ba59abbe56e057f20f883e

INSERT INTO usuarios (email, senha) VALUES
    ('admin@filmes.com', 'e10adc3949ba59abbe56e057f20f883e');
