import java.awt.*;

public class PokerGraphics {
    public static final int CARD_SIZE = 100;

    public static void main(String[] args) {
        DrawingPanel panel1 = new DrawingPanel(1000,600);
        Graphics s = panel1.getGraphics();
        makeBoard(s,panel1);
        Card card1 = new Card("7", Suit.CLUB,true);
        drawCard(card1, 400, 400, 100, s);
        Card card2 = new Card("Ace", Suit.HEART,true);
        drawCard(card2, 520, 400, 100, s);

    }

    // Draws an image of the playing board.
    public static void makeBoard(Graphics s, DrawingPanel panel1) {
        Color boardGreen = new Color(10,100,10);
        panel1.setBackground(boardGreen);
        s.setColor(Color.LIGHT_GRAY);
        for (int i = -3; i < 3; i++) {
            s.drawRect(panel1.getWidth() / 2 + i * (CARD_SIZE + CARD_SIZE / 6), panel1.getHeight() / 2 - (int)(CARD_SIZE * 0.7), CARD_SIZE,(int)(CARD_SIZE * 1.4));
        }
        s.drawOval((panel1.getWidth() / 8),panel1.getHeight() / 4,(int)(panel1.getWidth() * 6/8),panel1.getHeight() / 2);

        // Draws an image to show the deck of cards. Change i to increase the height of the deck.
        for (int i = 0; i <10; i++) {
            s.setColor(Color.WHITE);
            s.fillRect(panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + i, panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) - 2 * i, CARD_SIZE, (int)(CARD_SIZE * 1.4));
            s.setColor(Color.RED);
            s.fillRect(panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + (int)(CARD_SIZE * 0.05) + i,panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) + (int) (CARD_SIZE * 0.05) - 2 * i, (int)(CARD_SIZE * 0.9), (int)(CARD_SIZE * 1.4 * 0.935));
            s.setColor(Color.BLACK);
            s.drawRect(panel1.getWidth() / 2 + -3 * (CARD_SIZE + CARD_SIZE / 6) + i, panel1.getHeight() / 2 - (int) (CARD_SIZE * 0.7) - 2 * i, CARD_SIZE, (int)(CARD_SIZE * 1.4));
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

        // Shows card info if the card is visible
        if (card.getIsVisible()) {
            // Writes the value of the card on the card
            s.setColor(Color.BLACK);
            Font f = new Font("Helvetica", Font.BOLD, (int) (size / 3));
            s.setFont(f);
            s.drawString(card.getName().substring(0, 1), x + (int) (size / 2) + (int) (size / 7), y + (int) (size / 8) + (int) (size / 4));
            s.drawString(card.getName().substring(0, 1), x + (int) (size / 8), y + (int) (size * 1.4) - (int) (size / 8));
            // Creates a shape for each suit (The ratio of length to width on a card is 1.4, so 0.7 represents half the length.)
            Polygon heart = new Polygon();
            heart.addPoint(x + (int) (size / 2), y + (int) (size * 0.7));
            heart.addPoint(x + (int) (size / 2) + (int) (size / 8), y + (int) (size * 0.7) - (int) (size / 8));
            heart.addPoint(x + (int) (size / 2) + (int) (size / 4), y + (int) (size * 0.7) - (int) (size / 8));
            heart.addPoint(x + (int) (size / 2) + (int) (size / 8 * 3), y + (int) (size * 0.7));
            heart.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) + (int) (size / 3));
            heart.addPoint(x + (int) (size / 2) - (int) (size / 8 * 3), y + (int) (size * 0.7));
            heart.addPoint(x + (int) (size / 2) - (int) (size / 4), y + (int) (size * 0.7) - (int) (size / 8));
            heart.addPoint(x + (int) (size / 2) - (int) (size / 8), y + (int) (size * 0.7) - (int) (size / 8));

            Polygon diamond = new Polygon();
            diamond.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 3));
            diamond.addPoint(x + (int) (size / 2) + (int) (size / 4), y + (int) (size * 0.7));
            diamond.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) + (int) (size / 3));
            diamond.addPoint(x + (int) (size / 2) - (int) (size / 4), y + (int) (size * 0.7));

            Polygon spade = new Polygon();
            spade.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 3));
            spade.addPoint(x + (int) (size / 2) + (int) (size / 4), y + (int) (size * 0.7));
            spade.addPoint(x + (int) (size / 2) + (int) (size / 12), y + (int) (size * 0.7));
            spade.addPoint(x + (int) (size / 2) + (int) (size / 8), y + (int) (size * 0.7) + (int) (size / 8));
            spade.addPoint(x + (int) (size / 2) - (int) (size / 8), y + (int) (size * 0.7) + (int) (size / 8));
            spade.addPoint(x + (int) (size / 2) - (int) (size / 12), y + (int) (size * 0.7));
            spade.addPoint(x + (int) (size / 2) - (int) (size / 4), y + (int) (size * 0.7));
            spade.addPoint(x + (int) (size / 2) - (int) (size / 4), y + (int) (size * 0.7));

            // Draws the suit on the card
            if (card.getSuit() == Suit.HEART) {
                s.fillPolygon(heart);
            } else if (card.getSuit() == Suit.SPADE) {
                s.fillPolygon(spade);
            } else if (card.getSuit() == Suit.DIAMOND) {
                s.fillPolygon(diamond);
            } else if (card.getSuit() == Suit.CLUB) {          // Club requires more than a polygon, so is displayed here.
                s.fillOval(x + (int) (size / 3), y + (int) (size * 0.7) - (int) (size / 3), (int) (size / 3), (int) (size / 3));
                s.fillOval(x + (int) (size / 6), y + (int) (size * 0.7) - (int) (size / 8), (int) (size / 3), (int) (size / 3));
                s.fillOval(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 8), (int) (size / 3), (int) (size / 3));

                Polygon triangle = new Polygon();
                triangle.addPoint(x + (int) (size / 2), y + (int) (size * 0.7) - (int) (size / 20));
                triangle.addPoint(x + (int) (size / 2) + (int) (size / 8), y + (int) (size * 0.7) + (int) (size / 3));
                triangle.addPoint(x + (int) (size / 2) - (int) (size / 8), y + (int) (size * 0.7) + (int) (size / 3));
                s.fillPolygon(triangle);
            }

        }



    }


}
