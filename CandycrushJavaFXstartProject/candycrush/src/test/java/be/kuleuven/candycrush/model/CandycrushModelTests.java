package be.kuleuven.candycrush.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CandycrushModelTests {

    @Test
    public void ifSpelerDaanThenGetSpelerIsDaan(){
        CandycrushModel model = new CandycrushModel("Daan");
        String result = model.getSpeler();
        assert (result.equals("Daan"));
    }

    @Test
    public void ifWidthIs10ThenReturn10(){
        CandycrushModel model = new CandycrushModel("Daan");
        int width = model.getWidth();
        assertThat(width).isEqualTo(10);
    }

    @Test
    public void ifHeightIs10ThenReturn10(){
        CandycrushModel model = new CandycrushModel("Daan");
        int width = model.getHeight();
        assertThat(width).isEqualTo(10);
    }

    @Test
    public void ifSetSpeelbordReturnSameSpeelbord(){
        CandycrushModel model = new CandycrushModel("Daan");
        ArrayList<Integer> speelbord = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        model.setSpeelbord(speelbord);
        assertThat(model.getSpeelbord()).isEqualTo(speelbord);
    }

    @Test
    public void ifSpeelbordIsSize100ThenReturn100(){
        CandycrushModel model = new CandycrushModel("Daan");
        int length = model.getSpeelbordLength();
        assertThat(length).isEqualTo(100);
    }

    @Test
    public void ifSpeelbord10Return10(){
        CandycrushModel model = new CandycrushModel("Daan");
        model.setScorebord(10);
        assertThat(model.getScore()).isEqualTo(10);
    }

    @Test
    public void ifRow1Column1getIndexFromRowColumnReturn11(){
        CandycrushModel model = new CandycrushModel("Daan");
        int index = model.getIndexFromRowColumn(1, 1);
        assertThat(index).isEqualTo(11);
    }

    @Test
    public void ifScoreIsIncreasedFrom0ThenReturn1(){
        CandycrushModel model = new CandycrushModel("Daan");
        model.setScorebord(0);
        model.increaseScore();
        assertThat(model.getScore()).isEqualTo(1);
    }

    @Test
    public void ifSpeelbordResetReturnsNewSpeelbord(){
        CandycrushModel model = new CandycrushModel("Daan");
        ArrayList<Integer> oudSpeelbord= new ArrayList<Integer>();
        oudSpeelbord.addAll(model.getSpeelbord());
        model.resetSpeelbord();
        ArrayList<Integer> nieuwSpeelbord = model.getSpeelbord();
        assertThat(nieuwSpeelbord).isNotSameAs(oudSpeelbord);
    }

    @Test
    public void ifGridIsMadeCheckAllDefinedValues(){
        CandycrushModel model = new CandycrushModel("Daan");
        model.resetSpeelbord();
        ArrayList<Integer> speelbord = model.getSpeelbord();
        for (Integer i : speelbord){
            assertThat(i).isNotZero();
        }
    }

    //TODO: Delete previous test and test your own code

}
