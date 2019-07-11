package org.akceptor;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.akceptor.core.Thing;
import org.akceptor.db.ThingDAO;
import org.akceptor.health.AppHealthCheck;
import org.akceptor.resources.EntitiesRESTController;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Client;

public class TestApplication extends Application<TestConfiguration> {

    private final HibernateBundle<TestConfiguration> hibernateBundle = new HibernateBundle<TestConfiguration>(Thing.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TestConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private final MigrationsBundle<TestConfiguration> migrationsBundle = new MigrationsBundle<TestConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(TestConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new TestApplication().run(args);
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public void initialize(final Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(migrationsBundle);
    }

    @Override
    public void run(final TestConfiguration configuration,
                    final Environment environment) {
        final ThingDAO dao = new ThingDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new EntitiesRESTController(dao));
        //Health
        final Client client = new JerseyClientBuilder().build();
        environment.healthChecks().register("APIHealthCheck", new AppHealthCheck(client, configuration));
    }

}
