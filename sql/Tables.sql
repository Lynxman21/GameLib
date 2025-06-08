create table members
(
    member_id    serial primary key,
    first_name   varchar(50) not null,
    last_name    varchar(50) not null,
    email        varchar(100) unique,
    join_date    date        default current_date,
    street       varchar(100),
    house_number varchar(10),
    postal_code  varchar(10),
    city         varchar(50),
    country      varchar(50) default 'Polska'
);

create table categories
(
    category_id   serial primary key,
    category_name varchar(100) not null unique
);

create table publishers
(
    publisher_id serial primary key,
    company_name varchar(100) not null unique,
    start_date   date,
    country      varchar(50),
    city         varchar(50)
);

create table games
(
    game_id      serial primary key,
    name         varchar(100) not null,
    publisher_id int          not null,
    release_date date,
    foreign key (publisher_id) references publishers (publisher_id)
);

create table game_copies
(
    copy_id       serial primary key,
    game_id       int not null,
    acquired_date date,
    foreign key (game_id) references games (game_id)
);

create table curr_borrowed
(
    borrow_id     serial primary key,
    copy_id       int not null,
    member_id     int not null,
    borrowed_date date,
    due_to        date,
    foreign key (copy_id) references game_copies (copy_id),
    foreign key (member_id) references members (member_id)
);

create table borrowed_hist
(
    hist_id            serial primary key,
    copy_id            int not null,
    member_id          int not null,
    borrowed_date      date,
    due_to             date,
    actual_return_date date,
    status             varchar(1)     default 'R',
    penalty            decimal(10, 2) default 0.00,
    foreign key (copy_id) references game_copies (copy_id),
    foreign key (member_id) references members (member_id)
);

create table category_game_link
(
    category_id int not null,
    game_id     int not null,
    primary key (category_id, game_id),
    foreign key (category_id) references categories (category_id),
    foreign key (game_id) references games (game_id)
);