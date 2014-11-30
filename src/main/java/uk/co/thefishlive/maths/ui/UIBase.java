package uk.co.thefishlive.maths.ui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 */
public abstract class UIBase extends JFrame {

    public UIBase() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.initialiseUI();
    }

    public abstract void initialiseUI();

}
