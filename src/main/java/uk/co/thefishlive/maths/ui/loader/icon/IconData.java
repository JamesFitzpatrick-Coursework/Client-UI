package uk.co.thefishlive.maths.ui.loader.icon;

import uk.co.thefishlive.maths.ui.loader.css.CssElementList;

import java.util.regex.Pattern;

/**
 *
 */
public class IconData {

    public static final String DEFAULT_COLOUR = "black";
    public static final String DEFAULT_SIZE = "36dp";

    private static final Pattern FORMAT_NAME = Pattern.compile("_", Pattern.LITERAL);

    private String id;
    private String color;
    private String size;

    public IconData(String id) {
        this(id, DEFAULT_COLOUR, DEFAULT_SIZE);
    }

    public IconData(String id, String color) {
        this(id, color, DEFAULT_SIZE);
    }

    public IconData(String id, String color, String size) {
        this.id = id;
        this.color = color;
        this.size = size;
    }

    public IconData(CssElementList css) {
        String name = css.get("icon").getValue();

        if (name == null) {
            throw new IllegalArgumentException("Icon name must be specified");
        }

        String color = css.get("icon-color").getValue();

        if (color == null) {
            color = IconData.DEFAULT_COLOUR;
        }

        String size = css.get("icon-size").getValue();

        if (size == null) {
            size = IconData.DEFAULT_SIZE;
        }

        this.id = name;
        this.color = color;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getInternalName() {
        return "icon." + FORMAT_NAME.matcher(id).replaceAll(".");
    }

    @Override
    public String toString() {
        return "IconData{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IconData iconData = (IconData) o;

        if (color != null ? !color.equals(iconData.color) : iconData.color != null) return false;
        if (id != null ? !id.equals(iconData.id) : iconData.id != null) return false;
        if (size != null ? !size.equals(iconData.size) : iconData.size != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }
}
