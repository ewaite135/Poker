import java.util.ArrayList;

public class Hand {
    public ArrayList<Card> hand;

    //Initializes an empty hand
    public Hand() {
        hand = new ArrayList<Card>();
    }

    //Initializes a hand with an arraylist of cards (from the deal method).
    public Hand(ArrayList<Card> handCardArrayList) {
        hand = new ArrayList<Card>();
        hand.addAll(handCardArrayList);
    }

    public void addCardsToHand(ArrayList<Card> cardsDealt) {
        hand.addAll(cardsDealt);
    }

    public void resetHand() {
        hand.clear();
    }

    public String toString() {
        return hand.get(0).toString() + ", " + hand.get(1).toString();
    }

    public ArrayList<Card> getCards() {
        return hand;
    }
}
