package ru.nsu.scheboltasova.logoworld.commands;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.FieldParameters;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;

public class Draw implements Command{
    /**
     * Class Ward turns Abstract Interpreter into drawing state
     * @param commandLine - user-entered command with arguments. Should include: DRAW
     * @param param - instance of the class responsible for the graphical part of the program
     */
    private static final Logger log = Logger.getLogger(Draw.class.getName());

    @Override
    public void makeCommand(String[] commandLine, FieldParameters param) throws WrongParam {
        log.setLevel(Level.DEBUG);

        if(commandLine.length == 1) {
            log.debug("DRAW done");
            param.draw = true;
        }
        else{
            log.error("DRAW failed");
            throw new WrongParam("DRAW");
        }
    }
}
