package be.kuleuven.candycrushjavafx;

public class Candy {
    private CandySoort soort;

    public Candy(){
        this.veranderSoort();
    }
    public CandySoort getSoort() {
        return soort;
    }

    public void veranderSoort(){
        this.soort = CandySoort.values()[(int)(Math.random()*5)];
    }
}
