plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("java")
}

group = "net.splodgebox"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("net.dv8tion:JDA:5.0.0-beta.19")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // https://mvnrepository.com/artifact/com.paypal.sdk/rest-api-sdk
    implementation("com.paypal.sdk:rest-api-sdk:1.14.0")

    implementation("javax.json:javax.json-api:1.1")
    implementation("org.glassfish:javax.json:1.1.4")

    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    shadowJar {
        manifest {
            attributes["Main-Class"] = "net.splodgebox.discordpaypalbot.DiscordPayPalBot"
        }
    }
}