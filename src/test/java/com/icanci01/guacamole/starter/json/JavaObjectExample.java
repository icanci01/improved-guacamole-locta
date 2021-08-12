package com.icanci01.guacamole.starter.json;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JavaObjectExample {

    @Test
    void canMapJavaObjects() {
        final Customer volpo_customer = new Customer(3, "volpo", true, true, true, true, 2000);
        final JsonObject volpo = JsonObject.mapFrom(volpo_customer);

        Assertions.assertEquals(volpo_customer.getId(), volpo.getInteger("id"));
        Assertions.assertEquals(volpo_customer.getName(), volpo.getString("name"));
        Assertions.assertEquals(volpo_customer.isLovesVertx(), volpo.getBoolean("lovesVertx"));
        Assertions.assertEquals(volpo_customer.isLovesJava(), volpo.getBoolean("lovesJava"));
        Assertions.assertEquals(volpo_customer.isLovesC(), volpo.getBoolean("lovesC"));
        Assertions.assertEquals(volpo_customer.isLovesReact(), volpo.getBoolean("lovesReact"));
        Assertions.assertEquals(volpo_customer.getSalary(), volpo.getInteger("salary"));

    }
}
