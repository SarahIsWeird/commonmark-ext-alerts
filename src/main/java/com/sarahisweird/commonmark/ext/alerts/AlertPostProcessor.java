package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.node.*;
import org.commonmark.parser.PostProcessor;

public class AlertPostProcessor implements PostProcessor {
    private final boolean allowNonGfmAlerts;
    private final boolean allowMetadata;

    AlertPostProcessor(boolean allowNonGfmAlerts, boolean allowMetadata) {
        this.allowNonGfmAlerts = allowNonGfmAlerts;
        this.allowMetadata = allowMetadata;
    }

    @Override
    public Node process(Node node) {
        if (!canPostProcess(node)) {
            visitChildren(node);
            return node;
        }

        Alert alert = getAlert(node.getFirstChild());
        if (alert == null) {
            visitChildren(node);
            return node;
        }

        node.insertBefore(alert);
        node.unlink();
        visitChildren(alert);
        return alert;
    }

    private static boolean canPostProcess(Node node) {
        if (!(node instanceof BlockQuote blockQuote)) {
            return false;
        }

        Node paragraph = blockQuote.getFirstChild();
        if (!(paragraph instanceof Paragraph)) {
            return false;
        }

        Node paragraphChild = paragraph.getFirstChild();
        if (!(paragraphChild instanceof Text text)) {
            return false;
        }

        String line = text.getLiteral().trim();
        return line.startsWith("[!") && line.endsWith("]");
    }

    private Alert getAlert(Node paragraph) {
        Text text = (Text) paragraph.getFirstChild();
        String line = text.getLiteral().trim();

        int attributeStart = line.indexOf(' ');
        if (!allowMetadata && attributeStart != -1) {
            return null;
        }

        int typeEnd = attributeStart;
        if (typeEnd == -1) {
            typeEnd = line.lastIndexOf(']');
        }

        String type = line.substring("[!".length(), typeEnd);
        if (!allowNonGfmAlerts && !Alert.GFM_ALERT_TYPES.contains(type)) {
            return null;
        }

        String attributes = null;
        if (attributeStart != -1) {
            attributes = line.substring(attributeStart + 1, line.lastIndexOf(']'));
        }

        Alert alert = new Alert(type, attributes);
        paragraph.getFirstChild().unlink();
        if (paragraph.getFirstChild() instanceof SoftLineBreak) {
            paragraph.getFirstChild().unlink();
        }

        Node next = paragraph;
        while (next != null) {
            alert.appendChild(next);
            next = next.getNext();
        }

        return alert;
    }

    private void visitChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            process(node);
            node = next;
        }
    }
}
