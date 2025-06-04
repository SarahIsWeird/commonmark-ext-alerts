package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.markdown.MarkdownRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

public class AlertExtension implements Parser.ParserExtension, MarkdownRenderer.MarkdownRendererExtension, HtmlRenderer.HtmlRendererExtension, TextContentRenderer.TextContentRendererExtension {
    private AlertExtension() {}

    public static AlertExtension create() {
        return new AlertExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessor(new AlertPostProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new HtmlAlertNodeRenderer.Factory());
    }

    @Override
    public void extend(MarkdownRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new MarkdownAlertNodeRenderer.Factory());
    }

    @Override
    public void extend(TextContentRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new TextContentAlertNodeRenderer.Factory());
    }
}
