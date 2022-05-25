import java.util.ArrayList;

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
    //For the cards on the boards
    private Board board;
    //For the cards in your hand
    private ArrayList<Card> myCards;
    //For the cards in the deck and your opponents hands
    private ArrayList<Card> otherCards;

    public AIEvaluation(Board board, Hand hand) {
        this.board = board;
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
    //Complies a list of all the possible hands your opponent could have (sorted based on value)
    public void compilePossibleHands() {
    //    ArrayList<Card[]> handList= new ArrayList<Card>();
        //For each card that could possibly be in their hand
        for(int firstCardIndex = 0; firstCardIndex < otherCards.size(); firstCardIndex++) {
            //Add each other card that could possibly be in their hand
            for(int secondCardIndex = 0; secondCardIndex < otherCards.size(); secondCardIndex++) {
                //Create a new arrayList of hand/card list??
                //Add these two cards to the hand
                //add the board cards to the hand
            }
        }
        //sort all of the cards based off of current hand value
    }
}
