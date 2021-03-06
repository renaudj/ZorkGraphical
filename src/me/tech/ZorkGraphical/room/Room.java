package me.tech.ZorkGraphical.room;



import me.tech.ZorkGraphical.entity.Player;
import me.tech.ZorkGraphical.items.Item;
import me.tech.ZorkGraphical.entity.Character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room implements Serializable {
    public static final Long serialVersionUID = 1L;

    private HashMap<Direction, Room> exits;
    private String name;
    private String description;
    private ArrayList<Item> items;
    private ArrayList<Character> characters;
    private int lockCode;
    private Item requiredItem;
    private OnEnterRoomListener onEnterRoomListener;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<Direction, Room>();
        this.items = new ArrayList<Item>();
        this.characters = new ArrayList<Character>();
        this.lockCode = 0;
        this.requiredItem = null;
        this.onEnterRoomListener = new OnEnterRoomListener() {
            public void onEnter(Player player) {
                return;
            }
        };
    }

    public Room(String name, String description, int lockCode) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<Direction, Room>();
        this.items = new ArrayList<Item>();
        this.characters = new ArrayList<Character>();
        this.lockCode = lockCode;
        this.requiredItem = null;
        this.onEnterRoomListener = new OnEnterRoomListener() {
            public void onEnter(Player player) {
                return;
            }
        };
    }
    public Room(String name, String description, int lockCode, Item requiredItem) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<Direction, Room>();
        this.items = new ArrayList<Item>();
        this.characters = new ArrayList<Character>();
        this.lockCode = lockCode;
        this.requiredItem = requiredItem;
        this.onEnterRoomListener = new OnEnterRoomListener() {
            public void onEnter(Player player) {
                return;
            }
        };
    }

    public Item getRequiredItem(){
        return this.requiredItem;
    }

    public void setRequiredItem(Item i){
        this.requiredItem = i;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public boolean hasExit(Direction d) {
        return exits.containsKey(d);
    }

    public void addExit(Direction direction, Room room) {
        this.exits.put(direction, room);
    }

    public void addItem(Item i) {
        items.add(i);
    }

    public List<Item> getItems() {
        return items;
    }

    public Map<Direction, Room> getExits() {
        return exits;
    }

    public Room getExit(Direction direction) {
        if (exits.containsKey(direction))
            return exits.get(direction);
        else
            return null;
    }

    public String getName() {
        return name;
    }

    public void removeItem(Item i) {
        items.remove(i);
    }

    public String getDescription() {
        return description;
    }

    public boolean hasItems() {
        return items.size() > 0;
    }

    public boolean hasCharacters() {
        return characters.size() > 0;
    }

    public boolean hasCharacter(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public Character getCharacter(String name) {
        if (hasCharacter(name)) {
            for (Character c : characters) {
                if (c.getName().equalsIgnoreCase(name))
                    return c;
            }
        }
        return null;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    public String getExitString(){
        if (this.exits.size() > 0) {
            String output = "You can go";
            String temp = "";
            for (Direction d : exits.keySet()) {
                temp += ", " + d.toString().toLowerCase();
            }
            temp = temp.substring(1);
            return output + temp;
        }
        return "You can't go anywhere.";
    }

    public void onEnter(Player p) {
        this.onEnterRoomListener.onEnter(p);
    }

    public void setOnEnterRoomListener(OnEnterRoomListener l) {
        this.onEnterRoomListener = l;
    }
}
