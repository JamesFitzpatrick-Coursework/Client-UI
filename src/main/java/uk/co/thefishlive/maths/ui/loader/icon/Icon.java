package uk.co.thefishlive.maths.ui.loader.icon;

import javafx.scene.image.Image;

/**
 *
 */
public class Icon {

    private final IconData name;
    private final Image image;

    public Icon(IconData name, Image image) {
        this.name = name;
        this.image = image;
    }

    public IconData getName() {
        return this.name;
    }

    public Image getImage() {
        return this.image;
    }

}
