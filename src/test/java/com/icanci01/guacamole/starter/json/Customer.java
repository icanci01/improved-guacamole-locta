package com.icanci01.guacamole.starter.json;

public class Customer {

    private Integer id;
    private String name;
    private boolean lovesVertx;
    private boolean lovesJava;
    private boolean lovesC;
    private boolean lovesReact;
    private Integer salary;

    public Customer() {
        // Default constructor for Jackson
    }

    public Customer(Integer id, String name, boolean lovesVertx, boolean lovesJava, boolean lovesC, boolean lovesReact, Integer salary) {
        this.id = id;
        this.name = name;
        this.lovesVertx = lovesVertx;
        this.lovesJava = lovesJava;
        this.lovesC = lovesC;
        this.lovesReact = lovesReact;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLovesVertx() {
        return lovesVertx;
    }

    public void setLovesVertx(boolean lovesVertx) {
        this.lovesVertx = lovesVertx;
    }

    public boolean isLovesJava() {
        return lovesJava;
    }

    public void setLovesJava(boolean lovesJava) {
        this.lovesJava = lovesJava;
    }

    public boolean isLovesC() {
        return lovesC;
    }

    public void setLovesC(boolean lovesC) {
        this.lovesC = lovesC;
    }

    public boolean isLovesReact() {
        return lovesReact;
    }

    public void setLovesReact(boolean lovesReact) {
        this.lovesReact = lovesReact;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
