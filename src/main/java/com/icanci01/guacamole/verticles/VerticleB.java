package com.icanci01.guacamole.verticles;

import com.icanci01.guacamole.starter.Launcher;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleB extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(VerticleB.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Starting {}", getClass().getName());
    //TODO: Here add your code

    startPromise.complete();
  }

}

