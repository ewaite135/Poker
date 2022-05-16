import java.util.ArrayList;

public class Board {
    private int chipPot; //The number of chips in the pot.
    private ArrayList<Card> commCards;

    public Board() {
        commCards = new ArrayList<Card>();
    }

    public void resetBoard() {
        commCards.clear();
        chipPot = 0;
    }

    public void addCards(ArrayList<Card> newCards) {
        commCards.addAll(newCards);
    }

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

    public void addChipsToPot(int chips) {
        chipPot += chips;
    }

    public int getPotSize() { return chipPot; }

    public String toString() {
        String output = "";
        for(int i = 0; i < commCards.size(); i++) {
            output += commCards.get(i) + ", ";
        }
        return output;
    }
}
