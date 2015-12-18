package me.tech.ZorkGraphical.items;


import me.tech.ZorkGraphical.Zork;
import me.tech.ZorkGraphical.entity.Player;

import java.io.Serializable;

/**
 * Created by renaudj on 11/20/15.
 */
public class Food extends Item implements Serializable {
    public static final Long serialVersionUID = 1L;

    private int hpBonus;

    public Food(String name, int weight, int hpBonus, String description) {
        super(name, 0, weight, description);
        this.hpBonus = hpBonus;
    }

    public int getHPBonus() {
        return hpBonus;
    }

    public boolean use(Player p) {
        p.setHP(p.getHP() + getHPBonus());
        if (p.getHP() > p.getMaxHP()) {
            p.setHP(p.getMaxHP());
        }
        Zork.getInstance().println("You've gained " + getHPBonus() + " HP");
        return true;
    }
}
