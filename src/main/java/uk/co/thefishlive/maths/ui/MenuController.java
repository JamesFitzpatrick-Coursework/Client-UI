package uk.co.thefishlive.maths.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

public class MenuController extends Controller {

    @FXML private Pane pnlMenu;

    @FXML
    public void itmGroups_Click(MouseEvent event) throws ResourceException, IOException {
        UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/admin/group_list.fxml"));
        Main.getInstance().setCurrentUI(ui);
    }

    @FXML
    public void itmHome_Click(MouseEvent event) throws ResourceException, IOException {
        UI ui = UILoader.loadUI(Main.getInstance().getResourceManager().getResource("ui/user_main.fxml"));
        Main.getInstance().setCurrentUI(ui);
    }

    @FXML
    public void btnMenuClose_Clicked(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(-205);
        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
