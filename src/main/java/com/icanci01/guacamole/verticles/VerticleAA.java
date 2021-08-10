package com.icanci01.guacamole.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAA extends AbstractVerticle {

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    System.out.println("Starting " + getClass().getName());
    //TODO: Here add your code

    startPromise.complete();
  }

  public void stop(final Promise<Void> stopPromise) throws Exception {
    System.out.println("Stop " + getClass().getName());
    //TODO: Here add your code

    stopPromise.complete();
  }

}

