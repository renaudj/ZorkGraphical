package me.tech.ZorkGraphical;

import me.tech.ZorkGraphical.Ability.IAbility;
import me.tech.ZorkGraphical.api.events.EventExecutor;
import me.tech.ZorkGraphical.commands.Command;
import me.tech.ZorkGraphical.commands.CommandHandler;
import me.tech.ZorkGraphical.entity.Enemy;
import me.tech.ZorkGraphical.entity.EntityLiving;
import me.tech.ZorkGraphical.entity.Player;
import me.tech.ZorkGraphical.items.*;
import me.tech.ZorkGraphical.room.Direction;
import me.tech.ZorkGraphical.room.OnEnterRoomListener;
import me.tech.ZorkGraphical.room.Room;
import me.tech.ZorkGraphical.utils.Experience;
import me.tech.ZorkGraphical.utils.GUIManager;
import me.tech.ZorkGraphical.utils.Skill;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Zork implements Serializable {
    public static final Long serialVersionUID = 1L;
    private static Zork instance;
    public static boolean dead = false;
    //Create rooms here
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public boolean running = false;
    public transient CommandHandler commandHandler;
    private HashMap<String, Recipe> recipes = new HashMap<>();
    public HashMap<String, Room> rooms = new HashMap<>();
    private Player player;
    private transient EventExecutor events;
    private transient GUIManager guiManager;

    public transient boolean debug = true;

    public Room getRoom(String name){
        return rooms.get(name);
    }

    public void registerRooms(){
        rooms.put("room0", new Room("Room0", Lang.r0));
        rooms.put("room1a", new Room("Room1a", Lang.r1a));
        rooms.put("room1b", new Room("Room1b", Lang.r1b));
        rooms.put("room2a", new Room("Room2a", Lang.r2a));
        rooms.put("room2b", new Room("Room2b", Lang.r2b));
        rooms.put("room3a", new Room("Room3a", Lang.r3a));
        rooms.put("room3b", new Room("Room3b", Lang.r3b));
        rooms.put("room1c", new Room("Room1c", Lang.r1c));
        rooms.put("room2c", new Room("Room2c", Lang.r2c));
        rooms.put("room3c", new Room("Room3c", Lang.r3c));
        rooms.put("room1d", new Room("Room1d", Lang.r1d));
        rooms.put("room2d", new Room("Room2d", Lang.r2d));
        rooms.put("room3d", new Room("Room3d", Lang.r3d));
        rooms.put("room1e", new Room("Room1e", Lang.r1e));
        rooms.put("room2e", new Room("Room2e", Lang.r2e));
        rooms.put("room3e", new Room("Room3e", Lang.r3e));
        rooms.put("room4a", new Room("Room4a", Lang.r4a));
        rooms.put("room4b", new Room("Room4b", Lang.r4b));
        rooms.put("room4c", new Room("Room4c", Lang.r4c));
        rooms.put("room4d", new Room("Room4d", Lang.r4d));
        rooms.put("room4e", new Room("Room4e", Lang.r4e));
        rooms.put("room5a", new Room("Room5a", Lang.r5a));
        rooms.put("room5b", new Room("Room5b", Lang.r5b));
        rooms.put("room5c", new Room("Room5c", Lang.r5c));
        rooms.put("room5d", new Room("Room5d", Lang.r5d));
        rooms.put("room5e", new Room("Room5e", Lang.r5e));
        rooms.put("room6a", new Room("Room6a", Lang.r6a));
        rooms.put("room6b", new Room("Room6b", Lang.r6b));
        rooms.put("room6c", new Room("Room6c", Lang.r6c));
        rooms.put("room6d", new Room("Room6d", Lang.r6d));
        rooms.put("room6e", new Room("Room6e", Lang.r6e));
        rooms.put("room7a", new Room("Room7a", Lang.r7a));
        rooms.put("room7b", new Room("Room7b", Lang.r7b));
        rooms.put("room7c", new Room("Room7c", Lang.r7c));
        rooms.put("room7d", new Room("Room7d", Lang.r7d));
        rooms.put("room7e", new Room("Room7e", Lang.r7e));
        rooms.put("room8a", new Room("Room8a", Lang.r8a));
        rooms.put("room8b", new Room("Room8b", Lang.r8b));
        rooms.put("room8c", new Room("Room8c", Lang.r8c));
        rooms.put("room8d", new Room("Room8d", Lang.r8d));
        rooms.put("room8e", new Room("Room8e", Lang.r8e));
        rooms.put("room9a", new Room("Room9a", Lang.r9a));
        rooms.put("room9b", new Room("Room9b", Lang.r9b));
        rooms.put("room9c", new Room("Room9c", Lang.r9c));
        rooms.put("room9d", new Room("Room9d", Lang.r9d));
        rooms.put("room9e", new Room("Room9e", Lang.r9e));
    }

    public Zork(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                if(!dead)
                save();
            }
        });
        instance = this;
        commandHandler = new CommandHandler(this);
        events = new EventExecutor();
        events.registerListener(new PlayerListener());
        registerCommands();
        this.guiManager = new GUIManager(this);

        File f = new File("savegame.svg");
        Zork z = null;
        if(f.exists()){
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                Object in = ois.readObject();
                if(in instanceof Zork){
                    z = (Zork)in;
                }
                player = z.player;
                recipes = z.recipes;
                rooms = z.rooms;
                resume();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            player = new Player();
            registerRooms();
            setRoomExits();
            populateRooms();
            start();
        }
    }

    public HashMap<String, Recipe> getRecipes(){
        return recipes;
    }

    public static Zork getInstance() {
        return instance;
    }

    public static void main(String[] args){
        new Zork();
    }

    public GUIManager getGuiManager(){
        return guiManager;
    }

    public void println(String message){
        getGuiManager().log.append(message + "\n");
        getGuiManager().log.setCaretPosition(getGuiManager().log.getDocument().getLength());
    }

    public void print(String message){
        getGuiManager().log.append(message);
        getGuiManager().log.setCaretPosition(getGuiManager().log.getDocument().getLength());
    }

    public EventExecutor getEventExecutor() {
        return this.events;
    }

    public void start() {
        Zork.getInstance().println(Lang.intro);
        player.goToRoom(getRoom("room0"));
        player.setLevel(1);
        getGuiManager().updateExp(player.getLevel(), player.getExp());
    }

    public void resume(){
        player.resumeRoom(player.getCurrentRoom());
        getGuiManager().updateInventory();
        getGuiManager().updateExp(player.getLevel(), player.getExp());
    }

    public void registerCommands() {
        commandHandler.register("save", "Saves the gamestate to the disk", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length >= 0) {
                    save();
                }
                return true;
            }

        });

        commandHandler.register("skills", "List of skills", new Command() {

            public boolean onCommand(String command, String[] args) {
                for(String name : player.getSkillSet().keySet()){
                    Skill sk = player.getSkill(name);
                    println("Skill: " + sk.getName() + "\nLevel: " + sk.getLevel() + "\nDescription: " + sk.getDescription() + "\n");

                }
                return true;
            }

        });

        commandHandler.register("skill", "<Info/Upgrade> <Skill Name> - View info about a skill or upgrade it", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 2) {
                    String name = "";
                    for (int i = 1; i < args.length; i++){
                        name += args[i] + " ";
                    }
                    name = name.trim();

                    if(args[0].equalsIgnoreCase("info")){
                        if(player.getSkillSet().keySet().contains(name)){
                            Skill sk = player.getSkill(name);
                            println("Skill: " + sk.getName() + "\nLevel: " + sk.getLevel() + "\nDescription: " + sk.getDescription());
                            return true;
                        } else {
                            println("That skill doesn't exist!");
                            return true;
                        }
                    } else if(args[0].equalsIgnoreCase("upgrade")){
                        if(player.getSkillTokens() > 0){
                            if(player.getSkillSet().keySet().contains(name)){
                                player.removeSkillToken();
                                player.getSkill(name).increment();
                                println("Upgraded " + player.getSkill(name).getName() + " to level " +  player.getSkill(name).getLevel() + "!");
                            }
                        }
                    }
                }
                return true;
            }

        });
        commandHandler.register("attack", "<Character/Enemy> - Attacks specified Character/Enemy with equipped items, prioritizing the right hand", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length > 0) {
                    String name = "";
                    for(String s : args){
                        name += " " + s;
                    }
                    name = name.trim();
                    if (player.getCurrentRoom().hasCharacter(name)) {
                        player.attack(player.getCurrentRoom().getCharacter(name));
                    }
                }
                return true;
            }

        });
        commandHandler.register("use", "<Item> - Activates specified item's generic ability", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    String s = "";
                    for (String f : args) {
                        s += f + " ";
                    }
                    s = s.trim();
                    if (player.getInventory().hasItem(s)) {
                        if(player.getInventory().getItem(s) instanceof Food){
                            Item item = player.getInventory().getItem(s);
                            item.use(player);
                            player.getInventory().removeItem(item);
                            player.addExp(Experience.EAT);
                            return true;
                        }
                        Item item = player.getInventory().getItem(s);
                        item.use(player);
                    }
                }
                return true;
            }

        });

        commandHandler.register("eat", "<Food> - Eats specified food item", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length > 0) {
                    String s = "";
                    for (String f : args) {
                        s += f + " ";
                    }
                    s = s.trim();
                    if (player.getInventory().hasItem(s)) {
                        if (!(player.getInventory().getItem(s) instanceof Food)) {
                            Zork.getInstance().println("That's not food!");
                            return true;
                        }
                        Food item = (Food)player.getInventory().getItem(s);
                        item.use(player);
                        player.getInventory().removeItem(item);
                        player.addExp(Experience.EAT);
                    }
                }
                return true;
            }

        });
        commandHandler.register("quit", "Quit the game", new Command() {

            public boolean onCommand(String command, String[] args) {
                running = false;
                Zork.getInstance().println("Goodbye!");
                System.exit(0);
                return true;
            }

        });
        commandHandler.register("craft", "<Item> - Crafts specified item", new Command() {

            public boolean onCommand(String command, String[] args) {
                String s = "";
                for (String f : args) {
                    s += f + " ";
                }
                s = s.trim();
                for (String st : recipes.keySet()) {
                    if (st.equalsIgnoreCase(s)) {
                        recipes.get(s.toLowerCase()).craft(player);
                        player.addExp(Experience.CRAFT);
                        return true;
                    }
                }
                Zork.getInstance().println("That recipe doesn't exist!");
                return true;
            }

        });
        commandHandler.register("unequip", "[right/left] - Unequips specified slot, otherwise all slots", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    for (InventorySlotType slot : InventorySlotType.values()) {
                        if (args[0].equalsIgnoreCase(slot.name())) {
                            player.getInventory().unequip(slot);
                            Zork.getInstance().println("Unequipped " + slot.name().toLowerCase().replace("_", " "));
                        }
                    }
                } else {
                    for (InventorySlotType slot : InventorySlotType.values()) {
                        if (!slot.equals(InventorySlotType.INVENTORY)) {
                            player.getInventory().unequip(slot);
                        }
                    }
                    Zork.getInstance().println("Unequipped all equippable slots.");
                }
                return true;
            }

        });
        commandHandler.register("go", "<Direction> - Moves you to the room in the specified direction", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    if (Direction.exists(args[0])) {
                        Direction d = Direction.valueOf(args[0].toUpperCase());
                        if (player.getCurrentRoom().hasExit(d))
                            player.goToRoom(player.getCurrentRoom().getExit(d));
                        else
                            Zork.getInstance().println("There is no exit in that direction.");
                        return true;
                    } else {
                        Zork.getInstance().println("Your choices are north, south, east, west, up, down.");
                    }
                } else {
                    Zork.getInstance().println("I don't understand.. do you mean \"go north\"?");
                }
                return false;
            }
        });
        commandHandler.register("equip", "<Item> <Slot> - Equips specified item to specified slot", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length > 0) {
                    String slt = args[args.length - 1];
                    String item = "";
                    InventorySlotType slot2 = null;
                    boolean worked = false;
                    for (InventorySlotType slot : InventorySlotType.values()) {
                        if (slot.name().equalsIgnoreCase(slt) || slot.isAlias(slt)) {
                            if (slot.equals(InventorySlotType.INVENTORY)) {
                                Zork.getInstance().println("Please choose an equippable slot!");
                                break;
                            }
                            slot2 = slot;
                            worked = true;
                            break;
                        }
                    }
                    if (!worked) {
                        Zork.getInstance().println("Please enter a valid slot!");
                        return true;
                    }
                    for (int i = 0; i < args.length - 1; i++) {
                        item += " " + args[i];
                    }
                    item = item.trim();
                    if (!player.getInventory().hasItem(item)) {
                        Zork.getInstance().println("You do not have a " + item);
                        return false;
                    }
                    Item item1 = player.getInventory().getItem(item);
                    player.getInventory().equip(slot2, item1);
                    Zork.getInstance().println("Equipped " + item1.getName() + " to " + slot2.name().toLowerCase().replace("_", " "));
                }
                return true;
            }

        });
        commandHandler.register("take", "<Item> - Puts specified item in your inventory", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length > 0) {
                    if (player.getCurrentView() instanceof Room) {
                        String comm = "";
                        for (String arg : args) {
                            comm += arg + " ";
                        }
                        comm = comm.trim();
                        for (Item item : ((Room) player.getCurrentView()).getItems()) {
                            if (item.getName().toLowerCase().equals(comm.toLowerCase())) {
                                if (item.getWeight() == -1) {
                                    Zork.getInstance().println("You can't take that!");
                                    return false;
                                }
                                player.getInventory().addItem(item);
                                Zork.getInstance().println("Took " + item.getName());
                                ((Room) player.getCurrentView()).removeItem(item);
                                return true;
                            } else continue;
                        }
                        Zork.getInstance().println("There isn't a " + comm + " in this room!");
                        return true;
                    } else if (player.getCurrentView() instanceof Container) {
                        String comm = "";
                        for (String arg : args) {
                            comm += arg + " ";
                        }
                        comm = comm.trim();
                        for (Item item : ((Container) player.getCurrentView()).getItems()) {

                            if (item.getName().toLowerCase().equals(comm.toLowerCase())) {
                                if (item.getWeight() == -1) {
                                    Zork.getInstance().println("You can't take that!");
                                    return false;
                                }
                                player.getInventory().addItem(item);
                                Item i = item;
                                ((Container) player.getCurrentView()).removeItem(item);
                                Zork.getInstance().println("Took " + i.getName());
                                return true;
                            } else continue;
                        }
                        Zork.getInstance().println("There isn't a " + comm + " in this " + ((Container) player.getCurrentView()).getName() + "!");
                    }
                    return true;
                } else {
                    Zork.getInstance().println("I don't understand.. do you mean \"take item\"?");
                }
                return false;
            }
        });
        commandHandler.register("open", "<Container> - Opens the specified container", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    List<Container> containers = new ArrayList<Container>();
                    for (Item i : player.getCurrentRoom().getItems()) {
                        if (i instanceof Container) {
                            containers.add((Container) i);
                        }

                        for (Container cont : containers) {
                            if (cont.getName().toLowerCase().equals(args[0].toLowerCase())) {
                                player.setCurrentView(cont);
                                if (cont.getItems().size() > 0) {
                                    Zork.getInstance().print("You see");
                                    String o = "";
                                    for (Item it : cont.getItems()) {
                                        o += ", a " + it.getName();
                                    }
                                    Zork.getInstance().println(o.substring(1));
                                } else {
                                    Zork.getInstance().println("This " + cont.getName() + " is empty.");
                                }
                            }
                        }
                    }
                    return true;
                } else {
                    Zork.getInstance().println("I don't understand.. do you mean \"take item\"?");
                }
                return false;
            }
        });
        commandHandler.register("close", "Closes the currently opened container", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (player.getCurrentView() instanceof Container) {
                    Zork.getInstance().println("You closed the " + ((Container) player.getCurrentView()).getName() + ".");
                    player.setCurrentView(player.getCurrentRoom());
                }
                return false;
            }
        });
        commandHandler.register("inventory", "View a list of items in your inventory", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (player.getInventory().getFullInventory().size() > 0) {
                    Zork.getInstance().print("You have");
                    String o = "";
                    for (Item it : player.getInventory().getFullInventory()) {
                        o += ", a " + it.getName();
                    }
                    Zork.getInstance().println(o.substring(1));
                    return true;
                }
                return false;
            }
        });
    }

    public Player getPlayer(){
        return player;
    }
    //Assign neighboring rooms to the exits in each direction desired
    public void setRoomExits() {
        getRoom("room0").addExit(Direction.UP,     getRoom("room1a"));

        getRoom("room1a").addExit(Direction.DOWN,  getRoom("room0"));
        getRoom("room1a").addExit(Direction.EAST,  getRoom("room2a"));
        getRoom("room1a").addExit(Direction.SOUTH, getRoom("room1b"));

        getRoom("room2a").addExit(Direction.SOUTH, getRoom("room2b"));
        getRoom("room2a").addExit(Direction.EAST,  getRoom("room3a"));
        getRoom("room2a").addExit(Direction.WEST,  getRoom("room1a"));

        getRoom("room3a").addExit(Direction.WEST,  getRoom("room2a"));
        getRoom("room3a").addExit(Direction.SOUTH, getRoom("room3b"));
        getRoom("room3a").addExit(Direction.EAST,  getRoom("room4a"));

        getRoom("room4a").addExit(Direction.WEST,  getRoom("room3a"));
        getRoom("room4a").addExit(Direction.SOUTH, getRoom("room4b"));
        getRoom("room4a").addExit(Direction.EAST,  getRoom("room5a"));

        getRoom("room5a").addExit(Direction.WEST,  getRoom("room4a"));
        getRoom("room5a").addExit(Direction.SOUTH, getRoom("room5b"));
        getRoom("room5a").addExit(Direction.EAST,  getRoom("room6a"));

        getRoom("room6a").addExit(Direction.WEST,  getRoom("room5a"));
        getRoom("room6a").addExit(Direction.SOUTH, getRoom("room6b"));
        getRoom("room6a").addExit(Direction.EAST,  getRoom("room7a"));

        getRoom("room7a").addExit(Direction.WEST,  getRoom("room6a"));
        getRoom("room7a").addExit(Direction.SOUTH, getRoom("room7b"));
        getRoom("room7a").addExit(Direction.EAST,  getRoom("room8a"));

        getRoom("room8a").addExit(Direction.WEST,  getRoom("room7a"));
        getRoom("room8a").addExit(Direction.SOUTH, getRoom("room8b"));
        getRoom("room8a").addExit(Direction.EAST,  getRoom("room9a"));

        getRoom("room9a").addExit(Direction.WEST,  getRoom("room8a"));
        getRoom("room9a").addExit(Direction.SOUTH, getRoom("room9b"));

        getRoom("room1b").addExit(Direction.NORTH, getRoom("room1a"));
        getRoom("room1b").addExit(Direction.EAST,  getRoom("room2b"));
        getRoom("room1b").addExit(Direction.SOUTH, getRoom("room1c"));

        getRoom("room2b").addExit(Direction.WEST,  getRoom("room1b"));
        getRoom("room2b").addExit(Direction.NORTH, getRoom("room2a"));
        getRoom("room2b").addExit(Direction.EAST,  getRoom("room3b"));
        getRoom("room2b").addExit(Direction.SOUTH, getRoom("room2c"));

        getRoom("room3b").addExit(Direction.WEST,  getRoom("room2b"));
        getRoom("room3b").addExit(Direction.NORTH, getRoom("room3a"));
        getRoom("room3b").addExit(Direction.EAST,  getRoom("room4b"));
        getRoom("room3b").addExit(Direction.SOUTH, getRoom("room3c"));

        getRoom("room4b").addExit(Direction.WEST,  getRoom("room3b"));
        getRoom("room4b").addExit(Direction.NORTH, getRoom("room4a"));
        getRoom("room4b").addExit(Direction.EAST,  getRoom("room5b"));
        getRoom("room4b").addExit(Direction.DOWN,  getRoom("room4c"));

        getRoom("room5b").addExit(Direction.WEST,  getRoom("room4b"));
        getRoom("room5b").addExit(Direction.NORTH, getRoom("room5a"));
        getRoom("room5b").addExit(Direction.EAST,  getRoom("room6b"));
        getRoom("room5b").addExit(Direction.SOUTH, getRoom("room5c"));

        getRoom("room6b").addExit(Direction.WEST,  getRoom("room5b"));
        getRoom("room6b").addExit(Direction.NORTH, getRoom("room6a"));
        getRoom("room6b").addExit(Direction.EAST,  getRoom("room7b"));
        getRoom("room6b").addExit(Direction.SOUTH, getRoom("room6c"));

        getRoom("room7b").addExit(Direction.WEST,  getRoom("room6b"));
        getRoom("room7b").addExit(Direction.NORTH, getRoom("room7a"));
        getRoom("room7b").addExit(Direction.EAST,  getRoom("room8b"));
        getRoom("room7b").addExit(Direction.SOUTH, getRoom("room7c"));

        getRoom("room8b").addExit(Direction.WEST,  getRoom("room7b"));
        getRoom("room8b").addExit(Direction.NORTH, getRoom("room8a"));
        getRoom("room8b").addExit(Direction.EAST,  getRoom("room9b"));
        getRoom("room8b").addExit(Direction.SOUTH, getRoom("room8c"));

        getRoom("room9b").addExit(Direction.WEST,  getRoom("room8b"));
        getRoom("room9b").addExit(Direction.NORTH, getRoom("room9a"));
        getRoom("room9b").addExit(Direction.SOUTH, getRoom("room9c"));

        getRoom("room1c").addExit(Direction.NORTH, getRoom("room1b"));
        getRoom("room1c").addExit(Direction.EAST,  getRoom("room2c"));
        getRoom("room1c").addExit(Direction.SOUTH, getRoom("room1d"));

        getRoom("room2c").addExit(Direction.WEST,  getRoom("room1c"));
        getRoom("room2c").addExit(Direction.NORTH, getRoom("room2b"));
        getRoom("room2c").addExit(Direction.EAST,  getRoom("room3c"));
        getRoom("room2c").addExit(Direction.SOUTH, getRoom("room2d"));

        getRoom("room3c").addExit(Direction.WEST,  getRoom("room2c"));
        getRoom("room3c").addExit(Direction.NORTH, getRoom("room3b"));
        getRoom("room3c").addExit(Direction.EAST,  getRoom("room4c"));
        getRoom("room3c").addExit(Direction.SOUTH, getRoom("room3d"));

        getRoom("room4c").addExit(Direction.WEST,  getRoom("room3c"));
        getRoom("room4c").addExit(Direction.NORTH, getRoom("room4b"));
        getRoom("room4c").addExit(Direction.EAST,  getRoom("room5c"));
        getRoom("room4c").addExit(Direction.SOUTH, getRoom("room4d"));

        getRoom("room5c").addExit(Direction.WEST,  getRoom("room4c"));
        getRoom("room5c").addExit(Direction.NORTH, getRoom("room5b"));
        getRoom("room5c").addExit(Direction.EAST,  getRoom("room6c"));
        getRoom("room5c").addExit(Direction.SOUTH, getRoom("room5d"));

        getRoom("room6c").addExit(Direction.WEST,  getRoom("room5c"));
        getRoom("room6c").addExit(Direction.NORTH, getRoom("room6b"));
        getRoom("room6c").addExit(Direction.EAST,  getRoom("room7c"));
        getRoom("room6c").addExit(Direction.SOUTH, getRoom("room6d"));

        getRoom("room7c").addExit(Direction.WEST,  getRoom("room6c"));
        getRoom("room7c").addExit(Direction.NORTH, getRoom("room7b"));
        getRoom("room7c").addExit(Direction.EAST,  getRoom("room8c"));
        getRoom("room7c").addExit(Direction.SOUTH, getRoom("room7d"));

        getRoom("room8c").addExit(Direction.WEST,  getRoom("room7c"));
        getRoom("room8c").addExit(Direction.NORTH, getRoom("room8b"));
        getRoom("room8c").addExit(Direction.EAST,  getRoom("room9c"));
        getRoom("room8c").addExit(Direction.SOUTH, getRoom("room8d"));

        getRoom("room9c").addExit(Direction.WEST,  getRoom("room8c"));
        getRoom("room9c").addExit(Direction.NORTH, getRoom("room9b"));
        getRoom("room9c").addExit(Direction.SOUTH, getRoom("room9d"));

        getRoom("room1d").addExit(Direction.NORTH, getRoom("room1c"));
        getRoom("room1d").addExit(Direction.EAST,  getRoom("room2d'"));
        getRoom("room1d").addExit(Direction.SOUTH, getRoom("room1e"));

        getRoom("room2d").addExit(Direction.WEST,  getRoom("room1d"));
        getRoom("room2d").addExit(Direction.NORTH, getRoom("room2c"));
        getRoom("room2d").addExit(Direction.EAST,  getRoom("room3d"));
        getRoom("room2d").addExit(Direction.SOUTH, getRoom("room2e"));

        getRoom("room3d").addExit(Direction.WEST,  getRoom("room2d"));
        getRoom("room3d").addExit(Direction.NORTH, getRoom("room3c"));
        getRoom("room3d").addExit(Direction.EAST,  getRoom("room4d"));
        getRoom("room3d").addExit(Direction.SOUTH, getRoom("room3e"));

        getRoom("room4d").addExit(Direction.WEST,  getRoom("room3d"));
        getRoom("room4d").addExit(Direction.NORTH, getRoom("room4c"));
        getRoom("room4d").addExit(Direction.EAST,  getRoom("room5d"));
        getRoom("room4d").addExit(Direction.SOUTH, getRoom("room4e"));

        getRoom("room5d").addExit(Direction.WEST,  getRoom("room4d"));
        getRoom("room5d").addExit(Direction.NORTH, getRoom("room5c"));
        getRoom("room5d").addExit(Direction.EAST,  getRoom("room6d"));
        getRoom("room5d").addExit(Direction.SOUTH, getRoom("room5e"));

        getRoom("room6d").addExit(Direction.WEST,  getRoom("room5d"));
        getRoom("room6d").addExit(Direction.NORTH, getRoom("room6c"));
        getRoom("room6d").addExit(Direction.EAST,  getRoom("room7d"));
        getRoom("room6d").addExit(Direction.SOUTH, getRoom("room6e"));

        getRoom("room7d").addExit(Direction.WEST,  getRoom("room6d"));
        getRoom("room7d").addExit(Direction.NORTH, getRoom("room7c"));
        getRoom("room7d").addExit(Direction.EAST,  getRoom("room8d"));
        getRoom("room7d").addExit(Direction.SOUTH, getRoom("room7e"));

        getRoom("room8d").addExit(Direction.WEST,  getRoom("room7d"));
        getRoom("room8d").addExit(Direction.NORTH, getRoom("room8c"));
        getRoom("room8d").addExit(Direction.EAST,  getRoom("room9d"));
        getRoom("room8d").addExit(Direction.SOUTH, getRoom("room8e"));

        getRoom("room9d").addExit(Direction.WEST,  getRoom("room8d"));
        getRoom("room9d").addExit(Direction.NORTH, getRoom("room9c"));

        getRoom("room1e").addExit(Direction.NORTH, getRoom("room1d"));
        getRoom("room1e").addExit(Direction.EAST,  getRoom("room2e"));

        getRoom("room2e").addExit(Direction.WEST,  getRoom("room1e"));
        getRoom("room2e").addExit(Direction.NORTH, getRoom("room2d"));
        getRoom("room2e").addExit(Direction.EAST,  getRoom("room3e"));

        getRoom("room3e").addExit(Direction.WEST,  getRoom("room2e"));
        getRoom("room3e").addExit(Direction.NORTH, getRoom("room3d"));
        getRoom("room3e").addExit(Direction.EAST,  getRoom("room4e"));

        getRoom("room4e").addExit(Direction.WEST,  getRoom("room3e"));
        getRoom("room4e").addExit(Direction.NORTH, getRoom("room4d"));
        getRoom("room4e").addExit(Direction.EAST,  getRoom("room5e"));

        getRoom("room5e").addExit(Direction.WEST,  getRoom("room4e"));
        getRoom("room5e").addExit(Direction.NORTH, getRoom("room5d"));
        getRoom("room5e").addExit(Direction.EAST,  getRoom("room6e"));

        getRoom("room6e").addExit(Direction.WEST,  getRoom("room5e"));
        getRoom("room6e").addExit(Direction.NORTH, getRoom("room6d"));
        getRoom("room6e").addExit(Direction.EAST,  getRoom("room7e"));

        getRoom("room7e").addExit(Direction.WEST,  getRoom("room6e"));
        getRoom("room7e").addExit(Direction.NORTH, getRoom("room7d"));
        getRoom("room7e").addExit(Direction.EAST,  getRoom("room8e"));

        getRoom("room8e").addExit(Direction.WEST,  getRoom("room7e"));
        getRoom("room8e").addExit(Direction.NORTH, getRoom("room8d"));
        getRoom("room8e").addExit(Direction.EAST,  getRoom("room9e"));

        getRoom("room9e").addExit(Direction.WEST,  getRoom("room8e"));
        getRoom("room9e").addExit(Direction.NORTH, getRoom("room9d"));
    }

    public void populateRooms() {
        Weapon rock = new Weapon("Rock", 1, 1, "A rock", 5, 0);
        Item stick = new Item("Stick", 1, 1, "Stick");
        Item steel = new Item("Steel", 1, 2, "A piece of steel");
        rock.setAbility(new IAbility() {
            public void activate(EntityLiving entityLiving) {
                boolean full = entityLiving.getHP() == entityLiving.getMaxHP();
                entityLiving.setMaxHP(entityLiving.getMaxHP() + 20);
                if (full)
                    entityLiving.setHP(entityLiving.getMaxHP());
                Zork.getInstance().println("Your max HP has been increased by 20!");
            }
        });
        ArrayList<Item> ingredients = new ArrayList<>(); //ArrayList contains the list of required ingredients
        ingredients.add(rock); //Adding the rock as an ingredient
        ingredients.add(stick); //Adding the stick as an ingredient
        Weapon itemSpear = new Weapon("Spear", 10, 2, "A basic spear", 10, 0); //A new item, will be used as the product of the recipe
        Recipe spear = new Recipe(ingredients, itemSpear); //The recipe itself, with the ingredients and the product item as the arguments/
        recipes.put("spear", spear); //Register the recipe in the game. "arrow" is what the user will type after "Craft" to craft an arrow.

        getRoom("room0").addItem(rock);
        getRoom("room0").addItem(stick);
        getRoom("room0").addItem(steel);
        getRoom("room1a").addItem(steel);

        Enemy bear = new Enemy("Bear", 45, "Entering the edge of the wooded area you hear a crack and a bear appears from the bushes. Uh he looks hungry..");
        bear.setBaseDamage(20);

        getRoom("room9a").addCharacter(bear);

        Enemy dragon = new Enemy("Dragon", 100, "Fin boss");
        dragon.setBaseDamage(35);

        getRoom("room8d").addCharacter(dragon);

        Enemy assassin = new Enemy("Assassin", 20, "Lookout you gon get stabbed");

        Enemy alien = new Enemy("Alien", 50, "");

        Enemy lakeMonster = new Enemy("Lake Monster", 65, "");

        Enemy giantInsect = new Enemy("Giant Insect", 40, "");


        Enemy oldMan = new Enemy("Old Man", 4, "I've been here for 20 years trying to escape, I've gotten super close.\nThat guard dropped a paperclip the other day!");

        Item lockPick = new Item("Lock Pick", 0, 1, "Picks locks");
        oldMan.addDeathDrop(lockPick);
        getRoom("room0").addCharacter(oldMan);
        getRoom("room1a").setRequiredItem(lockPick);
        Enemy guard = new Enemy("Guard", 10, "Hey, how did you get out?!?");
        Weapon baton = new Weapon("Baton", 100, 5, "Security Baton", 3, 0);
        guard.getInventory().equip(InventorySlotType.RIGHT_HAND, baton);
        getRoom("room1a").addCharacter(guard);

        Enemy bandit = new Enemy("Bandit", 20, "Huh? There you are!");
        Weapon dagger = new Weapon("Dagger", 20, 10, "A basic dagger", 15, 0);
        Food porkChop = new Food("Porkchop", 1, 20, "Heals 20 HP");
        bandit.getInventory().equip(InventorySlotType.RIGHT_HAND, dagger);
        bandit.addDeathDrop(porkChop);

        getRoom("room5c").addCharacter(bandit);

        Enemy banditchief = new Enemy("Bandit Chief", 35, "Ha You stand no chance, prepare to die!");
        Weapon warhammer = new Weapon("War Hammer", 20, 20, "A diabolical instrument of death", 25, 0);
        banditchief.getInventory().equip(InventorySlotType.RIGHT_HAND, warhammer);
        Food magicApple = new Food("Magic Apple", 1, 50, "Heals 50 HP");
        banditchief.addDeathDrop(magicApple);

        getRoom("room5c").addCharacter(banditchief);

        OnEnterRoomListener kill = new OnEnterRoomListener() {
            public void onEnter(Player player) {
                player.setHP(0);
                player.onDeath(null);
            }
        };

        getRoom("room1e").setOnEnterRoomListener(kill);
        getRoom("room7e").setOnEnterRoomListener(kill);
        getRoom("room7c").setOnEnterRoomListener(kill);
        getRoom("room1c").setOnEnterRoomListener(kill);

        getRoom("room5c").setOnEnterRoomListener(new OnEnterRoomListener() {
            public void onEnter(Player player) {
                if (getRoom("room5c").hasCharacter("Bandit")) { //If the character is in the room (if it hasn't been killed yet)
                    getRoom("room5c").getCharacter("Bandit").attack(player); //attack the player
                }
                if (getRoom("room5c").hasCharacter("Bandit Chief")) { //If the character is in the room (if it hasn't been killed yet)
                    getRoom("room5c").getCharacter("Bandit Chief").attack(player); //attack the player
                }
            }
        });

        getRoom("room1a").setOnEnterRoomListener(new OnEnterRoomListener() {
            public void onEnter(Player player) {
                if (getRoom("room1a").hasCharacter("Guard"))
                    getRoom("room1a").getCharacter("Guard").attack(player);
            }
        });

    }

    public void println(){
        println("");
    }

    public void debug(Object o){
        println("[DEBUG] " + o);
    }

    public void save(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savegame.svg"));
            out.writeObject(this);
            out.close();
            println("Saved game!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
