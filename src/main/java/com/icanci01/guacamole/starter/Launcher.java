package com.icanci01.guacamole.starter;

import com.icanci01.guacamole.eventloops.EventLoopExample;
import com.icanci01.guacamole.verticles.VerticleA;
import com.icanci01.guacamole.verticles.VerticleB;
import com.icanci01.guacamole.verticles.VerticleN;
import com.icanci01.guacamole.verticles.http.HttpVerticle;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Launcher extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
        .setEventLoopPoolSize(4)
    );
    vertx.deployVerticle(new Launcher());
  }

  @Override
  public void start(final Promise<Void> startPromise) {
    LOG.debug("Launch {}", getClass().getName());
    vertx.deployVerticle(new HttpVerticle());
    vertx.deployVerticle(new EventLoopExample());
    vertx.deployVerticle(new VerticleA());
    vertx.deployVerticle(new VerticleB());
    vertx.deployVerticle(VerticleN.class.getName(),
      new DeploymentOptions()
        .setInstances(4)
        .setConfig(new JsonObject()
          .put("id", UUID.randomUUID().toString())
          .put("name",VerticleN.class.getSimpleName()))
      );

  }

}
