package me.tech.ZorkGraphical.events;


import me.tech.ZorkGraphical.api.events.IEvent;
import me.tech.ZorkGraphical.entity.Player;
public class PlayerLevelUpEvent implements IEvent {
    private int level;
    private Player player;

    public PlayerLevelUpEvent(Player player, int level) {
        this.player = player;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public Player getPlayer() {
        return this.player;
    }
}
