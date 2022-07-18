package ru.nsu.scheboltasova.logoworld;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.nsu.scheboltasova.logoworld.exceptions.WrongParam;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class is responsible for graphic interface
 */
public class FieldParameters {
    private static final Logger log = Logger.getLogger(FieldParameters.class.getName());

    public Scene scene;
    public Stage stage;
    public TextField textField;
    public Button button;
    public Image image;
    public ImageView paint;
    public Group field;
    public String width;
    public String height;
    private HBox root;
    public boolean draw = false;

    /**
     * Initializes parameters for graphic interface
     * @param width - width of the window
     * @param height - height of the window
     */
    public FieldParameters(String width, String height) throws IOException, WrongParam {
        log.setLevel(Level.DEBUG);
        log.debug("Initializing a graphic field");

        button = new Button("Enter");
        textField = new TextField();
        textField.setPrefColumnCount(16);

        final FileInputStream inputstream = new FileInputStream("C:\\Users\\sofya\\IdeaProjects\\LogoWorld\\src" +
                "\\main\\java\\ru\\nsu\\scheboltasova\\logoworld\\commands\\paint.png");

        image = new Image(inputstream);
        paint = new ImageView(image);
        paint.setFitHeight(30);
        paint.setFitWidth(30);
        root = new HBox(10, this.textField, this.button);

        makeField(width, height);
        inputstream.close();
    }


    /**
     * Method creates a new scene with new parameters
     * @param w - width of the window
     * @param h - height of the window
     */
    public void makeField(String w, String h) {
        log.setLevel(Level.DEBUG);

        width = w; height = h;
        int rectSize = 30;
        field = new Group();

        for(int i = 0; i < Double.parseDouble(width) / rectSize + 1; i++) {
            for(int j = 0; j < Double.parseDouble(height) / rectSize + 1; j++) {
                Rectangle rect = new Rectangle(rectSize, rectSize);
                rect.setX(i * rectSize);
                rect.setY(j * rectSize);
                rect.setStyle("-fx-fill: #d9d9b2; -fx-stroke: #f3f3dd; -fx-stroke-width: 1;");
                field.getChildren().add(rect);
            }
        }
        root = new HBox(10, this.textField, this.button);
        field.getChildren().addAll(paint, root);
        scene = new Scene(field, Double.parseDouble(width), Double.parseDouble(height));

        log.debug("Field created");
    }


    /**
     * Method to launch a new window with error message, if exception is caught
     * @param e - exception
     */
    public void excepWindow(Exception e){
        log.setLevel(Level.DEBUG);
        log.debug("Launching a window with exception");

        final Stage excep = new Stage();

        final Label lbl = new Label(e.getMessage());
        final FlowPane root = new FlowPane(Orientation.VERTICAL, 20, 20, lbl);
        final Scene scene = new Scene(root, 150, 30);
        excep.setScene(scene);
        excep.show();
    }
}
