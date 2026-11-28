ALTER TABLE repuestos ADD COLUMN IF NOT EXISTS marca VARCHAR(100);

UPDATE repuestos SET marca = 'Toyota'      WHERE codigo = 'EMP-TOYOTA-001';
UPDATE repuestos SET marca = 'Bosch'       WHERE codigo LIKE 'FIL-BOSCH%';
UPDATE repuestos SET marca = 'Volta'       WHERE codigo LIKE 'BAT-VOLTA%';
UPDATE repuestos SET marca = 'Denso'       WHERE codigo LIKE 'BUJ-DENSO%';
UPDATE repuestos SET marca = 'Continental' WHERE codigo LIKE 'CON-NGK%';

ALTER TABLE repuestos ALTER COLUMN marca SET NOT NULL;

CREATE TABLE IF NOT EXISTS divisiones (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo      VARCHAR(50)  NOT NULL,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT divisiones_codigo_uq UNIQUE (codigo)
);

INSERT INTO divisiones (codigo, nombre) VALUES
    ('EMPAQUETADURAS', 'Empaquetaduras'),
    ('BOSCH',          'Bosch'),
    ('BATERIAS',       'Baterías'),
    ('DENSO',          'Denso'),
    ('CONTINENTAL',    'Continental');
