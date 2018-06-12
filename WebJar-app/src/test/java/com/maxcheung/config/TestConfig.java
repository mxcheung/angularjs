package com.maxcheung.config;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

@TestConfiguration
public class TestConfig {

    @Bean
    public EmbeddedDatabase webJarDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .setName("web-jar")
                .build();
    }

    @Bean
    public IDataTypeFactory dataTypeFactory() {
        return new HsqldbDataTypeFactory();
    }

    @Bean
    public DatabaseConfigBean webJarDatabaseConfig(IDataTypeFactory dataTypeFactory) {
        DatabaseConfigBean config = new DatabaseConfigBean();
        config.setDatatypeFactory(dataTypeFactory);
        config.setAllowEmptyFields(true);
        return config;
    }

    @Bean(name = "dbUnitWebJarConnection")
    public DatabaseDataSourceConnection dbUnitWebJarConnection(
            @Autowired @Qualifier("webJarDatabaseConfig") DatabaseConfigBean databaseConfig,
            @Autowired  @Qualifier("webJarDatabase") EmbeddedDatabase database)
                    throws Exception {
        DatabaseDataSourceConnectionFactoryBean factory = new DatabaseDataSourceConnectionFactoryBean();
        factory.setDatabaseConfig(databaseConfig);
        factory.setDataSource(database);
        return factory.getObject();
    }
}