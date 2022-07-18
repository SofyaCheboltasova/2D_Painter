package ru.nsu.scheboltasova.logoworld.exceptions;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class WrongParam extends Exception{
    /**
     * If command parameters are wrong
     * @param commandName - user-entered command with arguments
     */
    private static final Logger log = Logger.getLogger(WrongParam.class.getName());

    public WrongParam(String commandName) {
        super("Wrong data in " + commandName);
        log.setLevel(Level.DEBUG);
        log.debug("Exception WrongParam was thrown");
    }
}
