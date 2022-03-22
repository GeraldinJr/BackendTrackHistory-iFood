create table pessoa_entregadora(
	id serial not null primary key, 
	nome   varchar(50) not null,
	email   varchar(50) not null,
	senha   varchar(255) not null
);


create table cliente(
	id      serial not null primary key, 
	nome        varchar(100) not null
);
  
create table pedido(
	id serial not null  primary key, 
	status_pedido integer not null,
	cliente_id integer not null,
	ultima_alteracao timestamp not null,
	
	constraint fk_cliente foreign key (cliente_id) references cliente(id)
);
    

create table track_history(
	id      serial not null primary key, 
	instante_registro  timestamp not null,
    latitude     varchar(20) not null,
    longitude     varchar(20) not null, 
	pedido_id   integer not null, 
    pessoa_entregadora_id   integer not null, 

	constraint fk_pessoa_entregadora foreign key (pessoa_entregadora_id) references pessoa_entregadora(id),
	constraint fk_pedido foreign key (pedido_id) references pedido(id)
);