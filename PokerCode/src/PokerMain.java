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
            int playerBetting = 0;
            while(playersInRound.size() > 1 && !allHaveChecked(playersInRound)) {
                getPlayerAction(playersInRound.get(playerBetting), board, console, playersInRound(playerBetting - 1).get)
            }
            //start the betting
            /*
            while(not everyone has checked) {
                continue the betting phase
            }
            deal 3 cards to the board
            while(not everyone has checked) {
                continue the betting phase
            }
            deal one more card to the board
            while(not everyone has checked) {
                continue the betting phase
            }
            deal one more card to the board
            while(not everyone has checked) {
                continue the betting phase
            }
            people show their cards and the winner is revealed (and the pot is paid out)

             */
        }

    }
    //deals cards to each player
    public static void dealCards(ArrayList<Player> playerList, Deck deck) {
        for(int i = 0; i < playerList.size();i++) {
            playerList.get(i).resetHand();
            playerList.get(i).dealCards(deck.dealCards(2));
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

    //Gets a response from the player (whether they bet, checked, or folded (and the amount if betting)
    public static void getPlayerAction(Player player, Board board, Scanner console, int lastPlayerBet) {
        System.out.println("Type B to bet, type F to fold, or type C to check");
        String actionType = console.next();
        //Makes sure the input is B, C, or F
        while(actionType != "C" && actionType != "F" && actionType != "B") {
            System.out.println("That's not a valid command./nPlease type B, C, or F");
            System.out.println("Type B to bet, type F to fold, type C to check");
            actionType = console.next();
        }
        if(actionType == "B") {
            System.out.println("How much do you want to bet?");
            int betAmount = console.nextInt();
            while (betAmount < lastPlayerBet || betAmount > player.getChips()) {
                System.out.println("Please bet an amount between" + lastPlayerBet + " and " + player.getChips());
                betAmount = console.nextInt();
            }
            player.addChips(-betAmount);
            player.setLastBetIncrease(betAmount - lastPlayerBet);
            board.addChipsToPot(betAmount);
        } else if(actionType == "C") {
            player.setLastBetIncrease(0);
        } else {
            player.setLastBetIncrease(-1);
        }
    }
}
