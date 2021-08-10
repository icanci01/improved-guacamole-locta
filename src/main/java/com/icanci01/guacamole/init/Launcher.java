package com.icanci01.guacamole.init;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class Launcher extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Launcher());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("MAIN!");
    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        LOG.info("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
