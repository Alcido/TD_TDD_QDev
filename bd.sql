create table if not exists mot (
  mot_no int primary key,
  mot char(5)
);

create table if not exists partie (
    nom_joueur character varying NOT NULL,
    mot character(5),
    nb_essais integer DEFAULT 0,
    termine boolean DEFAULT false
);

insert into mot (mot_no, mot) values
 (1,  'SOLID'),
 (2,  'TESTS'),
 (3,  'CLEAN'),
 (4,  'DEBUG'),
 (5,  'START'),
 (6,  'THINK'),
 (7,  'THROW'),
 (8,  'SLEEP'),
 (9,  'ERROR'),
 (10, 'FALSE') on conflict do nothing;