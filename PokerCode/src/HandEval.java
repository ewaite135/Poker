/*
This class is basically just the evalHand method that is used to evaluate hands.
Each hand is looked at with the board and given a double value. The greater the value, the better the hand.
When comparing hands, the one with the greater value wins.
 */
import java.util.ArrayList;

public class HandEval {
    //Turns a hand into an array of counts counting the times each number appears in the hand.
    private static int[] initializeCardCounter(ArrayList<Card> cardList) {
        int[] cardCounter = new int[13];
        for(int i = 0; i < cardList.size(); i++) {
            cardCounter[cardList.get(i).getCardVal()]++;
        }
        return cardCounter;
    }

    //The important method in this class. Evaluates a list of cards on the hand and the board and returns a double
    //This checks which type of hand a hand is, and then gives it a corresponding value and tiebreakers
    public static double evalHand(Card[] handCards, Card[] commCards) {
        ArrayList<Card> allCards = new ArrayList<Card>();
        allCards.add(handCards[0]);
        allCards.add(handCards[1]);
        if(commCards.length > 0) {
            for(int i = 0; i < commCards.length; i++) {
                allCards.add(commCards[i]);
            }
        }
        int[] cardCounter  = initializeCardCounter(allCards);
        handCards = sortCardArray(handCards);
        if((isHandFlush(handCards, allCards) > -1) && (isHandStraight(cardCounter) == 12)) {
            //Royal flush
            return calculateHandValue(HandType.ROYAL_FLUSH);
        } else if(isHandFlush(handCards, allCards) > -1 && isHandStraight(cardCounter) > -1) {
            //Straight Flush
            //First tiebreaker: the highest card in the straight
            return calculateHandValue(HandType.STRAIGHT_FLUSH, isHandStraight(cardCounter));
        } else if(isFourOfAKind(cardCounter) > -1) {
            //Four of a kind
            //First tie breaker: the value of the four of a kind
            //Second tie breaker: In case all 4 cards in four of a kind are on the board: the high card in the hand
            return calculateHandValue(HandType.FOUR_OF_A_KIND, isFourOfAKind(cardCounter));
        } else if(isFullHouse(cardCounter)[0] > -1 && isFullHouse(cardCounter)[1] > -1) {
            //Full House
            //First tiebreaker: the value of the three of a kind
            //second tie breaker: the value of the pair
            int[] fullHouseArray = isFullHouse(cardCounter);
            return calculateHandValue(HandType.FULL_HOUSE, isFullHouse(cardCounter)[0], isFullHouse(cardCounter)[1]);
        } else if(isHandFlush(handCards, allCards) > -1) {
            //Flush
            //First tie breaker: the highest card in the flush in your hand
            //TODO: Possibly implement fourth and fifth tie breakers
            return calculateHandValue(HandType.FLUSH, isHandFlush(handCards, allCards));
        } else if(isHandStraight(cardCounter) > -1) {
            //Straight
            //First tie breaker: The highest card of the straight.
            //I don't think there are any other tie breakers, but if you find any, please implement them.
            return calculateHandValue(HandType.STRAIGHT, isHandStraight(cardCounter));
        } else if(isThreeOfAKind(cardCounter) > -1) {
            //Three of a kind
            //First tie breaker: the value of the three of a kind
            //I don't know of any other tiebreakers
            return calculateHandValue(HandType.THREE_OF_A_KIND, isThreeOfAKind(cardCounter),
                    handCards[1].getCardVal(), handCards[0].getCardVal());
        } else if(isTwoPair(cardCounter)[1] > -1) {
            //Two pairs
            //First tie breaker: value of the higher pair
            //Second tie breaker: value of the lower pair
            return calculateHandValue(HandType.TWO_PAIRS, isTwoPair(cardCounter)[0], isTwoPair(cardCounter)[1]);
        } else if(isPair(cardCounter) > -1) {
            //One pair
            //First tie breaker: the value of the pair
            return calculateHandValue(HandType.PAIR, isPair(cardCounter), handCards[1].getCardVal(),
                    handCards[0].getCardVal());
        } else {
            //Just the highest card
            //The tiebreaker is the value of the highest card in the hand, the the value of the other card in the hand
            return calculateHandValue(HandType.HIGH_CARD, handCards[1].getCardVal(), handCards[0].getCardVal());
        }
    }

