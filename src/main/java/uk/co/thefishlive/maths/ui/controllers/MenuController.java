package uk.co.thefishlive.maths.ui.controllers;

import com.google.common.base.Throwables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import uk.co.thefishlive.auth.permission.PermissionRegistry;
import uk.co.thefishlive.auth.permission.SimplePermission;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UI;
import uk.co.thefishlive.maths.ui.loader.UILoader;

public class MenuController extends Controller {

    private static final Logger logger = LogManager.getLogger();

    static {
        PermissionRegistry.registerPermission(new SimplePermission("admin.management", "Allows for full admin management"));
    }

    @FXML private Pane pnlMenu;
    @FXML private Pane itmGroups;
    @FXML private Pane itmAssessments;

    @FXML private Label lblMenuUsername;

    @FXML
    public void itmHome_Click(MouseEvent event) throws ResourceException, IOException {
        // Load the user landing screen
        UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/user_main.fxml"));
        Main.getInstance().setCurrentUI(ui);
    }

    @FXML
    public void itmGroups_Click(MouseEvent event) throws ResourceException, IOException {
        // Load the group management screen
        UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/admin/group_list.fxml"));
        Main.getInstance().setCurrentUI(ui);
    }

    @FXML
    private void itmAssessments_Click(MouseEvent event) throws ResourceException, IOException {
        // Load the assessment management screen
        UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/admin/assessment_list.fxml"));
        Main.getInstance().setCurrentUI(ui);
    }

    @FXML
    public void btnMenuClose_Clicked(MouseEvent event) {
        // Slide the menu back off screen
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(-205);
        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UserProfile profile = Main.getInstance().getAuthHandler().getActiveSession().getProfile();
            lblMenuUsername.setText(profile.getDisplayName());

            User user = Main.getInstance().getAuthHandler().getUserManager().getUserProfile(profile);

            // Check to see if this user can manage groups
            if (user.hasPermission(PermissionRegistry.getPermission("admin.management"))) {
                itmGroups.setVisible(true);
                itmAssessments.setVisible(true);
                logger.info("User has got permission 'admin.management'");
            } else {
                itmGroups.setVisible(false);
                itmAssessments.setVisible(false);
                logger.info("User has not got permission 'admin.management'");
            }
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    protected Pane getContentPane() {
        return pnlMenu;
    }
}
