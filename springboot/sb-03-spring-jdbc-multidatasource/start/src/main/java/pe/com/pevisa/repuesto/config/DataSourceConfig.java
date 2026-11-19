package pe.com.pevisa.repuesto.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * TODO 1: Anotar esta clase con @Configuration, @EnableTransactionManagement
 *         y @EnableJpaRepositories apuntando a:
 *           basePackages     = "pe.com.pevisa.repuesto.repository.jpa"
 *           entityManagerFactoryRef = "principalEntityManagerFactory"
 *           transactionManagerRef   = "principalTransactionManager"
 *
 *         Pregunta: ¿por qué necesitamos @EnableJpaRepositories aquí
 *                   si Spring Boot normalmente lo hace automático?
 *
 * TODO 2: Crear el bean principalDataSourceProperties() con:
 *           @Bean("principalDataSourceProperties")
 *           @ConfigurationProperties("spring.datasource")
 *
 * TODO 3: Crear el bean principalDataSource() con:
 *           @Bean @Primary
 *           @ConfigurationProperties("spring.datasource.hikari")
 *           Pregunta: ¿qué hace @Primary? ¿cuándo NO lo usarías?
 *
 * TODO 4: Crear principalEntityManagerFactory() — el bean que configura JPA:
 *           - new HibernateJpaVendorAdapter()
 *           - setPackagesToScan("pe.com.pevisa.repuesto.model")
 *           - setJpaPropertyMap(props) con hibernate.hbm2ddl.auto, show_sql, etc.
 *
 * TODO 5: Crear principalTransactionManager() con @Bean @Primary
 *           Recibe EntityManagerFactory y retorna JpaTransactionManager.
 *
 * TODO 6: Crear legadoDataSourceProperties() y legadoDataSource() SIN @Primary
 *           Leen de "spring.datasource-legado.*"
 *           Pregunta: ¿qué pasa si olvidas el qualifier al inyectar este DataSource?
 *
 * TODO 7: Crear legadoJdbcClient() que retorna JdbcClient.create(legadoDataSource)
 *           Con @Bean("legadoJdbcClient") para poder inyectarlo por nombre.
 */
@EnableTransactionManagement
// TODO 1: completar anotaciones
public class DataSourceConfig {

    // ════════════════════════════════════════════════════════
    // DATASOURCE PRINCIPAL
    // ════════════════════════════════════════════════════════

    // TODO 2
    public DataSourceProperties principalDataSourceProperties() {
        return new DataSourceProperties();
    }

    // TODO 3
    public DataSource principalDataSource(DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    // TODO 4
    public LocalContainerEntityManagerFactoryBean principalEntityManagerFactory(DataSource dataSource) {
        return new LocalContainerEntityManagerFactoryBean();
    }

    // TODO 5
    public PlatformTransactionManager principalTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    // ════════════════════════════════════════════════════════
    // DATASOURCE LEGADO
    // ════════════════════════════════════════════════════════

    // TODO 6
    public DataSourceProperties legadoDataSourceProperties() {
        return new DataSourceProperties();
    }

    // TODO 6
    public DataSource legadoDataSource(DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    // TODO 7
    public JdbcClient legadoJdbcClient(DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }
}
