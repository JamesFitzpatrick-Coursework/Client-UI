package uk.co.thefishlive.maths.ui.controllers.editor;

import com.google.common.base.Throwables;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import uk.co.thefishlive.maths.assessment.editor.AssessmentBuilder;
import uk.co.thefishlive.maths.assessment.editor.question.multichoice.MultiChoiceQuestionBuilder;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.ColorPalette;
import uk.co.thefishlive.maths.ui.Controller;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.maths.ui.loader.icon.IconData;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class QuestionEditorController extends Controller {

    @FXML private Pane pnlContainer;
    @FXML private Pane pnlMenu;

    @FXML private GridPane pnlOptions;

    @FXML private Label lblQuestionNumber;
    @FXML private TextArea txtQuestion;

    private int options;
    private int questionNumber = 1;
    private MultiChoiceQuestionBuilder builder = new MultiChoiceQuestionBuilder();
    private boolean newQuestion = true;

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    @FXML
    public void btnMenu_Click(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pnlMenu);
        transition.setByX(205);
        transition.play();
    }

    @FXML
    private void btnPrevious_Click(MouseEvent event) {}

    @FXML
    private void btnNext_Click(MouseEvent event) {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onDisplay() {
        if (newQuestion) {
            buildPlaceholder(0);
            lblQuestionNumber.setText(this.questionNumber + ".");
        } else {
        }
    }

    public void setEditorInfo(AssessmentBuilder builder, int questionNumber) {
        if (questionNumber == -1) {
            this.questionNumber = builder.getNextQuestionNumber();
            this.builder = new MultiChoiceQuestionBuilder();
        } else {
            this.questionNumber = questionNumber;
            this.builder = (MultiChoiceQuestionBuilder) builder.getQuestionBuilder(questionNumber);
        }
    }

    private void buildPlaceholder(int position) {
        Pane placeholder = new Pane();
        placeholder.setBorder(new Border(new BorderStroke(Color.web("#757575"), BorderStrokeStyle.DASHED, null, new BorderWidths(1))));
        placeholder.setBackground(new Background(new BackgroundFill(ColorPalette.WHITE, null, null)));
        placeholder.setCursor(Cursor.HAND);
        placeholder.setOnMouseClicked(event -> {
            pnlOptions.getChildren().remove(placeholder);
            buildOption(options);

            if (options == 3) {
                return;
            }

            buildPlaceholder(++options);
        });

        try {
            ImageView icon = new ImageView(UILoader.getIconCache().getIcon(new IconData("add", "grey600", "48dp")).getImage());
            icon.setFitWidth(64);
            icon.setFitHeight(64);
            icon.setLayoutX(57);
            icon.setLayoutY(50);
            placeholder.getChildren().add(icon);
        } catch (ResourceException e) {
            Throwables.propagate(e);
        }

        pnlOptions.add(placeholder, position, 0);
    }

    private void buildOption(int pos) {
        Pane option = new Pane();
        option.setEffect(new DropShadow());
        option.setBackground(new Background(new BackgroundFill(ColorPalette.WHITE, null, null)));

        TextArea area = new TextArea();
        area.setPrefSize(168, 153);
        area.setLayoutX(5);
        area.setLayoutY(5);
        area.setFont(new Font("Roboto", 15));
        option.getChildren().add(area);

        pnlOptions.add(option, pos, 0);
    }

    public void save() {

    }
}
