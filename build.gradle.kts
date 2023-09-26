plugins {
	java
	id("org.springframework.boot") version "2.7.15"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.httpcomponents:httpcore:4.4.14")
  	implementation("org.apache.httpcomponents:httpclient:4.5.13") // Add this line
   	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")	
	implementation("mysql:mysql-connector-java:8.0.26")
	implementation("ch.qos.logback:logback-classic:1.2.6")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
