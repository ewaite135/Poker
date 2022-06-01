import java.awt.*;
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
        boolean playAgain = true;
        //Initialize the list of players
        System.out.println("How many players?");
        int numPlayers = console.nextInt();
        for(int i = 0; i < numPlayers; i++) {
            System.out.println("Please enter the name of Player " + (i + 1) + ": ");
            String playerName = console.next();
            players.add(new Player(STARTING_CHIPS, playerName));
        }
        DrawingPanel panel1 = new DrawingPanel(1200,800);
        Graphics s = panel1.getGraphics();

        //Main game loop
        while(!gameOver(players) && playAgain) {
            round++;
            //Start new round
            startRound(players, playersInRound, deck, board);
            panel1.clear();
            PokerGraphics.makeBoard(s,panel1,board, playersInRound);
            PokerGraphics.dealHands(playersInRound, panel1, s);
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console, panel1, s);
            //deal 3 cards to the board
            board.addCards(deck.dealCards(3));
            PokerGraphics.makeBoard(s,panel1,board, playersInRound);
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console, panel1, s);
            //deal 1 cards to the board
            board.addCards(deck.dealCards(1));
            PokerGraphics.makeBoard(s,panel1,board, playersInRound);
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console, panel1, s);
            //deal the final card to the board
            board.addCards(deck.dealCards(1));
            PokerGraphics.makeBoard(s,panel1,board, playersInRound);
            System.out.println(board);
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console, panel1, s);
            //Finds and pays out the winner
            Player roundWinner = findWinner(playersInRound, board);
            //for(Player player : players) {
            //    System.out.println(player);
            //}
            //System.out.println(board);
            System.out.println("The winner of this round is " +  roundWinner.getName() +
                    ". They get " + board.getPotSize() + " chips!");
            board.payOutPot(roundWinner);
            resetHands(players);
            removeLostPlayers(players);
            board.resetBoard();
            playAgain = checkForNextRound(console);
        }
        Player winner = players.get(0);
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getChips() > winner.getChips()) {
                winner = players.get(i);
            }
        }
        System.out.println("The winner of the game is " + winner.getName() + "!");
        System.out.println("Thanks for playing!");
    }

    //Initializes the round.
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
            //Sets the last bet increase to -1 so the computer won't think
            //people have checked this round when they checked last round
            playersInRound.get(i).setLastBet(-1);
        }
    }

    //removes all the players that have folded from the Array of active players that round.
    public static void updateActivePlayers(ArrayList<Player> playersInRound,Board board, DrawingPanel panel1, Graphics s) {
        for(int i = 0; i < playersInRound.size(); i++) {
            //If that player has folded, remove them
            if(playersInRound.get(i).getLastBet() == -2) {
                playersInRound.remove(i);
                i--;
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
        int chipsBetThisRound = playersInRound.get(0).getChipsBetThisRound();
        for(int i = 0; i < playersInRound.size(); i++) {
            //if one player has bet a different amount this round than the others or if they haven't bet this round
            if (playersInRound.get(i).getChipsBetThisRound() != chipsBetThisRound ||
                    playersInRound.get(i).getLastBet() == -1) {
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

    //If someone is all in, return the int amount they bet. Otherwise, return -1
    public static boolean allInSkipBetting(ArrayList<Player> players) {
        int someoneAllIn = -1;
        for(Player player: players) {
            if(player.getChips() == 0) {
                System.out.println(player.getName() + " is all in! Betting is skipped");
                someoneAllIn =  player.getChipsBetThisRound();
            }
        }
        //If someone is all in
        if(someoneAllIn > 0) {
            for(Player player : players) {
                if(player.getChips() < someoneAllIn) {
                    return false;
                }
            }
            //returns true only if everyone has bet at least as much as the player who is all in
            return true;
        } else {
            return false;
        }

    }

    //Removes all the players that lost from the list of people who are currently playing
    public static void removeLostPlayers(ArrayList<Player> players) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getChips() == 0) {
                players.remove(i);
                i--;
            }
        }
    }

    //Does the whole betting phase until everyone checks or someone folds.
    public static void doBettingPhase(ArrayList<Player> playersInRound, Board board, Scanner console,
                                      DrawingPanel panel1, Graphics s) {
        //What is the minimum you can bet?
        int playerBetting = 0;
        //start the betting
        while(playersInRound.size() > 1 && !allHaveChecked(playersInRound) && !allInSkipBetting(playersInRound)) {
            //Gets the player to bet or fold
            Player currPlayer = playersInRound.get(playerBetting);
            int minBet = findMinimumBet(playersInRound, currPlayer);
            currPlayer.getHand().setHandVisibility(true);
            PokerGraphics.dealHands(playersInRound, panel1, s);
            currPlayer.getHand().setHandVisibility(false);
            getPlayerAction(currPlayer, board, console, minBet);
            //removes the inactive players from the playersInRound ArrayList
            updateActivePlayers(playersInRound, board, panel1, s);
            //Testing only
            if(allHaveChecked(playersInRound)) {
                System.out.println("Everyone has checked, so we will move to the next round");
            }
            playerBetting = getNextPlayer(playersInRound, playerBetting);
            panel1.clear();
            PokerGraphics.makeBoard(s,panel1,board, playersInRound, findMinimumBet(playersInRound, playersInRound.get(playerBetting)));
            PokerGraphics.dealHands(playersInRound, panel1, s);
        }
        if(allHaveChecked(playersInRound)) {
            for(int i = 0; i < playersInRound.size(); i++) {
                playersInRound.get(i).setLastBet(-1);
            }
        }
    }

    //Compares everyone's hands and returns the player with the best hand (who's the winner of the round)
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

    public static int findMinimumBet(ArrayList<Player> playersInRound, Player currentPlayer) {
        int highestTotalBet = 0;
        for(Player player : playersInRound) {
            if (player.getChipsBetThisRound() > highestTotalBet) {
                highestTotalBet = player.getChipsBetThisRound();
            }
        }
        int betMinimum = highestTotalBet - currentPlayer.getChipsBetThisRound();
        betMinimum = Math.min(betMinimum, currentPlayer.getChips());
        return betMinimum;
    }

    public static boolean checkForNextRound(Scanner console) {
        System.out.println("Does everyone want to play another round?");
        System.out.println("Type Y for yes and N for no");
        String response = console.next().toUpperCase();
        while(!response.equals("Y") && !response.equals("N")) {
            System.out.println("Please try that again");
            System.out.println("Does everyone want to play another round?");
            System.out.println("Type Y for yes and N for no");
        }
        if(response.equals("Y")) {
            System.out.println("Great!");
            return true;
        } else {
            System.out.println("Thanks for playing!");
            return false;
        }
    }
    //Gets a response from the player (whether they bet, checked, or folded (and the amount if betting)
    public static void getPlayerAction(Player player, Board board, Scanner console, int minimumBet) {
        System.out.println("It is " + player.getName() + "'s turn.");
        //System.out.println( player.toString());
        //System.out.println(board.toString());
        System.out.println("There are " + board.getPotSize() + " chips in the pot.");
        System.out.println("Type B to bet or type F to fold");
        String actionType = console.next().toUpperCase();
        //Makes sure the input is B or F (can add Checking later but is already implemented in betting.)
        while(!actionType.equals("F") && !actionType.equals("B")) {
            System.out.println("You typed " + actionType);
            System.out.println("That's not a valid command.\nPlease type B to bet or type F to fold");
            actionType = console.next().toUpperCase();
        }
        if(actionType.equals("B")) {
            //BetMinimum is the minimum you can bet
            System.out.println("How much do you want to bet? You have to bet at least " + minimumBet + " chips.");
            int betAmount = console.nextInt();
            while (betAmount < minimumBet || betAmount > player.getChips()) {
                System.out.println("Please bet an amount between " + minimumBet + " and your current chips (" + player.getChips() + ").");
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