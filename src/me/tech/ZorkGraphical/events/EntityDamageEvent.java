package me.tech.ZorkGraphical.events;


import me.tech.ZorkGraphical.api.events.IEvent;
import me.tech.ZorkGraphical.entity.EntityLiving;
import me.tech.ZorkGraphical.entity.Player;
import me.tech.ZorkGraphical.items.Item;

/**
 * Created by renaudj on 11/22/15.
 */
public class EntityDamageEvent implements IEvent {
    private int damage;
    private EntityLiving entity;
    private Item itemInHand;
    private EntityLiving damager;

    public EntityDamageEvent(Player player, int damage, EntityLiving entity, Item itemInHand) {
        this.damager = damager;
        this.damage = damage;
        this.entity = entity;
        this.itemInHand = itemInHand;
    }

    public int getDamage() {
        return damage;
    }

    public EntityLiving getDamager() {
        return this.damager;
    }

    public EntityLiving getEntity() {
        return entity;
    }

    public Item getItemInHand() {
        return itemInHand;
    }
}
