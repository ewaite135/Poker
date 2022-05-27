
   // Just tests to make sure everything works properly. Like the shoppingTest of the Poker game.

import java.util.ArrayList;
public class UnitTest {
    public static void main(String[]args) {
        Deck myDeck2 = new Deck(true);
        Hand myHand = new Hand(myDeck2.dealCards(2));
        System.out.println("Your hand is: " + myHand);
        Board board = new Board();
        board.addCards(myDeck2.dealCards(5));
        System.out.println("The board is: " + board);
        System.out.println("This hand has an evaluation of: "
                + HandEval.evalHand(Utilities.toCardArray(myHand.getCards()), Utilities.toCardArray(board.getCards())));

    }
}
