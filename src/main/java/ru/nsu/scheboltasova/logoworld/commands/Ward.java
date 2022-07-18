package ru.nsu.scheboltasova.logoworld.commands;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.FieldParameters;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;

public class Ward implements Command{
    /**
     * Class Ward returns Abstract Interpreter out of drawing state
     * @param commandLine - user-entered command with arguments. Should include: WARD
     * @param param - instance of the class responsible for the graphical part of the program
     */
    private static final Logger log = Logger.getLogger(Ward.class.getName());

    @Override
    public void makeCommand(String[] commandLine, FieldParameters param) throws WrongParam {
        log.setLevel(Level.DEBUG);

        if(commandLine.length == 1) {
            log.debug("WARD done");
            param.draw = false;
        }
        else {
            log.error("WARD failed");
            throw new WrongParam("WARD");
        }
    }
}
