import java.util.ArrayList;
import java.util.Collections;

public class Hand implements Comparable<Hand>{
    private ArrayList<Card> hand;
    //All cards represent the list of all cards on the board and in the hand.
    private ArrayList<Card> allCards;

    //Initializes an empty hand
    public Hand() {
        hand = new ArrayList<Card>();
        allCards = new ArrayList<Card>();
    }

    //Initializes a hand with an arraylist of cards (from the deal method).
    public Hand(ArrayList<Card> cardArrayList) {
        hand = new ArrayList<Card>();
        hand.addAll(cardArrayList);
        allCards = new ArrayList<Card>();
        allCards.addAll(hand);
    }

    public void addCards(ArrayList<Card> cardsDealt) {
        hand.addAll(cardsDealt);
        allCards.addAll(cardsDealt);
    }

    public void resetHand() {
        allCards.removeAll(hand);
        hand.clear();
    }

    //resets the allCards arrayList with all the cards on the board and in the hand
    public void updateCardsOnBoard(ArrayList<Card> cardsOnBoard) {
        allCards.clear();
        allCards.addAll(cardsOnBoard);
        allCards.addAll(hand);
    }

    /*Poker hand order:
    Royal Flush
    Straight Flush
    4 of a Kind
    Full House
    Flush
    Straight
    3 of a kind
    2 pair
    pair
    high card
    */
    //Provides a score for that hand. The hand with the higher score wins.
    //The first number is the main value of the hand (which hands beat which others)
    //The smaller stuff is the tiebreakers, i.e. which straight flush has the higher card.
    //There are some very minor edge cases yet to be implemented.
    /*
    EDGE CASES TO BE WORKED ON:
    If both hands have the same 2 pairs, the one with the higher card should win
    If both hands have the same 4 of a kind, the one with the higher card should win
    If both hands have the same three of a kind, the other cards should determine who wins.
    EDGE CASES SOLVED:
    Full house
    Straight Flush
     */

    public double evalHand() {
        if(isHandFlush() && isHandStraight() && getHighestCard() == 12) {
            //Royal flush
            return 8.0;
        } else if(isHandFlush() && isHandStraight()) {
            //Straight Flush
            return 7.0 + (getHighestCard() * 0.01);
        } else if(isFourOfAKind() > 0) {
            //Four of a kind
            return 6.0 + (isFourOfAKind() * 0.01);
        } else if(isFullHouse() > 0) {
            //Full House
            return 5.0 + (isFullHouse() * 0.00001);
        } else if(isHandFlush()) {
            //Flush
            return 4.0 + (getSortedHandVal() * 0.01);
        } else if(isThreeOfAKind() > 0) {
            //Three of a kind
            return 3.0 + (isThreeOfAKind() * 0.01);
        } else if(isTwoPair() > 0) {
            //Two pairs
            return 2.0 + (isTwoPair() * 0.0001);
        } else if(isPair() > 0) {
            //One pair
            return 1.0 + (isPair() * 0.01 + getHighestCard() * 0.001);
        } else {
            //Just the highest card
            return getSortedHandVal();
        }
    }

    //Evaluates whether a hand is a flush or not.
    private boolean isHandFlush() {
        int[] suitCounter = new int[4];
        for(int i = 0; i < allCards.size(); i++) {
            suitCounter[allCards.get(i).getSuit().getSuitAsInt()]++;
        }
        for(int counter : suitCounter) {
            if (counter >= 5) {
                return true;
            }
        }
        return false;
    }

    //Evaluates whether a hand is a straight or not
    private boolean isHandStraight() {
        Collections.sort(allCards);
        int straightCounter = 1;
        for(int i = 0; i < allCards.size() - 1; i++) {
            if(allCards.get(i).getCardVal() + 1 == allCards.get(i + 1).getCardVal()) {
                straightCounter++;
                if(straightCounter >= 5) {
                    return true;
                }
            } else {
                straightCounter = 0;
            }
        }
        return false;
    }

    //If there is a pair, returns the cardVal of the pair
    private int isPair() {
        Collections.sort(allCards);
        for(int i = 0; i < allCards.size() - 1; i++) {
            if (allCards.get(i).getCardVal() == allCards.get(i+1).getCardVal()) {
                return allCards.get(i).getCardVal();
            }
        }
        return -1;
    }

