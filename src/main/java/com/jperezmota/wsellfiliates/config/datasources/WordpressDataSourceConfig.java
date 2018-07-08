package com.jperezmota.wsellfiliates.config.datasources;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
	    basePackages = "com.jperezmota.wsellfiliates.dao.wordpress", 
	    entityManagerFactoryRef = "wordpressEntityManagerFactory", 
	    transactionManagerRef = "wordpressTransactionManager"
        )
public class WordpressDataSourceConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "wordpress.datasource")
	public DataSource wordpressDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean wordpressEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(wordpressDataSource())
				      .packages("com.jperezmota.wsellfiliates.entity.wordpress")
				      .build();
	}
	
	@Bean
	@Autowired
	public PlatformTransactionManager wordpressTransactionManager(@Qualifier("wordpressEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
