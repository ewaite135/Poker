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

    //Sees if every player has checked.
    public boolean allHaveChecked(ArrayList<Player> playersInRound) {
        boolean allChecked = true;
        for(Player player : playersInRound) {
            if (player.getLastBet() != 0) {
                allChecked = false;
            }
        }
        return allChecked;
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
    public void getPlayerAction(Player player, Board board, Scanner console, boolean foldable, boolean checkable) {
        System.out.println("Type B to bet");
        if(foldable) {
            System.out.print(", type F to fold");
        }
        if(checkable) {
            System.out.print(", type C to check");
        }
        String actionType = console.next();
        //Makes sure the input is B, C, or F
        while(actionType != "C" && actionType != "F" && actionType != "B") {
            System.out.println("That's not a valid command./nPlease type B, C, or F/nType B to bet");
            if(foldable) {
                System.out.print(", type F to fold");
            }
            if(checkable) {
                System.out.print(", type C to check");
            }
            actionType = console.next();
        }
        if(actionType == "B") {
            System.out.println("How much do you want to bet?");
            int betAmount = console.nextInt();
            while (betAmount < 0 || betAmount > player.getChips()) {
                System.out.println("Please bet an amount between 0 and " + player.getChips());
                betAmount = console.nextInt();
            }
            player.addChips(-betAmount);
            player.setLastBet(betAmount);
            board.addChipsToPot(betAmount);
        } else if(actionType == "C") {
            player.setLastBet(0);
        } else {
            player.setLastBet(-1);
        }
    }
}
