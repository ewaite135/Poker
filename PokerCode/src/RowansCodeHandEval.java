public class RowansCodeHandEval {

    // handValues is an array of the number values of each card in all player's hands, from P1 card 1, P1 card 2, P2 card 1, etc.
    // commValues is an array of the number values of the community cards.
    // playerScore is an array of the score of each player, in points used only for determining the winner.

    for (int i = 0; i < handValues.length; i+=2) {          // Checks the pairs in all hands and community cards.
        int max = commValues.length;
        int copies = 0;
        for (int j = 0; j < max; j++) {
            if (handValues[i] == commValues[j]) {
                copies++;
                playerScore[i/2] = (100 * (10^copies) + (handValues[i] * 10));
            }
        }
        if (handValues[i] == handValues [i + 1]) {
            copies++;
            playerScore[i/2] = (100 * (10^copies) + (handValues[i] * 10));
        }

        if (handValues[i] != handValues[i + 1]) {        // Repeats checking the community cards if hand doesn't contain same cards
            copies = 0;
            int tempTotal = 0;
            for (int j = 0; j < max; j++) {
                if (handValues[i + 1] == commValues[j]) {
                    copies++;
                    tempTotal = (100 * (10^copies) + (handValues[i + 1] * 10));
                }
            }
            playerScore[i/2] += tempTotal;
        }
        if (playerScore[i/2] == 0) {
            if (handValues[i] >= handValues [i + 1]) {
                playerScore[i/2] += handValues[i];
            } else {
                playerScore[i/2] += handValues[i + 1];
            }
        }

    }
}
