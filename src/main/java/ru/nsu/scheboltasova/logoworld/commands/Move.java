package ru.nsu.scheboltasova.logoworld.commands;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.FieldParameters;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;

import java.util.Arrays;

/**
 * Class Move moves the AI in the direction by the specified number of steps. The field is considered toroidal.
 */

public class Move implements Command {
    private static final Logger log = Logger.getLogger(Move.class.getName());

    /**
     * If DRAW was called before, line/lines are drawn on the graphic field
     * There is only line, if movement is not looped. There are line and line1, if movement is looped
     * @param line- line, which is needed to be drawn, if command MOVE was selected (after DRAW)
     * @param line1 - line, which is needed to be drawn, if command MOVE was selected (after DRAW)
     * @param param - instance of the class responsible for the graphical part of the program
     */
    public void setLine(Line line, Line line1, FieldParameters param) {
        line.setStrokeWidth(5);
        line.setStroke(Color.DARKGREEN);
        param.field.getChildren().removeAll(param.textField, param.button);

        if(line1 != null) {
            log.debug("Lines were set");
            line1.setStrokeWidth(5);
            line1.setStroke(Color.DARKGREEN);
            param.field.getChildren().addAll(line,line1, param.textField, param.button);
        }
        else {
            log.debug("Line was set");
            param.field.getChildren().addAll(line, param.textField, param.button);
        }
    }


    /**
     * Method to set AI in valid coordinates in order to entered direction and coordinates
     * @param commandLine - user-entered command with arguments. Should include: MOVE, [L|R|U|D], [steps]
     * @param param - instance of the class responsible for the graphical part of the program
     */
    @Override
    public void makeCommand(String[] commandLine, FieldParameters param) throws WrongParam {
        log.setLevel(Level.DEBUG);

        if (commandLine.length == 3 && Integer.parseInt(commandLine[2]) >= 0) {
            final double height = Double.parseDouble(param.height);
            final double width = Double.parseDouble(param.width);
            final int steps = Integer.parseInt(commandLine[2]);
            String direction = commandLine[1];
            Line line, line1 = null;

            switch (direction) {
                case "L" -> {
                    log.debug(Arrays.toString(commandLine) + " done");

                    final double start = param.paint.getX();
                    final double end = param.paint.getX() - steps;
                    if (end >= 0)
                        param.paint.setX(end);
                    else
                        param.paint.setX(width + end % width);

                    if (param.draw) {
                        if (end >= 0)
                            line = new Line(start, param.paint.getY() + 30, end, param.paint.getY() + 30);
                        else {
                            line = new Line(start, param.paint.getY() + 30, 0, param.paint.getY() + 30);
                            line1 = new Line(width, param.paint.getY() + 30, width + end, param.paint.getY() + 30);
                        }
                        setLine(line, line1, param);
                    }


                }
                case "R" -> {
                    log.debug(Arrays.toString(commandLine) + " done");

                    final double start = param.paint.getX();
                    final double end = param.paint.getX() + steps;

                    if (end < width)
                        param.paint.setX(end);
                    else
                        param.paint.setX(end % width);

                    if (param.draw) {
                        if (end < width)
                            line = new Line(start, param.paint.getY() + 30, end, param.paint.getY() + 30);
                        else {
                            line = new Line(start, param.paint.getY() + 30, width, param.paint.getY() + 30);
                            line1 = new Line(0, param.paint.getY() + 30, end - width, param.paint.getY() + 30);
                        }
                        setLine(line, line1, param);
                    }
                }
                case "U" -> {
                    log.debug(Arrays.toString(commandLine) + " done");

                    final double start = param.paint.getY();
                    final double end = param.paint.getY() - steps;

                    if (end >= 0)
                        param.paint.setY(end);
                    else
                        param.paint.setY(height + end % height);

                    if (param.draw) {
                        if(end >= 0)
                            line = new Line(param.paint.getX(), start + 30, param.paint.getX(), end + 30);
                        else {
                            line = new Line(param.paint.getX(), start + 30, param.paint.getX(), 0);
                            line1 = new Line(param.paint.getX(), height, param.paint.getX(), height + end + 30);
                        }
                        setLine(line, line1, param);
                    }
                }
                case "D" -> {
                    log.debug(Arrays.toString(commandLine) + " done");

                    final double start = param.paint.getY();
                    final double end = param.paint.getY() + steps;

                    if (end < height)
                        param.paint.setY(end);
                    else
                        param.paint.setY(end % height);

                    if (param.draw) {
                        if(end < height)
                            line = new Line(param.paint.getX(), start + 30, param.paint.getX(), end + 30);

                        else {
                            line = new Line(param.paint.getX(), start + 30, param.paint.getX(), height);
                            line1 = new Line(param.paint.getX(), 0, param.paint.getX(), end - height + 30);
                        }
                        setLine(line, line1, param);
                    }
                }
                default  -> {
                    log.error("MOVE failed");
                    throw new WrongParam("MOVE");
                }
            }
        }
        else {
            log.error("MOVE failed");
            throw new WrongParam("MOVE");
        }
    }
}
