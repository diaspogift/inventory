package com.dddtraining.inventory.port.adapter.persistence.config;


import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

import javax.sql.DataSource;


//@Configuration
public class DataSourceConfig {


 /*  @Bean
    public DataSource dataSource() {

        return DataSourceBuilder
                .create()
                .username("root")
                .password("mysql")
                .url("jdbc:mysql://localhost:3306/inventory")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();
    }*/

 /*   @Bean
    public DataSource testDataSource() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/inventory");
        bds.setUsername("root");
        bds.setPassword("mysql");
        //bds.setDefaultAutoCommit(false);

        System.out.println("bds default auto commit = "+bds.getDefaultAutoCommit());
        System.out.println("bds default auto commit = "+bds.getDefaultAutoCommit());
        System.out.println("bds default auto commit = "+bds.getDefaultAutoCommit());
        System.out.println("bds default auto commit = "+bds.getDefaultAutoCommit());

        return bds;
    }*/



}
