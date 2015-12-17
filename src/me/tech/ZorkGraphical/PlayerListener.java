package me.tech.ZorkGraphical;

import me.tech.ZorkGraphical.api.events.Event;
import me.tech.ZorkGraphical.api.events.EventListener;
import me.tech.ZorkGraphical.entity.Player;
import me.tech.ZorkGraphical.events.PlayerAttackEvent;
import me.tech.ZorkGraphical.events.PlayerDamageEvent;
import me.tech.ZorkGraphical.events.PlayerLevelUpEvent;
import me.tech.ZorkGraphical.items.Food;
import me.tech.ZorkGraphical.utils.Experience;

public class PlayerListener implements EventListener {

    @Event
    public void onAttack(PlayerAttackEvent event) {
        Zork.getInstance().println("You dealt " + event.getDamage() + " damage on " + event.getEntity().getName() + " | " + event.getEntity().getHP() + "/" + event.getEntity().getMaxHP());
        if(event.getEntity().getHP() <= 0){
                Player pl = (Player) event.getPlayer();
                pl.addExp(Experience.KILL);
        }
    }

    @Event
    public void onPlayerDamaged(PlayerDamageEvent e) {
        Zork.getInstance().println(e.getEntity().getName() + " dealt " + e.getDamage() + " damage on you wielding " + (e.getItemInHand() == null ? "nothing" : e.getItemInHand().getName()) + " | " + e.getPlayer().getHP() + "/" + e.getPlayer().getMaxHP());
    }

    @Event
    public void onLevelUp(PlayerLevelUpEvent e){
        if(e.getLevel() >= 2){
            e.getPlayer().getInventory().addItem(new Food("Apple", 1, 20,  "Increases HP by 20"));
        }
        e.getPlayer().addSkillToken();
        Zork.getInstance().println("You've earned a skill token!");
    }
}
