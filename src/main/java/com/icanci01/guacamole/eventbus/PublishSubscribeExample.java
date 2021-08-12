package com.icanci01.guacamole.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public class PublishSubscribeExample extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(PublishSubscribeExample.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Starting {}", getClass().getName());
        vertx.deployVerticle(new Publisher(), whenDeployed -> {
            try {
                Thread.sleep(Duration.ofSeconds(3).toMillis());
            } catch (InterruptedException e) {
                LOG.error("ERROR -", e.getCause());
            }
            vertx.undeploy(whenDeployed.result());
        });
        vertx.deployVerticle(new Subscriber1());
        vertx.deployVerticle(Subscriber2.class.getName(),
            new DeploymentOptions()
                .setInstances(2)
        );
        startPromise.complete();
    }

    public static class Publisher extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());
            vertx.setPeriodic(Duration.ofSeconds(1).toMillis(), id -> {
                UUID randomUUID = UUID.randomUUID();
                final String message = "Message with UUID - " + randomUUID.toString() + " - Canciu Ionut_Cristian";
                LOG.debug(">>> Sending:  \"{}\"", message);
                vertx.eventBus().publish(Publisher.class.getName(), message);
            });
            startPromise.complete();
        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("Stop {}", getClass().getSimpleName());
            stopPromise.complete();
        }
    }

    public static class Subscriber1 extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(Subscriber1.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());
            vertx.eventBus().<String>consumer(Publisher.class.getName(), message -> {
                LOG.debug("\t>> [Received Message: \"{}\"]", message.body());
            });
        }

    }

    public static class Subscriber2 extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(Subscriber2.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());
            vertx.eventBus().<String>consumer(Publisher.class.getName(), message -> {
                LOG.debug("\t>> [Received Message: \"{}\"]", message.body());
            });
        }

    }
}
