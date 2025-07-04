package com.example.hr.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(
   exclude = {
		   MongoAutoConfiguration.class,
		   MongoDataAutoConfiguration.class,
		   MongoRepositoriesAutoConfiguration.class
   }
)
@ConditionalOnProperty(name="persistenceStrategy", havingValue = "jpa")
public class DisableMongoAutoConfiguration {

}
