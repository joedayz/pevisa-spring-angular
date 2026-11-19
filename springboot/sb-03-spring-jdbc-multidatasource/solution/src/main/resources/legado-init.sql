-- Script de inicialización del datasource legado.
-- Simula el sistema ERP anterior de PEVISA que almacena
-- el historial de pedidos de distribuidores.
-- En producción este schema ya existe en Oracle/SQL Server
-- y no lo controlamos con Flyway.

CREATE TABLE IF NOT EXISTS pedidos (
    id              BIGSERIAL PRIMARY KEY,
    numero_pedido   VARCHAR(20)    NOT NULL UNIQUE,
    codigo_repuesto VARCHAR(50)    NOT NULL,
    distribuidor    VARCHAR(100)   NOT NULL,
    cantidad        INTEGER        NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(12, 2) NOT NULL,
    total           NUMERIC(14, 2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
    estado          VARCHAR(20)    NOT NULL DEFAULT 'PENDIENTE',
    fecha_pedido    TIMESTAMP      NOT NULL DEFAULT NOW()
);

-- Datos de ejemplo
INSERT INTO pedidos (numero_pedido, codigo_repuesto, distribuidor, cantidad, precio_unitario, estado, fecha_pedido) VALUES
  ('PED-2024-0001', 'EMP-TOYOTA-001', 'Distribuidora Lima Norte SAC',    10, 185.00, 'ENTREGADO',  '2024-01-15 09:00:00'),
  ('PED-2024-0002', 'FIL-BOSCH-002',  'Autopartes del Sur EIRL',          50, 28.50,  'ENTREGADO',  '2024-01-20 10:30:00'),
  ('PED-2024-0003', 'BAT-VOLTA-003',  'Distribuidora Lima Norte SAC',      5, 320.00, 'EN_TRANSITO','2024-02-01 08:00:00'),
  ('PED-2024-0004', 'EMP-TOYOTA-001', 'Repuestos Arequipa SRL',           20, 185.00, 'ENTREGADO',  '2024-02-10 11:00:00'),
  ('PED-2024-0005', 'BUJ-DENSO-004',  'Autopartes del Sur EIRL',         100, 45.00,  'PENDIENTE',  '2024-03-01 14:00:00'),
  ('PED-2024-0006', 'FIL-BOSCH-002',  'Repuestos Arequipa SRL',           30, 28.50,  'ENTREGADO',  '2024-03-05 09:30:00'),
  ('PED-2024-0007', 'BAT-VOLTA-003',  'Distribuidora Trujillo SAC',        8, 320.00, 'PENDIENTE',  '2024-03-10 16:00:00');

-- Procedimiento almacenado que calcula el resumen de ventas por repuesto
CREATE OR REPLACE FUNCTION resumen_ventas_repuesto(p_codigo VARCHAR)
RETURNS TABLE (
    codigo_repuesto VARCHAR,
    total_unidades  BIGINT,
    total_ingresos  NUMERIC,
    num_pedidos     BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.codigo_repuesto,
        SUM(p.cantidad)::BIGINT,
        SUM(p.total),
        COUNT(*)::BIGINT
    FROM pedidos p
    WHERE p.codigo_repuesto = p_codigo
      AND p.estado = 'ENTREGADO'
    GROUP BY p.codigo_repuesto;
END;
$$ LANGUAGE plpgsql;
