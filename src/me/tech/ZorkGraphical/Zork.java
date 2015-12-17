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
    private static Zork instance;
    //Create rooms here
    public Room room0 = new Room("Room0", Lang.r0);
    public Room room1a = new Room("Room1a", Lang.r1a);
    public Room room1b = new Room("Room1b", Lang.r1b);
    public Room room2a = new Room("Room2a", Lang.r2a);
    public Room room2b = new Room("Room2b", Lang.r2b);
    public Room room3a = new Room("Room3a", Lang.r3a);
    public Room room3b = new Room("Room3b", Lang.r3b);
    public Room room1c = new Room("Room1c", Lang.r1c);
    public Room room2c = new Room("Room2c", Lang.r2c);
    public Room room3c = new Room("Room3c", Lang.r3c);
    public Room room1d = new Room("Room1d", Lang.r1d);
    public Room room2d = new Room("Room2d", Lang.r2d);
    public Room room3d = new Room("Room3d", Lang.r3d);
    public Room room1e = new Room("Room1e", Lang.r1e);
    public Room room2e = new Room("Room2e", Lang.r2e);
    public Room room3e = new Room("Room3e", Lang.r3e);
    public Room room4a = new Room("Room4a", Lang.r4a);
    public Room room4b = new Room("Room4b", Lang.r4b);
    public Room room4c = new Room("Room4c", Lang.r4c);
    public Room room4d = new Room("Room4d", Lang.r4d);
    public Room room4e = new Room("Room4e", Lang.r4e);
    public Room room5a = new Room("Room5a", Lang.r5a);
    public Room room5b = new Room("Room5b", Lang.r5b);
    public Room room5c = new Room("Room5c", Lang.r5c);
    public Room room5d = new Room("Room5d", Lang.r5d);
    public Room room5e = new Room("Room5e", Lang.r5e);
    public Room room6a = new Room("Room6a", Lang.r6a);
    public Room room6b = new Room("Room6b", Lang.r6b);
    public Room room6c = new Room("Room6c", Lang.r6c);
    public Room room6d = new Room("Room6d", Lang.r6d);
    public Room room6e = new Room("Room6e", Lang.r6e);
    public Room room7a = new Room("Room7a", Lang.r7a);
    public Room room7b = new Room("Room7b", Lang.r7b);
    public Room room7c = new Room("Room7c", Lang.r7c);
    public Room room7d = new Room("Room7d", Lang.r7d);
    public Room room7e = new Room("Room7e", Lang.r7e);
    public Room room8a = new Room("Room8a", Lang.r8a);
    public Room room8b = new Room("Room8b", Lang.r8b);
    public Room room8c = new Room("Room8c", Lang.r8c);
    public Room room8d = new Room("Room8d", Lang.r8d);
    public Room room8e = new Room("Room8e", Lang.r8e);
    public Room room9a = new Room("Room9a", Lang.r9a);
    public Room room9b = new Room("Room9b", Lang.r9b);
    public Room room9c = new Room("Room9c", Lang.r9c);
    public Room room9d = new Room("Room9d", Lang.r9d);
    public Room room9e = new Room("Room9e", Lang.r9e);
    public boolean running = false;
    public transient CommandHandler commandHandler;
    private HashMap<String, Recipe> recipes = new HashMap<>();
    private Player player;
    private transient EventExecutor events;
    private transient GUIManager guiManager;

    public transient boolean debug = true;

    public Zork(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
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
                room0 = z.room0;
                room1a = z.room1a;
                room2a = z.room2a;
                room3a = z.room3a;
                room4a = z.room4a;
                room5a = z.room5a;
                room6a = z.room6a;
                room7a = z.room7a;
                room8a = z.room8a;
                room9a = z.room9a;

                room1b = z.room1b;
                room2b = z.room2b;
                room3b = z.room3b;
                room4b = z.room4b;
                room5b = z.room5b;
                room6b = z.room6b;
                room7b = z.room7b;
                room8b = z.room8b;
                room9b = z.room9b;

                room1c = z.room1c;
                room2c = z.room2c;
                room3c = z.room3c;
                room4c = z.room4c;
                room5c = z.room5c;
                room6c = z.room6c;
                room7c = z.room7c;
                room8c = z.room8c;
                room9c = z.room9c;

                room1d = z.room1d;
                room2d = z.room2d;
                room3d = z.room3d;
                room4d = z.room4d;
                room5d = z.room5d;
                room6d = z.room6d;
                room7d = z.room7d;
                room8d = z.room8d;
                room9d = z.room9d;

                room1e = z.room1e;
                room2e = z.room2e;
                room3e = z.room3e;
                room4e = z.room4e;
                room5e = z.room5e;
                room6e = z.room6e;
                room7e = z.room7e;
                room8e = z.room8e;
                room9e = z.room9e;

                player = z.player;
                recipes = z.recipes;
                resume();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            player = new Player();
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
        player.goToRoom(room0);
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
                        Item item = player.getInventory().getItem(s);
                        item.use(player);
                        player.getInventory().removeItem(item);
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
        room0.addExit(Direction.UP, room1a);

        room1a.addExit(Direction.DOWN, room0);
        room1a.addExit(Direction.EAST, room2a);
        room1a.addExit(Direction.SOUTH, room1b);

        room2a.addExit(Direction.SOUTH, room2b);
        room2a.addExit(Direction.EAST, room3a);
        room2a.addExit(Direction.WEST, room1a);

        room3a.addExit(Direction.WEST, room2a);
        room3a.addExit(Direction.SOUTH, room3b);
        room3a.addExit(Direction.EAST, room4a);

        room4a.addExit(Direction.WEST, room3a);
        room4a.addExit(Direction.SOUTH, room4b);
        room4a.addExit(Direction.EAST, room5a);

        room5a.addExit(Direction.WEST, room4a);
        room5a.addExit(Direction.SOUTH, room5b);
        room5a.addExit(Direction.EAST, room6a);

        room6a.addExit(Direction.WEST, room5a);
        room6a.addExit(Direction.SOUTH, room6b);
        room6a.addExit(Direction.EAST, room7a);

        room7a.addExit(Direction.WEST, room6a);
        room7a.addExit(Direction.SOUTH, room7b);
        room7a.addExit(Direction.EAST, room8a);

        room8a.addExit(Direction.WEST, room7a);
        room8a.addExit(Direction.SOUTH, room8b);
        room8a.addExit(Direction.EAST, room9a);

        room9a.addExit(Direction.WEST, room8a);
        room9a.addExit(Direction.SOUTH, room9b);

        room1b.addExit(Direction.NORTH, room1a);
        room1b.addExit(Direction.EAST, room2b);
        room1b.addExit(Direction.SOUTH, room1c);

        room2b.addExit(Direction.WEST, room1b);
        room2b.addExit(Direction.NORTH, room2a);
        room2b.addExit(Direction.EAST, room3b);
        room2b.addExit(Direction.SOUTH, room2c);

        room3b.addExit(Direction.WEST, room2b);
        room3b.addExit(Direction.NORTH, room3a);
        room3b.addExit(Direction.EAST, room4b);
        room3b.addExit(Direction.SOUTH, room3c);

        room4b.addExit(Direction.WEST, room3b);
        room4b.addExit(Direction.NORTH, room4a);
        room4b.addExit(Direction.EAST, room5b);
        room4b.addExit(Direction.DOWN, room4c);

        room5b.addExit(Direction.WEST, room4b);
        room5b.addExit(Direction.NORTH, room5a);
        room5b.addExit(Direction.EAST, room6b);
        room5b.addExit(Direction.SOUTH, room5c);

        room6b.addExit(Direction.WEST, room5b);
        room6b.addExit(Direction.NORTH, room6a);
        room6b.addExit(Direction.EAST, room7b);
        room6b.addExit(Direction.SOUTH, room6c);

        room7b.addExit(Direction.WEST, room6b);
        room7b.addExit(Direction.NORTH, room7a);
        room7b.addExit(Direction.EAST, room8b);
        room7b.addExit(Direction.SOUTH, room7c);

        room8b.addExit(Direction.WEST, room7b);
        room8b.addExit(Direction.NORTH, room8a);
        room8b.addExit(Direction.EAST, room9b);
        room8b.addExit(Direction.SOUTH, room8c);

        room9b.addExit(Direction.WEST, room8b);
        room9b.addExit(Direction.NORTH, room9a);
        room9b.addExit(Direction.SOUTH, room9c);

        room1c.addExit(Direction.NORTH, room1b);
        room1c.addExit(Direction.EAST, room2c);
        room1c.addExit(Direction.SOUTH, room1d);

        room2c.addExit(Direction.WEST, room1c);
        room2c.addExit(Direction.NORTH, room2b);
        room2c.addExit(Direction.EAST, room3c);
        room2c.addExit(Direction.SOUTH, room2d);

        room3c.addExit(Direction.WEST, room2c);
        room3c.addExit(Direction.NORTH, room3b);
        room3c.addExit(Direction.EAST, room4c);
        room3c.addExit(Direction.SOUTH, room3d);

        room4c.addExit(Direction.WEST, room3c);
        room4c.addExit(Direction.NORTH, room4b);
        room4c.addExit(Direction.EAST, room5c);
        room4c.addExit(Direction.SOUTH, room4d);

        room5c.addExit(Direction.WEST, room4c);
        room5c.addExit(Direction.NORTH, room5b);
        room5c.addExit(Direction.EAST, room6c);
        room5c.addExit(Direction.SOUTH, room5d);

        room6c.addExit(Direction.WEST, room5c);
        room6c.addExit(Direction.NORTH, room6b);
        room6c.addExit(Direction.EAST, room7c);
        room6c.addExit(Direction.SOUTH, room6d);

        room7c.addExit(Direction.WEST, room6c);
        room7c.addExit(Direction.NORTH, room7b);
        room7c.addExit(Direction.EAST, room8c);
        room7c.addExit(Direction.SOUTH, room7d);

        room8c.addExit(Direction.WEST, room7c);
        room8c.addExit(Direction.NORTH, room8b);
        room8c.addExit(Direction.EAST, room9c);
        room8c.addExit(Direction.SOUTH, room8d);

        room9c.addExit(Direction.WEST, room8c);
        room9c.addExit(Direction.NORTH, room9b);
        room9c.addExit(Direction.SOUTH, room9d);

        room1d.addExit(Direction.NORTH, room1c);
        room1d.addExit(Direction.EAST, room2d);
        room1d.addExit(Direction.SOUTH, room1e);

        room2d.addExit(Direction.WEST, room1d);
        room2d.addExit(Direction.NORTH, room2c);
        room2d.addExit(Direction.EAST, room3d);
        room2d.addExit(Direction.SOUTH, room2e);

        room3d.addExit(Direction.WEST, room2d);
        room3d.addExit(Direction.NORTH, room3c);
        room3d.addExit(Direction.EAST, room4d);
        room3d.addExit(Direction.SOUTH, room3e);

        room4d.addExit(Direction.WEST, room3d);
        room4d.addExit(Direction.NORTH, room4c);
        room4d.addExit(Direction.EAST, room5d);
        room4d.addExit(Direction.SOUTH, room4e);

        room5d.addExit(Direction.WEST, room4d);
        room5d.addExit(Direction.NORTH, room5c);
        room5d.addExit(Direction.EAST, room6d);
        room5d.addExit(Direction.SOUTH, room5e);

        room6d.addExit(Direction.WEST, room5d);
        room6d.addExit(Direction.NORTH, room6c);
        room6d.addExit(Direction.EAST, room7d);
        room6d.addExit(Direction.SOUTH, room6e);

        room7d.addExit(Direction.WEST, room6d);
        room7d.addExit(Direction.NORTH, room7c);
        room7d.addExit(Direction.EAST, room8d);
        room7d.addExit(Direction.SOUTH, room7e);

        room8d.addExit(Direction.WEST, room7d);
        room8d.addExit(Direction.NORTH, room8c);
        room8d.addExit(Direction.EAST, room9d);
        room8d.addExit(Direction.SOUTH, room8e);

        room9d.addExit(Direction.WEST, room8d);
        room9d.addExit(Direction.NORTH, room9c);

        room1e.addExit(Direction.NORTH, room1d);
        room1e.addExit(Direction.EAST, room2e);

        room2e.addExit(Direction.WEST, room1e);
        room2e.addExit(Direction.NORTH, room2d);
        room2e.addExit(Direction.EAST, room3e);

        room3e.addExit(Direction.WEST, room2e);
        room3e.addExit(Direction.NORTH, room3d);
        room3e.addExit(Direction.EAST, room4e);

        room4e.addExit(Direction.WEST, room3e);
        room4e.addExit(Direction.NORTH, room4d);
        room4e.addExit(Direction.EAST, room5e);

        room5e.addExit(Direction.WEST, room4e);
        room5e.addExit(Direction.NORTH, room5d);
        room5e.addExit(Direction.EAST, room6e);

        room6e.addExit(Direction.WEST, room5e);
        room6e.addExit(Direction.NORTH, room6d);
        room6e.addExit(Direction.EAST, room7e);

        room7e.addExit(Direction.WEST, room6e);
        room7e.addExit(Direction.NORTH, room7d);
        room7e.addExit(Direction.EAST, room8e);

        room8e.addExit(Direction.WEST, room7e);
        room8e.addExit(Direction.NORTH, room8d);
        room8e.addExit(Direction.EAST, room9e);

        room9e.addExit(Direction.WEST, room8e);
        room9e.addExit(Direction.NORTH, room9d);
    }

    public void populateRooms() {
        Weapon sword = new Weapon("Divining Sword", 1000, 20, "Sword of the gods.", 10000, 20);
        List<Item> items = new ArrayList<Item>();
        items.add(sword);
        Container chest = new Container("Chest", items);
        Weapon rock = new Weapon("Rock", 1, 1, "A rock", 5, 0);
        Item stick = new Item("Stick", 1, 1, "Stick");
        rock.setAbility(new IAbility() {
            public void activate(EntityLiving entityLiving) {
                boolean full = entityLiving.getHP() == entityLiving.getMaxHP();
                entityLiving.setMaxHP(entityLiving.getMaxHP() + 20);
                if (full)
                    entityLiving.setHP(entityLiving.getMaxHP());
                Zork.getInstance().println("Your max HP has been increased by 20!");
            }
        });
        ArrayList<Item> ingredients = new ArrayList<Item>(); //ArrayList contains the list of required ingredients
        ingredients.add(rock); //Adding the rock as an ingredient
        ingredients.add(stick); //Adding the stick as an ingredient
        Weapon itemSpear = new Weapon("Spear", 10, 2, "A basic spear", 10, 0); //A new item, will be used as the product of the recipe
        Recipe spear = new Recipe(ingredients, itemSpear); //The recipe itself, with the ingredients and the product item as the arguments/
        recipes.put("spear", spear); //Register the recipe in the game. "arrow" is what the user will type after "Craft" to craft an arrow.

        room0.addItem(rock);
        room0.addItem(stick);
        room1a.addItem(chest);

        Enemy bear = new Enemy("Bear", 45, "Entering the edge of the wooded area you hear a crack and a bear appears from the bushes. Uh he looks hungry..");
        bear.setBaseDamage(20);

        room9a.addCharacter(bear);

        Enemy dragon = new Enemy("Dragon", 100, "Fin boss");
        dragon.setBaseDamage(35);

        room8d.addCharacter(dragon);

        Enemy assassin = new Enemy("Assassin", 20, "Lookout you gon get stabbed");

        Enemy alien = new Enemy("Alien", 50, "");

        Enemy lakeMonster = new Enemy("Lake Monster", 65, "");

        Enemy giantInsect = new Enemy("Giant Insect", 40, "");


        Enemy oldMan = new Enemy("Old Man", 4, "I've been here for 20 years trying to escape, I've gotten super close.\nThat guard dropped a paperclip the other day!");

        Item lockPick = new Item("Lock Pick", 0, 1, "Picks locks");
        oldMan.addDeathDrop(lockPick);
        room0.addCharacter(oldMan);
        room1a.setRequiredItem(lockPick);
        Enemy guard = new Enemy("Guard", 10, "Hey, how did you get out?!?");
        Weapon baton = new Weapon("Baton", 100, 5, "Security Baton", 3, 0);
        guard.getInventory().equip(InventorySlotType.RIGHT_HAND, baton);
        room1a.addCharacter(guard);

        Enemy bandit = new Enemy("Bandit", 20, "Huh? There you are!");
        Weapon dagger = new Weapon("Dagger", 20, 10, "A basic dagger", 15, 0);
        Food porkChop = new Food("Porkchop", 1, 20, "Heals 20 HP");
        bandit.getInventory().equip(InventorySlotType.RIGHT_HAND, dagger);
        bandit.addDeathDrop(porkChop);

        room5c.addCharacter(bandit);

        Enemy banditchief = new Enemy("Bandit Chief", 35, "Ha You stand no chance, prepare to die!");
        Weapon warhammer = new Weapon("War Hammer", 20, 20, "A diabolical instrument of death", 25, 0);
        banditchief.getInventory().equip(InventorySlotType.RIGHT_HAND, warhammer);
        Food magicApple = new Food("Magic Apple", 1, 50, "Heals 50 HP");
        banditchief.addDeathDrop(magicApple);

        room5c.addCharacter(banditchief);

        OnEnterRoomListener kill = new OnEnterRoomListener() {
            public void onEnter(Player player) {
                player.setHP(0);
                player.onDeath(null);
            }
        };

        room1e.setOnEnterRoomListener(kill);
        room7e.setOnEnterRoomListener(kill);
        room7c.setOnEnterRoomListener(kill);
        room1c.setOnEnterRoomListener(kill);

        room5c.setOnEnterRoomListener(new OnEnterRoomListener() {
            public void onEnter(Player player) {
                if (room5c.hasCharacter("Bandit")) { //If the character is in the room (if it hasn't been killed yet)
                    room5c.getCharacter("Bandit").attack(player); //attack the player
                }
                if (room5c.hasCharacter("Bandit Chief")) { //If the character is in the room (if it hasn't been killed yet)
                    room5c.getCharacter("Bandit Chief").attack(player); //attack the player
                }
            }
        });

        room1a.setOnEnterRoomListener(new OnEnterRoomListener() {
            public void onEnter(Player player) {
                if (room1a.hasCharacter("Guard"))
                    room1a.getCharacter("Guard").attack(player);
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
