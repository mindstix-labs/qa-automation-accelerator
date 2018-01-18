[![Build Status](https://travis-ci.org/mindstix-labs/qa-automation-accelerator.svg?branch=master)](https://travis-ci.org/mindstix-labs/qa-automation-accelerator)

# Index
1. [Introduction](#selenium-accelerator)
2. [Features](#features)
3. [Pre-requisites](#pre-requisites)
4. [Dependencies](#dependencies)
5. [Quick start](#quick-start)
6. [IDE setup](#ide-setup)
7. [Where do I see reports?](#where-do-i-see-reports)
8. [Report Samples](#report-samples) 
9. [How to utilize Jenkins pipeline feature?](#how-to-utilize-jenkins-pipeline-feature)
10. [How to utilize Selenium grid feature?](#how-to-utilize-selenium-grid-feature)
11. [Developer guide](#developer-guide)
12. [Feedback](#suggest-a-feature-or-report-a-bug)
13. [License](#license)

# qa-automation-accelerator
> A **_cloud-scale_**, **_opinionated_**, test automation framework for **_Web_**, **_APIs_**, and **_Micro Services_**. Strongly aligned with the **_DevOps-way_** of doing things

> Aim is to provide out-of-box **_patterns_** and reference implementation to **_accelerate_** test automation and to drive best practices

> *Disclaimer:* This is work in progress at this stage.

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
$ git clone https://github.com/mindstix-labs/qa-automation-accelerator.git
$ cd qa-automation-accelerator
$ ./gradlew clean build
```
> Above steps will execute the tests in Chrome hitting www.automationpractice.com portal. It would take approximately 1 or 2 minutes to complete. At the end of this, you will see build failed. Do not panic! We have intentionally added one failing test as a sample.

# IDE setup
```sh
$ ./gradlew idea (for IDEA)
$ ./gradlew eclipse (for Eclipse)
```
> Import the project in IDE after above steps generate required project setup files.

# Where do I see reports?
> Open: `build/reports/cucumberreport/index/index.html`

> Alternatively, open courgette reports: `target/courgette-report/index.html`

> The best reports are generated once you setup Jenkins with cucumber-reports plugin. We are working on getting these reports generated even for local builds. See [Issue 19](https://github.com/mindstix-labs/qa-automation-accelerator/issues/29)

# Report Samples
> Courtesy: https://github.com/damianszczepanik/cucumber-reporting

There is a feature overview page:

![feature overview page|50x50](https://github.com/damianszczepanik/cucumber-reporting/raw/master/.README/feature-overview.png)

And there are also feature specific results pages:

![feature specific page passing|50%](https://github.com/damianszczepanik/cucumber-reporting/raw/master/.README/feature-passed.png)

And useful information for failures:

![feature specific page passing|50%](https://github.com/damianszczepanik/cucumber-reporting/raw/master/.README/feature-failed.png)

If you have tags in your cucumber features you can see a tag overview:

![Tag overview](https://github.com/damianszczepanik/cucumber-reporting/raw/master/.README/tag-overview.png)

And you can drill down into tag specific reports:

![Tag report](https://github.com/damianszczepanik/cucumber-reporting/raw/master/.README/tag-report.png)

![Trends report](https://github.com/damianszczepanik/cucumber-reporting/raw/master/.README/trends.png)

# How to utilize Jenkins pipeline feature?
1. Setup Jenkins 2.88+. Refer https://jenkins.io/doc/pipeline/tour/getting-started/
2. Install *cucumber-reporting* plugin by going to *Manage Jenkins -> Manage Plugins -> Available* -> Search for *cucumber-reporting*. Select the *cucumber-reporting* plugin. Click on Install (without restart works just fine)
3. Install *Blue Ocean* plugin in similar way as above. Things will be much more beautiful this way!
4. Create a new pipeline job. Name it properly. Go to Pipeline section. Select pipeline script from SCM. Give the github URL. Add credentials using Add new or select existing.
5. Branches to build: keep default - **master**.
6. Script path: **Jenkinsfile-minimal** (good to start with, later you could try Jenkinsfile that is fully featured with docker-compose based selenium grid)
7. Save.
8. Go to Job page. Build the job. Keep default parameters if asked.
9. Once the job is complete, go to the specific build page. You would see **Cucumber reports** menu. Click to see report.
10. For email triggers to work from Jenkins, you will need to change the target email address in Script (Jenkinsfile-minimal or whichever you pick in Script path above).

> Too much just to get build run on Jenkins? We are working on simplifying it for you! See [Issue 30](https://github.com/mindstix-labs/qa-automation-accelerator/issues/30) 

# How to utilize Selenium grid feature?
1. Install docker using: https://docs.docker.com/engine/installation/#server
2. Install docker-compose using: https://docs.docker.com/compose/install/
3. Once you have above installed, you can go ahead and setup Jenkins pipeline with grid capabilities (Jenkinsfile).
4. For reference on commands fired from Jenkins pipeline, refer to docker-compose commands in Jenkinsfile.

# Developer Guide
> Refer Wiki [here](https://github.com/mindstix-labs/qa-automation-accelerator/wiki/Developer-Guide)

> For more topics related to development and design aspects, please refer Home page of wiki [Home](https://github.com/mindstix-labs/qa-automation-accelerator/wiki)

# Suggest a feature or report a bug
> [Click to report](https://github.com/mindstix-labs/qa-automation-accelerator/issues/new)

# License
> Licensed under [MIT license](https://github.com/mindstix-labs/qa-automation-accelerator/blob/master/LICENSE)
```
MIT License

Copyright (c) 2018 Mindstix Software Labs, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
