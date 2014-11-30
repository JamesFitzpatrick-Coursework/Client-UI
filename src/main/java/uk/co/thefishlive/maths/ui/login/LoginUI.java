package uk.co.thefishlive.maths.ui.login;

import uk.co.thefishlive.maths.ui.UIBase;
import uk.co.thefishlive.maths.ui.components.header.TitleBar;
import uk.co.thefishlive.maths.ui.components.header.UserBar;
import uk.co.thefishlive.maths.ui.components.common.Button;
import uk.co.thefishlive.maths.ui.components.login.LoginPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Arrays;

/**
 *
 */
public class LoginUI extends UIBase {

    @Override
    public void initialiseUI() {
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        Container container = this.getContentPane();
        container.setBackground(Color.white);

        LoginPanel panel = new LoginPanel(125, 75);
        container.add(new LoginPanel(125, 75));

        TitleBar title = new TitleBar();
        container.add(new TitleBar());

        UserBar user = new UserBar("James Fitzpatrick", this.getWidth(), 0);
        container.add(user);

        System.out.println(Arrays.toString(container.getComponents()));
    }

}
