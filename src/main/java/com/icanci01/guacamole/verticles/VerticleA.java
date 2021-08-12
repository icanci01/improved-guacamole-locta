package com.icanci01.guacamole.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Starting {}", getClass().getName());
        vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
            LOG.debug("Deployed " + VerticleAA.class.getName());
            //TODO: Here add your code

            vertx.undeploy(whenDeployed.result());
        });
        vertx.deployVerticle(new VerticleAB(), whenDeployed -> {
            LOG.debug("Deployed {}", VerticleAB.class.getName());
            //TODO: Here add your code

            vertx.undeploy(whenDeployed.result());
        });
        startPromise.complete();
    }

}

