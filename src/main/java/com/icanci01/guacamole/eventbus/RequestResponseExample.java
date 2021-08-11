package com.icanci01.guacamole.eventbus;

import com.icanci01.guacamole.verticles.VerticleAA;
import com.icanci01.guacamole.verticles.VerticleAB;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExample extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(RequestResponseExample.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Starting {}", getClass().getName());
        vertx.deployVerticle(new RequestVerticle(),
            new DeploymentOptions()
        , whenDeployed -> {
            vertx.undeploy(whenDeployed.result());
        });
        vertx.deployVerticle(new ResponseVerticle(),
            new DeploymentOptions()
            , whenDeployed -> {
                vertx.undeploy(whenDeployed.result());
            });
        startPromise.complete();
    }

    static class RequestVerticle extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(RequestVerticle.class);
        private static final String ADDRESS = "my.request.address";

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());
            startPromise.complete();
            var eventBus = vertx.eventBus();
            final String message = "Hello World! Example of Request with Event Bus";
            LOG.debug(">>> Sending:  \"{}\"", message);
            eventBus.request(ADDRESS, message, reply -> {
                LOG.debug("<<< Response: \"{}\"", reply.result().body().toString());
            });
        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("Stop {}", getClass().getSimpleName());

            stopPromise.complete();
        }

    }

    static class ResponseVerticle extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticle.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("\tStart {}", getClass().getSimpleName());
            startPromise.complete();
            vertx.eventBus().consumer(RequestVerticle.ADDRESS, message -> {
                LOG.debug("\t<<< Received Message: \"{}\"", message.body().toString());
                final String responseMessage = "Received your message. Thanks!";
                LOG.debug("\t>>> Sending Response:  \"{}\"", responseMessage);
                message.reply(responseMessage);
            });
        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("\tStop {}", getClass().getSimpleName());

            stopPromise.complete();
        }


    }

}
