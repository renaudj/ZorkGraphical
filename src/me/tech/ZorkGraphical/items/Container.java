package me.tech.ZorkGraphical.items;

import java.io.Serializable;
import java.util.List;

public class Container extends Item implements Serializable {
    public static final Long serialVersionUID = 1L;

    public List<Item> items;
    private String name;

    public Container(String name, List<Item> items) {
        super(name, 0, -1, "");
        this.items = items;
        this.name = name;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void removeItem(Item i) {
        items.remove(i);
    }

    public String getName() {
        return this.name;
    }

}
