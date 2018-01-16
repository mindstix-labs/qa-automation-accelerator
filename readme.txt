NOTE: This document may be outdated - Please refer README.md or Wiki

------------------------------Pre-Requisites---------------------------------------------
- Java 8 (lower version will not work since Selenium 3.5.3 needs Java 8)
- docker, docker-compose - If utilizing the selenium-grid features, then need latest version of docker, docker-compose
    Instructions to setup docker, docker-compose are -
        https://docs.docker.com/engine/installation/#server
        https://docs.docker.com/compose/install/
- Jenkins (2.88+) - See instructions here - https://jenkins.io/doc/pipeline/tour/getting-started/

------------------------Project Setup --------------------------------------------------

1. Open terminal and run the following command to pull down a copy of the 'qa-automation-accelerator' repo :

	git clone https://github.com/mindstix-labs/qa-automation-accelerator.git

2. Navigate to 'qa-automation-accelerator' directory and run following command to generate project structure based on your IDE:

	./gradlew eclipse OR ./gradlew idea

3. Open 'qa-automation-accelerator' in Eclipse by doing : File > Import > Existing Projects into Workspace and browse to the ‘qa-automation-accelerator’ folder and open it.

4. To download gradle on system and to build project execute the following command : 
 
	./gradlew clean build

5. The project is containing sample tests that target automationpractice.com website

6. Defaults should work in most cases - but if you need to change those, please refer build.gradle

-------------------------Execution---------------------------------------------------------------

1. To execute all test scenarios execute the following command :

	./gradlew clean test -Denv.browser=chrome

	---Note--- 
   -Here we are selecting chrome browser for our test execution. Other browsers supported are IE, firefox
   -The required web drivers will be automatically downloaded and cached on first run. (Courtesy: webdrivermanager)

2. To execute only one scenario using tag, add "-Dcucumber.options=<value>" to gradle command.
   For e.g. Following command will execute all the scenarios with '@tag1' tag :
	
	./gradlew clean test -Denv.browser=chrome -Dcucumber.options=" --tags @tag1"

3. Execution modes -
    To change chrome execution mode, add "-Denv.mode=<value>" to gradle command.
    For e.g. Following command will execute tests in grid mode on chrome :
	
	./gradlew clean test -Denv.browser=chrome -Denv.mode=grid -Denv.hubIP=172.17.0.2:4444

	---Note---
   -Chrome has following modes:-
	-normal(Default)
	-headless
	-grid
	-incognito

4. Multiple environments -
    To select environment to run test cases, add "-Denv.urlPrefix=<value>" in gradle command.
    For e.g. Following command will execute tests on 'www.automationpractice.com' environment :

	./gradlew clean test -Denv.browser=chrome -Denv.urlPrefix=www -Denv.domain=.automationpractice.com

	---Note---
   -By default urlPrefix is "www"

5. Use of tags -
    a) Run all scenarios with either @tag1 OR @tag2 OR both: --tags=@tag1,@tag2
    b) Run all scenarios with BOTH @tag1 AND @tag2 marked: --tags=@tag1 --tags=@tag2
    c) Run scenarios which are NOT marked as @tag1: --tags=~@tag1

6. Use of selenium grid
    Project contains docker-compose script that is used to launch selenium grid as docker containers
    It also demonstrates use of extra-hosts in case there are private DNS entries needed
    When launching grid, use -p <unique-prefix> to avoid confusion with containers being launched outside the purview of this project
    For reference, see Jenkinsfile docker-compose commands.
    As of now the grid only contains firefox and chrome nodes.

7. CI
    Jenkinsfile is used to setup pipeline jobs on CI server.
    Jenkinsfile-minimal : Uses gradle based trigger with minimal features - useful in developer's local environment.
    Jenkinsfile : Uses gradle based trigger which also takes care of grid setup/teardown.
    Jenkinsfile-nogrid : Uses gradle based trigger. Does not teardown grid. But starts it if down.
    The above files serve as ready references for basic steps, notifications.
    It is assumed that the pipeline job is created on a server and pointed to right path to Jenkinsfile
    If using nogrid file, make sure to stop/teardown grid when not in use.

