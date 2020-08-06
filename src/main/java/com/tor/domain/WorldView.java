package com.tor.domain;

public class WorldView {
    private String name;
    private int value;
    private ItemStyle itemStyle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
    }

    @Override
    public String toString() {
        return "WorldView{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", itemStyle=" + itemStyle +
                '}';
    }
}
