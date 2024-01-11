package com.customermanagement.management.configuration;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Bean
    public SparkSession getSparkSession() {
    	return SparkSession.builder()
    		      .appName("SparkDataProfilingService")
    		      .master("local")
    		      .getOrCreate();
         }
}
