-- =============================================================================
-- V4 — Agregar constraint CHECK en división y función trigger para updated_at
-- =============================================================================
-- Dos mejoras de integridad que conviene agregar por separado:
--   1. CHECK constraint en división para validar en BD (no solo en Java)
--   2. Trigger para mantener updated_at automáticamente
--
-- Separar en V4 (y no incluir en V1) muestra cómo el schema
-- evoluciona iterativamente — igual que el código.
-- =============================================================================

-- 1. Constraint CHECK en división
--    La validación en Java (@NotBlank) evita que llegue basura al Service,
--    pero un CHECK en BD garantiza integridad aunque alguien acceda
--    directamente con SQL o con otro cliente.
ALTER TABLE repuestos
    ADD CONSTRAINT repuestos_division_ck
    CHECK (division IN ('EMPAQUETADURAS', 'BOSCH', 'BATERIAS', 'DENSO', 'CONTINENTAL'));

-- 2. Función trigger para updated_at
--    En SB-02 usamos @PreUpdate en Java.
--    Este trigger garantiza que updated_at se actualiza incluso cuando
--    alguien modifica la fila directamente con SQL (ej: scripts de migración).
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER repuestos_updated_at_trigger
    BEFORE UPDATE ON repuestos
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
