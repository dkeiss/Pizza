-- User
INSERT INTO USER (ID, USER_NAME, PASSWORD, PASSWORD_TYPE, IS_ADMIN, FIRST_NAME, LAST_NAME, DISCOUNT, CREATION_DATE) VALUES (1, 'admin', 'password', 'PLAIN', true, 'Bruce', 'Wayne', 10, now());
INSERT INTO USER (ID, USER_NAME, IS_ADMIN, FIRST_NAME, LAST_NAME, DISCOUNT, CREATION_DATE) VALUES (2, 'user', false, 'Hans', 'Maulwurf', null, now());
