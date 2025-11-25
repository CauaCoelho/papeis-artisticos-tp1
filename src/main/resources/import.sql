-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into papel (id, textura, formato) values (1, 'CASCA_DE_OVO', 'A3');
insert into sketchbook (id, quantidadeFolhas, id_capa) values (1, 60, null);

insert into papel (id, textura, formato) values (2, 'KRAFT', 'A4');
insert into bloco (id, quantidadeFolhas) values (2, 50);

insert into papel (id, textura, formato) values (3, 'LISO', 'ROLO_M');
insert into rolo (id) values (3);

