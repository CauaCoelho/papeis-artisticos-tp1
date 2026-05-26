-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Inserir produtos base para sketchbooks (herança JOINED)
-- Não passamos o ID para a tabela pai (produto), deixando o BD gerar automaticamente
insert into produto (nome, preco, estoque, textura_id) values ('Sketchbook Kim Jung Gi Edition', 200.00, 10, 'LISO');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 100, 'COURO');

insert into produto (nome, preco, estoque, textura_id) values ('Sketchbook Moleskine', 65.50, 15, 'TRANCADO');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 98, 'BROCHURA');

insert into produto (nome, preco, estoque, textura_id) values ('Sketchbook Rústico', 45.00, 20, 'KRAFT');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 54, 'COURO');

insert into produto (nome, preco, estoque, textura_id) values ('Sketchbook Artístico', 120.00, 5, 'CASCA_DE_OVO');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 75, 'CARTAO');
