import java.util.ArrayList;

public class Hand implements Comparable<Hand>{
    public ArrayList<Card> hand;
    public ArrayList<Card> boardCards;

    //Initializes an empty hand
    public Hand() {
        hand = new ArrayList<Card>();
        boardCards = new ArrayList<Card>();
    }

    //Initializes a hand with an arraylist of cards (from the deal method).
    public Hand(ArrayList<Card> handCardArrayList) {
        hand = new ArrayList<Card>();
        hand.addAll(handCardArrayList);
        boardCards = new ArrayList<Card>();
    }

    public void addCardsToHand(ArrayList<Card> cardsDealt) {
        hand.addAll(cardsDealt);
    }

    public void resetHand() {
        hand.clear();
        boardCards.clear();
    }

    //resets the allCards arrayList with all the cards on the board and in the hand
    public void updateCardsOnBoard(ArrayList<Card> cardsOnBoard) {
        boardCards.clear();
        boardCards.addAll(cardsOnBoard);
    }

    public String toString() {
        String output = "";
        for(int i = 0; i < hand.size(); i++) {
            output += hand.get(i) + ", ";
        }
        return output;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public double getHandVal(){
        return HandEval.evalHand(Utilities.toCardArray(hand), Utilities.toCardArray(boardCards));
    }

    //Evaluates which hand is better.
    public int compareTo(Hand otherHand) {
        double otherHandVal = HandEval.evalHand(Utilities.toCardArray(otherHand.hand), Utilities.toCardArray(otherHand.boardCards));
        return (int) Math.signum(getHandVal() - otherHandVal);
    }
}
