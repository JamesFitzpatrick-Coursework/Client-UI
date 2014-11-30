package uk.co.thefishlive.maths.ui.components;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class UIPanel extends JPanel {

    private List<UIComponent> components = new ArrayList<>();

    public void addComponent(UIComponent component) {
        this.components.add(component);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    public abstract void paintComponent(Graphics2D g);

    @Override
    public void paintComponent(Graphics g) {
        this.paintComponent((Graphics2D) g);

        for (UIComponent component : this.components) {
            component.paintComponent(g);
        }
    }

}
