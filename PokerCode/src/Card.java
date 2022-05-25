public class Card implements Comparable<Card>{
    private int cardVal; //just to determine which card is higher. This value ranges from 0 (a 2) to 12 (an ace)
    private String cardName;
    private Suit suit;
    private boolean isVisible;

    public Card(String name, Suit suit, boolean isVisible) {
        this.suit = suit;
        cardName = name;
        this.isVisible = isVisible;
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

    public Card(String name, Suit suit) {
        this(name,suit, false);
    }

    public Suit getSuit() {
        return suit;
    }

    public String getName() {
        return cardName;
    }

    public int getCardVal() {
        return cardVal;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean visible) {
        isVisible = visible;
    }

    public String toString() {
        return cardName.toUpperCase() + " of " + suit + "S";
    }

    //We use this comparable method to sort the hands from lowest number to highest (2 to king to ace).
    //This just returns the cardVal.
    //This allows us to sort out hands to more easily see if we have straights or pairs, etc.
    public int compareTo(Card otherCard) {
        return cardVal - otherCard.getCardVal();
    }

    //returns true if two cards are the same (in both suit and number)
    public boolean isSameAs(Card otherCard) {
        return (suit == otherCard.getSuit() && cardVal == otherCard.getCardVal());
    }
}