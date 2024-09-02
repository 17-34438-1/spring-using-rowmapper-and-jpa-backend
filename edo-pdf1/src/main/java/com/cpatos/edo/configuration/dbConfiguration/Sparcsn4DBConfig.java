package com.cpatos.edo.configuration.dbConfiguration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "sparcsn4EntityManagerFactory",
        basePackages = {"com.cpatos.edo.repository.sparcsn4"}, transactionManagerRef = "sparcsn4TransactionManager")
public class Sparcsn4DBConfig {

    @Bean(name = "sparcsn4Datasource")
    @ConfigurationProperties(prefix = "spring.sparcsn4.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sparcsn4EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("sparcsn4Datasource") DataSource dataSource){

        Map<String,Object> properties = new HashMap<>();
        //properties.put("hibernate.hbm2ddl.auto", "update");
        //properties.put("hibernate.dialect", "org.hibernate.dialect.MYSQL5Dialect");

        return builder.dataSource(dataSource).properties(properties)
                .packages("com.cpatos.edo.model.sparcsn4").persistenceUnit("Sparcsn4").build();

    }

    @Bean(name = "sparcsn4TransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("sparcsn4EntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

}
