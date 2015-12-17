package me.tech.ZorkGraphical.room;

import java.io.Serializable;

public enum Direction implements Serializable {
	NORTH, SOUTH, EAST, WEST, UP, DOWN;

	public static boolean exists(String direction) {
		for (Direction d : Direction.values()) {
			if (d.toString().equalsIgnoreCase(direction)) {
				return true;
			}
		}
		return false;
	}
}
