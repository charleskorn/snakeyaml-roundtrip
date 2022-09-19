plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.snakeyaml:snakeyaml-engine:2.4")
}

application {
    mainClass.set("yaml.roundtrip.AppKt")
}
