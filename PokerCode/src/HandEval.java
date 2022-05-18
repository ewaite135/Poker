import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HandEval {
    //Turns a hand into an array of counts counting the times each number appears in the hand.
    private static int[] initializeCardCounter(ArrayList<Card> cardList) {
        int[] cardCounter = new int[13];
        for(int i = 0; i < cardList.size(); i++) {
            cardCounter[cardList.get(i).getCardVal()]++;
        }
        return cardCounter;
    }

    public static double evalHand(ArrayList<Card> handCards, ArrayList<Card> commCards) {
        ArrayList<Card> allCards = new ArrayList<Card>();
        allCards.addAll(handCards);
        if(commCards.size() > 0) {
            allCards.addAll(commCards);
        }
        Collections.sort(handCards);
        int[] cardCounter  = initializeCardCounter(allCards);
        //For testing
        System.out.println("Card value counting array:");
        System.out.println(Arrays.toString(cardCounter));
        System.out.println("cards in hand: " + Utilities.printCardArrayList(handCards));
        System.out.println("cards on board: " + Utilities.printCardArrayList(commCards));
        if((isHandFlush(handCards, allCards) > -1) && (isHandStraight(cardCounter) == 12)) {
            //Royal flush
            //No tiebreakers
            System.out.println("Royal Flush!");
            return calculateHandValue(HandType.ROYAL_FLUSH);
        } else if(isHandFlush(handCards, allCards) > -1 && isHandStraight(cardCounter) > -1) {
            //Straight Flush
            //First tiebreaker: the highest card in the straight
            System.out.println("Straight Flush");
            return calculateHandValue(HandType.ROYAL_FLUSH, isHandStraight(cardCounter));
        } else if(isFourOfAKind(cardCounter) > -1) {
            //Four of a kind
            //First tie breaker: the value of the four of a kind
            //Second tie breaker: In case all 4 cards in four of a kind are on the board: the high card in the hand
            System.out.println("Four of a Kind!");
            return calculateHandValue(HandType.FOUR_OF_A_KIND, isFourOfAKind(cardCounter));
        } else if(isFullHouse(cardCounter)[0] > -1 && isFullHouse(cardCounter)[1] > -1) {
            //Full House
            //First tiebreaker: the value of the three of a kind
            //second tie breaker: the value of the pair
            //testing purposes:
            System.out.println("Full House!");
            int[] fullHouseArray = isFullHouse(cardCounter);
            System.out.println("The three of a kind is " + fullHouseArray[0]);
            System.out.println("The pair is " + fullHouseArray[1]);
            return calculateHandValue(HandType.FULL_HOUSE, isFullHouse(cardCounter)[0], isFullHouse(cardCounter)[1]);
        } else if(isHandFlush(handCards, allCards) > -1) {
            //Flush
            //First tie breaker: the highest card in the flush in your hand
            //TODO: Possibly implement fourth and fifth tie breakers
            System.out.println("Flush!");
            return calculateHandValue(HandType.FLUSH, isHandFlush(handCards, allCards));
        } else if(isHandStraight(cardCounter) > -1) {
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
            return calculateHandValue(HandType.PAIR, isPair(cardCounter), handCards.get(1).getCardVal(),
                    handCards.get(0).getCardVal());
        } else {
            Collections.sort(handCards);
            //Just the highest card
            //The tiebreaker is the value of the highest card in the hand, the the value of the other card in the hand
            System.out.println("High Card!");
            return calculateHandValue(HandType.HIGH_CARD, handCards.get(1).getCardVal(), handCards.get(0).getCardVal());
        }
    }

    private static int isHandFlush(ArrayList<Card> handCards, ArrayList<Card> allCards) {
        int highFlushCardInHand = -1;
        int[] suitsCounter = new int[4];
        for(Card card: allCards) {
            suitsCounter[card.getSuit().getSuitAsInt()]++;
        }
        Suit flushSuit;
        for(int suitIndex = 0; suitIndex < 4; suitIndex++) {
            if (suitsCounter[suitIndex] >= 5) {
                //It's a flush. Sets the suit of the flush
                flushSuit = intToSuit(suitIndex);
                if(handCards.get(0).getSuit() == flushSuit) {
                    highFlushCardInHand = handCards.get(0).getCardVal();
                }
                if(handCards.get(1).getSuit() == flushSuit ) {
                    highFlushCardInHand = Math.max(highFlushCardInHand, handCards.get(1).getCardVal());
                }
            }
        }
        return highFlushCardInHand;
    }

    //Tests if it is a straight or not based on the array of counts of card numbers
    //returns the highest number of the straight if it is a straight, returns -1 if not a straight
    private static int isHandStraight(int[] countArray) {
        for(int i = 0; i < countArray.length - 4; i++) {
            boolean currentlyStraight = true;
            for(int j = 0; j < 5; j++) {
                if (countArray[i + j] < 1) {
                    currentlyStraight = false;
                }
            }
            if(currentlyStraight) {
                return (i + 4);
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