import java.util.ArrayList;
import java.util.TreeMap;

/*This class is just a place to put the math that will power our AI
Basically, we just need to figure out how likely it is that our opponents will have better hands than us based on the cards
then we can bet accordingly.
To do this we need to:
Figure out all the cards in the deck
Figure out all the possible combinations of 2 cards our opponent could have (about 1000)
For each of these combinations, figure out whether they have a better hand then you or not (and the chances they could)
Bet based on what cards you think they have/how likely your cards are to be better
Some sort of risk evaluation

Main issues: Brute force works only if all the cards are face up
otherwise, not super helpful
when 5 cards up:
*/

public class AIEvaluation {
    private static final int NUM_BOARDS_EVALUATED = 50;
    //For the cards on the boards
    private Board board;
    //For the cards in your hand
    private ArrayList<Card> commCards;
    private ArrayList<Card> myCards;
    //For the cards in the deck and your opponents hands
    private ArrayList<Card> otherCards;
    ArrayList<Card[]> handArray = new ArrayList<Card[]>();

    TreeMap<Double, Card[]> handList = new TreeMap<Double, Card[]>();

    public AIEvaluation(Board board, Hand hand) {
        this.board = board;
        commCards = this.board.getCards();
        myCards = hand.getHand();
        //Fills the otherCards array with all possible cards
        String[] cardNameList = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        Suit[] suitList = {Suit.DIAMOND, Suit.HEART, Suit.SPADE, Suit.CLUB};
        otherCards= new ArrayList<Card>();
        //Creates a deck of 52 unique cards
        for(int suitNum = 0; suitNum < 4; suitNum++) {
            for(int cardNum = 0; cardNum < 13; cardNum++) {
                String currCardNum = cardNameList[cardNum];
                Suit currCardSuit = suitList[suitNum];
                otherCards.add(new Card(currCardNum, currCardSuit));
            }
        }
        for(Card cardOnBoard : board.getCards()) {
            otherCards.remove(cardOnBoard);
        }
        for(Card cardInHand : myCards) {
            otherCards.remove(cardInHand);
        }
    }
    //Complies a list of all the possible hands your opponent could have
    public void compilePossibleHands() {
        ArrayList<Card> boardCards = board.getCards();
        Card[] tempCardList = new Card[2];
        //For each card that could possibly be in their hand
        for(int firstCardIndex = 0; firstCardIndex < otherCards.size(); firstCardIndex++) {
            //Add each other card that could possibly be in their hand
            for(int secondCardIndex = 0; secondCardIndex < otherCards.size(); secondCardIndex++) {
                //Add an array of these two cards to the handList
                if(firstCardIndex != secondCardIndex) {
                    handArray.add(new Card[] {otherCards.get(firstCardIndex), otherCards.get(secondCardIndex)});
                }
            }
        }
    }

    //Returns the percentage chance that you win (based on just the cards on the board and
    //the possible cards in your opponent's hand.
    public double findPresentHandOdds() {
        for(Card[] hand : handArray) {
            double tempHandVal = HandEval.evalHand(hand, Utilities.toCardArray(commCards));
            handList.put(tempHandVal, hand);
        }
        //Makes sure that the TreeMap is already initialized
        if(!handList.isEmpty()) {
            //Finds the hand that is closest to (but not better than) our hand
            double myHandEval = HandEval.evalHand(Utilities.toCardArray(myCards), Utilities.toCardArray(board.getCards()));
            double highestWorstKey = handList.floorKey(myHandEval);
            //Finds the number of possible hands worse than our hand
            double position = handList.headMap(highestWorstKey).size();
            //Returns the number of possible worse hands over the number of total hands
            //In other words, the percentile of our hand
            return (position / handList.size());
        }
        return -1;
    }

    public Card[][] generatePossibleBoards() {
        int cardsGenPerBoard = 5 - commCards.size();
        if(cardsGenPerBoard > 0) {
            Card[][] possibleBoards = new Card[NUM_BOARDS_EVALUATED][5 - commCards.size()];
            //For each of the boards we are creating
            for(int boardNum = 0; boardNum < NUM_BOARDS_EVALUATED; boardNum++) {
                int currCardGenerated = 0;
                //for each card in the board
                while(currCardGenerated < cardsGenPerBoard) {
                    //generate a random card
                    possibleBoards[boardNum][currCardGenerated] = getRandomCard();
                    //If that card is already in the board, keep generating random cards until you get a new one
                    while(hasDuplicates(possibleBoards[boardNum])) {
                        possibleBoards[boardNum][currCardGenerated] = getRandomCard();
                    }
                    currCardGenerated++;
                }
            }
            return possibleBoards;
        } else {
            throw new IllegalArgumentException("No boards need to be generated, all cards are on the table");
        }
    }

    private Card getRandomCard() {
        final String[] cardNameList = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        final Suit[] suitList = {Suit.DIAMOND, Suit.HEART, Suit.SPADE, Suit.CLUB};
        String cardName = cardNameList[(int) (Math.random() * 12)];
        Suit suit = suitList[(int) (Math.random() * 4)];
        return new Card(cardName, suit);
    }

    //Determines whether a Card array has duplicate cards or not.
    private boolean hasDuplicates(Card[] testArr) {
        for(int index1 = 0; index1 < testArr.length - 1; index1++) {
            for(int index2 = index1 + 1; index2 < testArr.length; index2++) {
                if(testArr[index1].isSameAs(testArr[index2])) {
                    return true;
                }
            }
        }
        return false;
    }

    //Combines the arrays and sees whether the combined array has duplicates
    private boolean hasDuplicates(Card[] arr1, Card[] arr2) {
        Card[] arr3 = new Card[arr1.length + arr2.length];
        for(int i = 0; i < arr1.length; i++) {
            arr3[i] = arr1[i];
        }
        for(int i = 0; i < arr2.length; i++) {
            arr3[arr1.length + i] = arr2[i];
        }
        return hasDuplicates(arr3);
    }

    public double evaluateRandomPossiblities() {
        int numWorseHands = 0;
        //A list of a given number of possible boards
        Card[][] possibleBoards = generatePossibleBoards();
        int totalHandsEval = handArray.size() * possibleBoards.length;
        //For each possible card combination your opponent could have (as shown by their hands in the handList)
        for(Card[] hand: handArray) {
            for(Card[] possibleBoard : possibleBoards) {
                double myHandVal = HandEval.evalHand(Utilities.toCardArray(myCards), possibleBoard);
                double oppHandVal = HandEval.evalHand(hand, possibleBoard);
                if(myHandVal > oppHandVal) {
                    numWorseHands++;
                }
            }
        }
        return ((double) numWorseHands / totalHandsEval);
    }
}

