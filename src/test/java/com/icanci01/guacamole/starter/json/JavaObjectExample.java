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

        final Customer volpo_customer2 = volpo.mapTo(Customer.class);
        Assertions.assertEquals(volpo_customer.getId(), volpo_customer2.getId());
        Assertions.assertEquals(volpo_customer.getName(), volpo_customer2.getName());
        Assertions.assertEquals(volpo_customer.isLovesVertx(), volpo_customer2.isLovesVertx());
        Assertions.assertEquals(volpo_customer.isLovesJava(), volpo_customer2.isLovesJava());
        Assertions.assertEquals(volpo_customer.isLovesC(), volpo_customer2.isLovesC());
        Assertions.assertEquals(volpo_customer.isLovesReact(), volpo_customer2.isLovesReact());
        Assertions.assertEquals(volpo_customer.getSalary(), volpo_customer2.getSalary());

    }
}
