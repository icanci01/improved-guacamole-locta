package com.icanci01.guacamole.eventbus.customcodec;

public class Pong {

    private String message;
    private boolean enabled;

    public Pong(){
        // Default Constructor
    }

    public Pong(String message, boolean enabled) {
        this.message = message;
        this.enabled = enabled;
    }

    public String getMessage() {
        return message;
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "Pong = {" +
            "message='" + message + '\'' +
            ", enabled=" + enabled +
            '}';
    }
}
