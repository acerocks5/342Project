module com.example.projectjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projectjavafx to javafx.fxml;
    opens model to javafx.fxml;
    exports com.example.projectjavafx;
    exports model;
}

