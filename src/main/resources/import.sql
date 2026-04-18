-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Inserir produtos base para sketchbooks (herança JOINED)
insert into produto (id, textura_id) values (1, 'LISO');
insert into produto (id, textura_id) values (2, 'TRANCADO');
insert into produto (id, textura_id) values (3, 'KRAFT');
insert into produto (id, textura_id) values (4, 'CASCA_DE_OVO');

-- Inserir sketchbooks
insert into sketchbook (id, quantidadeFolhas, capa_id) values (1, 100, 'PAPEL_COUCHE');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (2, 98, 'BROCHURA');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (3, 54, 'COURO');
insert into sketchbook (id, quantidadeFolhas, capa_id) values (4, 75, 'CARTAO');