8. Reporting
    Courgette Runner generates html reports after tests complete.
    However we recommend using cucumber-report plugin on Jenkins for better reporting
    Jenkinsfile is supplied with the reporting step

-------------------------Developer Guidelines---------------------------------------------------------------

A module is a set of distinct features like cart, gallery etc.
Features under cart module could be checkout, addtowishlist etc.
Each feature can have scenarios. e.g. Checkout single product, Checkout multiple products
Each module would have its own subfolder and subpackage.
All feature files will reside under its module.
All test classes (step definitions) reside under subpackage for that module.
All test classes will be named as <Featurename>Test.java
Each scenario can have one or more tags. Typically tags include module, feature, test-type, test-priority
e.g. @cart @checkout @singleproduct @smoke @medium
Tags can also be given at feature file level (typically module and feature level tags)

All POM classes kept under page package and extend from BasePage
Test data related POJOs kept under data package
Utilities are under utils package
All test data is kept under src/test/resources/testdata folder
Selectors are kept under test/resources/selectors.properties
Logging configuration exists in log4j.properties
Runner classes are kept under runner package
Step definitions are under stepdefinition package. May contain subfolders for modules.


Steps to add a new module and its tests -
1. Create a new folder in test/resources/feature folder with the module name <mymodule1>.
    e.g. test/resources/feature/mymodule1

2. Create a sub package with same name under stepdefinition package.
    e.g. com.mindstix.cb.stepdefinition.mymodule1

3. Create a new feature file in module specific folder
    e.g. test/resources/feature/mymodule1/myfeature1.feature

4. Add a stepdefinition class in stepdefinition.<mymodule1> package
    e.g. com.mindstix.cb.stepdefinition.mymodule1.MyFeature1Test.java

5. Assign tags to feature file scenarios based on module, feature, scenario, test-type, priority
    e.g. @cart @checkout @singleproduct @smoke @medium

6. After adding the module, include its declaration in runner file - RunnerCourgette.java
    e.g. Add the cucumber option for feature and glue attributes to include this module
    features = { ..., "src/test/resources/feature/mymodule1"}
    glue = {..., "com.mindstix.cb.stepdefinition.mymodule1"}

7. Once scenarios are written, add the corresponding step methods in test class

8. Typically for a new module dealing with new set of pages/functionality, a subclass of BaseScenarioContext needs to be written
    e.g. MyModuleScenarioContext or CartScenarioContext or GalleryScenarioContext

    What is ScenarioContext? Why do we need it?
    This class contains members of POM ie various page objects that need to be dealt with in the module's scenarios
    This context serves as a scope that gets spawned and destroyed along with scenario.
    Typically such context class holds a @Before and @After hooks with module specific tags for initialization and teardown for that module
    Each test in a given module will need to have a constructor that takes this context instance as a single parameter.
    The constructor based injection takes care of creating scenario context instance and making sure this is kept alive until scenario is executed.
    Having access to this context from within the test class simplifies the test class -
    access to webdriver, access to various page objects is through this context class.
    This concept can be compared to HttpSession or Spring's applicationContext
    BaseScenarioContext gives basic common abilities like initializing the driver, taking screenshots on failure.

Other features -
-----------------
1. Loading of test data via properties file, yaml files - see utils package
2. Driver related utilities are placed under DriverUtility
3. Keeping any data that is needed during reporting at the end of test, can be placed under GlobalContext
4. TODO - MailUtility to trigger emails containing test output - eg. orders placed during test
5. Ability to sign on canvas using specific coordinates - see data/SignatureCoordinates.java
6. TODO - API tests are under api folder. Sample weather API for given city.
