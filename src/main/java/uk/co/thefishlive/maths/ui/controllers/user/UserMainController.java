package uk.co.thefishlive.maths.ui.controllers.user;

import com.google.common.base.Throwables;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.assessment.Assessment;
import uk.co.thefishlive.maths.assessment.AssessmentView;
import uk.co.thefishlive.maths.assessment.QuestionType;
import uk.co.thefishlive.maths.assessment.question.Question;
import uk.co.thefishlive.maths.assessment.question.multichoice.MultiChoiceQuestion;
import uk.co.thefishlive.maths.assessment.question.multichoice.Option;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.utils.EffectsUtils;

/**
 *
 */
public class UserMainController extends Controller {

    @FXML
    private Pane pnlContainer;

    @FXML
    private GridPane pnlAssets;
    @FXML
    private Pane pnlMenu;

    @FXML
    private Label lblTitle;

    @FXML
    public void btnMenu_Clicked(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pane asset = new Pane();
        asset.setEffect(new DropShadow());
        asset.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        asset.setCursor(Cursor.HAND);
        asset.setOnMouseClicked(event -> {
            try {
                List<Option> options = new ArrayList<>(Arrays.asList(
                        new Option("Answer 1"),
                        new Option("Answer 2"),
                        new Option("Answer 3"),
                        new Option("Answer 4")
                ));

                Question question1 = QuestionType.MULTI_CHOICE.createInstance(
                        new Class[]{String.class, int.class, QuestionType.class, List.class},
                        new Object[]{"A Test Question", 1, QuestionType.MULTI_CHOICE, options}
                );

                Question question2 = QuestionType.MULTI_CHOICE.createInstance(
                        new Class[]{String.class, int.class, QuestionType.class, List.class},
                        new Object[]{"A Test Question 2", 2, QuestionType.MULTI_CHOICE, options}
                );

                Assessment assessment = new Assessment(
                        "Test Assessment",
                        Arrays.asList(
                                question1,
                                question2
                        )
                );
                assessment.display(AssessmentView.START);
            } catch (ReflectiveOperationException | ResourceException | IOException e) {
                e.printStackTrace();
                Throwables.propagate(e);
            }
        });
        pnlAssets.add(asset, 0, 0);
    }

    @Override
    public void onDisplay() {
        this.lblTitle.setText(Main.getInstance().getAuthHandler().getActiveSession().getProfile().getDisplayName());
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }
}
