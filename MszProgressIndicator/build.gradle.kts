import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("maven-publish")
    id("signing")
}

android {
    compileSdk = 31
    buildToolsVersion = "31.0.0"

    defaultConfig {
        minSdk = 21
        targetSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")

    implementation("androidx.activity:activity-compose:1.5.0-alpha03")

    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.foundation:foundation:${rootProject.extra["compose_version"]}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
}

/**
 * Publish
 * */
val publishGroupId = "com.ehsanmsz"
val publishVersion = "0.2.0"
val publishArtifactId = "msz-progress-indicator"

val sourceJar by tasks.creating(Jar::class) {
    archiveClassifier.set("source")
    from("src/main/java")
}

artifacts {
    archives(sourceJar)
}

group = publishGroupId
version = publishVersion

var ossrhUsername = ""
var ossrhPassword = ""

val file = rootProject.file("local.properties")
var isLocalPropertiesAvailable = file.exists()

if (isLocalPropertiesAvailable) {
    val properties = Properties().apply { load(FileInputStream(file)) }
    ossrhUsername = properties["ossrhUsername"] as String
    ossrhPassword = properties["ossrhPassword"] as String
} else {
    ossrhUsername = System.getenv("OSSRH_USERNAME")
    ossrhPassword = System.getenv("OSSRH_PASSWORD")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = publishGroupId
                version = publishVersion
                artifactId = publishArtifactId

                pom {
                    name.set(project.name)
                    description.set("Custom progress indicator for Android Jetpack compose")
                    url.set("https://github.com/EhsanMsz/MszProgressIndicator")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            name.set("Ehsan Msz")
                            email.set("e@ehsanmsz.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:github.com/EhsanMsz/MszProgressIndicator.git")
                        url.set("https://github.com/EhsanMsz/MszProgressIndicator")
                        developerConnection.set("scm:git:ssh://git@github.com:EhsanMsz/MszProgressIndicator.git")
                    }
                }
                artifact(sourceJar)
            }
        }

        repositories {
            maven {
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }
    }

    signing {
        if (!isLocalPropertiesAvailable) {
            val signingKey: String? by project
            val signingPassword: String? by project
            useInMemoryPgpKeys(signingKey, signingPassword)
        }
        sign(publishing.publications)
    }
}
