import java.util.ArrayList;

public class Board {
    private int chipPot; //The number of chips in the pot.
    private ArrayList<Card> commCards;

    public Board() {
        commCards = new ArrayList<Card>();
    }

    //Resets the board (occurs at the start of every round)
    public void resetBoard() {
        commCards.clear();
        chipPot = 0;
    }
    //Adds cards to the board (when they get dealt)
    public void addCards(ArrayList<Card> newCards) {
        commCards.addAll(newCards);
    }

    //Returns the cards on the board
    public ArrayList<Card> getCards() {
        return commCards;
    }


    //Pays out the pot to the winner
    public void payOutPot(Player winner) {
        winner.addChips(chipPot);
        chipPot = 0;
    }

    //Just in case of a tie, plays out the pot to two winners
    public void payOutPot(Player winner1, Player winner2) {
        winner1.addChips(chipPot / 2);
        if(chipPot % 2 == 0) {
            winner2.addChips(chipPot / 2);
        } else {
            winner2.addChips((chipPot + 1)/ 2);
        }
        chipPot = 0;
    }

    //Adds chips to the pot
    public void addChipsToPot(int chips) {
        chipPot += chips;
    }

    //returns the size of the pot (used for AI calculations)
    public int getPotSize() { return chipPot; }

    //Returns a string of all the cards on the board
    public String toString() {
        String output = "Cards on the board: ";
        for(int i = 0; i < commCards.size() - 1; i++) {
            output += commCards.get(i) + ", ";
        }
        output += commCards.get(commCards.size() - 1);
        return output;
    }
}
