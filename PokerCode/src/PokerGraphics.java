import java.awt.*;
import java.util.ArrayList;

public class PokerGraphics {
    public static final int CARD_SIZE = 100;
    public static final int PANEL_WIDTH = 1000;

    public static void main(String[] args) {
        DrawingPanel panel1 = new DrawingPanel(PANEL_WIDTH,(int) (PANEL_WIDTH * 0.6));
        Graphics s = panel1.getGraphics();
    }

    // Draws an image of the playing board.
    public static void makeBoard(Graphics s, DrawingPanel panel1, Board board) {
        Color boardGreen = new Color(10,100,10);
        panel1.setBackground(boardGreen);
        s.setColor(Color.LIGHT_GRAY);
        for (int i = -3; i < 3; i++) {
            s.drawRect(panel1.getWidth() / 2 + i * (CARD_SIZE + CARD_SIZE / 6), panel1.getHeight() / 2 - (int)(CARD_SIZE * 0.7), CARD_SIZE,(int)(CARD_SIZE * 1.4));
            if (board.getCards().size() > 0 && i < board.getCards().size() - 3) {
                drawCard(board.getCards().get(i+3),panel1.getWidth() / 2 + (i + 1) * (CARD_SIZE + CARD_SIZE / 6),panel1.getHeight() / 2 - (int)(CARD_SIZE * 0.7), CARD_SIZE, s);
            }

            //Just run the makeBoard method every time a new round starts, after the cards are added to the commCards in PokerMain
        }
        s.drawOval((panel1.getWidth() / 8),panel1.getHeight() / 4,(panel1.getWidth() * 6/8),panel1.getHeight() / 2);

        // Draws an image to show the deck of cards. Change i to increase the height of the deck.
        for (int i = 0; i <10; i++) {
            s.setColor(Color.WHITE);
            s.fillRect(panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + i, panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) - 2 * i, CARD_SIZE, (int)(CARD_SIZE * 1.4));
            s.setColor(Color.RED);
            s.fillRect(panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + (int)(CARD_SIZE * 0.05) + i,panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) + (int) (CARD_SIZE * 0.05) - 2 * i, (int)(CARD_SIZE * 0.9), (int)(CARD_SIZE * 1.4 * 0.935));
            s.setColor(Color.BLACK);
            s.drawRect(panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + i, panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) - 2 * i, CARD_SIZE, (int)(CARD_SIZE * 1.4));
            s.drawOval(panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + (int)(CARD_SIZE * 0.1) + i, panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) + (int) (CARD_SIZE*0.1) - 2 * i,(int) (CARD_SIZE * 0.8), (int) (CARD_SIZE * 1.4 * 0.85));
            Font g = new Font("Helvetica", Font.ITALIC, (int) (CARD_SIZE / 6));
            Font h = new Font("Helvetica", Font.ITALIC, (int) (CARD_SIZE / 10));
            s.setFont(h);
            s.drawString("Rowan & Eli\'s",panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + (int) (CARD_SIZE/5.5) + i,panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) + (int) (CARD_SIZE/3*1.4) - 2 * i);
            s.setFont(g);
            s.drawString("Texas",panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + (int) (CARD_SIZE/4) + i,panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) + (int) (CARD_SIZE/2.25*1.4) - 2 * i);
            s.drawString("Hold",panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + (int) (CARD_SIZE/3.3) + i,panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) + (int) (CARD_SIZE/1.7*1.4) - 2 * i);
            s.drawString("\'Em",panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + (int) (CARD_SIZE/3) + i,panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) + (int) (CARD_SIZE/2*1.4 + CARD_SIZE/4.5*1.4) - 2 * i);
        }
    }

    // Draws an image displaying a card. If visibility boolean is false, it displays the back of the card.
    public static void drawCard(Card card, int x, int y, double size, Graphics s) {
        // Draws the white border of the card
        s.setColor(Color.WHITE);
        s.fillRect(x,y, (int) size, (int) (size * 1.4));
        // Draws the red background of the card
        s.setColor(Color.RED);
        s.fillRect(x + (int) (size * 0.05),y + (int) (size * 0.05), (int) (size * 0.9), (int) (size * 1.4 * 0.935));
        // Outlines the card for visibility.
        s.setColor(Color.BLACK);
        s.drawRect(x,y, (int) size, (int) (size * 1.4));

        Font g = new Font("Helvetica", Font.ITALIC, (int) (size / 6));
        Font h = new Font("Helvetica", Font.ITALIC, (int) (size / 10));

        // Shows card info if the card is visible
        if (card.getIsVisible()) {
            // Writes the value of the card on the card
            s.setColor(Color.BLACK);
            Font f = new Font("Helvetica", Font.BOLD, (int) (size / 3));
            s.setFont(f);
            if (!card.getName().equals("10")) {
                s.drawString(card.getName().substring(0, 1), x + (int) (size / 2) + (int) (size / 5), y + (int) (size / 8) + (int) (size / 4));
                s.drawString(card.getName().substring(0, 1), x + (int) (size / 8), y + (int) (size * 1.4) - (int) (size / 8));
            } else {
                s.drawString(card.getName().substring(0, 2), x + (int) (size / 2.5) + (int) (size / 7), y + (int) (size / 8) + (int) (size / 4));
                s.drawString(card.getName().substring(0, 2), x + (int) (size / 16), y + (int) (size * 1.4) - (int) (size / 8));
            }
            // Creates a shape for each suit (The ratio of length to width on a card is 1.4, so 0.7 represents half the length.)
            Polygon heart = new Polygon();
            heart.addPoint(x + (int) (size / 2) + (int) (size / 3.8), y + (int) (size*1.05 * 0.7 - size/8));
            heart.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) + (int) (size / 3 - size/8));
            heart.addPoint(x + (int) (size / 2) - (int) (size / 3.8), y + (int) (size*1.05 * 0.7 - size/8));
            heart.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size/8));

            Polygon diamond = new Polygon();
            diamond.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 3));
            diamond.addPoint(x + (int) (size / 2) + (int) (size / 4), y + (int) (size * 0.7));
            diamond.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) + (int) (size / 3));
            diamond.addPoint(x + (int) (size / 2) - (int) (size / 4), y + (int) (size * 0.7));

            Polygon spade = new Polygon();
            spade.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 3));
            spade.addPoint(x + (int) (size / 2) + (int) (size / 4), y + (int) (size*0.95 * 0.7));
            spade.addPoint(x + (int) (size / 2), y + (int) (size * 0.7));
            spade.addPoint(x + (int) (size / 2) + (int) (size / 10), y + (int) (size * 0.7) + (int) (size / 4));
            spade.addPoint(x + (int) (size / 2) - (int) (size / 10), y + (int) (size * 0.7) + (int) (size / 4));
            spade.addPoint(x + (int) (size / 2), y + (int) (size * 0.7));
            spade.addPoint(x + (int) (size / 2) - (int) (size / 4), y + (int) (size*0.95 * 0.7));

            // Draws the suit on the card
            if (card.getSuit() == Suit.HEART) {
                s.fillPolygon(heart);
                s.fillOval(x + (int) (size / 4), y + (int) (size * 0.7) - (int) (size / 4), (int) (size / 4), (int) (size / 4));
                s.fillOval(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 4), (int) (size / 4), (int) (size / 4));
            } else if (card.getSuit() == Suit.SPADE) {
               s.fillPolygon(spade);
                s.fillOval(x + (int) (size / 4), y + (int) (size * 0.7) - (int) (size / 8), (int) (size / 4), (int) (size / 4));
                s.fillOval(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 8), (int) (size / 4), (int) (size / 4));
            } else if (card.getSuit() == Suit.DIAMOND) {
                s.fillPolygon(diamond);
            } else if (card.getSuit() == Suit.CLUB) {
                s.fillOval(x + (int) (size / 3), y + (int) (size * 0.7) - (int) (size / 3), (int) (size / 3), (int) (size / 3));
                s.fillOval(x + (int) (size / 6), y + (int) (size * 0.7) - (int) (size / 8), (int) (size / 3), (int) (size / 3));
                s.fillOval(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 8), (int) (size / 3), (int) (size / 3));
                Polygon triangle = new Polygon();
                triangle.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 20));
                triangle.addPoint(x + (int) (size / 2) + (int) (size / 8), y + (int) (size * 0.7) + (int) (size / 3));
                triangle.addPoint(x + (int) (size / 2) - (int) (size / 8), y + (int) (size * 0.7) + (int) (size / 3));
                s.fillPolygon(triangle);
            }
            // If card is not visible, draws art on the back of the card.
        } else {
            s.drawOval(x + (int)(size * 0.1), y + (int) (size*0.1),(int) (size * 0.8), (int) (size * 1.4 * 0.85));
            s.setFont(h);
            s.drawString("Rowan & Eli\'s",x + (int) (size/5.5),y + (int) (size/3*1.4));
            s.setFont(g);
            s.drawString("Texas",x + (int) (size/4),y + (int) (size/2.25*1.4));
            s.drawString("Hold",x + (int) (size/3.3),y + (int) (size/1.7*1.4));
            s.drawString("\'Em",x + (int) (size/3),y + (int) (size/2*1.4 + size/4.5*1.4));

        }
    }
    
    public static void dealHands (ArrayList<Player> players, DrawingPanel panel1, Graphics s) {
        int numPlayers = players.size();
        for (int i = 0; i < numPlayers/2; i++) {
            drawHand(players.get(i), (panel1.getWidth() / ((numPlayers/2) + 1)) + i * (panel1.getWidth() / ((numPlayers/2) + 1)) - (CARD_SIZE + CARD_SIZE/6),(panel1.getHeight() / 20), panel1, s);
        }

        for (int i = 0; i < (int)Math.round(numPlayers/2.0); i++) {
            drawHand(players.get(i + numPlayers/2 ), (int)(panel1.getWidth() / (Math.round(numPlayers/2.0) + 1)) + i  * (int)(panel1.getWidth() / (Math.round(numPlayers/2.0) + 1)) - (CARD_SIZE + CARD_SIZE/6),(panel1.getHeight() * 7/9), panel1, s);
        }
    }

    public static void drawHand (Player player, int x, int y, DrawingPanel panel1, Graphics s) {
        drawCard(player.getHand().getCard(0),x ,y, CARD_SIZE, s);
        drawCard(player.getHand().getCard(1),x + (CARD_SIZE + CARD_SIZE/6) ,y, CARD_SIZE, s);
        Font g = new Font("Helvetica", Font.ITALIC, (int) (CARD_SIZE / 6));
        s.setColor(Color.GRAY);
        s.fillRect(x - 5, y + ((int)(CARD_SIZE * 1.42)), (CARD_SIZE * 2 + CARD_SIZE/6), CARD_SIZE/6);
        s.setFont(g);
        s.setColor(Color.WHITE);
        s.drawString(player.getName() + ": " + player.getChips() + " chips.", x, y + ((int)(CARD_SIZE * 1.4) + CARD_SIZE/6));

    }
}
