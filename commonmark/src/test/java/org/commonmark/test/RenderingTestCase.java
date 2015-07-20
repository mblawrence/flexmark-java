package org.commonmark.test;

import org.commonmark.Extension;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.junit.Before;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public abstract class RenderingTestCase {

    protected Parser parser;
    protected HtmlRenderer renderer;

    @Before
    public void setup() {
        Iterable<? extends Extension> extensions = getExtensions();

        Parser.Builder parserBuilder = Parser.builder().extensions(extensions);
        configureParser(parserBuilder);
        parser = parserBuilder.build();

        HtmlRenderer.Builder rendererBuilder = HtmlRenderer.builder().extensions(extensions);
        configureRenderer(rendererBuilder);
        renderer = rendererBuilder.build();
    }

    protected Iterable<? extends Extension> getExtensions() {
        return Collections.emptyList();
    }

    protected void configureParser(Parser.Builder parserBuilder) {
    }

    protected void configureRenderer(HtmlRenderer.Builder rendererBuilder) {
    }

    protected void assertRendering(String source, String expectedHtml) {
        Node node = parser.parse(source);
        String html = renderer.render(node);

        // include source for better assertion errors
        String expected = showTabs(expectedHtml + "\n\n" + source);
        String actual = showTabs(html + "\n\n" + source);
        assertEquals(expected, actual);
    }

    private static String showTabs(String s) {
        // Tabs are shown as "rightwards arrow" for easier comparison
        return s.replace("\t", "\u2192");
    }
}
