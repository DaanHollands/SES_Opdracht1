module be.kuleuven.candycrushjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens be.kuleuven.candycrushjavafx to javafx.fxml;
    exports be.kuleuven.candycrushjavafx;
}