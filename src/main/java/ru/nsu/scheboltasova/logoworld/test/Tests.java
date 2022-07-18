package ru.nsu.scheboltasova.logoworld.test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import javafx.embed.swing.JFXPanel;
import ru.nsu.scheboltasova.logoworld.CommandHandler;
import ru.nsu.scheboltasova.logoworld.CommandsFactory;
import ru.nsu.scheboltasova.logoworld.commands.Command;
import ru.nsu.scheboltasova.logoworld.FieldParameters;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongName;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;
import java.io.IOException;
import static org.junit.Assert.*;

public class Tests {
    Command commandDraw;
    Command commandWard;
    Command commandTeleport;
    Command commandInit;
    Command commandMove;
    FieldParameters param;
    CommandsFactory factory;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() throws Exception {
        JFXPanel panel = new JFXPanel();
        factory = new CommandsFactory();
        commandDraw = factory.createCommand("DRAW");
        commandWard = factory.createCommand("WARD");
        commandTeleport = factory.createCommand("TELEPORT");
        commandMove = factory.createCommand("MOVE");
        commandInit = factory.createCommand("INIT");
        param = new FieldParameters("500", "500");

        commandInit.makeCommand("INIT 500 500 200 200".split(" "), param);
    }

    @Test
    public void CommandHandler_ValidFactory() throws IOException, WrongParam, WrongName {
        CommandHandler handler = new CommandHandler("INIT 300 300 100 100".split(" "));
        assertNotNull(handler.factory);
    }

    @Test
    public void CommandHandler_ValidParams() throws IOException, WrongParam, WrongName {
        CommandHandler handler = new CommandHandler("INIT 300 300 100 100".split(" "));
        assertNotNull(handler.param);
    }

    @Test
    public void CommandHandler_InvalidConstrParams() throws IOException {
        try {
            CommandHandler params = new CommandHandler("INIT -300 300 100 100".split(" "));
        }
        catch (WrongParam | WrongName e) {
            assertEquals("Wrong data in INIT", e.getMessage());
        }
    }

    @Test
    public void CommandHandler_InvalidConstrName() throws IOException {
        try {
            CommandHandler params = new CommandHandler("INIR 300 300 -100 -100".split(" "));
        }
        catch (WrongParam | WrongName e) {
            assertEquals("Wrong type of command!", e.getMessage());
        }
    }

    @Test
    public void Factory_CreateCommand_Invalid() throws IOException {
        try {
            CommandsFactory factory1 = new CommandsFactory();
            Command command1 = factory1.createCommand("INIR");
        }
        catch (WrongName e) {
            assertEquals("Wrong type of command!", e.getMessage());
        }
    }

    @Test
    public void Factory_CreateCommand_Valid() throws IOException, WrongName {
        CommandsFactory factory1 = new CommandsFactory();
        Command command1 = factory1.createCommand("DRAW");
        assertNotNull(factory1.usedCommands.get("DRAW"));
    }


    @Test
    public void FieldParameters_ValidConstructor() throws IOException, WrongParam {
        FieldParameters params = new FieldParameters("300", "500");
        assertArrayEquals(new double[]{300, 500}, new double[]{params.scene.getWidth(), params.scene.getHeight()}, 0.0);
    }

    @Test
    public void FieldParameters_InvalidConstructor() throws IOException {
        try {
            FieldParameters params = new FieldParameters("-300", "-500");
        }
        catch (WrongParam e) {
            assertEquals("Wrong data in INIT", e.getMessage());
        }
    }

    @Test
    public void Init_ValidCall_Positive() throws WrongParam {
        commandInit.makeCommand("INIT 500 500 100 100".split(" "), param);
        assertArrayEquals(new double[]{100, 130}, new double[]{param.paint.getX(), param.paint.getY()}, 0.0);
    }

    @Test
    public void Init_ValidCall_Negative() throws WrongParam {
        commandInit.makeCommand("INIT 500 500 -100 -234".split(" "), param);
        assertArrayEquals(new double[]{400, 296}, new double[]{param.paint.getX(), param.paint.getY()}, 0.0);
    }

    @Test
    public void Init_ValidCall_BigPos() throws WrongParam {
        commandInit.makeCommand("INIT 500 500 1000 1234".split(" "), param);
        assertArrayEquals(new double[]{0, 264}, new double[]{param.paint.getX(), param.paint.getY()}, 0.0);
    }

    @Test
    public void Init_ValidCall_BigNeg() throws WrongParam {
        commandInit.makeCommand("INIT 500 500 -1000 -1234".split(" "), param);
        assertArrayEquals(new double[]{0, 296}, new double[]{param.paint.getX(), param.paint.getY()}, 0.0);
    }

