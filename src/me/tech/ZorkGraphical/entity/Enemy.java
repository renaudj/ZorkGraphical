package me.tech.ZorkGraphical.entity;

import java.io.Serializable;

/**
 * Created by renaudj on 11/17/15.
 */
public class Enemy extends Character implements Serializable{
    public static final Long serialVersionUID = 1L;

    /**
     * Enemies automatically attack you back if you attack them.
     *
     * @param name Enemy's name
     * @param maxHp Enemy's Maximum HP
     * @param description Description or monologue
     */
    private int baseDamage;
    public Enemy(String name, int maxHp, String description) {
        super(name, maxHp, description);
        baseDamage = 1;
    }

    public int getBaseDamage() {
        return this.baseDamage;
    }

    public void setBaseDamage(int bd) {
        this.baseDamage = bd;
    }

    public void onDamage(EntityLiving c) {
        super.onDamage(c);
        if (getHP() > 0)
            attack(c);
    }
}
