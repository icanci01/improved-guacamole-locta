package com.icanci01.guacamole.verticles;

import com.icanci01.guacamole.starter.Launcher;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleN extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Starting " + getClass().getName() + " on thread " + Thread.currentThread().getName());
    LOG.debug(Thread.currentThread().getName() + " with config " + config().toString());
    //TODO: Here add your code

    startPromise.complete();
  }

}

