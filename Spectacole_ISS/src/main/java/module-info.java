module com.example.spectacole_iss {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.example.spectacole_iss to javafx.fxml;
    opens com.example.spectacole_iss.controller to javafx.fxml;
    exports com.example.spectacole_iss;
    exports com.example.spectacole_iss.controller;
}