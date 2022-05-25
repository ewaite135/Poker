import java.util.ArrayList;

public class AIPlayer extends Player {
    public AIPlayer(int startingChips, String name) {
        super(startingChips, name);
    }
    ArrayList<Double> playerConfidence = new ArrayList<Double>();
    public int getMove(int currentBet, int pot) {
        double ratio = (double) (currentBet) / pot;
        if(ratio > 3) {
            return currentBet;
        }
        return 0; //just checks for now
    }
}
