package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.candies.normalCandy;

public class CreateTemplateModel {
    public static CandycrushModel createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        BoardSize size = new BoardSize(lines.size(), lines.getFirst().length());
        var model = createNewModel(size);
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                model.replaceCellAt(new Position(col, row, size), characterToCandy(line.charAt(col)));
            }
        }
        return model;
    }

    private static CandycrushModel createNewModel(BoardSize size) {
        return new CandycrushModel("Test", size);
    }

    private static Candy characterToCandy(char c) {
        return switch(c) {
            case '.' -> null;
            case 'o' -> new normalCandy(0);
            case '*' -> new normalCandy(1);
            case '#' -> new normalCandy(2);
            case '@' -> new normalCandy(3);
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
}
