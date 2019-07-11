package org.akceptor;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.akceptor.health.AppHealthCheck;
import org.akceptor.resources.EntitiesRESTController;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Client;

public class TestApplication extends Application<TestConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TestApplication().run(args);
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public void initialize(final Bootstrap<TestConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TestConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register(new EntitiesRESTController(environment.getValidator()));
        //Health
        final Client client = new JerseyClientBuilder().build();
        environment.healthChecks().register("APIHealthCheck",new AppHealthCheck(client, configuration));
    }

}
