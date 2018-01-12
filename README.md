[![Build Status](https://travis-ci.org/mindstix-labs/selenium-accelerator.svg?branch=master)](https://travis-ci.org/mindstix-labs/selenium-accelerator)

# selenium-accelerator
> Opinionated baseline to bootstrap selenium and cucumber based web application test automation projects

# Features
1. Selenium + Cucumber based automation test examples for automationpractice.com
2. Demonstrates use of BDD style tests using cucumber
3. Demonstrates use of Selenium Grid via docker-compose (almost zero configuration)
4. Supports various modes - headless, grid, incognito, normal (default)
5. Support feature level parallelization. (Recommended not to use scenario level parallelization)
6. Utilities to deal with test data in properties, yaml format
7. Utilities to deal with driver, finders
8. Externalized selector configuration
9. Almost zero setup overhead - driver setup is automated via webdrivermanager
10. TODO support for BrowserStack, SauceLabs
11. TODO support to trigger emails from the tests
12. Fully functional Jenkinsfile that can be used to setup a Jenkins pipeline
13. Sample API tests using RestAssured

# Pre-requisites
> JDK 8

> docker, docker-compose - Optional for local development. Recommended for CI setup.

> Jenkins - 2.88+

# Dependencies
1. Cucumber - 1.2.5
2. Selenium - 3.8.1
3. Courgette - 1.4.3
4. WebDriverManager - 2.0.1
5. RestAssured - 3.0.6
6. SnakeYaml - 1.8
7. docker - 17
8. docker-compose - 1.17

# Quick start
```sh
$ git clone https://github.com/mindstix-labs/selenium-accelerator.git
$ cd selenium-accelerator
$ ./gradlew clean build
```
> Above steps will execute the tests in Chrome hitting www.automationpractice.com portal. It would take approximately 1 or 2 minutes to complete. At the end of this, you will see build failed. Do not panic! We have intentionally added one failing test as a sample.

# IDE setup
```sh
$ ./gradlew idea (for IDEA)
$ ./gradlew eclipse (for Eclipse)
```
> Import the project in IDE after above steps generate required project setup files.

# Reading reports
> Open: `build/reports/cucumberreport/index/index.html`

> Alternatively, open courgette reports: `target/courgette-report/index.html`

# Detailed Instructions
> Refer [readme.txt](https://github.com/mindstix-labs/selenium-accelerator/blob/master/readme.txt)

# Licensed under MIT license
