import java.util.ArrayList;
import java.util.Scanner;

public class PokerMain {
    public static void main(String[]args) {
        Scanner console = new Scanner(System.in);
        Deck deck = new Deck();
        Board board = new Board();
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

    public void getPlayerAction(boolean foldable, boolean checkable, Scanner console) {
        System.out.println("Type B to bet");
        if(foldable) {
            System.out.print(", type F to fold");
        }
        if(checkable) {
            System.out.print(", type C to check");
        }
        String actionType = console.next();
        if(actionType != "C" && actionType != "F" && actionType != "B") {
            
        }
    }


}
