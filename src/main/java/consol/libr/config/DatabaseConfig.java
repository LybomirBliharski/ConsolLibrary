package consol.libr.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import consol.libr.dao.BookDao;
import consol.libr.dao.impl.BookDaoImpl;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/lib?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("\u0444\u0432\u044C\u0448\u0442");
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setDataSource(dataSource);

		// Classpath scanning of @Component, @Service, etc annotated class
		entityManagerFactory.setPackagesToScan("consol.libr.*");

		// Vendor adapter
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

		// Hibernate properties
		Properties additionalProperties = new Properties();
		additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		additionalProperties.put("hibernate.show_sql", "false");
		additionalProperties.put("hibernate.hbm2ddl.auto", "update");
		entityManagerFactory.setJpaProperties(additionalProperties);

		return entityManagerFactory;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public BookDao bookdao() {
		return new BookDaoImpl();
	}

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;

}
