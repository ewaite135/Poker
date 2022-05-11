/*
    Just tests to make sure everything works properly. Like the shoppingTest of the Poker game.
*/
import java.util.ArrayList;
public class UnitTest {
    public static void main(String[]args) {
        /*
        //Tests out the Card class. Everything I've tested works so far
        Card card1 = new Card("2", Suit.SPADE);
        System.out.println("card1's suit is " + card1.getSuit());
        System.out.println("card1's cardValue is " + card1.getCardVal());
        System.out.println(card1);
        Card card2 = new Card("4", Suit.HEART);
        System.out.println("card2's suit is " + card2.getSuit());
        System.out.println("card2's cardValue is " + card2.getCardVal());
        System.out.println(card2);
        Card card3 = new Card("King", Suit.DIAMOND);
        System.out.println("card3's suit is " + card3.getSuit());
        System.out.println("card3's cardValue is " + card3.getCardVal());
        System.out.println(card3);
        */
        //Tests out the Deck class. Everything I've tested works so far
        /*
        Deck myDeck = new Deck();
        System.out.println(myDeck);
        myDeck.shuffleDeck();
        System.out.println(myDeck);
        ArrayList<Card> hand = myDeck.dealCards(3);
        System.out.println();
        for(Card card:hand) {
            System.out.print(card + ", ");
        }
        System.out.println();
        System.out.println(myDeck);
         */
        Deck myDeck2 = new Deck();
        myDeck2.shuffleDeck();
        Hand myHand = new Hand(myDeck2.dealCards(5));
        System.out.println(myHand);
        System.out.println("This hand has an evaluation of: " + HandEval.evalHand(myHand));
    }
}