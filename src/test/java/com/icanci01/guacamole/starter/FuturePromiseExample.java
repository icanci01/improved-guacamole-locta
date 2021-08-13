package com.icanci01.guacamole.starter;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FuturePromiseExample {

    private static final Logger LOG = LoggerFactory.getLogger(FuturePromiseExample.class);

    public static void main(String[] args) {
        LOG.debug("Starting {}", FuturePromiseExample.class.getSimpleName());
        var vertx = Vertx.vertx();
        final Promise<String> promise = Promise.promise();
        vertx.setPeriodic(500, id ->
            promise.complete("Success!"));
        final Future<String> future = promise.future();
        future
            .onSuccess(Future::succeededFuture)
            .onFailure(Future::failedFuture);

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
