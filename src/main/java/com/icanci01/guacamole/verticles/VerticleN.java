package com.icanci01.guacamole.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("Starting " + getClass().getName());
    //TODO: Here add your code

    startPromise.complete();
  }

}

