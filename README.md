# Spring Boot Secure
Security module for [Spring Boot](https://spring.io/projects/spring-boot) based on [OWASP Top Ten](https://cheatsheetseries.owasp.org/cheatsheets/HTTP_Headers_Cheat_Sheet.html#security-headers) recommendations that adds support for Secure HTTP Response Headers and more.

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?logo=spring&logoColor=white) [![Build](https://github.com/damianperera/spring-boot-secure/actions/workflows/build.yml/badge.svg)](https://github.com/damianperera/spring-boot-secure/actions/workflows/build.yml) [![CodeQL](https://github.com/damianperera/spring-boot-secure/actions/workflows/github-code-scanning/codeql/badge.svg)](https://github.com/damianperera/spring-boot-secure/actions/workflows/github-code-scanning/codeql)

## Features
- :white_check_mark: Dynamic HTTP Security Response Headers
- :soon: Modify Security Response Headers
- :soon: Request Size Limits
- :soon: Rate Limits
- :soon: Basic Auth
- :soon: CSRF

## Installation
### Using GitHub Packages
1. Authenticate with GitHub Packages as explained [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#authenticating-to-github-packages).
2. Add the repository to your `build.gradle` or `build.gradle.kts` file.
      ```kotlin
      repositories {
         maven {
            url = uri("https://maven.pkg.github.com/damianperera/spring-boot-secure")
            credentials {
               username = "<INSERT_USERNAME_HERE>"
               password = "<INSERT_TOKEN_HERE>"
            }
         }
      }
      ```
3. Add the package dependency to your `build.gradle` or `build.gradle.kts` file.
      ```kotlin
      dependencies {
         implementation("io.perera.secure-spring-boot-starter")
      }
      ```

## Usage
Annotate your main `@SpringBootApplication` class with `@EnableSpringSecure`.
```kotlin
@SpringBootApplication
@EnableSpringSecure
class TestApplication
```
For a sample implementation refer the [TestApplication.kt](/src/test/kotlin/io/perera/spring/secure/sample/TestApplication.kt) file.

## Inspiration
- Nuxt Security by [@Baroshem](https://github.com/Baroshem)
   - [GitHub](https://github.com/Baroshem/nuxt-security)
   - [Nuxt Docs](https://nuxt.com/modules/security)
