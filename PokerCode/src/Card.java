public class Card {
    private int cardVal; //just to determine which card is higher. This value ranges from 0 (a 2) to 12 (an ace)
    private String cardName;
    private Suit suit;

    public Card(String name, Suit suit) {
        this.suit = suit;
        cardName = name;
        //just for testing purposes
        cardVal = -1;
        String[] cardNameList = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        for(int i = 0; i < cardNameList.length; i++) {
            if(cardNameList[i].equals(cardName)) {
                cardVal = i;
            }
        }
        if(cardVal == -1) {
            throw new IllegalArgumentException("Card name is invalid. Please pick 2-10, Ace, King, Queen, and Jack");
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public int getCardVal() {
        return cardVal;
    }

    public String toString() {
        return cardName.toUpperCase() + " of " + suit + "S";
    }
}
