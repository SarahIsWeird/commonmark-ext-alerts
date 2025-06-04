package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.Extension;
import org.commonmark.node.Document;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class AlertPostProcessorTest {
    @Test
    void processorTest() {
        List<Extension> extensions = List.of(AlertExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();

        Node document = parser.parse("""
                > [!INFO example.com]
                > Hello! :D""");

        Assertions.assertInstanceOf(Document.class, document);
        Assertions.assertInstanceOf(Alert.class, document.getFirstChild());

        Alert alert = (Alert) document.getFirstChild();
        Assertions.assertEquals("INFO", alert.getAlertType());
        Assertions.assertEquals("example.com", alert.getAdditionalData());

        Assertions.assertInstanceOf(Paragraph.class, alert.getFirstChild());
        Assertions.assertInstanceOf(Text.class, alert.getFirstChild().getFirstChild());
        Text text = (Text) alert.getFirstChild().getFirstChild();

        Assertions.assertEquals("Hello! :D", text.getLiteral());
    }
}
