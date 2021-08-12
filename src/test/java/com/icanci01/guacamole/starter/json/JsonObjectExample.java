package com.icanci01.guacamole.starter.json;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonObjectExample {

    @Test
    void JsonObjectCanBeMapped() {
        final JsonObject myJsonObject = new JsonObject();
        myJsonObject.put("id", 1);
        myJsonObject.put("name", "Cristian");
        myJsonObject.put("loves_vertx", true);
        myJsonObject.put("loves_java", true);
        myJsonObject.put("loves_c", true);
        myJsonObject.put("loves_react", true);
        myJsonObject.put("loves_sql", true);
        myJsonObject.put("loves_postgres", true);

        final String encoded = myJsonObject.encode();
        Assertions.assertEquals("{\"id\":1,\"name\":\"Cristian\",\"loves_vertx\":true,\"loves_java\":true,\"loves_c\":true,\"loves_react\":true,\"loves_sql\":true,\"loves_postgres\":true}", encoded);

        final JsonObject decodedJsonObject = new JsonObject(encoded);
        Assertions.assertEquals(myJsonObject, decodedJsonObject);

    }

    @Test
    void jsonObjectCanBeCreatedFromMap() {
        final Map<String, Object> myMap = new HashMap<>();
        myMap.put("id", 2);
        myMap.put("name", "zorro");
        myMap.put("project", true);

        final JsonObject asJsonObject = new JsonObject(myMap);
        Assertions.assertEquals(myMap, asJsonObject.getMap());
        Assertions.assertEquals(2, asJsonObject.getInteger("id"));
        Assertions.assertEquals("zorro", asJsonObject.getString("name"));
        Assertions.assertEquals(true, asJsonObject.getBoolean("project"));
    }
}
