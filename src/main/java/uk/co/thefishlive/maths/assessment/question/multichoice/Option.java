package uk.co.thefishlive.maths.assessment.question.multichoice;

/**
 * Created by James on 04/02/2015.
 */
public class Option {

    private String text;

    public Option(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (text != null ? !text.equals(option.text) : option.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Option{" +
                "text='" + text + '\'' +
                '}';
    }
}