    //Checks to see whether a hand is a flush or not
    //If it is, returns the highest value of that flush in your hand, otherwise returns -1
    private static int isHandFlush(Card[] handCards, ArrayList<Card> allCards) {
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
                if(handCards[0].getSuit() == flushSuit) {
                    highFlushCardInHand = handCards[0].getCardVal();
                }
                if(handCards[1].getSuit() == flushSuit ) {
                    highFlushCardInHand = Math.max(highFlushCardInHand, handCards[1].getCardVal());
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


    //If it finds a four of a kind, returns the cardVal of the four of a kind. Otherwise, returns -1
    private static int isFourOfAKind(int[] countArray) {
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 4) {
                return i;
            }
        }
        return -1;
    }

    //If there is a three of a kind, return the cardvAl of the three of a kind. Otherwise, return -1
    private static int isThreeOfAKind(int[] countArray) {
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 3) {
                return i;
            }
        }
        return -1;
    }

    //If it finds a pair, then returns the highest pair
    private static int isPair(int[] countArray) {
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 2) {
                return i;
            }
        }
        return -1;
    }

    //If it finds two pairs, then returns an array of the pair numbers, with the higher pair number being first
    private static int[] isTwoPair(int[] countArray) {
        int pairNum = 0;
        int[] pairArray = {-1, -1};
        for(int i = 12; i >= 0; i--) {
            if(countArray[i] >= 2) {
                pairArray[pairNum] = i;
                pairNum++;
                if(pairArray[1] != -1) {
                    return pairArray;
                }
            }
        }
        return pairArray;
    }

    //If there is a full house, returns the position of the 3 of a kind and then the 2 of a kind
    private static int[] isFullHouse(int[] countArray) {
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

    //Converts an int to a suit. kind of bad and we should probably fix this but it works for now.
    private static Suit intToSuit(int number) {
        if(number > 3 || number < 0) {
            throw new IllegalArgumentException("Error: intToSuit input must be between 0 and 3");
        }
        Suit[] suitArray = {Suit.DIAMOND, Suit.HEART, Suit.SPADE, Suit.CLUB};
        return suitArray[number];
    }

    //Sorts a hand. This should probably be made into a good sorting algorithm but it works for now
    private static Card[] sortCardArray(Card[] startArr) {
        if(startArr.length != 2) {
            throw new IllegalArgumentException("Error: sortCardArr input array must have a length of 2");
        }
        if(startArr[0].getCardVal() > startArr[1].getCardVal()) {
            return new Card[] {startArr[1], startArr[0]};
        } else {
            return startArr;
        }
    }

    //Given the tiebreakers and a hand type, returns a double that represents the strength of the hand
    private static double calculateHandValue(HandType handType, int firstTieBreaker, int secondTieBreaker, int thirdTieBreaker) {
        double handVal = handType.getHandTypeVal();
        handVal += (firstTieBreaker * 0.01);
        handVal += (secondTieBreaker * 0.0001);
        handVal += (thirdTieBreaker * 0.000001);
        return handVal;
    }

    //Given the tiebreakers and a hand type, returns a double that represents the strength of the hand
    private static double calculateHandValue(HandType handType, int firstTieBreaker, int secondTieBreaker) {
        return calculateHandValue(handType, firstTieBreaker, secondTieBreaker, 0);
    }

    //Given the tiebreakers and a hand type, returns a double that represents the strength of the hand
    private static double calculateHandValue(HandType handType, int firstTieBreaker) {
        return calculateHandValue(handType, firstTieBreaker, 0, 0);
    }

    //Given the tiebreakers and a hand type, returns a double that represents the strength of the hand
    private static double calculateHandValue(HandType handType) {
        return calculateHandValue(handType, 0, 0, 0);
    }
}