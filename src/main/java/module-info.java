module Demo.demo1pro6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo1pro6 to javafx.fxml;
    exports Demo;
}