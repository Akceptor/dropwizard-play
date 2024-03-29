package org.akceptor.health;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.Configuration;
import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import org.akceptor.core.Thing;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AppHealthCheck extends HealthCheck {

    private final Configuration configuration;
    private final Client client;

    public AppHealthCheck(Client client, Configuration configuration) {
        super();
        this.client = client;
        this.configuration = configuration;
    }

    @Override
    protected Result check() throws Exception {
        Supplier<Stream<ConnectorFactory>> supplier = () -> configuration.getServerFactory() instanceof DefaultServerFactory
                ? ((DefaultServerFactory) configuration.getServerFactory()).getApplicationConnectors().stream()
                : Stream.of((SimpleServerFactory) configuration.getServerFactory()).map(SimpleServerFactory::getConnector);
        int port = supplier.get().filter(connector -> connector.getClass().isAssignableFrom(HttpConnectorFactory.class))
                .map(connector -> (HttpConnectorFactory) connector)
                .mapToInt(HttpConnectorFactory::getPort)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        String host = supplier.get().filter(connector -> connector.getClass().isAssignableFrom(HttpConnectorFactory.class))
                .map(connector -> (HttpConnectorFactory) connector)
                .map(cf -> cf.getBindHost() != null ? cf.getBindHost() : "localhost")
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        WebTarget webTarget = client.target("http://" + host + ":" + port + "/things");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        @SuppressWarnings("rawtypes")
        ArrayList<Thing> entities = response.readEntity(ArrayList.class);
        if (entities != null && entities.size() > 0) {
            return Result.healthy();
        }
        return Result.unhealthy("API Failed");
    }
}
