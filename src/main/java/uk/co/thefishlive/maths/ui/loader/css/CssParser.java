package uk.co.thefishlive.maths.ui.loader.css;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class CssParser {

    private static final Pattern LINE_SPLIT = Pattern.compile(";", Pattern.LITERAL);
    private static final Pattern REMOVE_QUOTES = Pattern.compile("\'", Pattern.LITERAL);
    private static final Pattern STATEMENT = Pattern.compile("(.+):(.+)");

    public CssElementList parseStyleString(String style) throws CssException {
        style = style.trim();
        String[] statements = LINE_SPLIT.split(style);
        CssElementList elements = new CssElementList(statements.length);

        for (String statement : statements) {
            if ("".equals(statement)) {
                continue;
            }

            statement = REMOVE_QUOTES.matcher(statement).replaceAll("");
            Matcher matcher = STATEMENT.matcher(statement);

            if (!matcher.matches()) {
                throw new CssException("Invalid css statement " + statement);
            }

            elements.add(new CssElement(matcher.group(1).trim(), matcher.group(2).trim()));
        }

        return elements;
    }
}
