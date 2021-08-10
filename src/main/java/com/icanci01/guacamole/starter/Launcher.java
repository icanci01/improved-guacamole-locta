package com.icanci01.guacamole.starter;

import com.icanci01.guacamole.verticles.VerticleA;
import com.icanci01.guacamole.verticles.VerticleB;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class Launcher extends AbstractVerticle {

  @Override
  public void start(final Promise<Void> startPromise) {
    System.out.println("Launch " + getClass().getName());
    vertx.deployVerticle(new VerticleA());
    vertx.deployVerticle(new VerticleB());

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
