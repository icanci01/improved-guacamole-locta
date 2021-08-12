package com.icanci01.guacamole.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * The sender sends the message periodically
 */
public class PointToPointExample extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(PointToPointExample.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Starting {}", getClass().getName());
        vertx.deployVerticle(new Sender(),
            new DeploymentOptions()
            , whenDeployed -> {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    LOG.error("ERROR -", e.getCause());
                }
                vertx.undeploy(whenDeployed.result());
            });
        vertx.deployVerticle(new Receiver(),
            new DeploymentOptions()
            , whenDeployed -> {
                vertx.undeploy(whenDeployed.result());
            });
        startPromise.complete();
    }

    static class Sender extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());
            vertx.setPeriodic(500, id -> {
                // Send a message every second
                UUID randomUUID = UUID.randomUUID();
                final String message = "Message with UUID - " + randomUUID.toString() + " - Canciu Ionut_Cristian";
                LOG.debug(">>> Sending:  \"{}\"", message);
                vertx.eventBus().<String>send(Sender.class.getName(), message
                    , new DeliveryOptions()
                        .setSendTimeout(250));
            });
            startPromise.complete();
        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("Stop {}", getClass().getSimpleName());
            stopPromise.complete();
        }
    }

    static class Receiver extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());
            startPromise.complete();
            vertx.eventBus().<String>consumer(Sender.class.getName(), message -> {
                LOG.debug("\t>> [Received Message: \"{}\"]", message.body());
            });
        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("Stop {}", getClass().getSimpleName());
            stopPromise.complete();
        }

    }
}
