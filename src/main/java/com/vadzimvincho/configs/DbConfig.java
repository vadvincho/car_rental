package com.vadzimvincho.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.vadzimvincho.repositories"})
public class DbConfig {

    @Value("${dataSource.driverClassName}")
    private String driverClassName;
    @Value("${dataSource.URL}")
    private String url;
    @Value("${dataSource.user}")
    private String username;
    @Value("${dataSource.password}")
    private String password;

    @Value("${jpa.hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;
    @Value("${jpa.hibernate.show_sql}")
    private String hibernateShowSql ;
    @Value("${jpa.hibernate.enable_lazy_load_no_trans}")
    private String hibernateEnableLazyLoadNoTrans;
    @Value("${jpa.hibernate.dialect}")
    private String hibernateDialect;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();
        containerEntityManagerFactoryBean.setDataSource(dataSource());
        containerEntityManagerFactoryBean.setPackagesToScan("com.vadzimvincho.models.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        containerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        containerEntityManagerFactoryBean.setJpaProperties(additionalProperties());
        return containerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }

    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        properties.setProperty("hibernate.show_sql", hibernateShowSql);
        properties.setProperty("hibernate.enable_lazy_load_no_trans", String.valueOf(true));
        properties.setProperty("hibernate.dialect", hibernateDialect);
        return properties;
    }
}
