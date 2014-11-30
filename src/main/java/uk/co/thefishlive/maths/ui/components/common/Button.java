package uk.co.thefishlive.maths.ui.components.common;

import uk.co.thefishlive.maths.ui.components.UIComponent;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 *
 */
public class Button extends UIComponent {

    private static final int DEFAULT_WIDTH = 60;
    private static final int DEFAULT_HEIGHT = 30;

    private final String text;
    private final Rectangle2D bounds;

    public Button(String text, int x, int y) {
        this(text, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Button(String text, int x, int y, int width, int height) {
        this.text = text;
        this.bounds = new Rectangle2D.Double(x, y, width, height);
    }


    @Override
    public void paintComponent(Graphics2D g) {
        g.setColor(new Color(0xFF3D00));
        g.fill(bounds);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 0, 18));
        g.drawString(this.text, (int) (bounds.getX() + 8), (int) (bounds.getY() + 20));
    }

}
