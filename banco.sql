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
