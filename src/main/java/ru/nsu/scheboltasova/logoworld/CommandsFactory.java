package ru.nsu.scheboltasova.logoworld;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.commands.Command;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongName;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class CommandsFactory {
    public final HashMap<String, Command> usedCommands = new HashMap<>();
    private final Properties property = new Properties();
    private static final Logger log = Logger.getLogger(CommandsFactory.class.getName());

    /**
     * Factory is configured at the time of its creation by a file with command names and class names
     */
    public CommandsFactory() throws IOException {
        log.setLevel(Level.DEBUG);
        log.debug("Factory configuration");

        final InputStream file =
                ClassLoader.getSystemResourceAsStream("ru/nsu/scheboltasova/logoworld/commands.properties");

        if(file == null){
            log.error("Factory configuration failed");
        }
        property.load(file);
        assert file != null;
        file.close();
    }


    /**
     * Method finds the command name in map "usedCommands" and returns instance. Otherwise, creates a new class
     * @param commandName - user-entered command with arguments. It is checked for compliance
     */

    public Command createCommand(String commandName) throws WrongName {
        log.setLevel(Level.DEBUG);
        log.debug("Getting instance of " + commandName);

        if (property.get(commandName) == null) {
            log.error("Wrong command was entered");
            throw new WrongName();
        }

        if (usedCommands.get(commandName) == null) {
            log.debug("Creating " + commandName);

            final String path = (String)property.get(commandName);

            try {
                final Class<?> newCommand = Class.forName(path);
                final Command command = (Command)newCommand.getConstructor().newInstance();
                usedCommands.put(commandName, command);
                return command;
            }
            catch (InstantiationException | ClassNotFoundException| IllegalAccessException
                    | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        else
            return usedCommands.get(commandName);
        log.debug(commandName + " created");

        return null;
    }
}
