import java.util.ArrayList;
import java.util.Collections;

public class HandEval {
    //here, hand refers to all the cards in the board and the hand.
    private static ArrayList<Card> boardCards = new ArrayList<Card>();
    private static ArrayList<Card> handCards = new ArrayList<Card>();
    private static ArrayList<Card> allCards = new ArrayList<Card>();

    public static void updateHandCards(ArrayList<Card> newHand) {
        handCards.clear();
        handCards.addAll(newHand);
        allCards.clear();
        allCards.addAll(handCards);
        allCards.addAll(boardCards);
    }

    public static void updateBoardCards(ArrayList<Card> newHand) {
        boardCards.clear();
        boardCards.addAll(newHand);
        allCards.clear();
        allCards.addAll(handCards);
        allCards.addAll(boardCards);
    }

    //Turns a hand into an array of counts counting the times each number appears in the hand.
    private static int[] initializeCardCounter(ArrayList<Card> cardList) {
        int[] cardCounter = new int[13];
        for(int i = 0; i < cardList.size(); i++) {
            cardCounter[cardList.get(i).getCardVal()]++;
        }
        return cardCounter;
    }

    public static double evalHand(Hand handCards, ArrayList<Card> commCards) {
        ArrayList<Card> allCards = handCards.hand;
        allCards.addAll(commCards);
        int[] cardCounter  = initializeCardCounter(allCards);
        if((isHandFlush(allCards)[0] > -1) && isHandStraight(cardCounter) == 12) {
            //Royal flush
            //No tiebreakers
            System.out.println("Royal Flush!");
            return calculateHandValue(HandType.ROYAL_FLUSH);
        } else if(isHandFlush(handCards)[0] > -1 && isHandStraight(cardCounter) >= -1) {
            //Straight Flush
            //First tiebreaker: the highest card in the straight
            System.out.println("Straight Flush");
            return calculateHandValue(HandType.ROYAL_FLUSH, isHandStraight(cardCounter));
        } else if(isFourOfAKind(cardCounter) > -1) {
            //Four of a kind
            //First tie breaker: the value of the four of a kind
            //TODO: implement second tiebreaker for four of a kind
            //Second tie breaker: In case all 4 cards in four of a kind are on the board: the high card in the hand
            System.out.println("Four of a Kind!");
            return calculateHandValue(HandType.FOUR_OF_A_KIND, isFourOfAKind(cardCounter));
        } else if(isFullHouse(cardCounter)[0] >= 0 && isFullHouse(cardCounter)[1] >= 0) {
            //Full House
            //First tiebreaker: the value of the three of a kind
            //second tie breaker: the value of the pair
            //testing purposes:
            System.out.println("Full House!");
            int[] fullHouseArray = isFullHouse(cardCounter);
            System.out.println("The three of a kind is " + fullHouseArray[0]);
            System.out.println("The pair is " + fullHouseArray[1]);
            return calculateHandValue(HandType.FULL_HOUSE, isFullHouse(cardCounter)[0], isFullHouse(cardCounter)[1]);
        } else if(isHandFlush(handCards)[0] > -1) {
            int[] flushNums = isHandFlush(handCards);
            //Flush
            //First tie breaker: the highest card in the flush
            //Second tie breaker: the second highest card in the flush
            //Third tie breaker: the third highest card in the flush
            //TODO: Possibly implement fourth and fifth tie breakers
            System.out.println("Flush!");
            return calculateHandValue(HandType.FLUSH, flushNums[0], flushNums[1], flushNums[2]);
        } else if(isHandStraight(cardCounter) >= -1) {
            //Straight
            //First tie breaker: The highest card of the straight.
            //I don't think there are any other tie breakers, but if you find any, please implement them.
            System.out.println("Straight!");
            return calculateHandValue(HandType.STRAIGHT, isHandStraight(cardCounter));
        } else if(isThreeOfAKind(cardCounter) > -1) {
            //Three of a kind
            //First tie breaker: the value of the three of a kind
            //I don't know of any other tiebreakers
            System.out.println("Three of a kind");
            return calculateHandValue(HandType.THREE_OF_A_KIND, isThreeOfAKind(cardCounter));
        } else if(isTwoPair(cardCounter)[1] > -1) {
            //Two pairs
            //First tie breaker: value of the higher pair
            //Second tie breaker: value of the lower pair
            System.out.println("Two pairs!");
            return calculateHandValue(HandType.TWO_PAIRS, isTwoPair(cardCounter)[0], isTwoPair(cardCounter)[1]);
        } else if(isPair(cardCounter) > -1) {
            //One pair
            //First tie breaker: the value of the pair
            System.out.println("Pair!");
            return calculateHandValue(HandType.PAIR, isPair(cardCounter));
        } else {
            //Just the highest card
            //TODO: Figure out how to only look at the cards in the hand.
            System.out.println("High Card!");
            return calculateHandValue(HandType.HIGH_CARD);
        }
    }

