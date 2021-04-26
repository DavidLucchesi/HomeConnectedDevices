package com.home.lucchesi.homemade;

public enum Color {
    BLACK("Black", 0),
    WHITE("White", 16777215),
    BLUE("Blue", 255),
    GREEN("Green", 65280),
    RED("Red", 16711680),
    CYAN("Cyan", 65535),
    MAGENTA("Magenta", 16711935),
    YELLOW("Yellow", 16776960);

    int value;
    String color;

    Color(String color, int value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }
}
