package me.tech.ZorkGraphical.commands;


import me.tech.ZorkGraphical.Zork;

import java.io.Serializable;
import java.util.HashMap;

public class CommandHandler {
    private Zork game;
    private HashMap<String, Command> commands;
    public HashMap<String, String> descriptions;

    public CommandHandler(Zork zork) {
        commands = new HashMap<String, Command>();
        descriptions = new HashMap<String, String>();
        this.game = zork;
    }


    public void register(String command, String description, Command cmd) {
        commands.put(command, cmd);
        descriptions.put(command, description);
    }

    public String getDescription(String command){
        return descriptions.get(command);
    }

    public boolean handle(String line) {
        String[] split = line.split(" ");
        String cmd = split[0];
        String[] args = new String[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            args[i - 1] = split[i];
        }
        if (commands.containsKey(cmd)) {
            Command c = commands.get(cmd);
            return c.onCommand(cmd, args);
        } else {
            Zork.getInstance().println("Unknown command: " + cmd + "!");
            return false;
        }
    }

}
