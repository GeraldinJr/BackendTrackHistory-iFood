drop table if exists track_history;
drop table if exists pedidos;
drop table if exists clientes;
drop table if exists pessoas_entregadoras;
drop table if exists status;
create table pessoas_entregadoras(
  id serial not null primary key,
  nome varchar(50) not null,
  email varchar(50) unique not null,
  senha varchar(255) not null
);
create table clientes(
  id serial not null primary key,
  nome varchar(100) not null
);
drop type if exists status;
create type status as enum ('EM_ABERTO', 'EM_ROTA', 'CONCLUIDO', 'CANCELADO');
create table pedidos(
  id serial not null primary key,
  status status not null default 'EM_ABERTO',
  cliente_id integer not null,
  ultima_alteracao timestamp not null,
  constraint fk_cliente foreign key (cliente_id) references clientes(id)
);
create table track_history(
  id serial not null primary key,
  instante_registro timestamp not null,
  latitude double precision not null,
  longitude double precision not null,
  pedido_id integer not null,
  pessoa_entregadora_id integer not null,
  constraint fk_pessoa_entregadora foreign key (pessoa_entregadora_id) references pessoas_entregadoras(id),
  constraint fk_pedido foreign key (pedido_id) references pedidos(id)
);