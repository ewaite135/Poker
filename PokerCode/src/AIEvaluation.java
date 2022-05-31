import java.util.ArrayList;
//import java.util.TreeMap;

/*This class is just a place to put the math that will power our AI
Basically, we just need to figure out how likely it is that our opponents will have better hands than us based on the cards
then we can bet accordingly.
To do this we to:
Figure out all the cards in the deck
Figure out all the possible combinations of 2 cards our opponent could have (about 1000)
Generate 50 random boards that could appear, and then evaluate our hand and our opponents hand based on those boards
This allows us to determine the general strength of our hand given the current board.

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
    ArrayList<Card[]> handArray = new ArrayList<>();

    //TreeMap<Double, Card[]> handList = new TreeMap<Double, Card[]>();

    public AIEvaluation(Board board, Hand hand) {
        this.board = board;
        commCards = this.board.getCards();
        myCards = hand.getCards();
        //Fills the otherCards array with all possible cards
        final String[] cardNameList = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        final Suit[] suitList = {Suit.DIAMOND, Suit.HEART, Suit.SPADE, Suit.CLUB};
        otherCards= new ArrayList<Card>();
        //Creates a deck of 52 unique cards
        for(int suitNum = 0; suitNum < 4; suitNum++) {
            for(int cardNum = 0; cardNum < 13; cardNum++) {
                String currCardNum = cardNameList[cardNum];
                Suit currCardSuit = suitList[suitNum];
                otherCards.add(new Card(currCardNum, currCardSuit));
            }
        }
        //Removes the cards that are already in your hand and on the board
        for(Card cardOnBoard : board.getCards()) {
            otherCards.remove(cardOnBoard);
        }
        for(Card cardInHand : myCards) {
            otherCards.remove(cardInHand);
        }
    }
    //Complies a list of all the possible hands your opponent could have, given the cards in your hand and on the board
    public void compilePossibleHands() {
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
    //I don't think we need to use a treeMap for this data but we could. I'll try the arrayList approach and see
    //if it's fast enough
    /*public double findPresentHandOdds() {
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
    }*/

    //Generates an array  of 50 possible boards.
    public Card[][] generatePossibleBoards() {
        Card[][] possibleBoards = new Card[NUM_BOARDS_EVALUATED][5];
        //Fills in an Array with the current cards
        Card[] currBoardCards = new Card[5];
        for(int i = 0; i < commCards.size(); i++) {
            currBoardCards[i] = commCards.get(i);
        }
        //For each of the boards we are creating
        for(int boardNum = 0; boardNum < NUM_BOARDS_EVALUATED; boardNum++) {
            //Fill the board with the cards we already know
            int currCard;
            for(currCard = 0; currCard < commCards.size(); currCard++) {
                possibleBoards[boardNum][currCard] = currBoardCards[currCard];
            }
            //for each card that needs to be generated
            while(currCard < 5) {
                //generate a random card
                possibleBoards[boardNum][currCard] = getRandomCard();
                //If that card is already in the board, keep generating random cards until you get a new one
                while(hasDuplicates(possibleBoards[boardNum])) {
                    possibleBoards[boardNum][currCard] = getRandomCard();
                }
                currCard++;
            }
        }
        return possibleBoards;
    }

    //Returns a random card
    private Card getRandomCard() {
        final String[] cardNameList = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        final Suit[] suitList = {Suit.DIAMOND, Suit.HEART, Suit.SPADE, Suit.CLUB};
        String cardName = cardNameList[(int) (Math.random() * 12)];
        Suit suit = suitList[(int) (Math.random() * 4)];
        return new Card(cardName, suit);
    }

    //Determines whether a single Card array has duplicate cards or not.
    //This method should only be used for small arrays because it will take a long time on larger arrays
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

    //Combines the card arrays and then sees whether the combined array has duplicates
    private boolean hasDuplicates(Card[] arr1, Card[] arr2) {
        Card[] arr3 = new Card[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, arr3, 0, arr1.length);
        System.arraycopy(arr2, 0, arr3, arr1.length, arr2.length);
        return hasDuplicates(arr3);
    }

    //evaluate the strength of your hand based on the cards already in your hand and on the board
    public double evaluateHandStrength() {
        Card[] handCards = Utilities.toCardArray(myCards);
        int numHandsEvaluated = 0;
        int numWorseHands = 0;
        //A list of a given number of possible boards
        Card[][] possibleBoards = generatePossibleBoards();
        //For each possible hand your opponent could have (about 1000 maximum)
        for(Card[] hand: handArray) {
            //For each of the 50 random boards generated
            for(Card[] possibleBoard : possibleBoards) {
                //If there are no duplicates between the hand and the board
                if(!hasDuplicates(possibleBoard, hand)) {
                    numHandsEvaluated++;
                    if (HandEval.evalHand(handCards, possibleBoard) > HandEval.evalHand(hand, possibleBoard)) {
                        numWorseHands++;
                    }
                }
            }
        }
        return ((double) numWorseHands / numHandsEvaluated);
    }
}

