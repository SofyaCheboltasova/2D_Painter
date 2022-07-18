package ru.nsu.scheboltasova.logoworld.commands;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.FieldParameters;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;

import java.util.Arrays;

public class Init implements Command {
    private static final Logger log = Logger.getLogger(Init.class.getName());

    /**
     * Initializes the graphic field with the given parameters by placing the AI at the specified position on the field
     * @param commandLine - user-entered command with arguments. Should include: INIT, width, height, X, Y
     * @param param - instance of the class responsible for the graphical part of the program
     */
        @Override
        public void makeCommand(String[] commandLine, FieldParameters param) throws WrongParam {
            log.setLevel(Level.DEBUG);

            if (commandLine.length == 5
                    && Double.parseDouble(commandLine[1]) >= 250 && Double.parseDouble(commandLine[2]) >= 250) {
                final double x = Double.parseDouble(commandLine[3]);
                double y = Double.parseDouble(commandLine[4]) + 30;
                final double width = Double.parseDouble(commandLine[1]);
                final double height = Double.parseDouble(commandLine[2]);

                if (param.stage != null && param.stage.isShowing())
                    param.stage.close();

                param.stage = new Stage();

                if(x >= 0 || x % width == 0)
                    param.paint.setX(x % width);
                else
                    param.paint.setX(width + x % width);

                if(y >= 0 || y % height == 0)
                    param.paint.setY(y % height);
                else
                    param.paint.setY(height + y % height);

                log.debug(Arrays.toString(commandLine) + " done");

                param.makeField(String.valueOf(width), String.valueOf(height));
                param.stage.setScene(param.scene);
                param.stage.setResizable(false);
                param.stage.show();
            }
            else {
                log.error("INIT failed");
                throw new WrongParam("INIT");
            }
        }
}
