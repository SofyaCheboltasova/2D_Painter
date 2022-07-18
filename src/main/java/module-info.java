module ru.nsu.scheboltasova.logoworld {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires junit;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.log4j;
    requires org.apache.logging.log4j;
    opens ru.nsu.scheboltasova.logoworld to javafx.fxml;
    exports ru.nsu.scheboltasova.logoworld;
    exports ru.nsu.scheboltasova.logoworld.commands;
    opens ru.nsu.scheboltasova.logoworld.commands to javafx.fxml;
    exports ru.nsu.scheboltasova.logoworld.test;
    opens ru.nsu.scheboltasova.logoworld.test to javafx.fxml;
    //exports ru.nsu.scheboltasova.logoworld.graphics;
    //opens ru.nsu.scheboltasova.logoworld.graphics to javafx.fxml;
}