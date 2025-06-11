package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.markdown.MarkdownRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

/**
 * Allows parsing of GitHub-flavoured Markdown "alerts" via the CustomNode {@link Alert}.
 * <p>
 * <pre><code>
 * > [!TIP]
 * > GFM alerts are these.
 * </code></pre>
 * <p>
 * Non-GFM alerts can also be allowed by passing {@code true} to {@link #create(boolean)}:
 * <p>
 * <pre><code>
 * > [!EXAMPLE]
 * > This is a non-standard alert block.
 * </code></pre>
 * <p>
 * Lastly, the library allows a non-standard extension to the alert format by allowing the Markdown writer
 * to provide metadata to the alert block by passing {@code true} to the second argument of
 * {@link #create(boolean, boolean)}:
 * <p>
 * <pre><code>
 * > [!EXAMPLE example.com]
 * > This non-standard alert block also has some metadata.
 * </code></pre>
 *
 * @see Alert
 */
public class AlertExtension implements Parser.ParserExtension, MarkdownRenderer.MarkdownRendererExtension, HtmlRenderer.HtmlRendererExtension, TextContentRenderer.TextContentRendererExtension {
    private final boolean allowNonGfmAlerts;
    private final boolean allowMetadata;

    private AlertExtension(boolean allowNonGfmAlerts, boolean allowMetadata) {
        this.allowNonGfmAlerts = allowNonGfmAlerts;
        this.allowMetadata = allowMetadata;
    }

    /**
     * Creates an AlertExtension with GFM behavior, i.e. only GFM alerts will be parsed.
     */
    public static AlertExtension create() {
        return new AlertExtension(false, false);
    }

    /**
     * Creates an AlertExtension, possibly allowing non-GFM alert types.
     *
     * @param allowNonGfmAlerts Whether to allow non-GFM alert types
     */
    public static AlertExtension create(boolean allowNonGfmAlerts) {
        return new AlertExtension(allowNonGfmAlerts, false);
    }

    /**
     * Creates an AlertExtension, possibly allowing both non-GFM alert types and non-standard metadata.
     *
     * @param allowNonGfmAlerts Whether to allow non-GFM alert types
     * @param allowMetadata Whether to allow non-standard additional metadata after the alert type
     */
    public static AlertExtension create(boolean allowNonGfmAlerts, boolean allowMetadata) {
        return new AlertExtension(allowNonGfmAlerts, allowMetadata);
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessor(new AlertPostProcessor(allowNonGfmAlerts, allowMetadata));
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
