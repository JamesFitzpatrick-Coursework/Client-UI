package uk.co.thefishlive.maths.ui.controllers.editor;

import com.google.common.base.Throwables;

import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.maths.resources.exception.ResourceException;
import uk.co.thefishlive.maths.ui.ColorPalette;
import uk.co.thefishlive.maths.ui.loader.UILoader;
import uk.co.thefishlive.maths.ui.loader.icon.IconData;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
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

public class MultichoiceQuestionEditorController extends QuestionEditorController {

    @FXML private Pane pnlContainer;
    
    @FXML private GridPane pnlOptions;
    @FXML private Label lblQuestionNumber;
    @FXML private TextArea txtQuestion;

    private int options;

    @FXML
    public void btnSave_Click(MouseEvent event) {
        close();
    }

    @Override
    protected Pane getContentPane() {
        return pnlContainer;
    }

    @Override
    public void onClose() {
        try {
            builder.setQuestionNumber(questionNumber);
            builder.setQuestion(txtQuestion.getText());
            builder.clearOptions();

            for (Node node : pnlOptions.getChildren()) {
                Pane pane = (Pane) node;
                TextArea[] textAreas = pane.getChildren().stream().filter(c -> c instanceof TextArea).toArray(TextArea[]::new);

                if (textAreas.length > 0) {
                    TextArea option = textAreas[0];
                    builder.addOption(option.getText());
                }
            }

            Question question = builder.build();
            callback.questionCreated(question);
        } catch (Exception e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public void onDisplay() {
        lblQuestionNumber.setText(this.questionNumber + ".");

        txtQuestion.setText(this.builder.getQuestion());

        int i;
        for (i = 0; i < this.builder.getOptions().size(); i++) {
            Pane option = buildOption(i);
            TextArea text = (TextArea) option.getChildren().get(0);
            text.setText(this.builder.getOptions().get(i).getText());
        }

        if (i < 4) {
            buildPlaceholder(i);
        }
    }

    private Pane buildPlaceholder(int position) {
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
        return placeholder;
    }

    private Pane buildOption(int pos) {
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
        return option;
    }

}
