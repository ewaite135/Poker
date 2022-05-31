import java.util.ArrayList;
/*
Represents a player, with a hand and the ability to bet or fold.
 */
public class Player {
    private String name;
    private int chips;
    //The last bet value stores a players last bet.
    //-1 means they have not bet yet this round, while -2 means they have folded
    private int lastBet;
    private int chipsBetThisRound;
    Hand playerHand = new Hand();

    public Player(int startingChips, String name) {
        this.chips = startingChips;
        this.name = Utilities.normalizeString(name);
        chipsBetThisRound = 0;
        lastBet = -1;
    }

    public void resetHand() {
        playerHand.resetHand();
    }
    //adds cards dealt to a player's hand
    public void dealCards(ArrayList<Card> cardsDealt) {
        playerHand.addCardsToHand(cardsDealt);
    }

    public void addChips(int newChips) {
        chips += newChips;
    }

    public int getChips() {
        return chips;
    }

    public void setLastBet(int betAmount) {
        //If you've bet or checked, add that to the total number of chips bet this round
        if(betAmount >= 0) {
            chipsBetThisRound += betAmount;
        } else { //If you've folded, set the chipsBetThisRound to 0;
            chipsBetThisRound = 0;
        }
        lastBet = betAmount;
    }

    public int getLastBet() {
        return lastBet;
    }

    public Hand getHand() { return playerHand; }

    public int getChipsBetThisRound() {return chipsBetThisRound;}

    public String getName() {
        return name;
    }

    public String toString() {
        return name + " has " + chips + "chips and has a hand of " + playerHand.toString();
    }
}
