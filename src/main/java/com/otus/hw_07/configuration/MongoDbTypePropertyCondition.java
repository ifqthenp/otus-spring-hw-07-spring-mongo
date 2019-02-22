package com.otus.hw_07.configuration;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.Nullable;

public class MongoDbTypePropertyCondition implements Condition {

    private static final String DB_TYPE = "mongo-prod";
    private static final String APP_DB_TYPE = "app.dbType";

    @Override
    public boolean matches(@Nullable final ConditionContext context, @Nullable final AnnotatedTypeMetadata metadata) {
        String dbType = context != null ? context.getEnvironment().getProperty(APP_DB_TYPE) : null;
        System.out.println(dbType);
        return DB_TYPE.equalsIgnoreCase(dbType);
    }

}
