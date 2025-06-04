package com.sarahisweird.commonmark.ext.alerts;

import org.commonmark.node.CustomBlock;

import java.util.List;

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
    /**
     * The alert types allowed by GitHub.
     * @see Alert#isValidGfmType()
     */
    public static final List<String> GFM_ALERT_TYPES = List.of("NOTE", "TIP", "IMPORTANT", "WARNING", "CAUTION");

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

    /**
     * Returns additional metadata, or {@code null} if not set.
     */
    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    /**
     * Checks whether this alert type is valid in GitHub-flavoured Markdown.
     * This function is case-sensitive!
     */
    public boolean isValidGfmType() {
        return GFM_ALERT_TYPES.contains(alertType);
    }
}
