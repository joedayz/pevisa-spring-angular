# SB-04 · Migraciones de BD con Flyway

**PEVISA Repuestos** · Spring Boot 4.1.2 · Java 25

## Qué aprenden en esta sesión

- Por qué `ddl-auto: create-drop` es un error en producción
- `ddl-auto: validate` + Flyway: quién crea el schema y quién lo verifica
- Convención `V{n}__{desc}.sql` y `R__{desc}.sql`
- Migraciones versionadas vs repetibles: cuándo usar cada una
- El orden correcto al agregar una columna: BD primero, Java después
- Locks distribuidos de Flyway en entornos multi-instancia
- Tests con migraciones H2 simplificadas (sin PL/pgSQL)
- `flyway_schema_history`: el registro auditado de todos los cambios

## Migraciones incluidas

| Archivo | Qué hace |
|---------|----------|
| `V1__crear_tabla_repuestos.sql` | Schema base, constraints, índices |
| `V2__datos_iniciales_repuestos.sql` | 10 repuestos semilla |
| `V3__agregar_columna_marca.sql` | ALTER TABLE + tabla divisiones |
| `V4__constraint_division_check.sql` | CHECK constraint + trigger updated_at |
| `R__vista_inventario.sql` | Vista consolidada (repetible) |

## Requisitos

- **Java 25**
- Docker Desktop

## Levantar

```bash
docker compose up -d
cd solution && mvn spring-boot:run
```

## Verificar Flyway

```bash
curl http://localhost:8080/actuator/flyway
```

## Tests (sin Docker)

```bash
mvn test
```

## Siguiente sesión

**SB-05 · Redis Cache** — `@Cacheable`, `@CacheEvict`, TTL y caché distribuida.
