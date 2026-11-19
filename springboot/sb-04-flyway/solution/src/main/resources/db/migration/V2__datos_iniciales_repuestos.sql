-- =============================================================================
-- V2 — Datos iniciales del catálogo PEVISA
-- =============================================================================
-- Los datos de referencia (catálogos, configuraciones base, divisiones)
-- también se versionan con Flyway.
-- Los datos de prueba para desarrollo van en un archivo R__ (repeatable)
-- o en un perfil separado — NUNCA en producción.
-- =============================================================================

INSERT INTO repuestos (codigo, descripcion, division, precio, stock) VALUES
    ('EMP-TOYOTA-001', 'Empaquetadura de motor Toyota Corolla 1.8L',  'EMPAQUETADURAS', 185.00, 42),
    ('EMP-HONDA-002',  'Empaquetadura de motor Honda Civic 1.5T',      'EMPAQUETADURAS', 210.00, 28),
    ('EMP-KIA-003',    'Empaquetadura de motor Kia Sportage 2.0L',     'EMPAQUETADURAS', 195.00,  5),
    ('FIL-BOSCH-001',  'Filtro de aceite Bosch universal',             'BOSCH',           28.50, 150),
    ('FIL-BOSCH-002',  'Filtro de combustible Bosch EFI',              'BOSCH',           45.00,  80),
    ('BAT-VOLTA-001',  'Batería Volta 60Ah 12V',                       'BATERIAS',        320.00,  8),
    ('BAT-VOLTA-002',  'Batería Volta 90Ah 12V',                       'BATERIAS',        420.00,  3),
    ('BUJ-DENSO-001',  'Bujía Denso Iridium IT20',                     'DENSO',            45.00,  0),
    ('BUJ-DENSO-002',  'Bujía Denso Twin Tip TT16',                    'DENSO',            32.00, 95),
    ('CON-NGK-001',    'Cable de bujía NGK RC-TE79',                   'CONTINENTAL',      88.00, 60);
