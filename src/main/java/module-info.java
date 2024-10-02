module com.alura.challengeconversormonedasjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.net.http;

    opens com.alura.challengeconversormonedasjavafx to javafx.fxml;
    opens com.alura.challengeconversormonedasjavafx.controlador to javafx.fxml;
    exports com.alura.challengeconversormonedasjavafx;
    exports com.alura.challengeconversormonedasjavafx.controlador;
}