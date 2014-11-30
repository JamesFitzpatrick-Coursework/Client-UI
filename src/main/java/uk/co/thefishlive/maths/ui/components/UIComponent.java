package uk.co.thefishlive.maths.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public abstract class UIComponent extends JComponent {

    @Override
    public boolean isOpaque() {
        return false;
    }

    public abstract void paintComponent(Graphics2D g);

    @Override
    public void paintComponent(Graphics g) {
        this.paintComponent((Graphics2D) g);
        super.paintComponent(g);
    }

}
