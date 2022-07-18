package ru.nsu.scheboltasova.logoworld.exceptions;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class WrongName extends Exception{
    /**
     * If command can't be found in property file
     */
    private static final Logger log = Logger.getLogger(WrongName.class.getName());

    public WrongName() {
        super("Wrong type of command!");
        log.setLevel(Level.DEBUG);
        log.debug("Exception WrongName was thrown");
    }
}
