package com.icanci01.guacamole.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Deployed as worker verticle {}", getClass().getName());
        Thread.sleep(5000);
        startPromise.complete();
        LOG.debug("Blocking operation done");
    }
}
