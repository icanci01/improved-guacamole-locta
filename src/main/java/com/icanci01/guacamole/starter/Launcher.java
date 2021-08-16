package com.icanci01.guacamole.starter;

import com.icanci01.guacamole.eventbus.PointToPointExample;
import com.icanci01.guacamole.eventbus.PublishSubscribeExample;
import com.icanci01.guacamole.eventbus.RequestResponseExample;
import com.icanci01.guacamole.eventbus.customcodec.CustomCodecPingPongExample;
import com.icanci01.guacamole.eventbus.json.RequestResponseJSON;
import com.icanci01.guacamole.verticles.VerticleN;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Launcher extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

    /**
     * Main used for configuring vertx with initial options.
     *
     * @param args - Program input
     */
    public static void main(final String[] args) {
        var vertx = Vertx.vertx(
            new VertxOptions()
                .setMaxEventLoopExecuteTime(500)
                .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
                .setBlockedThreadCheckInterval(1)
                .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
                .setEventLoopPoolSize(25)
        );
        vertx.deployVerticle(new Launcher(), whenDeployed -> {
            LOG.debug("Launcher end with {}", whenDeployed.result().toString());
            vertx.undeploy(whenDeployed.result());
        });
    }

    @Override
    public void start(final Promise<Void> startPromise) {
        LOG.debug("Starting launcher {}", getClass().getName());

        // vertx.deployVerticle(new HttpVerticle());

        // vertx.deployVerticle(new EventLoopExample());

        // vertx.deployVerticle(new WorkerExample());

        //  vertx.deployVerticle(new VerticleA());

        //  vertx.deployVerticle(new VerticleB());

        //  scalingVerticlesConfig();

        // requestResponseExample();

        // pointToPointExample();

        // publishSubscribeExample();

        // requestResponseJson();

        // customCodecPingPongExample();

        startPromise.complete();

    }

    private void scalingVerticlesConfig() {
        vertx.deployVerticle(VerticleN.class.getName(),
            new DeploymentOptions()
                .setInstances(4)
                .setConfig(new JsonObject()
                    .put("id", UUID.randomUUID().toString())
                    .put("name", VerticleN.class.getSimpleName()))
        );
    }

    private void requestResponseExample() {
        vertx.deployVerticle(new RequestResponseExample(),
            new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(2)
                .setWorkerPoolName("response-request-example")
            );
    }

    private void pointToPointExample() {
        vertx.deployVerticle(new PointToPointExample(),
            new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(2)
                .setWorkerPoolName("point-to-point-example")
        );
    }

    private void publishSubscribeExample() {
        vertx.deployVerticle(new PublishSubscribeExample(),
            new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(3)
                .setWorkerPoolName("publish-subscribe-example")
        );
    }

    private void requestResponseJson() {
        vertx.deployVerticle(new RequestResponseJSON(),
            new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(2)
                .setWorkerPoolName("response-request-example-json")
        );
    }

    private void customCodecPingPongExample() {
        vertx.deployVerticle(new CustomCodecPingPongExample()
            , new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(2)
                .setWorkerPoolName("ping-pong-codec-example")
        );
    }

}
