package com.icanci01.guacamole.eventbus.json;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseJSON extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(RequestResponseJSON.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Starting {}", getClass().getName());
        vertx.deployVerticle(new RequestVerticleJSONObject(), new DeploymentOptions(),
            whenDeployed -> vertx.undeploy(whenDeployed.result()));
        vertx.deployVerticle(new ResponseVerticleJSONObject(), new DeploymentOptions(),
            whenDeployed -> vertx.undeploy(whenDeployed.result()));
        startPromise.complete();
    }

    static class RequestVerticleJSONObject extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(RequestVerticleJSONObject.class);
        private static final String ADDRESS = "my.request.address.json";

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());
            var eventBus = vertx.eventBus();
            var message = new JsonObject()
                .put("message", "Hello from event bus!")
                .put("id", 4)
                .put("version", 1);
            LOG.debug(">>> Sending:  \"{}\"", message.toString());
            eventBus.<JsonObject>request(ADDRESS, message, reply -> {
                LOG.debug("<<< Response: \"{}\"", reply.result().body().toString());
            });
            startPromise.complete();
        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("Stop {}", getClass().getSimpleName());
            stopPromise.complete();
        }

    }

    static class ResponseVerticleJSONObject extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticleJSONObject.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("\tStart {}", getClass().getSimpleName());
            vertx.eventBus().<JsonObject>consumer(RequestVerticleJSONObject.ADDRESS, message -> {
                LOG.debug("\t>> [Received Message -- \"{}\"]", message.body().toString());
                var responseMessage = new JsonObject()
                    .put("message", "Received your message! Thanks!")
                    .put("id", 5);
                LOG.debug("\t<< [Sending Response -- \"{}\"]", responseMessage);
                message.reply(responseMessage);
                startPromise.complete();
            });
        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("\tStop {}", getClass().getSimpleName());
            stopPromise.complete();
        }

    }

}
