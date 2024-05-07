package be.kuleuven.candycrush;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kuleuven.candycrush.model.CandycrushModel;
import be.kuleuven.candycrush.model.Position;
import be.kuleuven.candycrush.view.CandycrushView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CandycrushController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Label;

    @FXML
    private Button btn;

    @FXML
    private Button reset;

    @FXML
    private AnchorPane paneel;

    @FXML
    private Text score;

    @FXML
    private AnchorPane speelbord;

    @FXML
    private TextField textInput;

    private CandycrushModel model;
    private CandycrushView view;
    @FXML
    void initialize() {
        assert Label != null : "fx:id=\"Label\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert paneel != null : "fx:id=\"paneel\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert speelbord != null : "fx:id=\"speelbord\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        model = new CandycrushModel(textInput.getText());
        view = new CandycrushView(model);
        speelbord.getChildren().add(view);
        view.setOnMouseClicked(this::onCandyClicked);
        btn.setOnMouseClicked(this::addSpeler);
        reset.setOnMouseClicked(this::resetSpeelbord);
    }

    public void update(){
        speelbord.setVisible(!model.getSpeler().isEmpty());
        score.setText("Score: " + model.getScore());
        view.update();
    }

    public void addSpeler(MouseEvent me){
        model.changeName(textInput.getText());
        update();
    }

    public void resetSpeelbord(MouseEvent me){
        model.resetSpeelbord();
        update();
    }

    public void onCandyClicked(MouseEvent me){
        Position candyPos = view.getPositionOfClicked(me);
        System.out.println(model.updateBoard());
        update();
//        model.changeNeighbours(candyPos);
//        System.out.println("Matches: " + model.findAllMatches());
//        System.out.println("Right Match: " + model.longestMatchToRight(candyPos));
//        System.out.println("Down Match: " + model.longestMatchDown(candyPos));
//        model.horizontalStartingPosition().forEach(System.out::println);
//        model.verticalStartingPosition().forEach(System.out::println);
    }

}
