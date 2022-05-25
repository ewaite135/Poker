import java.awt.*;

public class PokerGraphics {

    public static void main(String[] args) {
        DrawingPanel panel1 = new DrawingPanel(500,500);
        Graphics s = panel1.getGraphics();
        panel1.setBackground(Color.GRAY);
        Card card1 = new Card("3", Suit.CLUB);
        drawCard(card1, 50, 50, 100, s);
    }

    public static void drawCard(Card card, int x, int y, double size, Graphics s) {
        // Draws the white border of the card
        s.setColor(Color.WHITE);
        s.fillRect(x,y, (int) size, (int) (size * 1.4));
        // Draws the red background of the card
        s.setColor(Color.RED);
        s.fillRect(x + (int) (size * 0.05),y + (int) (size * 0.05), (int) (size * 0.9), (int) (size * 1.4 * 0.935));
        // Writes the value of the card on the card
        s.setColor(Color.BLACK);
        Font f = new Font ("Helvetica",Font.BOLD, (int) (size /3));
        s.setFont(f);
        s.drawString(card.getName().substring(0, 1), x + (int) (size / 2) + (int) (size / 7), y + (int) (size / 8) + (int) (size / 4));
        s.drawString(card.getName().substring(0, 1), x + (int) (size / 8), y + (int) (size * 1.4) - (int) (size / 8));
        // Creates a shape for each suit (The ratio of length to width on a card is 1.4, so 0.7 represents half the length.)
        Polygon heart = new Polygon();
        heart.addPoint(x + (int)(size/2),y + (int)(size * 0.7));
        heart.addPoint(x + (int)(size/2) + (int)(size/8),y + (int)(size * 0.7) - (int)(size/8));
        heart.addPoint(x + (int)(size/2) + (int)(size/4), y + (int)(size * 0.7) - (int)(size/8));
        heart.addPoint(x + (int)(size/2) + (int)(size/8*3),y + (int)(size * 0.7));
        heart.addPoint(x + (int)(size/2),y + (int)(size * 0.7) + (int)(size/3));
        heart.addPoint(x + (int)(size/2) - (int)(size/8*3),y + (int)(size * 0.7));
        heart.addPoint(x + (int)(size/2) - (int)(size/4), y + (int)(size * 0.7) - (int)(size/8));
        heart.addPoint(x + (int)(size/2) - (int)(size/8),y + (int)(size * 0.7) - (int)(size/8));

        Polygon diamond = new Polygon();
        diamond.addPoint(x + (int)(size/2),y + (int)(size * 0.7) - (int)(size/3));
        diamond.addPoint(x + (int)(size/2) + (int)(size/4),y + (int)(size * 0.7));
        diamond.addPoint(x + (int)(size/2),y + (int)(size * 0.7) + (int)(size/3));
        diamond.addPoint(x + (int)(size/2) - (int)(size/4),y + (int)(size * 0.7));

        Polygon spade = new Polygon();
        spade.addPoint(x + (int)(size/2), y + (int)(size * 0.7) - (int)(size/3));
        spade.addPoint(x + (int)(size/2) + (int)(size/4),y + (int)(size * 0.7));
        spade.addPoint(x + (int)(size/2) + (int)(size/12),y + (int)(size * 0.7));
        spade.addPoint(x + (int)(size/2) + (int)(size/8),y + (int)(size * 0.7) + (int)(size/8));
        spade.addPoint(x + (int)(size/2) - (int)(size/8),y + (int)(size * 0.7) + (int)(size/8));
        spade.addPoint(x + (int)(size/2) - (int)(size/12),y + (int)(size * 0.7));
        spade.addPoint(x + (int)(size/2) - (int)(size/4),y + (int)(size * 0.7));
        spade.addPoint(x + (int)(size/2) - (int)(size/4),y + (int)(size * 0.7));

        // Draws the suit on the card
        if (card.getSuit() == Suit.HEART) {
            s.fillPolygon(heart);
        } else if (card.getSuit() == Suit.SPADE) {
            s.fillPolygon(spade);
        } else if (card.getSuit() == Suit.DIAMOND) {
            s.fillPolygon(diamond);
        } else if (card.getSuit() == Suit.CLUB) {       // Club requires more than a polygon, so is displayed here.
            s.fillOval(x + (int)(size/3),y + (int)(size * 0.7) - (int)(size/3),(int)(size/3), (int)(size/3));
            s.fillOval(x + (int)(size/6),y + (int)(size * 0.7) - (int)(size/8),(int)(size/3), (int)(size/3));
            s.fillOval(x + (int)(size/2),y + (int)(size * 0.7) - (int)(size/8),(int)(size/3), (int)(size/3));
            Polygon triangle = new Polygon();
            triangle.addPoint(x + (int)(size/2),y + (int)(size * 0.7) - (int)(size/20));
            triangle.addPoint(x + (int)(size/2) + (int)(size/8), y + (int)(size * 0.7) + (int)(size/3));
            triangle.addPoint(x + (int)(size/2) - (int)(size/8), y + (int)(size * 0.7) + (int)(size/3));
            s.fillPolygon(triangle);
        }



    }


}
