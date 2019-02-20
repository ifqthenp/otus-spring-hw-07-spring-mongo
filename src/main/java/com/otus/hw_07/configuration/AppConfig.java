package com.otus.hw_07.configuration;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import com.mongodb.MongoClient;
import com.otus.hw_07.changelog.LibraryChangeLog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static final String LIBRARY = "library";

    @Bean
    public Mongock mongock(final ApplicationContext ac, final MongoClient mongoClient) {
        return new SpringBootMongockBuilder(mongoClient, LIBRARY,
            LibraryChangeLog.class.getPackage().getName())
            .setApplicationContext(ac)
            .setLockQuickConfig()
            .build();
    }

}
