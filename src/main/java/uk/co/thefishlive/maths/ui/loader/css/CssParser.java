package uk.co.thefishlive.maths.ui.loader.css;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class CssParser {

    private static final Pattern LINE_SPLIT = Pattern.compile(";", Pattern.LITERAL);
    private static final Pattern REMOVE_QUOTES = Pattern.compile("\'", Pattern.LITERAL);
    private static final Pattern STATEMENT = Pattern.compile("(.+):(.+)");

    /**
     * Parse a string into a css element list.
     *
     * @param style the css string
     * @return a css element list representing the string specified
     * @throws CssException if there is a issue with the css provided
     */
    public CssElementList parseStyleString(String style) throws CssException {
        // Trim the whitespace from the css string
        style = style.trim();
        // split the line into separate statements
        String[] statements = LINE_SPLIT.split(style);
        CssElementList elements = new CssElementList(statements.length);

        // Parse each statement
        for (String statement : statements) {
            // Skip empty statements
            if ("".equals(statement)) {
                continue;
            }

            statement = REMOVE_QUOTES.matcher(statement).replaceAll("");
            Matcher matcher = STATEMENT.matcher(statement);

            // Validate the provided statement
            if (!matcher.matches()) {
                throw new CssException("Invalid css statement " + statement);
            }

            // Add this element to the list
            elements.add(new CssElement(matcher.group(1).trim(), matcher.group(2).trim()));
        }

        return elements;
    }
}
