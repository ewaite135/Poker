public class RowansCodeHandEval<i> {

    // handValues is an array of the number values of each card in all player's hands, from P1 card 1, P1 card 2, P2 card 1, etc.
    // commValues is an array of the number values of the community cards.
    // playerScore is an array of the score of each player, in points used only for determining the winner.
    //Just one persons hand
    private int[] handValues = {0, 0};
    //The board
    private int[] commValues = {0, 0, 0, 0, 0};
    private int playerScore = 0;
    public int getEvaluation() {
        for (int cardInHand = 0; cardInHand < 2; cardInHand++) {          // Checks the pairs in all hands and community cards.
            int numCommCards = commValues.length;
            int copies = 0;
            for (int j = 0; j < numCommCards; j++) { //
                if (handValues[cardInHand] == commValues[j]) {
                    copies++;
                    playerScore = (100 * (10 ^ copies) + (handValues[cardInHand] * 10));
                }
            }
            if (handValues[cardInHand] == handValues[cardInHand + 1]) { //If your hand contains a pair
                copies++;
                playerScore = (100 * (10 ^ copies) + (handValues[cardInHand] * 10));
            } else {        // Repeats checking the community cards if hand doesn't contain a pair
                copies = 0;
                int tempTotal = 0;
                for (int j = 0; j < numCommCards; j++) {
                    if (handValues[cardInHand + 1] == commValues[j]) {
                        copies++;
                        tempTotal = (100 * (10 ^ copies) + (handValues[cardInHand + 1] * 10));
                    }
                }
                playerScore += tempTotal;
            }
            //If you have zero pairs, it adds the high card value to your hand.
            if (playerScore == 0) {
                if (handValues[cardInHand] >= handValues[cardInHand + 1]) {
                    playerScore += handValues[cardInHand];
                } else {
                    playerScore += handValues[cardInHand + 1];
                }
            }

        }
    }
}
