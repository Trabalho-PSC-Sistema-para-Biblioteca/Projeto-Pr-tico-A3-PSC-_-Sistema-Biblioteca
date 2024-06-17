CREATE DATABASE biblioteca;
USE biblioteca;

CREATE TABLE livro(
id INT AUTO_INCREMENT PRIMARY KEY,
titulo VARCHAR(255),
autor VARCHAR(255),
genero VARCHAR(255),
quantidade INT,
status ENUM('disponivel', 'emprestado') DEFAULT 'disponivel',
funcionario_responsavel INT,
FOREIGN KEY (funcionario_responsavel) REFERENCES funcionario(id)
);

ALTER TABLE livro ADD COLUMN quantidade_disponivel INT DEFAULT 0;
SELECT * FROM livro;
DESCRIBE livro;
UPDATE livro SET quantidade_disponivel = quantidade;

CREATE TABLE funcionario(
id INT AUTO_INCREMENT PRIMARY KEY,
login VARCHAR(255),
senha VARCHAR(255),
nome_completo VARCHAR(255)
);

INSERT INTO funcionario(id, login, senha, nome_completo) VALUES (null, "admin", "admin", "ADMIN");
INSERT INTO funcionario(id, login, senha, nome_completo) VALUES (null, "isadora.marinho", "qualquercoisa", "Isadora Rita Maldonado de Marinho");

CREATE TABLE aluno(
nome VARCHAR(255),
ra BIGINT PRIMARY KEY
);

INSERT INTO aluno(nome, ra) VALUES ("Guilherme Amaral Guerra", "4231922158");
INSERT INTO aluno(nome, ra) VALUES ("Raissa Rodrigues", "42414982");
INSERT INTO aluno(nome, ra) VALUES ("Diego Junio", "42411382");
INSERT INTO aluno(nome, ra) VALUES ("Arthur Alexandre Vieira Alves", "4231925521");
INSERT INTO aluno(nome, ra) VALUES ("Tayna Mariana Silva Vieira", "42415613");

SELECT * from aluno;
DESCRIBE aluno;
ALTER TABLE aluno MODIFY COLUMN ra BIGINT PRIMARY KEY;
ALTER TABLE aluno MODIFY COLUMN ra BIGINT PRIMARY KEY;


CREATE TABLE emprestimo(
id INT AUTO_INCREMENT PRIMARY KEY,
livro_id INT,
aluno_ra BIGINT,
data_emprestimo DATE,
prazo DATE,
reservado BOOLEAN DEFAULT FALSE,
funcionario_id INT,
FOREIGN KEY (livro_id) REFERENCES livro(id),
FOREIGN KEY (aluno_ra) REFERENCES aluno(ra),
FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
);

SELECT * FROM emprestimo;

ALTER TABLE emprestimo ADD COLUMN funcionario_nome VARCHAR(255);

DESCRIBE emprestimo;
ALTER TABLE emprestimo MODIFY COLUMN aluno_ra BIGINT;

SELECT CONSTRAINT_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_NAME = 'emprestimo' AND COLUMN_NAME = 'aluno_ra';

ALTER TABLE emprestimo DROP FOREIGN KEY emprestimo_ibfk_2;
ALTER TABLE emprestimo MODIFY COLUMN aluno_ra BIGINT;

ALTER TABLE emprestimo ADD COLUMN status VARCHAR(255);
ALTER TABLE emprestimo MODIFY COLUMN reservado BOOLEAN DEFAULT FALSE;

ALTER TABLE emprestimo
ADD CONSTRAINT fk_aluno_ra
FOREIGN KEY (aluno_ra) REFERENCES aluno(ra);

SHOW CREATE TABLE emprestimo;

ALTER TABLE emprestimo ADD COLUMN data_devolucao DATE DEFAULT NULL;