    @Test
    public void Init_InvalidCall_WrongParams() {
        try {
            commandInit.makeCommand("INIT 1 -100 0 0".split(" "), param);
        } catch (WrongParam e) {
            assertEquals("Wrong data in INIT", e.getMessage());
        }
    }

    @Test
    public void Draw_ValidCall() throws WrongParam {
        commandDraw.makeCommand("DRAW".split(" "), param);
        assertEquals(true, param.draw);
    }

    @Test
    public void Draw_InvalidCall_WrongParams() {
        try {
            commandDraw.makeCommand("DRAW 1".split(" "), param);
        } catch (WrongParam e) {
            assertEquals("Wrong data in DRAW", e.getMessage());
        }
    }

    @Test
    public void Ward_ValidCall() throws WrongParam {
        commandWard.makeCommand("WARD".split(" "), param);
        assertEquals(false, param.draw);
    }

    @Test
    public void Ward_InvalidCall_WrongParams() {
        try {
            commandWard.makeCommand("WARD 1".split(" "), param);
        } catch (WrongParam e) {
            assertEquals("Wrong data in WARD", e.getMessage());
        }
    }

    @Test
    public void Teleport_ValidCall_Positive() throws WrongParam {
        commandTeleport.makeCommand("TELEPORT 100 100".split(" "), param);
        assertArrayEquals(new double[]{100, 130}, new double[]{param.paint.getX(), param.paint.getY()}, 0.0);
    }

    @Test
    public void Teleport_ValidCall_Negative() throws WrongParam {
        commandTeleport.makeCommand("TELEPORT -100 -100".split(" "), param);
        assertArrayEquals(new double[]{400, 430}, new double[]{param.paint.getX(), param.paint.getY()}, 0.0);
    }

    @Test
    public void Teleport_ValidCall_BigPos() throws WrongParam {
        commandTeleport.makeCommand("TELEPORT 1234 1000".split(" "), param);
        assertArrayEquals(new double[]{234, 30}, new double[]{param.paint.getX(), param.paint.getY()}, 0.0);
    }

    @Test
    public void Teleport_ValidCall_BigNeg() throws WrongParam {
        commandTeleport.makeCommand("TELEPORT -1234 -1000".split(" "), param);
        assertArrayEquals(new double[]{266, 30}, new double[]{param.paint.getX(), param.paint.getY()}, 0.0);
    }

    @Test
    public void Teleport_InvalidCall(){
        try {
            commandTeleport.makeCommand("TELEPORT".split(" "), param);
        }
        catch (WrongParam e) {
            assertEquals("Wrong data in TELEPORT", e.getMessage());
        }
    }

    @Test
    public void Move_ValidCall_LSmall() throws WrongParam {
        commandMove.makeCommand("MOVE L 100".split(" "), param);
        assertEquals(100, param.paint.getX(), 0.0);
    }
    @Test
    public void Move_ValidCall_LBig() throws WrongParam {
        commandMove.makeCommand("MOVE L 1234".split(" "), param);
        assertEquals(466, param.paint.getX(), 0.0);
    }

    @Test
    public void Move_ValidCall_DSmall() throws WrongParam {
        commandMove.makeCommand("MOVE D 100".split(" "), param);
        assertEquals(330, param.paint.getY(), 0.0);
    }

    @Test
    public void Move_ValidCall_DBig() throws WrongParam {
        commandMove.makeCommand("MOVE D 1234".split(" "), param);
        assertEquals(464, param.paint.getY(), 0.0);
    }

    @Test
    public void Move_ValidCall_RSmall() throws WrongParam {
        commandMove.makeCommand("MOVE R 100".split(" "), param);
        assertEquals(300, param.paint.getX(), 0.0);
    }
    @Test
    public void Move_ValidCall_RBig() throws WrongParam {
        commandMove.makeCommand("MOVE R 1234".split(" "), param);
        assertEquals(434, param.paint.getX(), 0.0);
    }

    @Test
    public void Move_ValidCall_USmall() throws WrongParam {
        commandMove.makeCommand("MOVE U 100".split(" "), param);
        assertEquals(130, param.paint.getY(), 0.0);
    }

    @Test
    public void Move_ValidCall_UBig() throws WrongParam {
        commandMove.makeCommand("MOVE U 1234".split(" "), param);
        assertEquals(496, param.paint.getY(), 0.0);
    }

    @Test
    public void Move_InvalidCall_WrongParams(){
        try {
            commandMove.makeCommand("MOVE K 100".split(" "), param);
        }
        catch (WrongParam e) {
            assertEquals("Wrong data in MOVE", e.getMessage());
        }
    }
}
