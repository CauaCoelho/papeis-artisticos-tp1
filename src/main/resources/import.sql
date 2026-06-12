-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Inserir Marcas
insert into marca (nome) values ('Hahnemühle');
insert into marca (nome) values ('Canson');
insert into marca (nome) values ('Moleskine');

-- Inserir produtos base para sketchbooks (herança JOINED)
-- Não passamos o ID para a tabela pai (produto), deixando o BD gerar automaticamente
insert into produto (nome, preco, estoque, textura_id, marca_id) values ('Sketchbook Kim Jung Gi Edition', 200.00, 10, 'LISO', (select id from marca where nome = 'Hahnemühle'));
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 100, 'COURO');

insert into produto (nome, preco, estoque, textura_id, marca_id) values ('Sketchbook Moleskine', 65.50, 15, 'TRANCADO', (select id from marca where nome = 'Moleskine'));
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 98, 'BROCHURA');

-- Rústico will have generic / no brand (null) as requested
insert into produto (nome, preco, estoque, textura_id) values ('Sketchbook Rústico', 45.00, 20, 'KRAFT');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 54, 'COURO');

insert into produto (nome, preco, estoque, textura_id, marca_id) values ('Sketchbook Artístico', 120.00, 5, 'CASCA_DE_OVO', (select id from marca where nome = 'Hahnemühle'));
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 75, 'CARTAO');

-- Inserir PapelAvulso
insert into produto (nome, preco, estoque, textura_id, marca_id) values ('Papel Canson A4', 25.90, 50, 'LISO', (select id from marca where nome = 'Canson'));
insert into papelavulso (id, tipoPapel, tamanho) values (currval('produto_id_seq'), 'Canson', 'A4');

-- Inserir Rolo
insert into produto (nome, preco, estoque, textura_id, marca_id) values ('Papel Kraft em Rolo', 89.90, 12, 'KRAFT', (select id from marca where nome = 'Canson'));
insert into rolo (id, comprimento) values (currval('produto_id_seq'), 10.0);

-- Inserir Bloco
insert into produto (nome, preco, estoque, textura_id, marca_id) values ('Bloco de Aquarela Hahnemühle', 150.00, 8, 'TRANCADO', (select id from marca where nome = 'Hahnemühle'));
insert into bloco (id, quantidadeFolhas) values (currval('produto_id_seq'), 20);
