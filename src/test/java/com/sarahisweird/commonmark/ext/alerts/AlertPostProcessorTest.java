package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.Extension;
import org.commonmark.node.Document;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AlertPostProcessorTest {
    @Test
    void processorTest() {
        List<Extension> extensions = List.of(AlertExtension.create(true, true));
        Parser parser = Parser.builder().extensions(extensions).build();

        Node document = parser.parse("""
                A
                
                > [!INFO example.com]
                > Hello! :D
                
                B""");

        assertInstanceOf(Document.class, document);
        assertInstanceOf(Paragraph.class, document.getFirstChild());
        assertInstanceOf(Alert.class, document.getFirstChild().getNext());
        assertInstanceOf(Paragraph.class, document.getFirstChild().getNext().getNext());

        Alert alert = (Alert) document.getFirstChild().getNext();
        assertEquals("INFO", alert.getAlertType());
        assertEquals("example.com", alert.getAdditionalData());

        assertInstanceOf(Paragraph.class, alert.getFirstChild());
        assertInstanceOf(Text.class, alert.getFirstChild().getFirstChild());
        Text text = (Text) alert.getFirstChild().getFirstChild();

        assertEquals("Hello! :D", text.getLiteral());
    }
}
