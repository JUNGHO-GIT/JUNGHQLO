package com.example.junghqlo.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan("com.example.junghqlo.mapper")
public class MyBatisConfig {

  // dataSource
  DataSource dataSource;
  MyBatisConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  // SqlSessionFactory
  @Bean
  SqlSessionFactory sqlSessionFactory() throws Exception {

    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setTypeAliasesPackage("com.example.junghqlo.domain");
    return sessionFactory.getObject();
  }

  // SqlSessionTemplate
  @Bean
  SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {

    return new SqlSessionTemplate(sqlSessionFactory);
  }

    // DataSourceTransactionManager
    @Bean
    @DependsOnDatabaseInitialization
    DataSourceTransactionManager transactionManager() {

    return new DataSourceTransactionManager(dataSource);
  }

}