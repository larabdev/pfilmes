CREATE DATABASE IF NOT EXISTS filmes_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE filmes_db;

CREATE TABLE IF NOT EXISTS filmes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    ano_lancamento INT NOT NULL,
    diretor VARCHAR(150),
    genero VARCHAR(60),
    sinopse TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO categorias (nome)
SELECT nome FROM (
    SELECT 'Melhor Roteiro' AS nome UNION ALL
    SELECT 'Melhor Trilha Sonora' UNION ALL
    SELECT 'Melhor Fotografia' UNION ALL
    SELECT 'Melhor Direcao' UNION ALL
    SELECT 'Melhor Elenco' UNION ALL
    SELECT 'Acao' UNION ALL
    SELECT 'Comedia' UNION ALL
    SELECT 'Drama' UNION ALL
    SELECT 'Terror' UNION ALL
    SELECT 'Ficcao Cientifica'
) AS novas
WHERE NOT EXISTS (
    SELECT 1 FROM categorias c WHERE c.nome = novas.nome
);

CREATE TABLE IF NOT EXISTS avaliacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    filme_id INT NOT NULL,
    categoria_id INT NOT NULL,
    nota TINYINT NOT NULL CHECK (nota BETWEEN 1 AND 5),
    comentario TEXT,
    FOREIGN KEY (filme_id) REFERENCES filmes(id) ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO usuarios (email, senha)
SELECT 'admin@filmes.com', 'e10adc3949ba59abbe56e057f20f883e'
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE email = 'admin@filmes.com'
);

    ('admin@filmes.com', 'e10adc3949ba59abbe56e057f20f883e');

--MODIFICAÇOES FEITAS--

USE filmes_db;
ALTER TABLE usuarios ADD COLUMN nome VARCHAR(150);
UPDATE usuarios SET nome = 'Admin' WHERE email = 'admin@filmes.com';

SELECT * FROM usuarios WHERE email = 'admin@filmes.com' AND senha = MD5('123456');

USE filmes_db;
UPDATE usuarios SET senha = MD5('123456') WHERE email = 'admin@filmes.com';

USE filmes_db;
UPDATE usuarios SET senha = '123456' WHERE email = 'admin@filmes.com';

USE filmes_db;
SELECT id, email, senha, LENGTH(senha) FROM usuarios;

USE filmes_db;
SELECT id, nome FROM categorias ORDER BY nome;

USE filmes_db;
DELETE c1 FROM categorias c1
INNER JOIN categorias c2
WHERE c1.id > c2.id AND c1.nome = c2.nome;

SET SQL_SAFE_UPDATES = 0;
DELETE c1 FROM categorias c1
INNER JOIN categorias c2
WHERE c1.id > c2.id AND c1.nome = c2.nome;
SET SQL_SAFE_UPDATES = 1;

USE filmes_db;
SELECT id, nome FROM categorias ORDER BY nome;
