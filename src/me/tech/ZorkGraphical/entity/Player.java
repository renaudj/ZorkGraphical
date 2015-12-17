package me.tech.ZorkGraphical.entity;


import me.tech.ZorkGraphical.Zork;
import me.tech.ZorkGraphical.events.PlayerAttackEvent;
import me.tech.ZorkGraphical.events.PlayerDamageEvent;
import me.tech.ZorkGraphical.events.PlayerLevelUpEvent;
import me.tech.ZorkGraphical.items.Item;
import me.tech.ZorkGraphical.items.Weapon;
import me.tech.ZorkGraphical.room.Room;
import me.tech.ZorkGraphical.utils.Experience;

import java.io.Serializable;

public class Player extends EntityLiving implements Serializable {
    private Room currentRoom;
    private Object currentView = null;
    private int exp;
    private int level;

    private int skillTokens = 0;

    private int engineerSkill = 0;

    public Player() {
        super("", 100);
    }

    public int getExp(){
        return exp;
    }

    public void incrementEngineerSkill(){
        engineerSkill++;
    }

    public int getEngineerSkill(){
        return engineerSkill;
    }

    public void addSkillToken(){
        skillTokens++;
    }

    public void removeSkillToken(){
        skillTokens--;
    }

    public int getSkillTokens(){
        return skillTokens;
    }

    public void setExp(int exp){
        this.exp = exp;
    }

    public void addExp(int exp){
        int needed = Experience.getRequiredExpForLevel(level);
        this.exp += exp;

        if(this.exp >= needed){
        int remainder = this.exp - needed;
            if(remainder >= 0){
                incrementLevel();
                setExp(remainder);
                Zork.getInstance().println("Level up!");
                Zork.getInstance().getEventExecutor().executeEvent(new PlayerLevelUpEvent(this, getLevel()));
            }
        }
        Zork.getInstance().getGuiManager().updateExp(level, this.exp);
    }

    public int getLevel(){
        return this.level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public void incrementLevel(){
        level++;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public Object getCurrentView() {
        return this.currentView;
    }

    public void setCurrentView(Object view) {
        this.currentView = view;
    }

    public void goToRoom(Room room) {
            Zork.getInstance().println();
        if (getCurrentRoom() != null) {
            if (getCurrentRoom().hasCharacters()) {
                for (Character c : getCurrentRoom().getCharacters()) {
                    if (c instanceof Enemy) {
                        Enemy e = (Enemy) c;
                        if (e.getHP() < e.getMaxHP()) {
                            Zork.getInstance().println("You must kill " + e.getName() + " before you can go anywhere!");
                            return;
                        }
                    }
                }
            }
        }
        if (room.getRequiredItem() != null) {
            if (!getInventory().hasItem(room.getRequiredItem().getName())) {
                Zork.getInstance().println("You need a " + room.getRequiredItem().getName() + " to enter here!");
                return;
            }
        }
        getInventory().removeItem(room.getRequiredItem());
        room.setRequiredItem(null);
        setCurrentRoom(room);
        setCurrentView(room);
        Zork.getInstance().println(room.getDescription() + "\n");
        if (room.hasItems()) {
            Zork.getInstance().print("You see");
            for (Item i : room.getItems()) {
                String o = ", a " + i.getName();
                Zork.getInstance().print(o.substring(1));
            }
            Zork.getInstance().println();
        } else
            Zork.getInstance().println("There's nothing interesting here.");
        if (room.hasCharacters()) {
            for (Character i : room.getCharacters()) {
                Zork.getInstance().println(i.getName() + " is here.");
                Zork.getInstance().println(i.getDescription());
            }
            Zork.getInstance().println();
        }
        if(!getCurrentRoom().getName().equals("Room0"))
        Zork.getInstance().println(getCurrentRoom().getExitString());
        room.onEnter(this);
    }


    public void resumeRoom(Room room) {
        setCurrentRoom(room);
        setCurrentView(room);
        Zork.getInstance().println(room.getDescription() + "\n");
        if (room.hasItems()) {
            Zork.getInstance().print("You see");
            for (Item i : room.getItems()) {
                String o = ", a " + i.getName();
                Zork.getInstance().print(o.substring(1));
            }
            Zork.getInstance().println();
        } else
            Zork.getInstance().println("There's nothing interesting here.");
        if (room.hasCharacters()) {
            for (Character i : room.getCharacters()) {
                Zork.getInstance().println(i.getName() + " is here.");
                Zork.getInstance().println(i.getDescription());
            }
            Zork.getInstance().println();
        }
        if(!getCurrentRoom().getName().equals("Room0"))
            Zork.getInstance().println(getCurrentRoom().getExitString());
    }

    public void attack(EntityLiving c) {
        int damage = 0;
        Item i = null;
        if (getRightHand() != null || getLeftHand() != null) {
            if (getRightHand() != null)
                if (getRightHand() instanceof Weapon) {
                    damage = ((Weapon) getRightHand()).getPower();
                    i = getRightHand();
                    int rand = Experience.randomInt(1, 100);
                    if(!(rand <= 0 + (10*getEngineerSkill()))){
                        getRightHand().setDurability(getRightHand().getDurability() - 1);
                            if ((getRightHand()).getDurability() <= 0) {
                                getInventory().removeItem(getRightHand());
                            }
                        }
                    ((Weapon) getRightHand()).activateAbility(this, c);
                } else {
                    damage = 1;
                }
            else
            if (getLeftHand() != null)
                if (getLeftHand() instanceof Weapon) {
                    damage = ((Weapon) getLeftHand()).getPower();
                    i = getLeftHand();
                    int rand = Experience.randomInt(1, 100);
                    if(!(rand <= 0 + (10*getEngineerSkill()))) {
                        getLeftHand().setDurability(getLeftHand().getDurability() - 1);
                        if ((getLeftHand()).getDurability() <= 0) {
                            getInventory().removeItem(getLeftHand());
                        }
                    }
                    ((Weapon) getLeftHand()).activateAbility(this, c);
                } else {
                    damage = 1;
                }
        } else {
            damage = 1;
        }
        c.setHP(c.getHP() - damage);
        c.onDamage(this);
        Zork.getInstance().getGuiManager().updateInventory();
        Zork.getInstance().getEventExecutor().executeEvent(new PlayerAttackEvent(this, damage, c, i));
    }

    public void onDamage(EntityLiving p) {
        int damage;
        Item i = null;
        if (p.getRightHand() != null || p.getLeftHand() != null) {
            if (p.getRightHand() != null)
                i = p.getRightHand();
            if (p.getLeftHand() != null)
                i = p.getLeftHand();
            if (i instanceof Weapon) {
                damage = ((Weapon) i).getPower();
            } else {
                damage = 1;
            }
        } else {
            damage = ((Enemy) p).getBaseDamage();
        }
        super.onDamage(p);
        Zork.getInstance().getEventExecutor().executeEvent(new PlayerDamageEvent(this, damage, p, i));
    }

    public void onDeath(EntityLiving p) {
        Zork.getInstance().println("You've died!");
        System.exit(0);
    }
}
