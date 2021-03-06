package me.tech.ZorkGraphical.items;


import me.tech.ZorkGraphical.Ability.IAbility;
import me.tech.ZorkGraphical.entity.EntityLiving;
import me.tech.ZorkGraphical.entity.Player;

import java.io.Serializable;

public class Item implements Serializable {
    public static final Long serialVersionUID = 1L;

    private String name;
    private int durability;
    private int maxDurability;
    private int weight;
    private String description;
    private IAbility ability;

    /**
     * A generic Item, can be put into containers or inventories.
     *
     * @param name          Item's name
     * @param maxDurability How many times the item can be used before it breaks
     * @param weight        Weight for inventory limitation
     * @param description   Item description
     */

    public Item(String name, int maxDurability, int weight, String description) {
        this.name = name;
        this.maxDurability = maxDurability;
        this.durability = maxDurability;
        this.weight = weight;
        this.description = description;
        this.ability = new IAbility() {
            public void activate(EntityLiving entityLiving) {
                return;
            }
        };
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public IAbility getAbility() {
        return this.ability;
    }

    public void setAbility(IAbility ability) {
        this.ability = ability;
    }

    public int getWeight() {
        return weight;
    }

    public boolean use(Player p) {
        ability.activate(p);
        return true;
    }
}
