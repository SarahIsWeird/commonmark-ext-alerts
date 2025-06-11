plugins {
    `java-library`
    `maven-publish`
}

group = "com.sarahisweird.commonmark"
version = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    val commonmarkVersion: String by project
    implementation("org.commonmark:commonmark:$commonmarkVersion")

    val junitVersion: String by project
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "mavenSarah"
            url = uri("https://maven.sarahisweird.com/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}
