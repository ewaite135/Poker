import java.util.ArrayList;
/*
public class HandEval {
    //here, hand refers to all the cards in the board and the hand.
    private static ArrayList<Card> hand = new ArrayList<Card>();
    private static int[] cardCounter = new int[13];
    public static void setCurrHand(ArrayList<Card> newHand) {
        hand.clear();
        hand.addAll(newHand);
    }

    private static void initializeCardCounter(Hand newHand) {
        for(Card card: newHand) {
            cardCounter[card.getCardVal()]++;
        }
    }

    public double evalHand() {
        if() {
            //Royal flush
        } else if() {
            //Straight Flush
        } else if() {
            //Four of a kind
        } else if() {
            //Full House
        } else if() {
            //Flush
        } else if() {
            //Three of a kind
        } else if() {
            //Two pairs
        } else if() {
            //One pair
        } else {
            //Just the highest card
        }
    }
    private boolean isHandFlush() {
        int[] suitCounter = new int[4];
        for(int i = 0; i < hand.size(); i++) {
            suitCounter[hand.get(i).getSuit().getSuitAsInt()]++;
        }
        for(int counter : suitCounter) {
            if (counter >= 5) {
                return true;
            }
        }
        return false;
    }

    //Tests if it is a straight or not based on the array of counts of card numbers
    //returns the highest number of the straight if it is a straight, returns -1 if not a straight
    private int isHandStraight(int[] countArray) {
        for(int i = 0; i < 9; i++) {
            boolean currentlyStraight = true;
            for(int j = 0; j < 5; j++) {
                if (countArray[i + j] < 1) {
                    currentlyStraight = false;
                }
            }
            if(currentlyStraight) {
                //returns the highest value of the straight
                return (i + 4);
            }
        }
        return -1;
    }

    private boolean
 */
}