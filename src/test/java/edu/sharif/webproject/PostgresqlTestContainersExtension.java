package edu.sharif.webproject;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.Arrays;

public class PostgresqlTestContainersExtension implements BeforeAllCallback, AfterAllCallback {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("web_project_test")
            .withUsername("mazzi")
            .withPassword("secret!")
//            .withExposedPorts(5432)
            .withReuse(true);

    public static void registerJdbcProperties(DynamicPropertyRegistry registry) {
//        postgresContainer.setPortBindings(Arrays.asList("5433:5432"));
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        postgresContainer.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        postgresContainer.stop();
    }
}
