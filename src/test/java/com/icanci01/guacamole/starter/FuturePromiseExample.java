package com.icanci01.guacamole.starter;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {

    private static final Logger LOG = LoggerFactory.getLogger(FuturePromiseExample.class);

    /**
     * Asynchronous call with a delay of 500 ms
     * Call successes example
     *
     * @param vertx   - var vertx = Vertx.vertx();
     * @param context - VertxTestContext
     */
    @Test
    void promise_success(Vertx vertx, VertxTestContext context) {
        LOG.debug("Start test");

        final Promise<String> promise = Promise.promise();

        vertx.setPeriodic(500, id -> {
            promise.complete("Success");
            LOG.debug("Success");
            context.completeNow();
        });

        LOG.debug("End test");
    }

    /**
     * Asynchronous call with a delay of 500 ms
     * Call fails example
     *
     * @param vertx   - var vertx = Vertx.vertx();
     * @param context - VertxTestContext
     */
    @Test
    void promise_fail(Vertx vertx, VertxTestContext context) {
        LOG.debug("Start test");

        final Promise<String> promise = Promise.promise();

        vertx.setPeriodic(500, id -> {
            promise.fail(new RuntimeException("Failed!"));
            LOG.debug("Failed");
            context.completeNow();
        });

        LOG.debug("End test");
    }

    /**
     * Asynchronous call with a delay of 500 ms
     * Call successes example
     *
     * @param vertx   - var vertx = Vertx.vertx();
     * @param context - VertxTestContext
     */
    @Test
    void future_success(Vertx vertx, VertxTestContext context) {
        LOG.debug("Start test");

        final Promise<String> promise = Promise.promise();

        vertx.setPeriodic(500, id -> {
            promise.complete("Success");
            LOG.debug("Timer Done");
        });

        final Future<String> future = promise.future();
        future
            .onSuccess(result -> {
                LOG.debug("End Future Result: {}", result);
                context.completeNow();
            })
            .onFailure(context::failNow);

        LOG.debug("End test");
    }

    /**
     * Asynchronous call with a delay of 500 ms
     * Call fails example
     *
     * @param vertx   - var vertx = Vertx.vertx();
     * @param context - VertxTestContext
     */
    @Test
    void future_fail(Vertx vertx, VertxTestContext context) {
        LOG.debug("Start test");

        final Promise<String> promise = Promise.promise();

        vertx.setPeriodic(500, id -> {
            promise.fail(new RuntimeException("Failed!"));
            LOG.debug("Timer done");
        });

        final Future<String> future = promise.future();
        future
            .onSuccess(context::failNow)
            .onFailure(error -> {
                LOG.error("Error:", error);
                context.completeNow();
            });

        LOG.debug("End test");
    }

    public static void main(String[] args) {

        final Promise<Void> returnVoid = Promise.promise();
        returnVoid.complete();
        final Promise<String> returnString = Promise.promise();
        returnString.complete("Hello!");
        final Promise<JsonObject> returnJsonObject = Promise.promise();
        returnJsonObject.complete(new JsonObject().put("message", "Hello!!"));

        final Promise<Void> one = Promise.promise();
        final Promise<Void> two = Promise.promise();
        final Promise<Void> three = Promise.promise();

        final Future<Void> futureOne = one.future();
        final Future<Void> futureTwo = two.future();
        final Future<Void> futureThree = three.future();

        CompositeFuture.all(futureOne, futureTwo, futureThree)
            .onFailure(Future::failedFuture)
            .onSuccess(result -> {
                LOG.debug("Success");
                Future.succeededFuture();
            });
    }
}
