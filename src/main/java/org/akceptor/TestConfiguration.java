package org.akceptor;

import io.dropwizard.Configuration;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;

public class TestConfiguration extends Configuration {
    // TODO: implement service configuration

    public TestConfiguration() {
        super();
        // The following is to make sure it runs with a random port. parallel tests clash otherwise
        ((HttpConnectorFactory) ((DefaultServerFactory) getServerFactory()).getApplicationConnectors().get(0)).setPort(9000);
        // this is for admin port
        ((HttpConnectorFactory) ((DefaultServerFactory) getServerFactory()).getAdminConnectors().get(0)).setPort(9001);
    }

}
