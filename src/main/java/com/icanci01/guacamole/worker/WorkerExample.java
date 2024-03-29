package com.icanci01.guacamole.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExample extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(WorkerExample.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        vertx.deployVerticle(new WorkerVerticle(),
            new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(1)
                .setWorkerPoolName("my-worker-verticle")
        );
        LOG.debug("Starting {}", getClass().getName());
        startPromise.complete();
        executeBlockingCode();
    }

    private void executeBlockingCode() {
        vertx.executeBlocking(event -> {
            LOG.debug("Executing blocking code");
            try {
                Thread.sleep(5000);
                event.complete();
            } catch (InterruptedException e) {
                LOG.error("Failed: ", e);
                event.fail(e);
            }
        }, result -> {

            if (result.succeeded()) {
                LOG.debug("Blocking call done");
            } else {
                LOG.debug("Blocking call failed due to: ", result.cause());
            }
        });
    }

}
