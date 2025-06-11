# commonmark-ext-alerts

commonmark-ext-alerts adds GitHub-flavoured Markdown (GFM)-style alerts to commonmark-java.

> [!NOTE]
> This is a GFM `NOTE` alert.

Custom alert types can be parsed as well, if the extension is configured that way.
Custom metadata can be parsed as well.

```markdown
> [!NOTE]
> This is the alert from above.

> [!EXAMPLE]
> This is an alert with a custom type.

> [!EXAMPLE example.com]
> This is an alert with a custom type and metadata.
> The metadata is available as a nullable String from Alert#getAdditionalData().
```

## Setup

### Add the dependency

Add this maven repository to your `repositories` block:
```kotlin
// build.gradle.kts
repositories {
    maven("https://maven.sarahisweird.com/releases") {
        name = "Sarah's Maven"
    }
}

// build.gradle
repositories {
    maven {
        url = uri("https://maven.sarahisweird.com/releases")
        name = "Sarah's Maven"
    }
}
```

Include the library as a dependency in your build file:

```groovy
// build.gradle.kts
implementation("com.sarahisweird.commonmark:commonmark-ext-alerts:1.0.2")

// build.gradle
implementation "com.sarahisweird.commonmark:commonmark-ext-alerts:1.0.2"
```

### Initialization

Add the extension to your list of extensions via `AlertExtension#create`.

```java
List<Extension> extensions = List.of(
    // Only use GFM-style alerts
    AlertExtension.create(), // or .create(false)
        
    // Use GFM-style alerts with custom alert types (and no metadata)
    AlertExtension.create(true),
    
    // Use metadata with GFM-style alerts
    AlertExtension.create(false, true),
    
    // Use custom alert types and metadata
    AlertExtension.create(true, true)
);

Parser parser = Parser.builder()
    .extensions(myExtensions)
    .build();
```

## Usage

### HTML rendering

If you just want to render the alerts to HTML, it's going to be rendered similarly
to a block quote, but with an additional attribute `alertType` on the `blockquote` tag.

So for example, the note alert from the very top would be rendered to HTML like this:

```html
<blockquote alertType="NOTE">
    <p>This is a GFM <code>NOTE</code> alert.</p>
</blockquote>
```

### Markdown rendering

Rendering the Alert back to Markdown will just create the GFM alert again.

### Text content rendering

Rendering the Alert into text content will drop the alert type and metadata.

### Consuming the data in code

In your `(Abstract)Visitor`, add an `instanceof` check to `visit(CustomBlock node)`:

```java
@Override
public void visit(CustomBlock node) {
    if (node instanceof Alert alert) {
        // Won't be null, unless it is explicitly set to null.
        String alertType = alert.getAlertType();
        
        // Might be null if no metadata was provided.
        String metadata = alert.getAdditionalData();
        
        // If the extension is initialized with GFM-style alerts, and you don't
        // modify the alert type, this will always return true.
        boolean isValidGfmType = alert.isValidGfmType();
        
        // Don't forget to visit the children!
        visitChildren(alert);
    }
}
```
