

 create table door
(
    id bigint not null,
    doorname varchar(255),
    filename varchar(255),
    password varchar(2048) not null,
    user_id bigint,
    primary key (id)
)   engine=InnoDB;




 create table hibernate_sequence(
    next_val bigint NOT NULL
 )engine=InnoDB;

 insert into hibernate_sequence values ( 2 ) ;


create table user_role
(
    user_id bigint not null,
    roles varchar(255)
)   engine=InnoDB;

create table usr
(
    id bigint not null,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
)engine=InnoDB;

alter table door
    add constraint door_user_fk
    foreign key (user_id) references usr (id);

alter table user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr (id);