    private static void isHandFlush(ArrayList<Card> cardList) {
        int[] suitsCounter = new int[4];
        for(Card card: cardList) {
            suitsCounter[card.getSuit().getSuitAsInt()];
        }
        if()
    }

    //Tests if it is a straight or not based on the array of counts of card numbers
    //returns the highest number of the straight if it is a straight, returns -1 if not a straight
    private static int isHandStraight(int[] countArray) {
        for(int i = 0; i < countArray.length - 4; i++) {
            boolean currentlyStraight = true;
            for(int j = 0; j < 5; j++) {
                if (countArray[i + j] < 1) {
                    System.out.println("There is no "+ (i+j) + ", so the straight is ended");
                    currentlyStraight = false;
                } else {
                    System.out.println("There is a " + (i + j) + ", so the straight continues");
                }
            }
            if(currentlyStraight) {
                System.out.println("There is a straight, starting at " + i + "and ending at " + (i+4));
                //returns the highest value of the straight
                return (i + 4);
            } else {
                System.out.println("New Straight test, starting at " + i);
            }
        }
        return -1;
    }


    //If it finds a four of a kind, returns the highest one.
    private static int isFourOfAKind(int[] countArray) {
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 4) {
                return i;
            }
        }
        return -1;
    }

    //If it finds a three of a kind, returns the highest one.
    private static int isThreeOfAKind(int[] countArray) {
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 3) {
                return i;
            }
        }
        return -1;
    }

    //If it finds a pair, then returns the highest one.
    private static int isPair(int[] countArray) {
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 2) {
                return i;
            }
        }
        return -1;
    }

    //If it finds two pairs, then returns an array of the pair numbers
    private static int[] isTwoPair(int[] countArray) {
        int pairNum = 0;
        int[] pairArray = {-1, -1};
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 2) {
                pairArray[pairNum] = i;
                pairNum++;
            }
        }
        return pairArray;
    }

    private static int[] isFullHouse(int[] countArray) {
        //returns the position of the 3 and then the 2
        int[] fullHouseArray = {-1, -1};
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 3) {
                fullHouseArray[0] = i;
            }
        }
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 2 && i != fullHouseArray[0]) {
                fullHouseArray[1] = i;
            }
        }
        if(fullHouseArray[1] == -1) {
            fullHouseArray[0] = -1;
        }
        return fullHouseArray;
    }

    private static int[] handIntoArray(ArrayList<Card> cardList) {
        int[] cardArray = new int[cardList.size()];
        Collections.sort(cardList);
        for(int i = 0; i < cardList.size(); i++) {
            cardArray[i] = cardList.get(i).getCardVal();
        }
        return cardArray;
    }

    private static Suit intToSuit(int number) {
        if(number > 3 || number < 0) {
            throw new IllegalArgumentException("Error: intToSuit input must be between 0 and 3");
        }
        Suit[] suitArray = {Suit.DIAMOND, Suit.HEART, Suit.SPADE, Suit.CLUB};
        return suitArray[number];
    }

    private static double calculateHandValue(HandType handType, int firstTieBreaker, int secondTieBreaker, int thirdTieBreaker) {
        double handVal = handType.getHandTypeVal();
        handVal += (firstTieBreaker * 0.01);
        handVal += (secondTieBreaker * 0.0001);
        handVal += (thirdTieBreaker * 0.000001);
        return handVal;
    }

    private static double calculateHandValue(HandType handType, int firstTieBreaker, int secondTieBreaker) {
        return calculateHandValue(handType, firstTieBreaker, secondTieBreaker, 0);
    }

    private static double calculateHandValue(HandType handType, int firstTieBreaker) {
        return calculateHandValue(handType, firstTieBreaker, 0, 0);
    }

    private static double calculateHandValue(HandType handType) {
        return calculateHandValue(handType, 0, 0, 0);
    }
}