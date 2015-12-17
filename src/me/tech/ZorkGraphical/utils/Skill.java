package me.tech.ZorkGraphical.utils;

/**
 * Created by renaudj on 12/17/15.
 */
public class Skill {
    private String name;
    private int level;
    private String description;

    public Skill(String name, int level, String description) {
        this.name = name;
        this.level = level;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void increment(){
        level++;
    }

    public void decrement(){
        level--;
    }
}
