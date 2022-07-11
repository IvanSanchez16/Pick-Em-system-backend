INSERT INTO roles(name) VALUES
('ADMIN'),
('MODERATOR'),
('USER')

INSERT INTO users_roles(user_id, role_id) VALUES
(2, 1)