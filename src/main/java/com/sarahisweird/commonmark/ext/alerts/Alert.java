package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.node.CustomBlock;

/**
 * A GFM-style alert block with a type and possibly additional data.
 * <p>
 * Example for a standard GFM warning alert:
 * <pre><code>
 * > [!WARNING]
 * > Do not do the thingy!
 * </code></pre>
 * </p>
 * <p>
 * The corresponding node would look like this:
 * <ul>
 *     <li>{@link #getAlertType()} returns {@code "WARNING"}</li>
 *     <li>{@link #getAdditionalData()} returns {@code null}</li>
 * </ul>
 * </p>
 * <p>
 * Example for a non-GFM alert:
 * <pre><code>
 * > [!EXAMPLE example.com example.net]
 * > This is an example!
 * </code></pre>
 * </p>
 * <p>
 * The corresponding node would look like this:
 * <ul>
 *     <li>{@link #getAlertType()} returns {@code "EXAMPLE"}</li>
 *     <li>{@link #getAdditionalData()} returns {@code "example.com example.net"}</li>
 * </ul>
 * </p>
 * <br />
 * <p>
 * Otherwise, the structure is identical to {@link org.commonmark.node.BlockQuote},
 * i.e., the contents of the alert are available as the children of this block.
 * </p>
 */
public class Alert extends CustomBlock {
    private String alertType;
    private String additionalData;

    public Alert() {}

    public Alert(String alertType, String additionalData) {
        this.alertType = alertType;
        this.additionalData = additionalData;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
}
