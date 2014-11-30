package uk.co.thefishlive.maths.ui.components.header;

import uk.co.thefishlive.maths.ui.components.UIComponent;

import java.awt.*;

/**
 *
 */
public class UserBar extends UIComponent {
    private static final int HEIGHT = 40;

    private final int x;
    private final int y;
    private double width = -1;
    private String text;

    public UserBar(String name, int x, int y) {
        this.x = x;
        this.y = y;

        this.text = name;
    }

    @Override
    public void paintComponent(Graphics2D g) {
        if (width == -1) {
            width = g.getFontMetrics(new Font("Arial", 0, 18)).getStringBounds(text, g).getWidth() + 30;
        }

        g.setColor(new Color(0x2196f3));
        g.fillRect((int) (x - width), y, (int) width, HEIGHT);
        g.fillPolygon(new int[]{(int) (x - width), (int) (x - width - HEIGHT), (int) (x - width)}, new int[]{y, y, y + HEIGHT}, 3);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 0, 18));
        g.drawString(this.text, (int) (x - width + 5), 24);

        g.setPaint(new GradientPaint((int) (x - (width / 2)), y + HEIGHT, new Color(0xBDBDBD), (int) (x - (width / 2)), y + HEIGHT + 4, Color.WHITE));
        g.fillPolygon(new int[]{(int) (x - width), (int) (x - width + 4), x, x}, new int[]{y + HEIGHT, y + HEIGHT + 4, y + HEIGHT + 4, y + HEIGHT}, 4);

    }
}
