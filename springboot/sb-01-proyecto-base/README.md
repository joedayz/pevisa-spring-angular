# SB-01 · Proyecto base y arquitectura en capas

**PEVISA Repuestos** · Spring Boot 3.3 · Java 21

## Qué aprenden en esta sesión

- Estructura de paquetes para un proyecto Spring Boot que escala en equipo
- Separación Controller → Service → Repository y por qué importa
- DTOs de entrada (Request) y salida (Response) separados del modelo
- Validación con Bean Validation (`@Valid`, `@NotBlank`, `@DecimalMin`…)
- Manejo de errores con `@RestControllerAdvice` y RFC 7807 (`ProblemDetail`)
- Por qué el Repository es una interfaz (lo verán pagar en SB-02)

## Cómo usar este módulo

```
sb-01-proyecto-base/
├── start/      ← abrir esto al inicio de la sesión (tiene TODOs)
├── solution/   ← implementación completa (referencia del instructor)
└── guia.html   ← abrir en el navegador durante la sesión
```

## Requisitos

- **Java 21** (Lombok no es compatible con Java 22+)
- Maven 3.9+

Si tienes múltiples JDKs (sdkman, etc.), apunta a Java 21 antes de correr:

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
# o en Linux/sdkman:
sdk use java 21.0.6-oracle
```

## Correr

```bash
# start o solution
cd solution
mvn spring-boot:run
```

```bash
# Tests
mvn test
```

## Endpoints

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/v1/repuestos` | Listar todos |
| GET | `/api/v1/repuestos?division=BOSCH` | Filtrar por división |
| GET | `/api/v1/repuestos/{id}` | Buscar por id |
| POST | `/api/v1/repuestos` | Crear (201) |
| PUT | `/api/v1/repuestos/{id}` | Actualizar |
| DELETE | `/api/v1/repuestos/{id}` | Eliminar (204) |

## Verificar salud

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/info
```

## Siguiente sesión

**SB-02 · JPA + Spring Data** — `InMemoryRepuestoRepository` desaparece,
`Repuesto` se convierte en entidad JPA, el Service no cambia.
