import java.awt.*;

public class PokerGraphics {

    public static void main(String[] args) {
        DrawingPanel panel1 = new DrawingPanel(500,500);
        Graphics s = panel1.getGraphics();
        panel1.setBackground(Color.WHITE);
        Card card1 = new Card("King", Spades);


    }

    public static void drawCard(Card card, int x, int y, int size, Graphics s) {
        s.setColor(Color.WHITE);
        s.drawRect(x,y, size, size * 2);
        s.setColor(Color.RED);
        s.drawRect(x,y, size * 0.95, size * 2 * 0.95);
        s.setColor(Color.BLACK);
        Font f = new Font ("Helvetica",Font.BOLD, size * 2/3)
        s.drawString("", x + 1/3, y + 1/3);


    }


}
