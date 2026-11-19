# SB-03 · Spring JDBC + Múltiples Datasources

**PEVISA Repuestos** · Spring Boot 4.1.2 · Java 25

## Qué aprenden en esta sesión

- Por qué Spring Boot pierde la auto-configuración con 2+ datasources
- `@Primary` y `@Qualifier` para resolver ambigüedad de beans
- `@EnableJpaRepositories` con `entityManagerFactoryRef` explícito
- `JdbcClient` (Spring 7): API fluida, named params, mapeo automático
- Cuándo usar `.list()` vs `.optional()` vs `.single()`
- Llamar procedimientos almacenados con `JdbcClient`
- Separar modelos por datasource: `Repuesto` (`@Entity`) vs `Pedido` (POJO)
- JPA vs JdbcClient: cuándo usar cada uno

## Arquitectura de esta sesión

```
App Spring Boot
├── Datasource principal (Postgres 5432)
│   ├── JPA + Hibernate
│   ├── RepuestoJpaRepository (paquete jpa/)
│   └── Tabla: repuestos
└── Datasource legado (Postgres 5433)
    ├── JdbcClient
    ├── PedidoLegadoRepository (paquete jdbc/)
    └── Tabla: pedidos + función resumen_ventas_repuesto()
```

## Requisitos

- **Java 25**
- Docker Desktop

## Levantar infraestructura

```bash
docker compose up -d
# Esperar healthcheck de ambos contenedores
docker compose ps
```

## Correr

```bash
cd solution
mvn spring-boot:run
```

## Tests (sin Docker — 13 tests)

```bash
mvn test
```

## Endpoints nuevos en SB-03

| Método | URL | Datasource |
|--------|-----|------------|
| GET | `/api/v1/pedidos` | Legado |
| GET | `/api/v1/pedidos/{numeroPedido}` | Legado |
| GET | `/api/v1/pedidos?estado=PENDIENTE` | Legado |
| GET | `/api/v1/pedidos/repuesto/{codigo}` | Legado |
| GET | `/api/v1/pedidos/repuesto/{codigo}/resumen` | Legado (proc. almacenado) |

## Siguiente sesión

**SB-04 · Flyway** — versionar el schema de BD como si fuera código.
`ddl-auto: create-drop` desaparece para siempre.
