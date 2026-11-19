-- =============================================================================
-- R__ — Migración repetible: vista de inventario
-- =============================================================================
-- Convención Flyway para migraciones repetibles:
--   R__{descripcion}.sql   (sin número de versión)
--
-- Se ejecuta siempre que su checksum cambie.
-- Ideal para vistas, funciones y procedimientos que se "reemplazan"
-- en lugar de "modificarse" — así no necesitas crear V5, V6...
-- cada vez que ajustas la lógica de la vista.
--
-- IMPORTANTE: las repetibles se ejecutan DESPUÉS de todas las versionadas.
-- =============================================================================

CREATE OR REPLACE VIEW vista_inventario AS
SELECT
    r.id,
    r.codigo,
    r.descripcion,
    r.marca,
    r.division,
    d.nombre        AS nombre_division,
    r.precio,
    r.stock,
    r.precio * r.stock                          AS valor_inventario,
    CASE
        WHEN r.stock = 0   THEN 'AGOTADO'
        WHEN r.stock < 10  THEN 'BAJO'
        WHEN r.stock < 50  THEN 'NORMAL'
        ELSE                    'ALTO'
    END                                         AS estado_stock,
    r.created_at,
    r.updated_at
FROM repuestos r
LEFT JOIN divisiones d ON d.codigo = r.division;

COMMENT ON VIEW vista_inventario IS
    'Vista consolidada del inventario con estado de stock y valor calculado';
