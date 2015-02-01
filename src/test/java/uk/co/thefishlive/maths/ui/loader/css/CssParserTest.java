package uk.co.thefishlive.maths.ui.loader.css;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CssParserTest {

    private CssParser parser;

    @Before
    public void setup() {
        this.parser = new CssParser();
    }

    @Test
    public void testParseSingleElement() throws CssException {
        CssElementList elements = this.parser.parseStyleString("key: value");
        assertTrue(elements.size() == 1);

        CssElement element = elements.get("key");
        assertNotNull(element);
        assertEquals("value", element.getValue());

        System.out.println(elements);
    }

    @Test
    public void testParseMultiElement() throws CssException {
        CssElementList elements = this.parser.parseStyleString("key1: value1; key2: value2; ");
        assertTrue(elements.size() == 2);

        System.out.println(elements);
    }
}
