-- Versión H2 de V1 — sin índice GIN (full-text Postgres, no soportado en H2)
CREATE TABLE IF NOT EXISTS repuestos (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo      VARCHAR(50)     NOT NULL,
    descripcion VARCHAR(200)    NOT NULL,
    division    VARCHAR(50)     NOT NULL,
    precio      NUMERIC(12, 2)  NOT NULL,
    stock       INTEGER         NOT NULL DEFAULT 0,
    created_at  TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP       NOT NULL DEFAULT NOW(),

    CONSTRAINT repuestos_codigo_uq  UNIQUE (codigo),
    CONSTRAINT repuestos_precio_ck  CHECK  (precio > 0),
    CONSTRAINT repuestos_stock_ck   CHECK  (stock >= 0)
);

CREATE INDEX IF NOT EXISTS repuestos_division_idx ON repuestos (division);
