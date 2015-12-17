package me.tech.ZorkGraphical.entity;


import me.tech.ZorkGraphical.items.Inventory;
import me.tech.ZorkGraphical.items.InventorySlotType;
import me.tech.ZorkGraphical.items.Item;
import me.tech.ZorkGraphical.items.Weapon;

import java.io.Serializable;

public abstract class EntityLiving implements Serializable{
    private String name;
    private Inventory inventory;
    private int HP;
    private int maxHP;

    public EntityLiving(String name, int maxHp) {
        this.name = name;
        this.inventory = new Inventory();
        this.HP = maxHp;
        this.maxHP = maxHp;
    }

    public Item getLeftHand() {
        return getInventory().getItemInSlot(InventorySlotType.LEFT_HAND);
    }

    public Item getRightHand() {
        return getInventory().getItemInSlot(InventorySlotType.RIGHT_HAND);
    }

    public int getHP() {
        return this.HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return this.maxHP;
    }

    public void setMaxHP(int HP) {
        this.maxHP = HP;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void attack(EntityLiving c) {
        if (getRightHand() != null || getLeftHand() != null) {
            if (getRightHand() != null)
                if (getRightHand() instanceof Weapon) {
                    if(c instanceof Player){
                        int hptoremove = (((Weapon) getRightHand()).getPower() - ((Player) c).getSkill("toughness").getLevel());
                        if(hptoremove <= 0)
                            hptoremove = 0;
                        c.setHP(c.getHP() - hptoremove);
                    } else {
                        c.setHP(c.getHP() - ((Weapon) getRightHand()).getPower());
                    }
                    getRightHand().setDurability(getRightHand().getDurability() - 1);
                    ((Weapon) getRightHand()).activateAbility(this, c);
                    if ((getRightHand()).getDurability() <= 0) {
                        getInventory().removeItem(getRightHand());
                    }
                } else {
                    c.setHP(c.getHP() - 1);
                }
            if (getLeftHand() != null)
                if (getLeftHand() instanceof Weapon) {
                    if(c instanceof Player){
                        int hptoremove = (((Weapon) getRightHand()).getPower() - ((Player) c).getSkill("toughness").getLevel());
                        if(hptoremove <= 0)
                            hptoremove = 0;
                        c.setHP(c.getHP() - hptoremove);
                    } else {
                        c.setHP(c.getHP() - ((Weapon) getLeftHand()).getPower());
                    }
                    getLeftHand().setDurability(getLeftHand().getDurability() - 1);
                    ((Weapon) getLeftHand()).activateAbility(this, c);
                    if ((getLeftHand()).getDurability() <= 0) {
                        getInventory().removeItem(getLeftHand());
                    }
                } else {
                    c.setHP(c.getHP() - 1);
                }
        } else {
            c.setHP(c.getHP() - 1);
        }
        c.onDamage(this);
    }

    public void onDamage(EntityLiving p) {
        if (getHP() <= 0)
            onDeath(p);
    }

    public void onDeath(EntityLiving p) {
    }
}
