package com.example.LinkShortener;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class HibernateHelper {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    private HibernateHelper() {
        throw new IllegalStateException("Utility classes cannot be instantiated.");
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                registry = new StandardServiceRegistryBuilder().configure().build();

                // Create MetadataSources
                final MetadataSources sources = new MetadataSources(registry);
                sources.addAnnotatedClass(com.example.LinkShortener.Link.class);

                // Create Metadata
                final Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (final Exception e) {
                e.printStackTrace();
                shutdown();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}