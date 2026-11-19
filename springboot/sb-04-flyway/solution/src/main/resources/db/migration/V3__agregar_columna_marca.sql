-- =============================================================================
-- V3 — Agregar columna marca y tabla divisiones
-- =============================================================================
-- Esta migración simula un cambio de negocio real:
-- el equipo de producto pidió registrar la marca del fabricante
-- por separado de la división comercial.
--
-- ¿Cómo habríamos hecho esto sin Flyway?
--   1. Alguien corre un ALTER TABLE en producción a mano
--   2. Nadie lo documenta
--   3. El entorno de QA no tiene el cambio
--   4. Un nuevo dev en el equipo no sabe que existe esa columna
--
-- Con Flyway:
--   1. Crear este archivo V3__...sql
--   2. Commit en Git
--   3. En el próximo deploy Flyway lo ejecuta automáticamente
--      en TODOS los entornos en el orden correcto
-- =============================================================================

-- Agregar columna marca (nullable para no romper los datos existentes)
ALTER TABLE repuestos
    ADD COLUMN marca VARCHAR(100);

-- Poblar la marca en los datos existentes basándose en la división
UPDATE repuestos SET marca = 'Toyota'      WHERE codigo = 'EMP-TOYOTA-001';
UPDATE repuestos SET marca = 'Honda'       WHERE codigo = 'EMP-HONDA-002';
UPDATE repuestos SET marca = 'Kia'         WHERE codigo = 'EMP-KIA-003';
UPDATE repuestos SET marca = 'Bosch'       WHERE codigo LIKE 'FIL-BOSCH%';
UPDATE repuestos SET marca = 'Volta'       WHERE codigo LIKE 'BAT-VOLTA%';
UPDATE repuestos SET marca = 'Denso'       WHERE codigo LIKE 'BUJ-DENSO%';
UPDATE repuestos SET marca = 'Continental' WHERE codigo LIKE 'CON-NGK%';

-- Una vez actualizados los datos existentes, hacer la columna NOT NULL
ALTER TABLE repuestos
    ALTER COLUMN marca SET NOT NULL;

-- Tabla de divisiones como catálogo normalizado
CREATE TABLE divisiones (
    id          SERIAL      PRIMARY KEY,
    codigo      VARCHAR(50) NOT NULL,
    nombre      VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activo      BOOLEAN     NOT NULL DEFAULT TRUE,

    CONSTRAINT divisiones_codigo_uq UNIQUE (codigo)
);

INSERT INTO divisiones (codigo, nombre, descripcion) VALUES
    ('EMPAQUETADURAS', 'Empaquetaduras',   'Fabricación propia de empaquetaduras de motor'),
    ('BOSCH',          'Bosch',            'Distribuidor oficial Bosch Perú'),
    ('BATERIAS',       'Baterías',         'Línea de baterías Volta y otras marcas'),
    ('DENSO',          'Denso',            'Distribuidor oficial Denso Perú'),
    ('CONTINENTAL',    'Continental',      'Cables, correas y componentes Continental');

-- Índice en marca para búsquedas futuras
CREATE INDEX repuestos_marca_idx ON repuestos (marca);
