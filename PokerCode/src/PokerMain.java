import java.util.ArrayList;
import java.util.Scanner;

public class PokerMain {
    public static void main(String[]args) {
        Scanner console = new Scanner(System.in);
        Deck deck = new Deck(true);
        Board board = new Board();
        //An arrayList of all players in the game
        ArrayList<Player> players = new ArrayList<Player>();
        //An arrayList of the players in the current round
        ArrayList<Player> playersInRound = new ArrayList<Player>();
        int ante;
        //Initialize the list of players
        System.out.println("How many players?");
        int numPlayers = console.nextInt();
        for(int i = 0; i < numPlayers; i++) {
            System.out.println("Please enter the name of Player " + i + ": ");
            String playerName = console.next();
            players.add(new Player(100, playerName));
        }

    }
    //deals cards to each player
    public void dealCards(ArrayList<Player> playerList, Deck deck) {
        for(int i = 0; i < playerList.size();i++) {
            playerList.get(i).resetHand();
            playerList.get(i).dealCards(deck.dealCards(2));
        }
    }

    public void startRound(ArrayList<Player> players, ArrayList<Player> playersInRound, Board board, int ante) {
        playersInRound.clear();
        playersInRound.addAll(players);
        System.out.println("Everyone bets the ante");
        for(int i = 0; i < playersInRound.size(); i++) {
            playersInRound.get(i).addChips(-ante);
            board.addChipsToPot(ante);
        }
    }

    //removes all of the players that have folded
    public void updateActivePlayers(ArrayList<Player> playersInRound) {
        for(int i = 0; i < playersInRound.size(); i++) {
            if(playersInRound.get(i).getLastBet() == -1) {
                playersInRound.remove(i);
            }
        }
    }

    //returns the index of the next active player
    public int getNextPlayer(ArrayList<Player> playersInRound, int index) {
        if(index < playersInRound.size() - 1) {
            return index + 1;
        } else {
            return 0;
        }
    }

    //Removes all the players that lost from the list of people who are currently playing
    public void removeLostPlayers(ArrayList<Player> players) {
        for(int i = 0; i < players.size(); i++) {
            int chips = players.get(i).getChips();
            boolean lostLastHand = (players.get(i).getLastBet() == -1);
            if(chips == 0 && lostLastHand) {
                players.remove(i);
            }
        }
    }

    //Gets a response from the player (whether they bet, checked, or folded (and the amount if betting)
    public void getPlayerAction(Player player, Board board, Scanner console, ArrayList<Player> players, ArrayList<Player> playersInRound) {
        System.out.println("Type B to bet");
        System.out.print(", type F to fold");
        String actionType = console.next();
        //Makes sure the input is B or F (can add Checking later but is already implemented in betting.)
        while(actionType != "F" && actionType != "B") {
            System.out.println("That's not a valid command./nPlease type B, or F/nType B to bet");
            System.out.print(", type F to fold");;
            actionType = console.next();
        }
        if(actionType == "B") {
            System.out.println("How much do you want to bet? The current bet is " + players.get(players.indexOf(player) - 1).getLastBet() + " chips.");
            int betAmount = console.nextInt();
            while (betAmount < players.get(players.indexOf(player) - 1).getLastBet() || betAmount > player.getChips()) {
                System.out.println("Please bet an amount between " + players.get(players.indexOf(player) - 1).getLastBet() + " and your current chips (" + player.getChips() + ").");
                betAmount = console.nextInt();
            }
            player.addChips(-betAmount);
            player.setLastBet(betAmount);
            board.addChipsToPot(betAmount);
        } else if (actionType == "F") {
            playersInRound.remove(players.indexOf(player));
            System.out.println("You folded. Better luck next round!");
        }
    }
}
