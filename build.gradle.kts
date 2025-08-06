plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springframework.boot:spring-boot-starter-webflux")

	implementation("org.springframework.boot:spring-boot-starter-validation")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

// JPA 필수 설정 - 코틀린 생성자는 기본적으로 final. open을 허용해서 JPA가 클래스를 프록시로 쓸 수 있게!!
allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

// 파라미터 없는 기본 생성자 자동 생성
// JPA는 객체 생성을 위해 기본 생성자가 반드시 필요하므로
noArg {
	annotation("jakarta.persistence.Entity")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
