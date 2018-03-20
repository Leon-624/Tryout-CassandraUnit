package org.liyanxu.tryout.cassandraunit;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TryoutCassandraUnitApplication extends Application<TryoutCassandraUnitConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TryoutCassandraUnitApplication().run(args);
    }

    @Override
    public String getName() {
        return "TryoutCassandraUnit";
    }

    @Override
    public void initialize(final Bootstrap<TryoutCassandraUnitConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TryoutCassandraUnitConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
