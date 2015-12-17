package me.tech.ZorkGraphical.room;


import me.tech.ZorkGraphical.entity.Player;

import java.io.Serializable;

/**
 * Created by renaudj on 11/30/15.
 */
public interface OnEnterRoomListener extends Serializable{
    void onEnter(Player player);
}
