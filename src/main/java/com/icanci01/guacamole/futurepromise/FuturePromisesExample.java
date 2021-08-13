package com.icanci01.guacamole.futurepromise;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class FuturePromisesExample extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {

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

    }
}
