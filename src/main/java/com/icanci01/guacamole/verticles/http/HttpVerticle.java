package com.icanci01.guacamole.verticles.http;

import com.icanci01.guacamole.starter.Launcher;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(HttpVerticle.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.info("Starting http verticle {}",  HttpVerticle.class.getName());
    vertx.createHttpServer().requestHandler(req -> req.response()
      .putHeader("content-type", "text/plain")
      .end("HERE!")).listen(16080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        LOG.debug("Started http server successfully");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

}
