-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Inserir produtos base para sketchbooks (herança JOINED)
-- Não passamos o ID para a tabela pai (produto), deixando o BD gerar automaticamente
insert into produto (textura_id) values ('LISO');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 100, 'PAPEL_COUCHE');

insert into produto (textura_id) values ('TRANCADO');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 98, 'BROCHURA');

insert into produto (textura_id) values ('KRAFT');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 54, 'COURO');

insert into produto (textura_id) values ('CASCA_DE_OVO');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (currval('produto_id_seq'), 75, 'CARTAO');
