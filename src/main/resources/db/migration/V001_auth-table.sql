create table auth_table (
	id bigint primary key not null auto_increment,
    login varchar(100) not null unique,
    password varchar(255) not null,
    role varchar(100) not null
);