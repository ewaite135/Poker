import java.util.ArrayList;

public class Utilities {
    public static String printCardList(Card[] cardList) {
        String output = "";
        for(int i = 0; i < cardList.length - 1; i++) {
            output += cardList[i] + ", ";
        }
        output += cardList[cardList.length - 1];
        return output;
    }

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
}
