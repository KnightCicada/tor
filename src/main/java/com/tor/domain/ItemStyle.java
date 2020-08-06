package com.tor.domain;

public class ItemStyle {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ItemStyle{" +
                "color='" + color + '\'' +
                '}';
    }
}
