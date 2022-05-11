import java.util.ArrayList;
import java.util.Collections;

public class Hand implements Comparable<Hand>{
    public ArrayList<Card> hand;
    //All cards represent the list of all cards on the board and in the hand.
    public ArrayList<Card> allCards;

    //Initializes an empty hand
    public Hand() {
        hand = new ArrayList<Card>();
        allCards = new ArrayList<Card>();
    }

    //Initializes a hand with an arraylist of cards (from the deal method).
    public Hand(ArrayList<Card> cardArrayList) {
        hand = new ArrayList<Card>();
        hand.addAll(cardArrayList);
        allCards = new ArrayList<Card>();
        allCards.addAll(hand);
    }

    public void addCards(ArrayList<Card> cardsDealt) {
        hand.addAll(cardsDealt);
        allCards.addAll(cardsDealt);
    }

    public void resetHand() {
        allCards.removeAll(hand);
        hand.clear();
    }

    //resets the allCards arrayList with all the cards on the board and in the hand
    public void updateCardsOnBoard(ArrayList<Card> cardsOnBoard) {
        allCards.clear();
        allCards.addAll(cardsOnBoard);
        allCards.addAll(hand);
    }

    public String toString() {
        String output = "";
        for(int i = 0; i < hand.size(); i++) {
            output += hand.get(i) + ", ";
        }
        return output;
    }

    public int getAllCardSize() {
        return allCards.size();
    }

    //Evaluates which hand is better.
    public int compareTo(Hand otherHand) {
        return (int) Math.signum(HandEval.evalHand(this) - HandEval.evalHand(this));
    }
}
