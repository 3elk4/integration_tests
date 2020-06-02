--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'kate@domain.com', 'Kate', 'Jackson')
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'tester@domain.com', 'Tester', 'Tester')

insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian', 'Williams')
insert into user (account_status, email, first_name) values ('NEW', 'mary@domain.com', 'Mary', 'Poppins')

insert into blog_post (user_id, entry) values (1, 'testing post')

insert into like_post (user_id, post_id) values(3, 1)