package ru.nsu.scheboltasova.logoworld;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongName;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;
import java.io.IOException;


/**
 * Creates the first window and gets command INIT
 */
public final class StartWindow extends Application {
    private final Text text = new Text("Type a command:");
    private final Button btn = new Button("Enter");
    private final Label lbl = new Label();
    private final TextField textField = new TextField();

    private static final Logger log = Logger.getLogger(StartWindow.class.getName());
    @Override
    public void start(final Stage stage)  {
        log.setLevel(Level.DEBUG);
        log.debug("Launching application");

        textField.setPrefColumnCount(16);

        btn.setOnAction(event -> {
            final String[] commandLine = textField.getText().split(" ");

            if(commandLine.length == 5 && commandLine[0].equals("INIT")
                    && Double.parseDouble(commandLine[1]) >= 250 && Double.parseDouble(commandLine[2]) >= 250) {

                try {
                    CommandHandler commandHandler = new CommandHandler(commandLine);
                    commandHandler.start();
                    stage.close();
                }
                catch (IOException | WrongParam | WrongName e) {
                    e.printStackTrace();
                    log.error("Application failed");
                }
            }
            else {
                lbl.setText("Wrong data in INIT");
                log.error("Wrong data in INIT");
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 20, 20, text, textField, btn, lbl);
        final Scene scene = new Scene(root, 194, 150);
        stage.setScene(scene);
        stage.show();
    }
}
