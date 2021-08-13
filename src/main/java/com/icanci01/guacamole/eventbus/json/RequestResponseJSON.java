package com.icanci01.guacamole.eventbus.json;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseJSON extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(RequestResponseJSON.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Starting {}", getClass().getName());
        vertx.deployVerticle(new RequestVerticleJSON());
        vertx.deployVerticle(new ResponseVerticleJSON());
        startPromise.complete();
    }

    static class RequestVerticleJSON extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(RequestVerticleJSON.class);
        private static final String ADDRESS = "my.request.address.json";

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());
            startPromise.complete();
            var eventBus = vertx.eventBus();
            var message = new JsonObject()
                .put("message", "Hello from event bus!")
                .put("id", 4);
            LOG.debug(">>> Sending:  \"{}\"", message.toString());
            eventBus.<JsonObject>request(ADDRESS, message, reply -> {
                LOG.debug("<<< Response: \"{}\"", reply.result().body().toString());
            });

        }

    }

    static class ResponseVerticleJSON extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticleJSON.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("\tStart {}", getClass().getSimpleName());
            startPromise.complete();
            vertx.eventBus().<JsonObject>consumer(RequestVerticleJSON.ADDRESS, message -> {
                LOG.debug("\t>> [Received Message -- \"{}\"]", message.body().toString());
                var responseMessage = new JsonObject()
                    .put("message", "Received your message! Thanks!")
                    .put("id", 5);
                LOG.debug("\t<< [Sending Response -- \"{}\"]", responseMessage);
                message.reply(responseMessage);
            });
        }

    }

}
