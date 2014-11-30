package uk.co.thefishlive.maths.ui.components.header;

import uk.co.thefishlive.maths.ui.components.UIComponent;

import java.awt.*;

/**
 *
 */
public class TitleBar extends UIComponent {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 40;

    public TitleBar() {
        setBounds(0, 0, WIDTH + 4, HEIGHT + 4);
    }

    @Override
    public void paintComponent(Graphics2D g) {
        g.setColor(new Color(0x2196f3));

        g.fillRect(0, 0, WIDTH - HEIGHT, HEIGHT);
        g.fillPolygon(new int[]{WIDTH - HEIGHT, WIDTH, WIDTH - HEIGHT}, new int[]{0, 0, HEIGHT}, 3);

        g.setPaint(new GradientPaint(WIDTH / 2, HEIGHT, new Color(0xBDBDBD), WIDTH / 2, HEIGHT + 4, Color.WHITE));
        g.fillPolygon(new int[]{0, 0, (WIDTH - HEIGHT), (WIDTH - HEIGHT)}, new int[]{HEIGHT, HEIGHT + 4, HEIGHT + 4, HEIGHT}, 4);

        g.setPaint(new GradientPaint(WIDTH - (HEIGHT / 2), HEIGHT / 2, new Color(0xBDBDBD), WIDTH - (HEIGHT / 2) + 4, (HEIGHT / 2) + 4, Color.WHITE));
        g.fillPolygon(new int[]{WIDTH, WIDTH + 4, (WIDTH - HEIGHT), (WIDTH - HEIGHT)}, new int[]{0, 0, HEIGHT + 4, HEIGHT}, 4);
    }
}
