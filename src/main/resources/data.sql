delete from records;
delete from users;
delete from operations;

insert into users (id, username, password, status) values (10001, 'pedro@mail.com', '$2a$10$KvBOI7Vnh6mLJgLIsbgJZ.gO5fE8PotX5ctuftTO50Y7mkbUI6tpO', 'active');
insert into users (id, username, password, status) values (10002, 'ana@mail.com', '$2a$10$KvBOI7Vnh6mLJgLIsbgJZ.gO5fE8PotX5ctuftTO50Y7mkbUI6tpO', 'active');
insert into users (id, username, password, status) values (10003, 'peter@mail.com', '$2a$10$KvBOI7Vnh6mLJgLIsbgJZ.gO5fE8PotX5ctuftTO50Y7mkbUI6tpO', 'active');
insert into users (id, username, password, status) values (10004, 'inactive@mail.com', '$2a$10$KvBOI7Vnh6mLJgLIsbgJZ.gO5fE8PotX5ctuftTO50Y7mkbUI6tpO', 'inactive');

insert into operations (id, type, cost) values (101, 'addition', 1.00);
insert into operations (id, type, cost) values (102, 'subtraction', 1.00);
insert into operations (id, type, cost) values (103, 'multiplication', 2.00);
insert into operations (id, type, cost) values (104, 'division', 2.00);
insert into operations (id, type, cost) values (105, 'square_root', 4.00);
insert into operations (id, type, cost) values (106, 'random_string', 10.00);

insert into records (id, operation_id, user_id, amount, user_balance, operation_response, date, status) values (1, 101, 10001, 1, 100, 'credited', '2024-11-19 11:30:45', 'inactive');
insert into records (id, operation_id, user_id, amount, user_balance, operation_response, date, status) values (2, 101, 10002, 1, 100, 'credited', '2024-11-19 11:30:45', 'inactive');
insert into records (id, operation_id, user_id, amount, user_balance, operation_response, date, status) values (3, 101, 10003, 1, 100, 'credited', '2024-11-19 11:30:45', 'inactive');
insert into records (id, operation_id, user_id, amount, user_balance, operation_response, date, status) values (4, 101, 10004, 1, 100, 'credited', '2024-11-19 11:30:45', 'inactive');
