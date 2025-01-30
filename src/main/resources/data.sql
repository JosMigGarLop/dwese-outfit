-- Insertar tipos de ropa
INSERT INTO tipo_de_ropa (nombre, descripcion) VALUES
('Casual', 'Ropa para el día a día, cómoda y relajada.'),
('Deportiva', 'Ropa adecuada para hacer ejercicio y actividades deportivas.'),
('Formales', 'Ropa adecuada para ocasiones especiales y eventos formales.');

-- Insertar outfits
INSERT INTO outfits (nombre, descripcion, imagen, fecha_creacion, tipo_de_ropa_id) VALUES
('Conjunto de Verano', 'Conjunto ligero para el calor del verano.', 'https://example.com/imagen_verano.jpg', NOW(), 1),
('Ropa Deportiva', 'Conjunto deportivo para entrenamientos de gimnasio.', 'https://example.com/imagen_deporte.jpg', NOW(), 2),
('Traje Formal', 'Traje adecuado para una boda o evento formal.', 'https://example.com/imagen_traje.jpg', NOW(), 3);


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