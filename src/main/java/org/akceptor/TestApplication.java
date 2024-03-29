package org.akceptor;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.akceptor.core.Child;
import org.akceptor.core.Thing;
import org.akceptor.db.ChildDAO;
import org.akceptor.db.ThingDAO;
import org.akceptor.health.AppHealthCheck;
import org.akceptor.resources.ChildrenRESTController;
import org.akceptor.resources.ThingsRESTController;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Client;

public class TestApplication extends Application<TestConfiguration> {

    private final HibernateBundle<TestConfiguration> hibernateBundle =
            //Pass all the entity classes to constructor or use ScanningHibernateBundle
            new HibernateBundle<TestConfiguration>(Thing.class, Child.class) {
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
        final ThingDAO thingDAO = new ThingDAO(hibernateBundle.getSessionFactory());
        final ChildDAO childDAO = new ChildDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new ThingsRESTController(thingDAO));
        environment.jersey().register(new ChildrenRESTController(childDAO));
        //Health
        final Client client = new JerseyClientBuilder().build();
        environment.healthChecks().register("APIHealthCheck", new AppHealthCheck(client, configuration));
    }

}
