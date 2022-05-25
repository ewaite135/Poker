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
        int lastBet = 0;
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
            //Start new round
            startRound(players, playersInRound, deck, board, ANTE);
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
            //Continues betting until everyone has folded or checked
            doBettingPhase(playersInRound, board, console);
            //Finds and pays out the winner
            Player roundWinner = findWinner(playersInRound);
            board.payOutPot(roundWinner);
            resetHands(players);
            removeLostPlayers(players);
            board.resetBoard();
        }
    }

    public static void startRound(ArrayList<Player> players, ArrayList<Player> playersInRound, Deck deck, Board board, int ante) {
        playersInRound.clear();
        playersInRound.addAll(players);
        System.out.println("Everyone bets the ante");
        for(int i = 0; i < playersInRound.size(); i++) {
            //deals 2 cards to each player
            playersInRound.get(i).dealCards(deck.dealCards(2));
            //everybody bets the ante
            playersInRound.get(i).addChips(-ante);
            board.addChipsToPot(ante);
        }
    }

    //removes all of the players that have folded
    public static void updateActivePlayers(ArrayList<Player> playersInRound) {
        for(int i = 0; i < playersInRound.size(); i++) {
            if(playersInRound.get(i).getLastBetIncrease() == -1) {
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
        boolean allChecked = true;
        for(Player player : playersInRound) {
            if (player.getLastBetIncrease() != 0) {
                allChecked = false;
            }
        }
        return allChecked;
    }

    public static boolean gameOver(ArrayList<Player> players) {
        int playersWithChips = 0;
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getChips() > 0) {
                playersWithChips++;
            }
        }
        return (playersWithChips < 2);
    }

    //Removes all the players that lost from the list of people who are currently playing
    public static void removeLostPlayers(ArrayList<Player> players) {
        for(int i = 0; i < players.size(); i++) {
            int chips = players.get(i).getChips();
            boolean lostLastHand = (players.get(i).getLastBetIncrease() == -1);
            if(chips == 0 && lostLastHand) {
                players.remove(i);
            }
        }
    }

    public static void doBettingPhase(ArrayList<Player> playersInRound, Board board, Scanner console) {
        int playerBetting = 0;
        int lastBet = 0;
        //start the betting
        while(playersInRound.size() > 1 && !allHaveChecked(playersInRound)) {
            //Gets the player to bet or fold
            getPlayerAction(playersInRound.get(playerBetting), board, console, lastBet);
            //increases the last bet variable to know the minimum amount needed to bet.
            lastBet += playersInRound.get(playerBetting).getLastBetIncrease();
            //Goes to the next player.
            playerBetting = getNextPlayer(playersInRound, playerBetting);
            updateActivePlayers(playersInRound);

        }
    }

    public static Player findWinner(ArrayList<Player> playersInRound) {
        int highestHandIndex = 0;
        for(int i = 0; i < playersInRound.size(); i++) {
            if(playersInRound.get(i).getHandVal() > highestHandIndex) {
                highestHandIndex = i;
            }
        }
        return playersInRound.get(highestHandIndex);
    }

    public static void resetHands(ArrayList<Player> players) {
        for(int i = 0; i < players.size(); i++) {
            players.get(i).resetHand();
        }
    }

    public static void updateCardsOnBoard(ArrayList<Player>players, Board board) {
        for(int i = 0; i < players.size(); i++) {
        }
    }
    //Gets a response from the player (whether they bet, checked, or folded (and the amount if betting)
    public static void getPlayerAction(Player player, Board board, Scanner console, int lastPlayerBet) {
        System.out.println("Type B to bet or type F to fold");
        String actionType = console.next();
        //Makes sure the input is B or F (can add Checking later but is already implemented in betting.)
        while(actionType != "F" && actionType != "B" && actionType != "C") {
            System.out.println("That's not a valid command./nPlease type B, or F");
            System.out.print("Type B to bet or type F to fold");;
            actionType = console.next();
        }
        if(actionType == "B") {
            System.out.println("How much do you want to bet? The current bet is " + lastPlayerBet + " chips.");
            int betAmount = console.nextInt();
            while (betAmount < lastPlayerBet || betAmount > player.getChips()) {
                System.out.println("Please bet an amount between " + lastPlayerBet + " and your current chips (" + player.getChips() + ").");
                betAmount = console.nextInt();
            }
            player.addChips(-betAmount);
            player.setLastBetIncrease(betAmount - lastPlayerBet);
            board.addChipsToPot(betAmount);
        } else if (actionType == "F") {
            System.out.println("You folded. Better luck next round!");
        }
    }
}
