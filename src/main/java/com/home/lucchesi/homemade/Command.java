package com.home.lucchesi.homemade;

public enum Command {
    TOGGLE("{\"id\":1,\"method\":\"toggle\",\"params\":[]}\r\n"),
    COLOR("{\"id\":1,\"method\":\"set_rgb\",\"params\":[COLOR_VALUE, \"smooth\", 500]}\r\n"),
    POWER_ON("{\"id\":1,\"method\":\"set_power\",\"params\":[\"on\", \"smooth\", 500]}\r\n"),
    POWER_OFF("{\"id\":1,\"method\":\"set_power\",\"params\":[\"off\", \"smooth\", 500]}\r\n");

    String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
