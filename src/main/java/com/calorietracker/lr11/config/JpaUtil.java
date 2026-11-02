
package com.calorietracker.lr11.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

public final class JpaUtil {

    private static final EntityManagerFactory FACTORY =
            createFactory();

    private JpaUtil() {
    }

    private static EntityManagerFactory createFactory() {
        Properties properties = new Properties();

        try (InputStream input = JpaUtil.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Cannot read application.properties", e
            );
        }

        Path local = Path.of("application-local.properties");

        if (Files.exists(local)) {
            try (InputStream input = Files.newInputStream(local)) {
                properties.load(input);
            } catch (IOException e) {
                throw new IllegalStateException(
                        "Cannot read local database configuration", e
                );
            }
        }

        Map<String, Object> settings = new HashMap<>();

        settings.put(
                "jakarta.persistence.jdbc.url",
                System.getenv().getOrDefault(
                        "DB_URL",
                        properties.getProperty("db.url", "")
                )
        );

        settings.put(
                "jakarta.persistence.jdbc.user",
                System.getenv().getOrDefault(
                        "DB_USERNAME",
                        properties.getProperty("db.username", "")
                )
        );

        settings.put(
                "jakarta.persistence.jdbc.password",
                System.getenv().getOrDefault(
                        "DB_PASSWORD",
                        properties.getProperty("db.password", "")
                )
        );

        return Persistence.createEntityManagerFactory(
                "calorieTrackerPU",
                settings
        );
    }

    public static EntityManager createEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static <T> T read(
            Function<EntityManager, T> action
    ) {
        try (EntityManager em = createEntityManager()) {
            return action.apply(em);
        }
    }

    public static <T> T transaction(
            Function<EntityManager, T> action
    ) {
        try (EntityManager em = createEntityManager()) {
            EntityTransaction tx = em.getTransaction();

            try {
                tx.begin();

                T result = action.apply(em);

                tx.commit();
                return result;

            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }

    public static void transaction(
            Consumer<EntityManager> action
    ) {
        transaction(em -> {
            action.accept(em);
            return null;
        });
    }

    public static void close() {
        if (FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}
