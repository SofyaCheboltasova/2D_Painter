package ru.nsu.scheboltasova.logoworld.commands;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.FieldParameters;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;

import java.util.Arrays;

/**
 * Class Teleport puts Abstract Interpreter in given coordinates of the field.
 * @see Command
 */
public class Teleport implements Command{

    /**
     * Method to set AI to received X and Y
     * @param commandLine - user-entered command with arguments. It is checked for compliance
     *                      Should include: TELEPORT, X, Y
     * @param param - instance of the class responsible for the graphical part of the program
     * @see FieldParameters#paint - the image of AI
     */
    private static final Logger log = Logger.getLogger(Teleport.class.getName());

    @Override
    public void makeCommand(String[] commandLine, FieldParameters param) throws WrongParam {
        log.setLevel(Level.DEBUG);

        if (commandLine.length == 3) {
            double shiftX = Double.parseDouble(commandLine[1]);
            double shiftY = Double.parseDouble(commandLine[2]) + 30;
            final double width = Double.parseDouble(param.width);
            final double height = Double.parseDouble(param.height);

            if(shiftX < 0 && -shiftX % width == 0){
                shiftX = 0;
            }
            if(shiftY < 0 && -shiftY % height == 0){
                shiftY = 0;
            }

            if (shiftX >= 0)
                param.paint.setX(shiftX % width);
            else
                param.paint.setX(width + shiftX % width);

            if (shiftY >= 0)
                param.paint.setY(shiftY % height);
            else
                param.paint.setY(height + shiftY % height);

            log.debug(Arrays.toString(commandLine) + " done");
        }
        else {
            log.error("TELEPORT failed");
            throw new WrongParam("TELEPORT");
        }
    }
}
