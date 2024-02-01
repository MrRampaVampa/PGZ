module com.example.pgz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.json;


    opens com.example.pgz to javafx.fxml;
    exports com.example.pgz;
}