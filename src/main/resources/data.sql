-- insert initial admin if no user exists
INSERT INTO USER SELECT * FROM (
  SELECT 1, 'admin@test.de', 'password', 'PLAIN', TRUE, 'Bruce', 'Wayne', 0.0, NOW(), NULL
) X WHERE NOT EXISTS(SELECT * FROM USER);