/*
    Just tests to make sure everything works properly. Like the shoppingTest of the Poker game.
*/
public class UnitTest {
    public static void main(String[]args) {
        /*
        //Tests out the Card class
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

        //Tests out the Deck class
        Deck myDeck = new Deck();
        System.out.println(myDeck);
        myDeck.shuffleDeck();
        System.out.println(myDeck);
    }
}
