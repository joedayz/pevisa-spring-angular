# SB-02 · JPA + Spring Data

**PEVISA Repuestos** · Spring Boot 3.3 · Java 21

## Qué aprenden en esta sesión

- Convertir un POJO en entidad JPA con `@Entity`, `@Table`, `@Column`
- `GenerationType.IDENTITY` vs `SEQUENCE` y cuándo usar cada uno
- Consultas derivadas: Spring Data genera el SQL a partir del nombre del método
- `@Query` JPQL vs SQL nativo: cuándo usar cada uno
- Paginación con `Pageable` → `Page<T>` → `PageResponse<T>` propio
- Proyecciones: traer solo los campos necesarios
- `@Transactional(readOnly = true)` y por qué importa en producción
- El problema N+1 y cómo evitarlo con `JOIN FETCH`
- Tests con `@DataJpaTest` + H2: sin Docker, sin Postgres

## El punto clave de esta sesión

`InMemoryRepuestoRepository` desaparece. `RepuestoService` no cambió ni una línea en su firma pública. Eso es lo que compra definir una interfaz en SB-01.

## Requisitos

- **Java 21** (Lombok no es compatible con Java 22+)
- Docker Desktop corriendo

## Levantar infraestructura

```bash
docker compose up -d
# Esperar hasta que el healthcheck diga "healthy"
docker compose ps
```

## Correr

```bash
cd solution
mvn spring-boot:run
```

## Tests (sin Docker)

```bash
mvn test
# 16 tests, 0 fallos — usan H2 en memoria
```

## Endpoints nuevos en SB-02

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/v1/repuestos/paginado?pagina=0&tamano=10&ordenarPor=codigo` | Lista paginada |
| GET | `/api/v1/repuestos/stock-bajo?umbral=10` | Stock crítico |
| GET | `/api/v1/repuestos/buscar?q=toyota` | Búsqueda en descripción |

## Siguiente sesión

**SB-03 · Spring JDBC + múltiples datasources** — conectar la app a un segundo datasource (Oracle legado con historial de pedidos) y aprender `JdbcClient` para cuando JPA es demasiado.
