-- Insertar datos en la tabla tipos_de_ropa
INSERT INTO tipos_de_ropa (nombre, descripcion) VALUES
('Camiseta', 'Prenda básica de manga corta para la parte superior del cuerpo.'),
('Pantalón', 'Prenda de vestir para la parte inferior del cuerpo, generalmente con piernas largas.'),
('Chaqueta', 'Prenda de abrigo que cubre el torso y generalmente tiene mangas.'),
('Zapatos', 'Prenda de calzado para los pies, comúnmente de cuero o tela.');

-- Insertar datos en la tabla outfits
INSERT INTO outfits (nombre, descripcion, fecha_creacion, tipo_de_ropa_id) VALUES
('Outfit Casual', 'Conjunto de ropa casual para el día a día.', CURRENT_DATE, 1),  -- Tipo de ropa: Camiseta
('Outfit Formal', 'Conjunto de ropa para ocasiones formales.', CURRENT_DATE, 2),  -- Tipo de ropa: Pantalón
('Outfit Invierno', 'Conjunto de ropa para temperaturas frías.', CURRENT_DATE, 3),  -- Tipo de ropa: Chaqueta
('Outfit Deportivo', 'Conjunto de ropa para hacer ejercicio.', CURRENT_DATE, 4);  -- Tipo de ropa: Zapatos

-- Insertar datos de ejemplo para 'roles'
INSERT IGNORE INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_USER');


-- Insertar datos de ejemplo para 'users'. La contraseña de cada usuario es password
INSERT IGNORE INTO users (id, username, password, enabled, first_name, last_name, image, created_date, last_modified_date, last_password_change_date) VALUES
(1, 'admin', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Admin', 'User', '/images/admin.jpg', NOW(), NOW(), NOW()),
(2, 'manager', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Manager', 'User', '/images/manager.jpg', NOW(), NOW(), NOW()),
(3, 'normal', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Regular', 'User', '/images/user.jpg', NOW(), NOW(), NOW()),
(4, 'JosMigGarLop', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'José Miguel', 'García', '/images/user.jpg', NOW(), NOW(), NOW());

-- Asignar el rol de administrador al usuario con id 1
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1);
-- Asignar el rol de gestor al usuario con id 2
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(2, 2);
-- Asignar el rol de usuario normal al usuario con id 3
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(3, 3);
-- Asignar el rol de usuario normal al usuario con id 3
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(4, 3);