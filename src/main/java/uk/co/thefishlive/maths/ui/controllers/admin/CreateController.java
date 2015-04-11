package uk.co.thefishlive.maths.ui.controllers.admin;

import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.maths.ui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Represents a controller for a interface used to create a object.
 */
public abstract class CreateController<T> extends Controller {

    protected CreateCallback<T> callback;

    @FXML
    public void btnCancel_Click(ActionEvent event) {
        Main.getInstance().setCurrentUI(getParent());
    }

    /**
     * Set the callback for this interface.
     *
     * @param callback
     *      the new callback for this interface
     */
    public void setCreateCallback(CreateCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * Represents a callback called when the 'Create' Button is pressed on the interface
     *
     * @param <T>
     *      the type of object being created
     */
    public static interface CreateCallback<T> {

        /**
         * Called when the 'Create' button is pressed on the interface.
         *
         * @param profile
         *      the new object created
         */
        public void created(T profile);
    }
}
