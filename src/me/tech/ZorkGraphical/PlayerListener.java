package me.tech.ZorkGraphical;

import me.tech.ZorkGraphical.api.events.Event;
import me.tech.ZorkGraphical.api.events.EventListener;
import me.tech.ZorkGraphical.events.PlayerAttackEvent;
import me.tech.ZorkGraphical.events.PlayerDamageEvent;

/**
 * Created by renaudj on 11/22/15.
 */
public class PlayerListener implements EventListener {

    @Event
    public void onAttack(PlayerAttackEvent event) {
        Zork.getInstance().println("You dealt " + event.getDamage() + " damage on " + event.getEntity().getName() + " | " + event.getEntity().getHP() + "/" + event.getEntity().getMaxHP());
    }

    @Event
    public void onPlayerDamaged(PlayerDamageEvent e) {
        Zork.getInstance().println(e.getEntity().getName() + " dealt " + e.getDamage() + " damage on you wielding " + (e.getItemInHand() == null ? "nothing" : e.getItemInHand().getName()) + " | " + e.getPlayer().getHP() + "/" + e.getPlayer().getMaxHP());
    }
}
