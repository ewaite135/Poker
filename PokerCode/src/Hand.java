import java.util.ArrayList;
import java.util.Collections;

public class Hand implements Comparable<Hand>{
    public ArrayList<Card> hand;
    public ArrayList<Card> boardCards;
    //All cards represent the list of all cards on the board and in the hand.
    public ArrayList<Card> allCards;

    //Initializes an empty hand
    public Hand() {
        hand = new ArrayList<Card>();
        allCards = new ArrayList<Card>();
        boardCards = new ArrayList<Card>();
    }

    //Initializes a hand with an arraylist of cards (from the deal method).
    public Hand(ArrayList<Card> handCardArrayList) {
        hand = new ArrayList<Card>();
        hand.addAll(handCardArrayList);
        boardCards = new ArrayList<Card>();
        allCards = new ArrayList<Card>();
        allCards.addAll(hand);
    }

    public void addCardsToHand(ArrayList<Card> cardsDealt) {
        hand.addAll(cardsDealt);
        allCards.addAll(cardsDealt);
    }

    public void resetHand() {
        allCards.clear();
        hand.clear();
        boardCards.clear();
    }

    //resets the allCards arrayList with all the cards on the board and in the hand
    public void updateCardsOnBoard(ArrayList<Card> cardsOnBoard) {
        boardCards.clear();
        boardCards.addAll(cardsOnBoard);
        allCards.clear();
        allCards.addAll(boardCards);
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

    public double getHandVal(){
        return HandEval.evalHand(hand, boardCards);
    }

    //Evaluates which hand is better.
    public int compareTo(Hand otherHand) {
        double otherHandVal = HandEval.evalHand(otherHand.hand, otherHand.boardCards);
        return (int) Math.signum(getHandVal() - otherHandVal);
    }
}