    //Evaluates whether there are two pairs or not
    //Precondition: There is not a three of a kind
    private double isTwoPair() {
        Collections.sort(allCards);
        int numPairs = 0;
        //Stores the pair values for each of the pairs
        int[] pairVals = new int[2];
        for(int i = 0; i < allCards.size() - 1; i++) {
            if (allCards.get(i).getCardVal() == allCards.get(i+1).getCardVal()) {
                pairVals[numPairs] = allCards.get(i).getCardVal();
                numPairs++;
            }
        }
        if(numPairs == 2) {
            double output = 0.01 * Math.max(pairVals[0], pairVals[1]);
            output+= 0.0001 * Math.min(pairVals[0], pairVals[1]);
            return output;
        } else {
            return -1;
        }
    }

    //Evaluates whether there is a three of a kind or not
    private int isThreeOfAKind() {
        Collections.sort(allCards);
        for(int i = 0; i < allCards.size() - 2; i++) {
            //If the collection is sorted and two values are equal to eachother, all middle values must also be equal
            if (allCards.get(i).getCardVal() == allCards.get(i+2).getCardVal()) {
                return allCards.get(i).getCardVal();
            }
        }
        return -1;
    }
    //Returns the value of the four of a kind if it exists, returns -1 otherwise.
    private int isFourOfAKind() {
        Collections.sort(allCards);
        //If it's four of a kind, either the first and fourth value will be the same,
        // or the second and third value will be.
        if(allCards.get(0).getCardVal() == allCards.get(3).getCardVal()) {
            return allCards.get(0).getCardVal();
        } else if (allCards.get(1).getCardVal() == allCards.get(4).getCardVal()) {
            return allCards.get(1).getCardVal();
        } else {
            return -1;
        }
    }

    //This is kinda complicated so i decided to just write a method for it
    //if it's a full house, returns (100* threeOfAKind card val) + pairCardVAl, else returns -1
    private int isFullHouse() {
        Collections.sort(allCards);
        //Makes sure the first 2 cards are a pair
        boolean firstPair = allCards.get(0).getCardVal() == allCards.get(1).getCardVal();
        //Makes sure the last two cards are a pair
        boolean secondPair = allCards.get(3).getCardVal() == allCards.get(4).getCardVal();
        //Makes sure the middle card is in a three of a kind with the first or second pair
        boolean threeOfAKind = allCards.get(2).getCardVal() == allCards.get(1).getCardVal()
                || allCards.get(2).getCardVal() == allCards.get(3).getCardVal();
        if (firstPair && secondPair && threeOfAKind) {
            //if it's a full house, the middle card will always be the three of a kind value
            int tripleCardVal = allCards.get(2).getCardVal();
            int pairCardVal;
            if(allCards.get(2).getCardVal() == allCards.get(3).getCardVal()) {
                pairCardVal = allCards.get(1).getCardVal();
            }
            else{
                pairCardVal = allCards.get(3).getCardVal();
            }
            return (100 * tripleCardVal) + pairCardVal;
        } else {
            return -1;
        }
    }
    //returns the highest card of all the cards on the hand and the board.
    private int getHighestCard() {
        int highestCard = 0;
        for(int i = 0; i < allCards.size(); i++) {
            if (allCards.get(i).getCardVal() > highestCard) {
                highestCard = allCards.get(i).getCardVal();
            }
        }
        return highestCard;
    }

    //returns 0.01 * the largest card + 0.0001 * the smallest card
    //This is to be used for tiebreakers, as it only tells how high the cards are, not an evaluation of hand strength.
    private double getSortedHandVal() {
        return 0.01 * Math.max(hand.get(0).getCardVal(), hand.get(1).getCardVal())
                + 0.0001 * Math.min(hand.get(0).getCardVal(), hand.get(1).getCardVal());
    }
    public String toString() {
        String output = "";
        for(int i = 0; i < hand.size(); i++) {
            output += hand.get(i) + ", ";
        }
        return output;
    }

    //Evaluates which hand is better.
    public int compareTo(Hand otherHand) {
        return (int) Math.signum(this.evalHand() - otherHand.evalHand());
    }
}
