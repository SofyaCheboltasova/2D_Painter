package ru.nsu.scheboltasova.logoworld;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.commands.Command;
import ru.nsu.scheboltasova.logoworld.commands.Draw;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongName;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;

import java.io.IOException;

/**
 * Class CommandHandler handles all actions on command classes
 */
public class CommandHandler  {
    private static final Logger log = Logger.getLogger(CommandHandler.class.getName());
    public final CommandsFactory factory;
    public final FieldParameters param;
    private Command command;


    /**
     * Constructor creates factory, class instance of INIT, graphic field then launches INIT.
     * @param commandLine - user-entered command with arguments. Should include: INIT,width, height, X, Y
     */
    public CommandHandler(String[] commandLine) throws IOException, WrongParam, WrongName {
        log.setLevel(Level.DEBUG);
        log.debug("Creating CommandHandler");

        final String commandName = commandLine[0];
        final String width = commandLine[1];
        final String height = commandLine[2];

        factory = new CommandsFactory();
        command = factory.createCommand(commandName);
        param  = new FieldParameters(width, height);
        command.makeCommand(commandLine, param);
    }


    /**
     * Method to handle clicks on button in opened window and launch entered commands.
     */
    public void start() {
        log.setLevel(Level.DEBUG);

        param.button.setOnAction(event -> {
            log.debug("Button pressed");

            final String[] enteredCommand = param.textField.getText().split(" ");
            try {
                log.debug(enteredCommand[0] + " was entered");
                command = factory.createCommand(enteredCommand[0]);
                command.makeCommand(enteredCommand, param);
            }
            catch (WrongName | WrongParam e) {
                log.error("Creating instance of " + enteredCommand[0] + " failed");
                e.printStackTrace();
                param.excepWindow(e);
            }
        });
    }
}
