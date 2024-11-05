plugins {
    java

    application

    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories { 
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jool:0.9.15")
    implementation("mysql:mysql-connector-java:8.0.29")
}

application {
    mainClass.set("it.unibo.gestione_concessionario.Main")
}
