package pl.pongut.warehouse.domain.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "pl.pongut.warehouse.domain")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoPort;

    @Override
    protected String getDatabaseName() {
        return "warehouse";
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://" + mongoHost + ":" + mongoPort);
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://" + mongoHost + ":" + mongoPort);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
    }
}