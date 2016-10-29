package com.stardust.tool;

/**
 * Created by Stardust on 2016/7/7.
 */
public abstract class MicroScriptHost {

    public void execute(String commands) {
        String[] commandsArray = commands.split(";");
        for (String command : commandsArray) {
            executeInternal(command.trim().split(" "));
        }
    }

    public void executeInternal(String[] command) {
        for (int i = 0; i < command.length; i++) {
            command[i] = command[i].trim();
        }
        execute(command);
    }

    public abstract void execute(String[] command);
}
