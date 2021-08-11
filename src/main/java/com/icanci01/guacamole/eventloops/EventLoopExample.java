package com.icanci01.guacamole.eventloops;

import com.icanci01.guacamole.starter.Launcher;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLoopExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(EventLoopExample.class);


  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Starting {}", getClass().getName());
    startPromise.complete();

    // Do not do this inside a verticle
    // Thread.sleep(5000);

  }
}
