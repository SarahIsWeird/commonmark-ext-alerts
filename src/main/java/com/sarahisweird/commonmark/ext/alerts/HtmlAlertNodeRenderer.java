package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Map;
import java.util.Set;

public class HtmlAlertNodeRenderer implements NodeRenderer {
    private final HtmlNodeRendererContext context;
    private final HtmlWriter html;

    HtmlAlertNodeRenderer(HtmlNodeRendererContext context) {
        this.context = context;
        this.html = context.getWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Set.of(Alert.class);
    }

    @Override
    public void render(Node node) {
        Alert alert = (Alert) node;
        html.line();

        Map<String, String> attrs = Map.of("alertType", alert.getAlertType());
        html.tag("blockquote", context.extendAttributes(node, "blockquote", attrs));

        html.line();
        visitChildren(alert);
        html.line();
        html.tag("/blockquote");
        html.line();
    }

    private void visitChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }

    public static class Factory implements HtmlNodeRendererFactory {
        @Override
        public NodeRenderer create(HtmlNodeRendererContext context) {
            return new HtmlAlertNodeRenderer(context);
        }
    }
}
