import java.util.ArrayList;

public class Utilities {
    public static String printCardArrayList(ArrayList<Card> cardList) {
        String output = "";
        for(int i = 0; i < cardList.size() - 1; i++) {
            output += cardList.get(i) + ", ";
        }
        output += cardList.get(cardList.size() - 1);
        return output;
    }
}
