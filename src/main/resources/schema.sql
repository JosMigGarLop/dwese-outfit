-- Crear tabla para los tipos de ropa
CREATE TABLE IF NOT EXISTS tipos_de_ropa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS outfits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    fecha_creacion DATE NOT NULL DEFAULT CURRENT_DATE,
    tipo_de_ropa_id BIGINT,  -- Hacemos nullable para que pueda ser NULL
    CONSTRAINT fk_tipo_de_ropa FOREIGN KEY (tipo_de_ropa_id)
    REFERENCES tipos_de_ropa(id)
    ON DELETE SET NULL  -- Esto asegura que se ponga NULL en `outfits` cuando elimines un `TipoDeRopa`
    ON UPDATE CASCADE
);




-- Crear la tabla 'users'
CREATE TABLE IF NOT EXISTS users (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   username VARCHAR(50) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   enabled BOOLEAN NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   image VARCHAR(255),
   created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   last_password_change_date TIMESTAMP
);
-- Crear la tabla 'roles'
CREATE TABLE IF NOT EXISTS roles (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(50) UNIQUE NOT NULL
);
-- Crear la tabla 'user_roles'
CREATE TABLE IF NOT EXISTS user_roles (
   user_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL,
   PRIMARY KEY (user_id, role_id),
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
