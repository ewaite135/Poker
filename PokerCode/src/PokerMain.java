import java.util.ArrayList;
import java.util.Scanner;

public class PokerMain {
    public static final int STARTING_CHIPS = 100;
    public static final int ANTE = 5;
    public static void main(String[]args) {
        Scanner console = new Scanner(System.in);
        Deck deck = new Deck(true);
        Board board = new Board();
        //An arrayList of all players in the game
        ArrayList<Player> players = new ArrayList<Player>();
        //An arrayList of the players in the current round
        ArrayList<Player> playersInRound = new ArrayList<Player>();
        int round = 0;
        int betMinimum = 0;
        //Initialize the list of players
        System.out.println("How many players?");
        int numPlayers = console.nextInt();
        for(int i = 0; i < numPlayers; i++) {
            System.out.println("Please enter the name of Player " + i + ": ");
            String playerName = console.next();
            players.add(new Player(STARTING_CHIPS, playerName));
        }
        //Main game loop
        while(!gameOver(players)) {
            round++;
            //Start new round
            startRound(players, playersInRound, deck, board);
            //printAllPlayerInfo(players);
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console);
            //deal 3 cards to the board
            board.addCards(deck.dealCards(3));
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console);
            //deal 1 cards to the board
            board.addCards(deck.dealCards(1));
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console);
            //deal the final card to the board
            board.addCards(deck.dealCards(1));
            System.out.println(board);
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console);
            printAllPlayerInfo(players);
            //Finds and pays out the winner
            Player roundWinner = findWinner(playersInRound, board);
            System.out.println("The winner of this round is " +  roundWinner.getName());
            board.payOutPot(roundWinner);
            resetHands(players);
            removeLostPlayers(players);
            board.resetBoard();
        }
    }

    //Intializes the round.
    // Resets the active player list, shuffles the deck and deals cards, and bets the ante for everyone
    public static void startRound(ArrayList<Player> players, ArrayList<Player> playersInRound, Deck deck, Board board) {
        playersInRound.clear();
        playersInRound.addAll(players);
        deck.resetDeck();
        deck.shuffle();
        System.out.println();
        System.out.println("Everyone bets the ante");
        for(int i = 0; i < playersInRound.size(); i++) {
            //deals 2 cards to each player
            playersInRound.get(i).dealCards(deck.dealCards(2));
            //everybody bets the ante
            playersInRound.get(i).addChips(-ANTE);
            board.addChipsToPot(ANTE);
        }
    }

    //removes all of the players that have folded from the Array of active players that round.
    public static void updateActivePlayers(ArrayList<Player> playersInRound) {
        for(int i = 0; i < playersInRound.size(); i++) {
            //If that player has folded, remove them
            if(playersInRound.get(i).getLastBet() == -2) {
                playersInRound.remove(i);
            }
        }
    }

    //returns the index of the next active player
    public static int getNextPlayer(ArrayList<Player> playersInRound, int index) {
        if(index < playersInRound.size() - 1) {
            return index + 1;
        } else {
            return 0;
        }
    }

    //Sees if every player has checked.
    public static boolean allHaveChecked(ArrayList<Player> playersInRound) {
        for(Player player : playersInRound) {
            //if that player has not checked, return false
            if (player.getLastBet() != 0) {
                return false;
            }
        }
        return true;
    }

    //Checks to see if the game is over (if everyone is out of chips)
    public static boolean gameOver(ArrayList<Player> players) {
        int playersWithChips = 0;
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getChips() > 0) {
                playersWithChips++;
            }
        }
        //If two people still have chips, the game isn't over yet
        return (playersWithChips < 2);
    }

    //Removes all the players that lost from the list of people who are currently playing
    public static void removeLostPlayers(ArrayList<Player> players) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getChips() == 0) {
                players.remove(i);
            }
        }
    }

    //Does the whole betting phase until everyone checks or someone folds.
    public static void doBettingPhase(ArrayList<Player> playersInRound, Board board, Scanner console) {
        int playerBetting = 0;
        int lastBet = 0;
        //Sets the last bet increase to -1 so the computer won't think
        //people have checked this round when they checked last round
        for(int i = 0; i < playersInRound.size(); i++) {
            playersInRound.get(i).setLastBet(-1);
        }
        //start the betting
        while(playersInRound.size() > 1 && !allHaveChecked(playersInRound)) {
            //Gets the player to bet or fold
            getPlayerAction(playersInRound.get(playerBetting), board, console, lastBet);
            //increases the last bet variable to know the minimum amount needed to bet.
            if (playersInRound.get(playerBetting).getLastBet() >= 0) {
                lastBet = playersInRound.get(playerBetting).getLastBet();
            }
            //Goes to the next player.
            playerBetting = getNextPlayer(playersInRound, playerBetting);
            //removes the inactive players from the playersInRound ArrayList
            updateActivePlayers(playersInRound);
            //TEsting only
            if(allHaveChecked(playersInRound)) {
                System.out.println("Everyone has checked, so we will move to the next round");
            }
        }
    }

    //Compares everyone's hands and returns the player with the best hand (whose the winner of the round)
    public static Player findWinner(ArrayList<Player> playersInRound, Board board) {
        int highestHandIndex = 0;
        double highestHandVal = 0;
        for(int i = 0; i < playersInRound.size(); i++) {
            Card[] playerHand = Utilities.toCardArray(playersInRound.get(i).getHand().getCards());
            double playerHandVal = HandEval.evalHand(playerHand, Utilities.toCardArray(board.getCards()));
            if(playerHandVal > highestHandVal) {
                highestHandVal = playerHandVal;
                highestHandIndex = i;
            }
        }
        return playersInRound.get(highestHandIndex);
    }

    //Clears everyone's hands
    public static void resetHands(ArrayList<Player> players) {
        for(int i = 0; i < players.size(); i++) {
            players.get(i).resetHand();
        }
    }

    //for testing purposes only
    public static void printAllPlayerInfo(ArrayList<Player>playersInRound) {
        for(int i = 0; i < playersInRound.size(); i++) {
            System.out.println("Player " + (i + 1) + ":" + playersInRound.get(i).toString());
        }
    }

    //Gets a response from the player (whether they bet, checked, or folded (and the amount if betting)
    public static void getPlayerAction(Player player, Board board, Scanner console, int betMinimum) {
        System.out.println("It is " + player.getName() + "'s turn.");
        System.out.println( player.toString());
        System.out.println(board.toString());
        System.out.println("There are " + board.getPotSize() + " chips in the pot.");
        System.out.println("Type B to bet or type F to fold");
        String actionType = console.next();
        //Makes sure the input is B or F (can add Checking later but is already implemented in betting.)
        while(!actionType.equals("F") && !actionType.equals("B")) {
            System.out.println("You typed " + actionType);
            System.out.println("That's not a valid command.\nPlease type B or F");
            System.out.print("Type B to bet or type F to fold");
            actionType = console.next();
        }
        if(actionType.equals("B")) {
            System.out.println("How much do you want to bet? You have to bet at least " + betMinimum + " chips.");
            int betAmount = console.nextInt();
            while (betAmount < betMinimum || betAmount > player.getChips()) {
                System.out.println("Please bet an amount between " + betMinimum + " and your current chips (" + player.getChips() + ").");
                betAmount = console.nextInt();
            }
            player.addChips(-betAmount);
            player.setLastBet(betAmount);
            board.addChipsToPot(betAmount);
            System.out.println();
        } else if (actionType.equals("F")) {
            System.out.println("You folded. Better luck next round!");
            System.out.println();
            player.setLastBet(-2);
        }
    }
}