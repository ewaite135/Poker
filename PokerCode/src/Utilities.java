import java.util.ArrayList;

public class Utilities {
    //Turns an arrayList of cards into an array.
    public static Card[] toCardArray(ArrayList<Card> cardList) {
        if(cardList.size() > 0) {
            Card[] cardArr = new Card[cardList.size()];
            for(int i = 0; i < cardList.size(); i++) {
                cardArr[i] = cardList.get(i);
            }
            return cardArr;
        }
        return new Card[] {};
    }

    //Normalizes a string so that only the first letter is capitalized.
    public static String normalizeString(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
