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

    //Adds cards to the hand
    public void addCardsToHand(ArrayList<Card> cardsDealt) {
        hand.addAll(cardsDealt);
    }

    public void resetHand() {
        hand.clear();
    }

    //returns a string of the cards in the hand
    public String toString() {
        return hand.get(0).toString() + ", " + hand.get(1).toString();
    }

    public ArrayList<Card> getCards() {
        return hand;
    }

    //Sets all the cards in the hand to visible or not (for drawing purposes)
    public void setHandVisibility(boolean visible) {
        for(int i = 0; i < hand.size(); i++) {
            hand.get(i).setIsVisible(visible);
        }
    }

    public Card getCard(int index) { return hand.get(index);}
}
