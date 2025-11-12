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
import com.example.junghqlo.handler.LocalDateTimeTypeHandler;
import com.example.junghqlo.handler.ListTypeHandler;

@Configuration
@MapperScan("com.example.junghqlo.mapper")
public class MyBatisConfig {

  // dataSource ------------------------------------------------------------------------------------
  private final DataSource dataSource;

  public MyBatisConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  // SqlSessionFactory -----------------------------------------------------------------------------
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setTypeAliasesPackage("com.example.junghqlo.domain");
    sessionFactory.setTypeHandlersPackage("com.example.junghqlo.handler");

    SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
    if (sqlSessionFactory != null) {

      // 타입 핸들러 별칭 설정
      sqlSessionFactory
        .getConfiguration()
        .getTypeAliasRegistry()
        .registerAlias("LocalDateTimeTypeHandler", LocalDateTimeTypeHandler.class);
      sqlSessionFactory
        .getConfiguration()
        .getTypeAliasRegistry()
        .registerAlias("ListTypeHandler", ListTypeHandler.class);

      // 타입 핸들러 등록
      sqlSessionFactory
        .getConfiguration()
        .getTypeHandlerRegistry()
        .register(LocalDateTimeTypeHandler.class);
      sqlSessionFactory
        .getConfiguration()
        .getTypeHandlerRegistry()
        .register(ListTypeHandler.class);
    }

    return sqlSessionFactory;
  }

  // SqlSessionTemplate ----------------------------------------------------------------------------
  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  // DataSourceTransactionManager ------------------------------------------------------------------
  @Bean
  @DependsOnDatabaseInitialization
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }
}
