package com.icanci01.guacamole.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleAA extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(VerticleAA.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Starting {}", getClass().getName());
        //TODO: Here add your code

        startPromise.complete();
    }

    @Override
    public void stop(final Promise<Void> stopPromise) throws Exception {
        LOG.debug("Stop {}", getClass().getName());
        //TODO: Here add your code

        stopPromise.complete();
    }

}

