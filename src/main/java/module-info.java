module simulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens gui to javafx.fxml;

    exports gui;
}
