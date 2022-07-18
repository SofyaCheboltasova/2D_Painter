package ru.nsu.scheboltasova.logoworld.commands;
import ru.nsu.scheboltasova.logoworld.FieldParameters;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;

/**
 * Interface Command has a method, which is overrides by other classes
 */
public interface Command {
     void makeCommand(String[] commandLine, FieldParameters f) throws WrongParam;
}
