package com.icanci01.guacamole.starter.json;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonArrayExample {

    @Test
    void jsonArrayCanBeMapped() {
        final JsonArray myJsonArray = new JsonArray();
        myJsonArray
            .add(new JsonObject().put("id", 1))
            .add(new JsonObject().put("id", 2))
            .add(new JsonObject().put("id", 3))
            .add("randomValue");

        Assertions.assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3},\"randomValue\"]", myJsonArray.encode());
    }

}
