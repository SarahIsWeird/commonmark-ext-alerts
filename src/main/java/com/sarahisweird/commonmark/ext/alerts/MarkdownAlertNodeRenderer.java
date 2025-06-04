package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.markdown.MarkdownNodeRendererContext;
import org.commonmark.renderer.markdown.MarkdownNodeRendererFactory;
import org.commonmark.renderer.markdown.MarkdownWriter;

import java.util.Set;

public class MarkdownAlertNodeRenderer implements NodeRenderer {
    private final MarkdownNodeRendererContext context;
    private final MarkdownWriter md;

    MarkdownAlertNodeRenderer(MarkdownNodeRendererContext context) {
        this.context = context;
        this.md = context.getWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Set.of(Alert.class);
    }

    @Override
    public void render(Node node) {
        Alert alert = (Alert) node;

        md.writePrefix("> ");
        md.pushPrefix("> ");

        if (alert.getAlertType() != null) {
            renderAlertHead(alert);
        }

        md.line();
        visitChildren(alert);
        md.popPrefix();
        md.block();
    }

    private void renderAlertHead(Alert alert) {
        md.raw("[!" + alert.getAlertType());

        if (alert.getAdditionalData() != null) {
            md.raw(" " + alert.getAdditionalData());
        }

        md.raw("]");
    }

    private void visitChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }

    public static class Factory implements MarkdownNodeRendererFactory {
        @Override
        public NodeRenderer create(MarkdownNodeRendererContext context) {
            return new MarkdownAlertNodeRenderer(context);
        }

        @Override
        public Set<Character> getSpecialCharacters() {
            return Set.of();
        }
    }
}
