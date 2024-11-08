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
import com.example.junghqlo.handler.MultipartFileHandler;
import com.example.junghqlo.handler.LocalDateTimeTypeHandler;

@Configuration
@MapperScan("com.example.junghqlo.mapper")
public class MyBatisConfig {

  // dataSource ------------------------------------------------------------------------------------
  DataSource dataSource;
  MyBatisConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  // SqlSessionFactory -----------------------------------------------------------------------------
  @Bean
  SqlSessionFactory sqlSessionFactory() throws Exception {

    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

    sessionFactory.setDataSource(dataSource);
    sessionFactory.setTypeAliasesPackage("com.example.junghqlo.domain");
    sessionFactory.setTypeHandlersPackage("com.example.junghqlo.handler");

    // 타입 핸들러 별칭 설정
    sessionFactory
      .getObject()
      .getConfiguration()
      .getTypeAliasRegistry()
      .registerAlias("LocalDateTimeTypeHandler", LocalDateTimeTypeHandler.class);


    sessionFactory
      .getObject()
      .getConfiguration()
      .getTypeAliasRegistry()
      .registerAlias("MultipartFileHandler", MultipartFileHandler.class);

    return sessionFactory.getObject();
  }

  // SqlSessionTemplate ----------------------------------------------------------------------------
  @Bean
  SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {

    return new SqlSessionTemplate(sqlSessionFactory);
  }

  // DataSourceTransactionManager ------------------------------------------------------------------
  @Bean
  @DependsOnDatabaseInitialization
  DataSourceTransactionManager transactionManager() {

    return new DataSourceTransactionManager(dataSource);
  }

}