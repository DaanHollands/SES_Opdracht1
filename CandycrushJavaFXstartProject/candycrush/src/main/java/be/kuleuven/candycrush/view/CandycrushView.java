package be.kuleuven.candycrush.view;

import be.kuleuven.candycrush.model.*;
import be.kuleuven.candycrush.model.candies.*;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.Iterator;

public class CandycrushView extends Region {
    private CandycrushModel model;
    private int widthCandy;
    private int heigthCandy;

    public CandycrushView(CandycrushModel model) {
        this.model = model;
        widthCandy = 30;
        heigthCandy = 30;
        update();
    }

    public void update(){
        getChildren().clear();
        for (Position position : model.getBoardSize().positions()) {
            Node node = makeShapeCandy(position, model.getCandyFromPosition(position));

            Rectangle rectangle = new Rectangle(widthCandy, heigthCandy);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setLayoutX(position.x()*widthCandy);
            rectangle.setLayoutY(position.y()*heigthCandy);

            getChildren().addAll(node, rectangle);
        }
    }

    public Position getPositionOfClicked(MouseEvent me){
        int row = (int) me.getX()/heigthCandy;
        int column = (int) me.getY()/widthCandy;
        Position pos = new Position(row, column, model.getBoardSize());
        System.out.println(me.getX()+" - "+me.getY()+" - "+row+" - "+column);
        if (row < model.getBoardSize().width() && column < model.getBoardSize().height()){
            System.out.println(pos.toIndex());
        }
        return pos;
    }

    public Node makeShapeCandy(Position position, Candy candy){
        if(candy instanceof normalCandy){
            Circle circle = new Circle(position.x() * widthCandy + widthCandy/2, position.y() * heigthCandy + heigthCandy/2, 0.4*widthCandy);
            circle.setStroke(Color.BLACK);
            switch (((normalCandy) candy).color()){
                case 0 -> circle.setFill(Color.BLUE);
                case 1 -> circle.setFill(Color.RED);
                case 2 -> circle.setFill(Color.GREEN);
                case 3 -> circle.setFill(Color.YELLOW);
                default -> throw new IllegalStateException("Unexpected value: " + ((normalCandy) candy).color());
            };
            return circle;
        } else if(candy instanceof EmptyCandy){
              Rectangle rectangle = new Rectangle(widthCandy*0.8, heigthCandy*0.8);
              rectangle.setLayoutX(position.x()*widthCandy + 0.1 * widthCandy);
              rectangle.setLayoutY(position.y()*heigthCandy + 0.1 * heigthCandy);
              rectangle.setFill(Color.WHITE);
              return rectangle;
        } else {
            Rectangle rectangle = new Rectangle(widthCandy*0.8, heigthCandy*0.8);
            rectangle.setStroke(Color.BLACK);
            rectangle.setLayoutX(position.x()*widthCandy + 0.1 * widthCandy);
            rectangle.setLayoutY(position.y()*heigthCandy + 0.1 * heigthCandy);
            switch (candy) {
                case MultiCandy multiCandy -> rectangle.setFill(Color.BLUE);
                case RareCandy rareCandy -> rectangle.setFill(Color.RED);
                case RowSnapper rowSnapper -> rectangle.setFill(Color.GREEN);
                case TurnMaster turnMaster -> rectangle.setFill(Color.YELLOW);
                default -> throw new IllegalStateException("Unexpected value: " + candy);
            };
            return rectangle;
        }
    }
}
