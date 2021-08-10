package com.icanci01.guacamole.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(final Promise<Void> startPromise) {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("HERE!");
    }).listen(16080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("Started");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
