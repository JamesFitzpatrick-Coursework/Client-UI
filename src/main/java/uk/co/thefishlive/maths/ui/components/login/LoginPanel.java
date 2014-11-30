package uk.co.thefishlive.maths.ui.components.login;

import uk.co.thefishlive.maths.ui.components.UIComponent;
import uk.co.thefishlive.maths.ui.components.UIPanel;
import uk.co.thefishlive.maths.ui.components.common.Button;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

/**
 *
 */
public class LoginPanel extends UIPanel {

    private final int x;
    private final int y;

    public LoginPanel(int x, int y) {
        this.x = x;
        this.y = y;

        setBounds(x - 10, y - 10, x + 350, y + 350);

        addComponent(new Button("Login", getX() + 200, getY() + 250, 60, 30));
    }

    @Override
    public void paintComponent(Graphics2D g) {
        g.setColor(new Color(0x2196f3));
        g.fillRect(x, y, 300, 300);

        g.setColor(this.getParent().getBackground());
        g.fillPolygon(new int[]{x, x + 50, x}, new int[]{y, y, y + 50}, 3);
        g.fillPolygon(new int[]{x + 300, x + 250, x + 300}, new int[]{y + 300, y + 300, y + 250}, 3);

        g.setPaint(new GradientPaint(x + 150, y + 300, new Color(0xBDBDBD), x + 150, y + 305, Color.WHITE));
        g.fillRect(x, y + 300, 250, 5);

        g.setPaint(new GradientPaint(x + 300, y + 150, new Color(0xBDBDBD), x + 305, y + 150, Color.WHITE));
        g.fillRect(x + 300, y, 5, 250);

        g.setPaint(new GradientPaint(x + 150, y, new Color(0xBDBDBD), x + 150, y - 5, Color.WHITE));
        g.fillRect(x + 50, y - 5, 250, 5);

        g.setPaint(new GradientPaint(x, y + 150, new Color(0xBDBDBD), x - 5, y + 150, Color.WHITE));
        g.fillRect(x - 5, y + 50, 5, 250);

        g.setPaint(new GradientPaint(x + 275, y + 275, new Color(0xBDBDBD), x + 280, y + 280, Color.WHITE));
        g.fillPolygon(new int[]{x + 300, x + 305, x + 250, x + 250}, new int[]{y + 250, y + 250, y + 305, y + 300}, 4);

        g.setPaint(new GradientPaint(x + 25, y + 25, new Color(0xBDBDBD), x + 20, y + 20, Color.WHITE));
        g.fillPolygon(new int[]{x, x - 5, x + 50, x + 50}, new int[]{y + 50, y + 50, y - 5, y}, 4);
    }

}
