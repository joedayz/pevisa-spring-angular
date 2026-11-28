package pe.com.pevisa.repuesto.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.flyway.autoconfigure.FlywayDataSource;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuración explícita de múltiples datasources.
 *
 * PROBLEMA:
 * Cuando Spring Boot detecta un solo DataSource en el classpath,
 * auto-configura JPA, HikariCP y el TransactionManager por nosotros.
 * Al agregar un segundo DataSource Spring Boot no sabe cuál usar
 * para JPA — lanza: "No qualifying bean of type DataSource".
 * Debemos tomar el control manualmente.
 *
 * SOLUCIÓN:
 * 1. @Primary en el datasource principal → Spring lo usa por defecto
 *    cuando haya ambigüedad.
 * 2. Declarar explícitamente: EntityManagerFactory, TransactionManager,
 *    y JdbcClient para cada datasource.
 * 3. @EnableJpaRepositories(entityManagerFactoryRef, transactionManagerRef)
 *    → apunta los repositorios JPA al datasource correcto.
 *
 * SEPARACIÓN DE RESPONSABILIDADES:
 *   datasource principal  → JPA + Hibernate → catálogo de repuestos
 *   datasource legado     → JdbcClient      → historial de pedidos (solo lectura)
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "pe.com.pevisa.repuesto.repository.jpa",
        entityManagerFactoryRef = "principalEntityManagerFactory",
        transactionManagerRef = "principalTransactionManager"
)
public class DataSourceConfig {

    // ════════════════════════════════════════════════════════════════════════
    // DATASOURCE PRINCIPAL — Postgres, catálogo de repuestos, JPA
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Lee spring.datasource.* del application.yml y construye el DataSource.
     * @Primary → cuando algún bean pide DataSource sin qualifier, recibe este.
     */
    @Bean
    @Primary
    @FlywayDataSource
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource principalDataSource(
            @Qualifier("principalDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    @Bean("principalDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties principalDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * EntityManagerFactory para JPA.
     * Escanea entidades solo en el paquete "model" — aisladas del datasource legado.
     */
    @Bean("principalEntityManagerFactory")
    @Primary
    @DependsOn("flywayInitializer")
    public LocalContainerEntityManagerFactoryBean principalEntityManagerFactory(
            @Qualifier("principalDataSource") DataSource dataSource) {

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(false);

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "validate");
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.show_sql", true);
        props.put("hibernate.format_sql", true);
        props.put("hibernate.jdbc.batch_size", 50);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("pe.com.pevisa.repuesto.model");
        factory.setJpaVendorAdapter(adapter);
        factory.setJpaPropertyMap(props);
        return factory;
    }

    /**
     * TransactionManager para el datasource principal.
     * @Transactional sin qualifier usará este por ser @Primary implícito.
     */
    @Bean("principalTransactionManager")
    @Primary
    public PlatformTransactionManager principalTransactionManager(
            @Qualifier("principalEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    // ════════════════════════════════════════════════════════════════════════
    // DATASOURCE LEGADO — historial de pedidos, solo JdbcClient
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Lee spring.datasource-legado.* del application.yml.
     * SIN @Primary — solo se inyecta cuando se pide explícitamente
     * con @Qualifier("legadoDataSource").
     */
    @Bean("legadoDataSource")
    @ConfigurationProperties("spring.datasource-legado.hikari")
    public DataSource legadoDataSource(
            @Qualifier("legadoDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    @Bean("legadoDataSourceProperties")
    @ConfigurationProperties("spring.datasource-legado")
    public DataSourceProperties legadoDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * JdbcClient para el datasource legado.
     *
     * JdbcClient (Spring 6 / Spring Boot 3.2) es la evolución de JdbcTemplate:
     *   - API fluida y encadenable
     *   - Mapeo automático a records y POJOs
     *   - Named parameters sin PreparedStatementSetter
     *   - Sin ResultSet manual
     *
     * Lo usamos para el legado porque:
     *   1. No necesitamos JPA — el schema no lo controlamos nosotros
     *   2. Las queries son complejas y específicas de ese esquema
     *   3. Es solo lectura — no necesitamos el ciclo de vida de las entidades
     */
    @Bean("legadoJdbcClient")
    public JdbcClient legadoJdbcClient(
            @Qualifier("legadoDataSource") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }
}
