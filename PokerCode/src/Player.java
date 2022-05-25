import java.util.ArrayList;
/*
Represents a player, with a hand and the ability to bet.
Need to integrate the betting with the board class.
 */
public class Player {
    private String name;
    private boolean isTurn;
    private int chips;
    private int lastBetIncrease;
    //There are basically 3 actions a player can do when it's their turn: pass, bet, or fold.
    Hand playerHand = new Hand();

    public Player(int startingChips, String name) {
        this.chips = startingChips;
        boolean isTurn = false;
        this.name = name;
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

    public void setLastBetIncrease(int betAmount) {
        lastBetIncrease = betAmount;
    }

    public int getLastBetIncrease() {
        return lastBetIncrease;
    }

    public double getHandVal() {
        return playerHand.getHandVal();
    }
    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }
}
