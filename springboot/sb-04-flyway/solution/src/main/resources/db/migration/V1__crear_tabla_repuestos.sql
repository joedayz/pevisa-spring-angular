-- =============================================================================
-- V1 — Crear tabla repuestos
-- =============================================================================
-- Convención de nombres Flyway:
--   V{version}__{descripcion}.sql
--   V  = versión (incrementa siempre, nunca repite)
--   __ = dos guiones bajos (separador obligatorio)
--   descripción = snake_case
--
-- Flyway ejecuta este archivo UNA sola vez.
-- Si lo modificas después de ejecutado, Flyway lanza un error
-- porque el checksum ya no coincide con lo registrado en flyway_schema_history.
-- Para cambiar el schema: crea V2, V3, etc.
-- =============================================================================

CREATE TABLE repuestos (
    id          BIGSERIAL       PRIMARY KEY,
    codigo      VARCHAR(50)     NOT NULL,
    descripcion VARCHAR(200)    NOT NULL,
    division    VARCHAR(50)     NOT NULL,
    precio      NUMERIC(12, 2)  NOT NULL,
    stock       INTEGER         NOT NULL    DEFAULT 0,
    created_at  TIMESTAMP       NOT NULL    DEFAULT NOW(),
    updated_at  TIMESTAMP       NOT NULL    DEFAULT NOW(),

    -- Constraints explícitos con nombre — facilita identificar errores en logs
    CONSTRAINT repuestos_codigo_uq     UNIQUE (codigo),
    CONSTRAINT repuestos_precio_ck     CHECK  (precio > 0),
    CONSTRAINT repuestos_stock_ck      CHECK  (stock >= 0)
);

-- Índice para búsquedas por división (muy frecuentes en PEVISA)
CREATE INDEX repuestos_division_idx ON repuestos (division);

-- Índice para búsquedas full-text en descripción (nativo Postgres)
CREATE INDEX repuestos_descripcion_fts_idx
    ON repuestos USING gin(to_tsvector('spanish', descripcion));

COMMENT ON TABLE  repuestos            IS 'Catálogo de repuestos automotrices PEVISA';
COMMENT ON COLUMN repuestos.codigo     IS 'Código interno PEVISA. Ej: EMP-TOYOTA-001';
COMMENT ON COLUMN repuestos.division   IS 'División comercial. Ej: BOSCH, DENSO, BATERIAS';
