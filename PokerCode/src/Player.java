import java.util.ArrayList;
/*
Represents a player, with a hand and the ability to bet.
Need to integrate the betting with the board class.
 */
public class Player {
    boolean isTurn;
    int chips;
    //There are basically 3 actions a player can do when it's their turn: pass, bet, or fold.
    Hand playerHand = new Hand();

    public Player(int startingChips) {
        this.chips = startingChips;
        boolean isTurn = false;
    }

    //adds cards dealt to a player's hand
    public void dealCards(ArrayList<Card> cardsDealt) {
        playerHand.addCards(cardsDealt);
    }

    public void addChips(int newChips) {
        if(newChips <= 0) {
            throw new IllegalArgumentException("Error! Chips added must be positive!");
        }
        chips += newChips;
    }
}
