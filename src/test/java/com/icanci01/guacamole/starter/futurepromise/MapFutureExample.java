package com.icanci01.guacamole.starter.futurepromise;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class MapFutureExample {

    private static final Logger LOG = LoggerFactory.getLogger(MapFutureExample.class);

    /**
     * Asynchronous call with a delay of 500 ms
     * Call successes example
     *
     * @param vertx   - var vertx = Vertx.vertx();
     * @param context - VertxTestContext
     */
    @Test
    void future_map_success(Vertx vertx, VertxTestContext context) {
        LOG.debug("Start test");

        final Promise<String> promise = Promise.promise();

        vertx.setPeriodic(500, id -> {
            promise.complete("Success");
            LOG.debug("Timer Done");
        });

        final Future<String> future = promise.future();
        future
            .map(asString -> {
                LOG.debug("Map String to JsonObject");
                return new JsonObject().put("key", asString);
            })
            .map(jsonObject -> {
                LOG.debug("Map JsonObject to JsonArray");
                return new JsonArray().add(jsonObject);
            })
            .onSuccess(jsonArray -> {
                LOG.debug("End Future Result: {}", jsonArray.toString());
                context.completeNow();
            })
            .onFailure(context::failNow);

        LOG.debug("End test");
    }

}
