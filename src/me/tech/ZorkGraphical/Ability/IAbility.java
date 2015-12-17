package me.tech.ZorkGraphical.Ability;


import me.tech.ZorkGraphical.entity.EntityLiving;

import java.io.Serializable;

/**
 * Created by renaudj on 12/4/15.
 */
public interface IAbility extends Serializable{

    public void activate(EntityLiving entityLiving);

}
