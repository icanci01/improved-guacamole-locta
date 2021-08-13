package com.icanci01.guacamole.eventbus.customcodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCodecPingPongExample extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(CustomCodecPingPongExample.class);

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        LOG.debug("Starting {}", getClass().getName());
        vertx.deployVerticle(new PingVerticle(), logOnErrorUndeploy());
        vertx.deployVerticle(new PongVerticle(), logOnErrorUndeploy());
        startPromise.complete();
    }

    private Handler<AsyncResult<String>> logOnErrorUndeploy() {
        return ar -> {
            if (ar.failed()) {
                LOG.error("Error: ", ar.cause());
            }

            vertx.undeploy(ar.result());

        };
    }

    public static class PingVerticle extends AbstractVerticle {

        static final String ADDRESS = PingVerticle.class.getName();
        private static final Logger LOG = LoggerFactory.getLogger(PingVerticle.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("Start {}", getClass().getSimpleName());

            // Register Codec
            var eventBus = vertx.eventBus()
                .registerDefaultCodec(
                    Ping.class,
                    new LocalMessageCodec<>(Ping.class)
                );

            final Ping message = new Ping("My custom message", true);
            LOG.debug(">>> Sending:  \"{}\"", message);
            eventBus.<Pong>request(ADDRESS, message, getAsyncResultHandler(startPromise));

        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("Stop {}", getClass().getSimpleName());
            stopPromise.complete();
        }

        private Handler<AsyncResult<Message<Pong>>> getAsyncResultHandler(final Promise<Void> startPromise) {
            return reply -> {
                if (reply.failed()) {
                    LOG.error("Failed: ", reply.cause());
                    return;
                }
                LOG.debug("<<< Response: \"{}\"", reply.result().body());
                startPromise.complete();
            };
        }

    }

    public static class PongVerticle extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(PongVerticle.class);

        @Override
        public void start(final Promise<Void> startPromise) throws Exception {
            LOG.debug("\tStart {}", getClass().getSimpleName());

            // Register Codec
            var eventBus = vertx.eventBus()
                .registerDefaultCodec(
                    Pong.class,
                    new LocalMessageCodec<>(Pong.class)
                );

            eventBus.<Ping>consumer(PingVerticle.ADDRESS
                    , getPingSendPong(startPromise))
                .exceptionHandler(error -> LOG.error("Error: ", error));

        }

        @Override
        public void stop(final Promise<Void> stopPromise) throws Exception {
            LOG.debug("Stop {}", getClass().getSimpleName());
            stopPromise.complete();
        }


        private Handler<Message<Ping>> getPingSendPong(Promise<Void> startPromise) {
            return message -> {
                LOG.debug("\t>> [Received Message -- \"{}\"]", message.body().toString());
                final Pong responseMessage = new Pong("Received your message", true);
                LOG.debug("\t<< [Sending Response -- \"{}\"]", responseMessage);
                message.reply(responseMessage);
                startPromise.complete();
            };
        }

    }

}
