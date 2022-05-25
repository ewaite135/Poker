/*
Creates an ArrayList of Card objects
 */
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cardDeck;
    public Deck(boolean shuffle) {
        //Initializes the deck
        final String[] cardNameList = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        final Suit[] suitList = {Suit.DIAMOND, Suit.HEART, Suit.SPADE, Suit.CLUB};
         cardDeck= new ArrayList<Card>();
         //Creates a deck of 52 unique cards
         for(int suitNum = 0; suitNum < 4; suitNum++) {
             for(int cardNum = 0; cardNum < 13; cardNum++) {
                 String currCardNum = cardNameList[cardNum];
                 Suit currCardSuit = suitList[suitNum];
                 cardDeck.add(new Card(currCardNum, currCardSuit));
             }
         }
         if (shuffle) {
             shuffleDeck();
         }
    }

    //Shuffles the deck by randomly switching two cards
    public void shuffleDeck() {
        for(int i = 0; i < cardDeck.size(); i++) {
            //Random number between 0 and 51
            int randNum = (int)(Math.random() * 52);
            Card temporaryCard = cardDeck.get(randNum);
            cardDeck.set(randNum, cardDeck.get(i));
            cardDeck.set(i, temporaryCard);
        }
    }

    //Returns an array of the top numCards in the deck
    public ArrayList<Card> dealCards(int numCards) {
        ArrayList<Card> cardsDealt = new ArrayList<Card>();
        for(int i = 0; i < numCards; i++) {
            cardsDealt.add(cardDeck.get(0));
            cardDeck.remove(0);
        }
        return cardsDealt;
    }

    //Prints out the deck. For testing purposes only, we obviously don't want people to see what cards are in the deck.
    public String toString() {
        String deckString = "";
        for(Card card:cardDeck) {
            deckString = deckString + card.toString() + ", ";
        }
        return deckString;
    }
}
