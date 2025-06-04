package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.text.TextContentNodeRendererContext;
import org.commonmark.renderer.text.TextContentNodeRendererFactory;
import org.commonmark.renderer.text.TextContentWriter;

import java.util.Set;

public class TextContentAlertNodeRenderer implements NodeRenderer {
    private final TextContentNodeRendererContext context;
    private final TextContentWriter text;

    TextContentAlertNodeRenderer(TextContentNodeRendererContext context) {
        this.context = context;
        this.text = context.getWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Set.of(Alert.class);
    }

    @Override
    public void render(Node node) {
        // Impl copied from CoreTextContentNodeRenderer#visit(BlockQuote blockQuote)

        text.write('«');
        visitChildren(node);
        text.resetBlock();
        text.write('»');
        text.block();
    }

    private void visitChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }

    public static class Factory implements TextContentNodeRendererFactory {
        @Override
        public NodeRenderer create(TextContentNodeRendererContext context) {
            return new TextContentAlertNodeRenderer(context);
        }
    }
}
